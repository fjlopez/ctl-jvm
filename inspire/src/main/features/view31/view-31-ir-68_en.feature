Feature: Requirement 68
  The name of this parameter shall be “LANGUAGE”. The parameter values are based on ISO 639-2/B 
  alpha 3 codes as used in **INS MDTG**.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Launch a GetCapabilities query by its default language
    Given the service's capabilities document
    Then GetCapabilities response is the same as with default language response when no language is specified
