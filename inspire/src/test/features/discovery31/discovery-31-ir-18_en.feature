Feature: Requirement 18
  **CSW ISO AP** as the base specification for the INSPIRE Discovery Service is based on the 
  ISO 19115/19119 information model. As such, the INSPIRE metadata elements (see **INS MD**) 
  shall be requested through the INSPIRE Discovery Service interface within a query.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE Discovery Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  for **discovery services** based on the international standard **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU) 
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check if ISO Profile is declared
    Given the service's capabilities document
    Then ISO profile is declared as a Constraint in the OperationsMetadata section
    And http://www.isotc211.org/2005/gmd outputSchema is supported in GetRecords operation
    And gmd:MD_Metadata typeNames is supported in GetRecords operation
