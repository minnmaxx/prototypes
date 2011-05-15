'''
Created on Jan 27, 2011

@author: Work
'''

import mechanize
import re
import BeautifulSoup
import datetime
import time
import threading

TABLE_CONTENT_URL_BASE = 'http://www.londonstockexchange.com/exchange/news/market-news/market-news-home.html?newsSource=RNS&headlineId=61&newsPerPage=200&rbDate=range&dd1={day}&mm1={month}&yyyy1={year}&dd2={day}&mm2={month}&yyyy2={year}'
ANNOUNCEMENT_URL_BASE = 'http://www.londonstockexchange.com/exchange/news/market-news/market-news-detail.html?announcementId={id}'
URL_BASE='http://www.londonstockexchange.com/exchange/news/market-news/market-news-detail.html?announcementId=10437065'
FILE_BASE='C:/Workspace/scraper/htmls/lse-toc.html'
SAMPLE_DIRECTORY = 'Z:/VMShared/Personal/lse-announcements/'

def get_browser():
    
    browser = mechanize.Browser()
    browser.set_handle_robots(False)
    browser.addheaders = [('User-agent','Mozilla/4.0 (compatible; MSIE 5.0; Windows 98;)')]

    return browser

def retrieve():
    
    browser = get_browser()
    fd = browser.open( TABLE_CONTENT_URL_BASE )
    soup = BeautifulSoup.BeautifulSoup(fd)

    header = soup.find(name='th',text='Time/Date') 
    links = header.parent.parent.findNextSiblings('tbody')

    

    return links

def parse_announcement(id):
    
    #url = URL_BASE
    #fd = urllib2.urlopen(url)
    
    with open(FILE_BASE, 'r') as fd:
        
        soup = BeautifulSoup.BeautifulSoup(fd)
    
        #totalHoldingText = soup.find(text='Total holding following notification')
        totalHoldingText = soup.find(text=re.compile('^Total holding following notification'))
        holdings = totalHoldingText.parent.parent.findNextSiblings('p')
        
        for i in holdings:
            print i.find(text=True)
    
        return holdings
    
def download_announcement_as_file(id):
    
    url = ANNOUNCEMENT_URL_BASE.format(id=id)
    outputFile = SAMPLE_DIRECTORY + 'announcement_' + id + '.html'
    
    with open( outputFile, 'w' ) as output:
    
        browser = get_browser()
        input = browser.open( url )
        for line in input:
            output.write( line )
            
def download_announcements_as_files():

    start = datetime.date( 2011, 2, 2 )
    numDays = 10
    interval = 1
    total = 0
    
    dates = [ start + datetime.timedelta(days=x*interval) for x in range(0,numDays) ]
    for date in dates:
        try: 
            ids = download_table_of_content(date)
            total += ids.__len__()
            for id in ids:
                download_announcement_as_file(id)
                time.sleep(0.25)
            print 'completed ', date, ' ', ids.__len__()
        except Exception as e:
            print 'encountered exception ', date
            print e
    
    print 'total downloaded ', total        
        
def download_table_of_content( date ):
        
    url = TABLE_CONTENT_URL_BASE.format(day=date.day,month=date.month,year=date.year) 
    
    browser = get_browser()
    fd = browser.open( url )
    #with open(FILE_BASE, 'r') as fd:
        
    soup = BeautifulSoup.BeautifulSoup(fd)
        
    ids = parse_table_of_content(soup)
    fd.close()
    
    return ids

def parse_table_of_content(soup):
    
    if soup.find(text='No news available') or soup.find(text='The start date can not be older than two years ago'):
        return []
    
    announcementIdRe = re.compile( '\?announcementId=(\d+)' )
    announcementIds = []
        
    header = soup.find(name='th',text='Time/Date') 
    links = header.parent.parent.parent.findNextSiblings('tbody')
    
    for i in links:
        aTags = i.findAll(name='a')
        for j in aTags:
            announcementId = announcementIdRe.search(str(j))
            if announcementId:
                announcementIds.append( announcementId.group(1) )
    
    return announcementIds

def strip_value(keyTag):
    
    valueTag = keyTag.parent.parent.findNextSibling('td')
    valueContent = valueTag.findAll(text=True)
    for item in valueContent:
        item = item.strip()
        if len(item) != 0:
            return item
        
    #valueContent = valueTag.find(text=True).strip()
    
    return None

def strip_content():
  
    inputFile = 'C:/Workspace/scraper/htmls/announcement_10767194.html'
    tempFile = 'C:/Workspace/scraper/htmls/temp.html'
    
    contentRe = re.compile( 'Begin announcement content\s*\-\-\>(.*)\<\!\-\-\s*End announcement content', re.DOTALL )
    
    with open(inputFile,'r') as input:
        with open(tempFile,'w') as output:
        
            tables = BeautifulSoup.SoupStrainer('table')
            soup = BeautifulSoup.BeautifulSoup( input, parseOnlyThese=tables )
        
            keyTag = soup.find(text='Company')
            tidm = strip_value( keyTag )
            print tidm

            keyTag = soup.find(text='TIDM')
            tidm = strip_value( keyTag )
            print tidm

            keyTag = soup.find(text='Headline')
            headline = strip_value( keyTag )
            print headline
            
            keyTag = soup.find(text='Released')
            released = strip_value( keyTag )
            print released
            
            keyTag = soup.find(text='Number')
            number = strip_value( keyTag )
            print number                                               
        
            # extract the content
#            input.seek(0)
#            fileContent = input.read()
#            
#            announcementContent = contentRe.search( fileContent )
#            if announcementContent:
#                output.write( announcementContent.group(1) )
           
class Printer():
    
    def __init__(self):
        #super( Printer, self ).__init__()
        self.__count = 0
        #self.daemon = True        

    def run(self):
        try:
            while self.__count < 5:
                print self.__count
                self.__count += 1
                time.sleep(1)

        except Exception as e:
            print e

 

printer = Printer()
thread = threading.Thread(target=printer.run)
thread.start()
#printer.start()
#printer.join( 20 )




#today = datetime.date.today()
#timer = threading.Timer(1,test)
#timer.start()


