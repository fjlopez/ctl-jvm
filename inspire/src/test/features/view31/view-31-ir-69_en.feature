Feature: Requirement 69
  If a client request specifies an unsupported language, or the parameter is absent in the request, 
  the above fields shall be provided in the service default language.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check if GetCapabilities responses are the same as for the default language when the specified language is not supported
    Given the service's capabilities document
    Then GetCapabilities response is the same as with default language response when language is not suppoted
    And GetCapabilities response is the same as with default language response when no language is specified
