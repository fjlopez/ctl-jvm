Feature: Requirement 8
  **CSW ISO AP** specifies a *GetCapabilities* operation with several service metadata sections. The 
  service metadata in the capabilities documents shall be in conformance with the requirements of 
  INSPIRE service metadata **INS NS**.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE Discovery Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  for **discovery services** based on the international standard **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU) 
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: All required metadata is included explicitly in the GetCapabilities response (no MetadataUrl)
    Given the service's capabilities document
    And prefix ows is http://www.opengis.net/ows
    And prefix ogc is http://www.opengis.net/ogc
    And prefix csw is http://www.opengis.net/cat/csw/2.0.2
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    And prefix inspire_ds is http://inspire.ec.europa.eu/schemas/inspire_ds/1.0
    When there is not a inspire_common:MetadataUrl node in the inspire_ds:ExtendedCapabilities section
    Then there is a ows:Title node in the ows:ServiceIdentification section
    And there is an ows:Abstract node in the ows:ServiceIdentification section
    And there is a inspire_common:ResourceType node in the inspire_ds:ExtendedCapabilities section
    And there is a inspire_common:ResourceLocator node in the inspire_ds:ExtendedCapabilities section
    And there is a inspire_common:SpatialDataServiceType node in the inspire_ds:ExtendedCapabilities section
    And there is a inspire_common:MandatoryKeyword node in the inspire_ds:ExtendedCapabilities section
    And there is at least in the inspire_ds:ExtendedCapabilities section one of
      | inspire_common:TemporalExtent     |
      | inspire_common:DateOfPublication  |
      | inspire_common:DateOfLastRevision |
      | inspire_common:DateOfCreation     |
    And there is a inspire_common:Conformity node in the inspire_ds:ExtendedCapabilities section
    And there is a inspire_common:Specification node in the inspire_common:Conformity section
    And there is a inspire_common:Degree node in the inspire_common:Conformity section
    And there is a ows:Fees node in the ows:ServiceIdentification section
    And there is an ows:AccessConstraints node in the ows:ServiceIdentification section
    And there is a ows:ServiceProvider node in the csw:Capabilities section
    And there is a ows:ProviderName node in the ows:ServiceProvider section
    And there is a ows:ServiceContact node in the ows:ServiceProvider section
    And there is a ows:ContactInfo node in the ows:ServiceContact section
    And there is an ows:Address node in the ows:ContactInfo section
    And there is an ows:ElectronicMailAddress node in the ows:Address section
    And there is a ows:Role node in the ows:ServiceContact section
    And there is a inspire_common:MetadataPointOfContact node in the inspire_ds:ExtendedCapabilities section
    And there is a inspire_common:MetadataDate node in the inspire_ds:ExtendedCapabilities section
    And there is a inspire_common:ResponseLanguage node in the inspire_ds:ExtendedCapabilities section
    And there is a inspire_common:Language node in the inspire_common:ResponseLanguage section
