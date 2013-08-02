Feature: Requirement 33
  Layer title is mapped with <wms:Title>. The harmonised title of a layer for an INSPIRE spatial data theme is 
  defined by **INS DS** and shall be subject to multilingualism (translations shall appear in each 	mono-lingual 
  capabilities localised documents).
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check if theres a Title for each Layer
    Given the service's capabilities document
    And prefix wms is http://www.opengis.net/wms
    Then there is a wms:Title node in each wms:Layer section
