Feature: Requirement 71
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
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: There should be a DefaultLanguage declaration
    Given the service's capabilities document
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    Then there is a inspire_common:Language node in the inspire_common:DefaultLanguage section

  Scenario: There is a Language declaration inside each SupportedLanguage delcaration
    Given the service's capabilities document
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    When there is a inspire_common:SupportedLanguage node in the inspire_common:SupportedLanguages section
    Then there is a inspire_common:Language node in each inspire_commonSupportedLanguage section
