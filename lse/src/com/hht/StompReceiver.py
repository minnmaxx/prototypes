'''
Created on Feb 13, 2011

@author: Work
'''

import logging
import stomp

class StompReceiver(stomp.listener.ConnectionListener):
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
        
    def on_connected(self):
        self.__conn.subscribe( destination=self.__destination, ack='auto' )
        
    def on_message(self, headers, body):
        print body
        
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
    
    receiver = StompReceiver('announcement') 
    
    while True:
        line = input()
        if line:
            break
    
    receiver.close()
        
if __name__ == '__main__':
    main()   