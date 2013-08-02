Feature: Requirement 30
  A client CSW Discovery.GetRecords request containing a language specific filter requires a response
  of metadata records that comply to the request. If no metadata records comply to that request, the 
  service shall respond normally with an empty result set (without raising an exception).
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE Discovery Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  for **discovery services** based on the international standard **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU) 
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Background: 
    Given the service's capabilities document
    And the service description has a declaration of default language
    And the service description has optionally a declaration of supported languages

  Scenario: Check that all the metadata returned by a language-GetRecords query correspond to the specified language
    Then the metadata returned by getRecords query by every supported language correspond to de specified language
