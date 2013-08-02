Feature: Requirements 7
  INSPIRE metadata are mapped to WMS capabilities elements to its full extent. It is mandatory 
  to use the mapping provided in this Technical Guideline (described in Section 4.2.3.3.1.1 to
  4.2.3.3.1.16. INSPIRE metadata elements that cannot be mapped to available **ISO 19128** – WMS1.3.0 
  elements are implemented as Extended Capabilities. Metadata are published through a service's
  capabilities document and can be harvested by an INSPIRE Discovery service.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Use (extended) capabilities to map all INSPIRE metadata elements to the [ISO 19128] – WMS1.3.0 elements INSPIRE metadata are mapped to WMS capabilities elements to its full extent. It is mandatory to use the mapping provided in this Technical Guideline (described in Section 4.2.3.3.1.1 to 4.2.3.3.1.16. INSPIRE metadata elements that cannot be mapped to available [ISO 19128] – WMS1.3.0 elements are implemented as Extended Capabilities. Metadata are published through a service's capabilities document and can be harvested by an INSPIRE Discovery service
    Given the service's capabilities document
    And prefix wms is http://www.opengis.net/wms
    And prefix inspire_vs is http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    When there is not an inspire_common:MetadataUrl node in the inspire_vs:ExtendedCapabilities section
    Then there is a wms:Title node in the wms:Service section
    And there is an wms:Abstract node in the wms:Service section
    And there is a inspire_common:ResourceType node in the inspire_vs:ExtendedCapabilities section
    And there is a inspire_common:ResourceLocator node in the inspire_vs:ExtendedCapabilities section
    And there is a wms:MetadataURL node in each wms:Layer section
    And there is a inspire_common:SpatialDataServiceType node in the inspire_vs:ExtendedCapabilities section
    And there is a wms:KeywordList node in the wms:Service section
    And there is a inspire_common:MandatoryKeyword node in the inspire_vs:ExtendedCapabilities section
    And there is a wms:EX_GeographicBoundingBox node in each wms:Layer section
    And there is a inspire_common:TemporalReference node in the inspire_vs:ExtendedCapabilities section
    And there is a inspire_common:Conformity node in the inspire_vs:ExtendedCapabilities section
    And there is a wms:Fees node in the wms:Service section
    And there is an wms:AccessConstraints node in the wms:Service section
    And there is a wms:ContactInformation node in the wms:Service section
    And there is a inspire_common:MetadataPointOfContact node in the inspire_vs:ExtendedCapabilities section
    And there is a inspire_common:MetadataDate node in the inspire_vs:ExtendedCapabilities section
    And there is a inspire_common:SupportedLanguages node in the inspire_vs:ExtendedCapabilities section
