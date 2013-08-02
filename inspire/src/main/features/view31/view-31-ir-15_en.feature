Feature: Requirement 15
  For the Spatial Data Service Type as defined by the INSPIRE Metadata Regulation **INS MD** 
  (‘view’) an extension shall be used to map this to an <inspire_common:SpatialDataServiceType> 
  element within an <inspire_vs:ExtendedCapabilities> element. For an INSPIRE View Service 
  the Spatial Data Service Type shall have a fixed value “view” according to INSPIRE Metadata 
  Regulation **INS MD Part 3**.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check SpatialDataServiceType value (provided it exists)
    Given the service's capabilities document
    And prefix inspire_vs is http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    When there exists an inspire_common:SpatialDataServiceType node in the inspire_vs:ExtendedCapabilities section
    Then node inspire_common:SpatialDataServiceType in the inspire_vs:ExtendedCapabilities section should be set to 'view'
