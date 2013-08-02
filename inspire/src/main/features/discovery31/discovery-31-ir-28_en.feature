Feature: Requirement 28
  The Extended Capabilities shall contain the list of supported languages indicated in 
  <inspire_common:SupportedLanguages>.
  This list of supported languages shall consist of
  1. exact one element <inspire_common:DefaultLanguage> indicating the service default
    language, and
  2. zero or more elements <inspire_common:SupportedLanguage> to indicate all additional
    supported languages.	Regardless of the response language, the list of supported 
    languages is invariant for each *GetCapabilities*-Response.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE Discovery Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  for **discovery services** based on the international standard **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU) 
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: There should be a DefaultLanguage declaration
    Given the service's capabilities document
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    Then there is a inspire_common:Language node in the inspire_common:DefaultLanguage section

  Scenario: There is a Language declaration inside each SupportedLanguage delcaration
    Given the service's capabilities document
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    When there is a inspire_common:SupportedLanguage node in the inspire_common:SupportedLanguages section
    Then there is a inspire_common:Language node in each inspire_common:SupportedLanguage section
