Feature: Requirement 12
  An extension shall be used to map Resource Locator to an <inspire_common:ResourceLocator> 
  element within an <inspire_vs:ExtendedCapabilities> element.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check if ResourceLocator contains a URL node (provided ResourceLocator exists)
    Given the service's capabilities document
    And prefix inspire_vs is http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    When there exists an inspire_common:ResourceLocator node in the inspire_vs:ExtendedCapabilities section
    Then there is a inspire_common:URL node in the inspire_common:ResourceLocator section
