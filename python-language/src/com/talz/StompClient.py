'''
Created on Feb 9, 2011

@author: Work
'''

import logging
import stomp
import time
import json

# create logger for stomp
formatter = logging.Formatter( "%(asctime)s - %(name)s - %(message)s" )

handler = logging.StreamHandler()
handler.setLevel( logging.DEBUG )
handler.setFormatter( formatter )

logger = logging.getLogger( "stomp" )
logger.setLevel( logging.DEBUG )
logger.addHandler( handler )


class JmsClient(stomp.listener.ConnectionListener):
    
    def __init__(self, host='localhost', port=60002, user='', passcode=''):
        self.conn = stomp.Connection([(host, port)], user, passcode)
        self.conn.set_listener('', self)
        #print 'start before'
        self.conn.start()
        #print 'start after'
        
    def on_connecting(self, host_and_port):
        #print self.conn.is_connected()
        self.conn.connect(wait=True)
        #print self.conn.is_connected()
                
    def send(self):
    
#        announcement = LseAnnouncement()
#        announcement.id = '12345678'
#        announcement.company = 'Goldman Sacks'
#        announcement.tidm = 'GS'
#        announcement.headline = 'DirectorTrading'
#        announcement.released = '13:07 02-Feb-2011'
#        announcement.number = 'HUG1485504'
#        announcement.exchange = 'LSE'
#        announcement.content = '<html><title>an announcement</title></html>'
        
        #destination='/topic/announcement'
        destination='/topic/lse'

        #self.conn.send( message=announcement.__str__(), destination=destination )
        self.conn.send( message='hello', destination=destination )


def connect_to_jms_server():
    
    client = JmsClient()
    #print 'finished init', client.conn.is_connected()
    
    client.send();
       
    #time.sleep(2)
    #print('slept')

    client.conn.disconnect()
    #print('disconnected')
    
def test_json():
    
#    announcement = LseAnnouncement()
#    announcement.companyName = 'Goldman Sacks'
#    announcement.releasedTimestamp = '20101101'
#    announcement.ticker = 'GS'
#    announcement.content = '<html><title>an announcement</title></html>'

    announcement = dict()
    announcement['companyName'] = 'Goldman Sacks'
    announcement['releasedTimestamp'] = '20101101'
    announcement['ticker'] = 'GS'
    announcement['content'] = '<html><title>an announcement</title></html>'
    
    print json.dumps( announcement )

    
#test_json()   
connect_to_jms_server()
