Feature: Requirement 36
  This Layer metadata element shall be mapped to the <wms:BoundingBox> element. The minimum bounding rectangle 
  of the area covered by the Layer in all supported CRS shall be given.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check if there is a BoundingBox for each Layer and each CRS
    Given the service's capabilities document
    And prefix wms is http://www.opengis.net/wms
    Then there is a wms:BoundingBox node in each wms:Layer section for each wms:CRS declared
