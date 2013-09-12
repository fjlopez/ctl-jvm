Feature: Requirement 7
  The response shall include discovery service metadata parameters **INS NS** by implementing either 
  scenario below: Option 1: Referencing a URL mapped to the *GetCapabilities* response by the MetadataURL element 
    in the ExtendedCapabilities of the **CSW ISO AP**; Mandatory **OGC CSW ISO AP** capabilities parameters
    (see Table 2) shall be mapped to INSPIRE metadata elements to implement a consistent interface.
  Option 2: Including all required metadata explicitly in the *GetCapabilities* response **CSW ISO AP**.
    INSPIRE metadata elements that cant be mapped to **CSW ISO AP** elements are implemented as Extended 
    Capabilities.
  
  To fulfil the specific language requirements of the INSPIRE Network Services Regulation **INS NS**, 
  a language section shall be added in the extended capability of the service.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE Discovery Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  for **discovery services** based on the international standard **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU) 
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Option 1, there is a MetadataURL pointing to the service metadata
    Given the service's capabilities document
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    And prefix inspire_ds is http://inspire.ec.europa.eu/schemas/inspire_ds/1.0
    When there exists an inspire_common:MetadataUrl node in the inspire_ds:ExtendedCapabilities section
    Then there is a inspire_common:URL node in the inspire_common:MetadataUrl section

  Scenario: Option 2, all required metadata is included explicitly in the GetCapabilities response
    Given the service's capabilities document
    And prefix ows is http://www.opengis.net/ows
    And prefix ogc is http://www.opengis.net/ogc
    And prefix csw is http://www.opengis.net/cat/csw/2.0.2
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    And prefix inspire_ds is http://inspire.ec.europa.eu/schemas/inspire_ds/1.0
    When there is not a inspire_common:MetadataUrl node in the inspire_ds:ExtendedCapabilities section
    Then there is a ows:ServiceType node in the ows:ServiceIdentification section
    And there is a ows:ServiceTypeVersion node in the ows:ServiceIdentification section
    And node ows:ServiceTypeVersion in the ows:ServiceIdentification section should be set to '2.0.2'
    And there is a ows:Title node in the ows:ServiceIdentification section
    And there is an ows:Abstract node in the ows:ServiceIdentification section
    And there is a ows:Keywords node in the ows:ServiceIdentification section
    And there is a ows:Fees node in the ows:ServiceIdentification section
    And there is an ows:AccessConstraints node in the ows:ServiceIdentification section
    And there is a ows:ProviderName node in the ows:ServiceProvider section
    And there is a ows:ProviderSite node in the ows:ServiceProvider section
    And there is a ows:ServiceContact node in the ows:ServiceProvider section
    And there is a inspire_common:ResourceType node in the inspire_ds:ExtendedCapabilities section
    And there is a inspire_common:ResourceLocator node in the inspire_ds:ExtendedCapabilities section
    And there is a inspire_common:SpatialDataServiceType node in the inspire_ds:ExtendedCapabilities section
    And there is a ows:Operation node in the ows:OperationsMetadata section
    And there is a ows:Parameter node in the ows:OperationsMetadata section
    And there is a ows:Constraint node in the ows:OperationsMetadata section
    And there is an inspire_ds:ExtendedCapabilities node in the ows:OperationsMetadata section
    And there is a inspire_common:MandatoryKeyword node in the inspire_ds:ExtendedCapabilities section
    And there is a inspire_common:TemporalReference node in the inspire_ds:ExtendedCapabilities section
    And there is a inspire_common:Conformity node in the inspire_ds:ExtendedCapabilities section
    And there is a inspire_common:MetadataPointOfContact node in the inspire_ds:ExtendedCapabilities section
    And there is a inspire_common:MetadataDate node in the inspire_ds:ExtendedCapabilities section
    And there is a inspire_common:SupportedLanguages node in the inspire_ds:ExtendedCapabilities section
    And there is a ogc:Filter_Capabilities node in the csw:Capabilities section
