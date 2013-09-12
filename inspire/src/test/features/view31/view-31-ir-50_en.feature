Feature: Requirement 50
  The mandatory VERSION parameter. The value "1.3.0" shall be used for *GetMap* requests that 
  comply with the **ISO 19128** standard.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check if version Parameter with value to 1.3.0 is accepted and that an exception is thrown when no parameter version is used
    Given the service's capabilities document
    Then a request with VERSION=1.3.0 should return an image
    And a request with no version should return an exception
