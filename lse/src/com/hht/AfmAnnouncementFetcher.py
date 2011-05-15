'''
Created on Feb 17, 2011

@author: Work
'''
import re
import datetime
import BeautifulSoup
from com.hht.BaseFectcher import BaseFetcher
from com.hht.AfmAnnouncement import AfmAnnouncement

#from=09-02-2011
#to=10-02-2011  to retrieve announcements published on feb 09 2011
TABLE_CONTENT_URL_BASE = 'http://www.afm.nl/en/professionals/registers/alle-huidige-registers.aspx?type={{E3AD1885-7092-45F9-B399-C2736E9CB9B1}}&from={fromDate}&to={toDate}&pagnr={pageNumber}&perpage=50'
ANNOUNCEMENT_URL_BASE = 'http://www.afm.nl/en/professionals/registers/alle-huidige-registers/insider-transacties-560-wft.aspx?id={announcementId}'

COMPANY_NAME_RE = re.compile( 'Issuing institution' )
INSIDER_NAME_RE = re.compile( 'Notifiable' )
TRADE_DATE_RE = re.compile( 'Transaction' )

class AfmAnnouncementFetcher(BaseFetcher):
    '''
    classdocs
    '''


    def __init__(self):
        super(AfmAnnouncementFetcher,self).__init__()
        '''
        Constructor
        '''
       
    def __get_url(self,fromDate,toDate,pageNumber):
        '''
        fromDate - announcements published starting and on this date.
        toDate - announcements published prior to this date.  
                 announcements published on toDate is not included.
        '''
        fromDateStr = fromDate.strftime( '%d-%m-%Y' )
        toDateStr = toDate.strftime( '%d-%m-%Y' )
        return TABLE_CONTENT_URL_BASE.format( fromDate=fromDateStr, toDate=toDateStr, pageNumber=pageNumber ) 

    def fetch(self,date):
        
        fromDate = date
        toDate = fromDate + datetime.timedelta(days=1)
        pageNumber = 1
        
        url = self.__get_url( fromDate, toDate, pageNumber)
        
        print url
        
        announcementIds = self.fetch_announcement_ids(url)        
       
    def __get_value_summary_table(self, keyTag):
        valueTag = keyTag.parent.findNextSibling('td')
        valueContent = valueTag.findAll(text=True)
        for item in valueContent:
            item = item.strip()
            if len(item) != 0:
                return item
        return None
       
    def fetch_announcement(self,id):
        
        input = None
        
        try: 
            url = ANNOUNCEMENT_URL_BASE.format( announcementId=id )
            browser = super( AfmAnnouncementFetcher, self ).get_browser()
            input = browser.open( url )            
            announcementStr = input.read()
            
            #print announcementStr
                        
            tables = BeautifulSoup.SoupStrainer('table')
            soup = BeautifulSoup.BeautifulSoup( announcementStr, parseOnlyThese=tables )
            
            # extract meta fields      
            keyTag = soup.find(text=COMPANY_NAME_RE)
            companyName = self.__get_value_summary_table( keyTag )

            keyTag = soup.find(text=INSIDER_NAME_RE)
            insiderName = self.__get_value_summary_table( keyTag )

            keyTag = soup.find(text=TRADE_DATE_RE)
            tradeDate = self.__get_value_summary_table( keyTag )

            # transaction lines
            linesTableTag = soup.find( name='table', attrs={'class':'register_details_overview'} )
            trades = []
            
            for line in linesTableTag.tbody:
                
                texts = line.findAll(text=True)
                
                currency = texts[0].strip()
                                
                action = texts[1].strip()
                if action == 'Koop':
                    action = 'buy'
                else:
                    print 'Undefined action: ', action, ' in ', id
                    continue
                
                pricePerShare = texts[3].strip()
                                
                trade = AfmAnnouncement()
                trade.announcementId = id
                trade.company = companyName
                trade.released = tradeDate
                trade.insider = insiderName
                trade.action = action
                trade.quantity = texts[2]
                trade.pricePerShare = pricePerShare
                trade.currency = currency
                
                print trade
                
                trades.append( trade )
                         
        finally:
            if input:
                input.close()            

    def fetch_announcement_ids(self,url):
        
    
        input = None
        
        try: 
            browser = super( AfmAnnouncementFetcher, self ).get_browser()
            input = browser.open( url )
            soup = BeautifulSoup.BeautifulSoup( input )
    
            # parse the content
            announcementIds = set()
            
            if soup.find(text='No results found'):
                return announcementIds
        
            announcementIdRegex = re.compile( '\?id=(\d+)' )
            
            header = soup.find(name='th',text='Transaction') 
            links = header.parent.parent.findNextSiblings('tr')
        
            for i in links:
                aTags = i.findAll(name='a')
                for j in aTags:
                    announcementIdMatch = announcementIdRegex.search(str(j))
                    if announcementIdMatch:
                        id = announcementIdMatch.group(1)
                        print id
                        announcementIds.add( id )
                        break
        
            return announcementIds

        finally:
            if input:
                input.close()

date = datetime.date( 2011, 2, 9 ) 

fetcher = AfmAnnouncementFetcher()
fetcher.fetch_announcement( '43774' )