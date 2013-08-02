Feature: Requirement 8
  Regardless of the scenario chosen to be implemented, a language section shall be added in the extended 
  capability of the service to fulfil the language requirements of the Network Services Regulation **INS NS**.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check if there is a SupportedLanguages and a ResponseLanguage section
    Given the service's capabilities document
    And prefix inspire_vs is http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    Then there is a inspire_common:SupportedLanguages node in the inspire_vs:ExtendedCapabilities section
    And there is a inspire_common:ResponseLanguage node in the inspire_vs:ExtendedCapabilities section
