'''
Created on Feb 10, 2011

@author: Work
'''

class IAnnouncementProcessor(object):
    '''
    an interface that processes Announcement grabbed by a fetcher
    '''

    def process(self,announcement):
        pass
    
    def close(self):
        pass