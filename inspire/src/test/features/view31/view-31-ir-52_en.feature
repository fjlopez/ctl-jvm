Feature: Requirement 52
  The mandatory LAYERS parameter lists the map layer(s) to be returned by this *GetMap* request. 
  The value of the LAYERS parameter shall be a comma-separated list of one or more valid INSPIRE 
  harmonized layer names.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check if that an exception is thrown when no parameter layers is used
    Given the service's capabilities document
    Then a request with no Layers should return an exception
