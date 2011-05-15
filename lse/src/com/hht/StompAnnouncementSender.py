'''
Created on Feb 10, 2011

@author: Work
'''
from com.hht.IAnnouncementProcessor import IAnnouncementProcessor
from com.hht.LseAnnoucement import LseAnnouncement
import logging
import stomp

class StompAnnouncementSender(stomp.listener.ConnectionListener,IAnnouncementProcessor):
    '''
    classdocs
    '''
    def __init__(self, destination):
        
        self.__destination = '/topic/' + destination;
        
        self.__conn = stomp.Connection([('localhost',60002)], '', '')
        self.__conn.set_listener('', self)
        self.__conn.start()
        
    def on_connecting(self, host_and_port):
        self.__conn.connect(wait=True)
                
    def process(self,announcement):
        self.__send( announcement.__str__() );
    
    def __send(self,message):
        self.__conn.send( message=message, destination=self.__destination )
    
    def close(self):
        self.__conn.disconnect()

def init_logging():
    # create logger for stomp
    formatter = logging.Formatter( "%(asctime)s - %(name)s - %(message)s" )
    
    handler = logging.StreamHandler()
    handler.setLevel( logging.DEBUG )
    handler.setFormatter( formatter )
    
    logger = logging.getLogger( "stomp" )
    logger.setLevel( logging.DEBUG )
    logger.addHandler( handler )
     
def main():
    
    init_logging()
     
    sender = StompAnnouncementSender('lse')
    announcement = LseAnnouncement()
    announcement.id = '12345678'
    announcement.company = 'Goldman Sacks'
    announcement.tidm = 'GS'
    announcement.headline = 'DirectorTrading'
    announcement.released = '13:07 02-Feb-2011'
    announcement.number = 'HUG1485504'
    announcement.exchange = 'LSE'
    announcement.content = '<html><title>an announcement</title></html>'
    sender.process(announcement)
    sender.close()
 
if __name__ == '__main__':
    main()    
