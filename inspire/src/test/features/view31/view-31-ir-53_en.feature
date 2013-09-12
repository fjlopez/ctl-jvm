Feature: Requirement 53
  The mandatory STYLES parameter lists the style in which each layer is to be rendered. 
  The value of the STYLES parameter shall be a comma-separated list of one or more valid 
  INSPIRE style names. A client may request the default Style using a null value (as in 
  "STYLES=").
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check that Styles with empty value is accepted and that an exception is thrown when no parameter styles is used and t
    Given the service's capabilities document
    Then a request with styles= should return an image
    And a request with no sytles should return an exception
