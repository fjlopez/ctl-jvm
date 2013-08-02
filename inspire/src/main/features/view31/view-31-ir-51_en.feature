Feature: Requirement 51
  The mandatory REQUEST parameter is defined in **ISO 19128, Section 6.9.2**. To invoke the 
  *GetMap* operation, the value "GetMap" shall be used to comply with the **ISO 19128** standard.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check if request Parameter with value to "GetMap" is accepted and that an exception is thrown when no parameter version is used
    Given the service's capabilities document
    Then a request with request=GetMap should return an image
    And a request with no request should return an exception
