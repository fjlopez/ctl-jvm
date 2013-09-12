Feature: Requirement 4
  The metadata response parameters shall be provided through the service Capabilities, as defined in the 
  WMS Standard **ISO 19128, Section 7.2.4**. These capabilities are mandatory and defined when a WMS is set up. 
  They consist of service information, supported operations and parameters values. The extended capabilities 
  section shall be used to fully comply with the INSPIRE View Service metadata requirements (see section 
  4.2.3.3.1).
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Ensure the XMLSchema is properly defined
    Given the service's capabilities document
    Then schema URI is set to http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    And schema location points to http://inspire.ec.europa.eu/schemas/inspire_vs/1.0/inspire_vs.xsd
    Then the service description must comply with the INSPIRE XMLSchema
