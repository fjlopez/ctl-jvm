Feature: Requirement 23
  A network service metadata response shall contain a list of the natural languages supported by 
  the service. This list shall contain one or more languages that are supported.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE Discovery Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  for **discovery services** based on the international standard **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU) 
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check that there is a list of supported languages
    Given the service's capabilities document
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    And prefix inspire_ds is http://inspire.ec.europa.eu/schemas/inspire_ds/1.0
    Then there is a inspire_common:SupportedLanguages node in the inspire_ds:ExtendedCapabilities section
    And there is a inspire_common:SupportedLanguage node in the inspire_common:SupportedLanguages section
