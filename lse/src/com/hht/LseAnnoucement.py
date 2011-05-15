

class LseAnnouncement(object):
    '''
    Represents an announcement from the London Stock Exchange.
    Properties:
        id
        company 
        tidm
        headline
        released
        number
        exchange
        content
    '''

    def __init__(self):
        self.exchange = 'LSE'
        
    def __str__(self):
        return "%s|%s|%s|%s|%s|%s|%s\n%s" % (self.id,
                                          self.company,
                                          self.tidm,
                                          self.headline,
                                          self.released,
                                          self.number,
                                          self.exchange,
                                          self.content)

    