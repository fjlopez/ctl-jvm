Feature: Requirement 40
  It is mandatory to use geographical coordinate system based on ETRS89 in continental Europe and ITRS outside 
  continental Europe.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check if every Layer supports EPSG:4258, EPSG:4326 or CRS:84
    Given the service's capabilities document
    And prefix wms is http://www.opengis.net/wms
    Then node wms:CRS in each wms:Layer section of wms:Capability node should be in
      | EPSG:4258 |
      | EPSG:4326 |
      | CRS:84    |
