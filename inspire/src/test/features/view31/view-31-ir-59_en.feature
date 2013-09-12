Feature: Requirement 59
  The default value shall be "XML" if this parameter is absent from the request. Other valid values are 
  INIMAGE and BLANK.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check if a query without request return an XML, and that an image is return with INIMAGE or BLANK
    Given the service's capabilities document
    Then a request with no request should return an exception
    And a request with no request and EXCEPTIONS=XML should return an exception xml
    And a request with no request and EXCEPTIONS=INIMAGE should return an exception image
    And a request with no request and EXCEPTIONS=BLANK should return a blank image
