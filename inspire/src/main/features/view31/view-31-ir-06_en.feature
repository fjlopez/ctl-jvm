Feature: Requirement 6
  The <inspire_common:MetadataURL> element within the extended INSPIRE capabilities 
  of an **ISO 19128** – WMS 1.3.0 <wms:Capability> element shall be used to reference 
  the INSPIRE service metadata available through an INSPIRE Discovery Service.
  Mandatory **ISO 19128** – WMS 1.3.0 metadata elements shall be mapped to INSPIRE metadata	
  elements to implement a consistent interface.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: INSPIRE network service metadata in a Discovery Service is referenced through an extended capability Implementation Requirement 6 The <inspire_common:MetadataURL> element within the extended INSPIRE capabilities of an [ISO 19128] – WMS 1.3.0 <wms:Capability> element shall be used to reference the INSPIRE service metadata available through an INSPIRE Discovery Service. Mandatory [ISO 19128] – WMS 1.3.0 metadata elements shall be mapped to INSPIRE metadata elements to implement a consistent interface.
    Given the service's capabilities document
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    And prefix inspire_vs is http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    When there exists an inspire_common:MetadataUrl node in the inspire_vs:ExtendedCapabilities section
    Then MetadataUrl link response must be a valid INSPIRE metadata
