Feature: Requirement 24
  Metadata concerning to conditions for access and use shall be mapped to the <wms:Fees> element of the capabilities. 
  If no conditions apply to the access and use of the resource, "no conditions apply" shall be used. If conditions are
  unknown "conditions unknown" shall be used.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check Fees field
    Given the service's capabilities document
    And prefix wms is http://www.opengis.net/wms
    Then there is a wms:Fees node in the wms:Service section
