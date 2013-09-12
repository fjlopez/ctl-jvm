Feature: Requirement 20
  The only queryable that is not defined above, but is required to comply with **INS MDTG** is 
  “Metadata language”. This is a mandatory queryable for INSPIRE Discovery Service to support 
  the “Language” query parameter as defined in **INS NS, Annex II, Part B, Section 3.1**.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE Discovery Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  for **discovery services** based on the international standard **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU) 
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check that Language is listed as SupportedISOQueryable
    Given the service's capabilities document
    Then Language is one of the values for Constraint SupportedISOQueryables in GetRecords operation
