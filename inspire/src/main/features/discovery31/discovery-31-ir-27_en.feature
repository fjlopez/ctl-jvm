Feature: Requirement 27
  The Extended Capabilities shall indicate the response language used for the *GetCapabilities*-Response:
  Depending on the requested language the value of the <inspire_common:ResponseLanguage> corresponds to the current used language.
  If a supported language was requested, <inspire_common:ResponseLanguage>shall correspond to that requested language. 
  If an unsupported language was requested or if no specific language was requested <inspire_common:ResponseLanguage> shall correspond
  to the service default language <inspire_common:DefaultLanguage>.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE Discovery Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  for **discovery services** based on the international standard **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU) 
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Background: 
    Given the service's capabilities document
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    And the service description has a declaration of default language
    And the service description has optionally a declaration of supported languages

  Scenario: Responses correspond to requested language if a supported language was requested
    When I request a GetCapabilities document for each supported language to the service
    And I request a GetCapabilities document in the default language to the service
    Then each response shall correspond to that requested language

  Scenario: Responses correspond to the service default language if an unsupported language was requested
    Given the language xyz is unsupported by the service
    When I request a GetCapabilities document with the language xyz to the service
    Then the response shall correspond to the service default language

  Scenario: Responses correspond to the service default language if no specific language was requested
    When I request a GetCapabilities document with no specific language to the service
    Then the response shall correspond to the service default language
