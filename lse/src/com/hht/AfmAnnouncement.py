
class AfmAnnouncement(object):
    '''
    Represents an announcement from AFM.
    http://www.afm.nl
    Properties:
        announcementId
        company 
        released
        exchange
        insider
        action
        quantity
        pricePerShare
        currency
    '''

    def __init__(self):
        '''
        Constructor
        '''
        self.exchange = 'AMS'

    def __str__(self):
        return "%s|%s|%s|%s|%s|%s|%s|%s|%s" % (self.announcementId,
                                          self.company,
                                          self.released,
                                          self.exchange,
                                          self.insider,
                                          self.action,
                                          self.quantity,
                                          self.pricePerShare,
                                          self.currency)
        
        