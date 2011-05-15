
import mechanize
import re
import BeautifulSoup
import time
import datetime
import threading
from com.hht.LseAnnoucement import LseAnnouncement
from com.hht.IAnnouncementProcessor import IAnnouncementProcessor
from com.hht.StompAnnouncementSender import StompAnnouncementSender

TABLE_CONTENT_URL_BASE = 'http://www.londonstockexchange.com/exchange/news/market-news/market-news-home.html?newsSource=RNS&headlineId=61&newsPerPage=200&rbDate=range&dd1={day}&mm1={month}&yyyy1={year}&dd2={day}&mm2={month}&yyyy2={year}'
ANNOUNCEMENT_URL_BASE = 'http://www.londonstockexchange.com/exchange/news/market-news/market-news-detail.html?announcementId={id}'

class LseAnnouncementFetcher():
    
    __seenAnnouncementIds = set()
    __count = 0
    __processors = dict()
    
    def __init__(self,date,interval,numIterations):       
        '''
            date - fetch data published on date
            interval - number of seconds between consecutive checks
            numIterations - number of fetches desired
        '''
        browser = mechanize.Browser()
        browser.set_handle_robots(False)
        browser.addheaders = [('User-agent','Mozilla/4.0 (compatible; MSIE 5.0; Windows 98;)')]
        self.__browser = browser
        self.__date = date
        self.__interval = interval
        self.__numIterations = numIterations
        
    def add_processor(self, name, processor ):
        if not isinstance(processor,IAnnouncementProcessor):
            #log.debug("processor doesn't implement IAnnouncementProcessor")
            return
        self.__processors[name] = processor
        
    def remove_processor(self, name):
        del self.__processors[name] 
    
    def __process_announcement(self,announcement):
        
        #forward to all processors
        for processor in self.__processors.values():
            processor.process(announcement)
    
    def run(self):
        try:
            while self.__count < self.__numIterations:
                print "[", self.__count, "] ---------------------------------- "
                self.fetch()
                self.__count += 1
                time.sleep(self.__interval)

            self.__close()

        except Exception as e:
            print e
            
    def __close(self):
        for processor in self.__processors.values():
            processor.close()
        
    def fetch(self):
        
        announcementIds = self.fetch_announcement_ids()
        newAnnouncementIds = announcementIds.difference( self.__seenAnnouncementIds )
        
        print "about to grab ", len(newAnnouncementIds), " announcements"
        
        for id in newAnnouncementIds:
            announcement = self.fetch_announcement(id)
            self.__process_announcement(announcement)
            self.__seenAnnouncementIds.add(id)
            time.sleep(0.2)
            
        
    def fetch_announcement(self,id):
        
        url = ANNOUNCEMENT_URL_BASE.format(id=id)
    
        input = None
        
        try:        
            input = self.__browser.open(url)
            announcementStr = input.read()
            announcement = LseAnnouncement()
            announcement.id = id
            
            # extract meta fields
            tables = BeautifulSoup.SoupStrainer('table')
            soup = BeautifulSoup.BeautifulSoup( announcementStr, parseOnlyThese=tables )
        
            keyTag = soup.find(text='Company')
            company = self.strip_value( keyTag )
            announcement.company = company

            keyTag = soup.find(text='TIDM')
            tidm = self.strip_value( keyTag )
            announcement.tidm = tidm

            keyTag = soup.find(text='Headline')
            headline = self.strip_value( keyTag )
            announcement.headline = headline
            
            keyTag = soup.find(text='Released')
            released = self.strip_value( keyTag )
            announcement.released = released
            
            keyTag = soup.find(text='Number')
            number = self.strip_value( keyTag )
            announcement.number = number                             
            
            # extract content
            contentRegex = re.compile( 'Begin announcement content\s*\-\-\>(.*)\<\!\-\-\s*End announcement content', re.DOTALL )
            
            contentMatch = contentRegex.search( announcementStr )
            if contentMatch:
                announcement.content = contentMatch.group(1)            
            
            return announcement
            
        finally:
            if input:
                input.close()
                
        return None
                
    def strip_value(self,keyTag):
    
        valueTag = keyTag.parent.parent.findNextSibling('td')
        valueContent = valueTag.findAll(text=True)
        for item in valueContent:
            item = item.strip()
            if len(item) != 0:
                return item
        return None
                    
    def fetch_announcement_ids(self):
        
        url = TABLE_CONTENT_URL_BASE.format(day=self.__date.day,
                                            month=self.__date.month,
                                            year=self.__date.year) 
    
        input = None
        
        try: 
            input = self.__browser.open( url )
            soup = BeautifulSoup.BeautifulSoup( input )
    
            # parse the content
            announcementIds = set()
            
            if soup.find(text='No news available') or soup.find(text='The start date can not be older than two years ago'):
                return announcementIds
        
            announcementIdRegex = re.compile( '\?announcementId=(\d+)' )
            
            header = soup.find(name='th',text='Time/Date') 
            links = header.parent.parent.parent.findNextSiblings('tbody')
        
            for i in links:
                aTags = i.findAll(name='a')
                for j in aTags:
                    announcementIdMatch = announcementIdRegex.search(str(j))
                    if announcementIdMatch:
                        announcementIds.add( announcementIdMatch.group(1) )
        
            return announcementIds

        finally:
            if input:
                input.close()
            
class PrintId(IAnnouncementProcessor):
    def process(self,announcement):
        print announcement.id
                

def main():
    
    interval = 10
    numInterations = 2
    duration = interval * numInterations + 1;
    
    sender = StompAnnouncementSender('lse')                
                    
    date = datetime.date( 2011, 2, 8 )
    fetcher = LseAnnouncementFetcher( date, interval, numInterations )
    fetcher.add_processor( 'stompSender', sender )
    fetcher.add_processor( 'idPrinter', PrintId() )
    
    thread = threading.Thread(target=fetcher.run)
    thread.start()
    thread.join( duration )

if __name__ == '__main__':
    main()   

