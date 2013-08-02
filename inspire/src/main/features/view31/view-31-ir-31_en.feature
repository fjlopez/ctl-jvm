Feature: Requirement 31
  *GetMap* operation metadata shall be mapped to the <wms:GetMap> element. Either PNG or GIF format (without LZW 
  compression) with transparency shall be supported by the View service **INS NS, Annex III, Part B**.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check if PNG or GIF formats are supported
    Given the service's capabilities document
    And prefix wms is http://www.opengis.net/wms
    Then there is a wms:GetMap node in the wms:Request section
    And there is a wms:Format node in the wms:GetMap section
    And node wms:Format in the wms:GetMap section should be in
      | image/png |
      | image/gif |
