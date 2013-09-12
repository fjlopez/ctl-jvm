Feature: Requirement 57
  The mandatory FORMAT parameter states the desired format of the map. The **INS NS, Annex III, Part B, Section 2** Image 
  format states that at least one of “image/png” or “image/gif” must be supported and therefore advertised
  in the *GetCapabilities* operation.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario Outline: GetCapabilities operation supports desired formats
    Given the service's capabilities document
    And prefix wms is http://www.opengis.net/wms
    When I look the list of values in <path>
    Then <format> is present when the root node is <root>
    And a request with no format should return an exception

    Examples: 
      | root                 | path                           | format    |
      | wms:WMS_Capabilities | //wms:GetMap/wms:Format/text() | image/png |
      | wms:WMS_Capabilities | //wms:GetMap/wms:Format/text() | image/gif |
