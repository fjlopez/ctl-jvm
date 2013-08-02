Feature: Requirement 22
  All supported ISO queryables shall be advertised to be supported by an INSPIRE Discover Metadata 
  operation; in addition, all INSPIRE search criteria (queryables) shall be listed in the section 
  “AdditionalQueryables”.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE Discovery Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  for **discovery services** based on the international standard **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU) 
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check that there are IsoQueryables and AdditionalQueryables constraints
    Given the service's capabilities document
    And prefix ows is http://www.opengis.net/ows
    Then there is a ows:Constraint node in the operation GetRecords with the attribute name set to SupportedISOQueryables
    And there is a ows:Constraint node in the operation GetRecords with the attribute name set to AdditionalQueryables
