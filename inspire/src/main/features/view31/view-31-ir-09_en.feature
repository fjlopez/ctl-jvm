Feature: Requirement 9
  Regardless of the scenario chosen to be implemented View Service Metadata shall be published in an INSPIRE 
  Discovery Service. This is required to support a) the INSPIRE View Link service operation and b) discovery 
  of View services by client applications such as the INSPIRE geoportal.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: There is a MetadataUrl section
    Given the service's capabilities document
    And prefix inspire_vs is http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    When there is a inspire_common:MetadataUrl node in the inspire_vs:ExtendedCapabilities section
    Then MetadataUrl section should contain a getRecordById query
    And MetadataUrl reference should point to an existing location

  Scenario: There is not a MetadataUrl section
    Given the service's capabilities document
    And prefix inspire_vs is http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    When there is not a inspire_common:MetadataUrl node in the inspire_vs:ExtendedCapabilities section
    Then there should be a Discovery Service Catalog serving its metadata
