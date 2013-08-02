Feature: Requirement 56
  The mandatory WIDTH and HEIGHT parameters specify the size in integer pixels of the map to be produced.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check that an exception is thrown when no parameter width or height is used
    Given the service's capabilities document
    Then a request with width=100 should return an image
    And a request with height=100 should return an image
    And a request with no width should return an exception
    And a request with no height should return an exception
