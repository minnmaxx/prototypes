
import mechanize

class BaseFetcher(object):
    '''
    classdocs
    '''
    def __init__(self):        
        browser = mechanize.Browser()
        browser.set_handle_robots(False)
        browser.addheaders = [('User-agent','Mozilla/4.0 (compatible; MSIE 5.0; Windows 98;)')]
        self.__browser = browser
        
    def get_browser(self):
        return self.__browser