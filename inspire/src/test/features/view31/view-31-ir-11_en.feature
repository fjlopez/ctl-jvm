Feature: Requirement 11
  Within the scope defined by the INSPIRE directive the value of the Resource Type shall be 
  fixed to *service* for spatial data services. As the Resource Type is not supported by 
  **ISO 19128** – WMS 1.3.0, an extension shall be used to map this to an <inspire_common:ResourceType> 
  element within an <inspire_vs:ExtendedCapabilities> element.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check ResourceType value when it exists
    Given the service's capabilities document
    And prefix inspire_vs is http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    When there exists an inspire_common:ResourceType node in the inspire_vs:ExtendedCapabilities section
    Then node inspire_common:ResourceType in the inspire_vs:ExtendedCapabilities section should be set to 'service'
