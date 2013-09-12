Feature: Requirement 2
  The use of **ISO 19128** de jure standard as a basis for implementing an INSPIRE View service
  means that this service shall comply with the “basic WMS” conformance class as defined in this de
  jure standard.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Ensure the service is declared an ISO 19129
    Given the service's capabilities document
    Then the root node name is WMS_Capabilities
    And the root node namespace URI is http://www.opengis.net/wms
    And the attribute version in root node has value 1.3.0
