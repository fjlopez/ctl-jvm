Feature: Requirement 6
  See **CSW ISO AP**. INSPIRE extends this operation with an additional parameter that indicates the 
  client’s preferred language. The recommended approach to implement this extension is described in 
  Section 4.5.1.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE Discovery Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  for **discovery services** based on the international standard **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU) 
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: launch getCapabilities query with default language
    Given the service's capabilities document
    Then GetCapabilities response is the same as with default language response when no language is specified
