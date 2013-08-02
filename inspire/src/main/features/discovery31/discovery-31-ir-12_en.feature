Feature: Requirement 12
  The Discover Metadata response shall contain at least the INSPIRE metadata elements of each 
  resource matching the query. **INS NS, Annex II, Section 3.2.1**.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE Discovery Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  for **discovery services** based on the international standard **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU) 
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check that the first metadata of a GetRecords response complies with INSPIRE metadata rules
    Given the service's capabilities document
    Then a metadata resulting from a GetRecords query should be a valid INSPIRE metadata
