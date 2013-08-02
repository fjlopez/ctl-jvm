Feature: Requirement 26
  The value domain of the Responsible Party role shall comply with the INSPIRE Metadata Regulation **INS MD, Part D6**. 
  The Responsible Party Role shall be mapped to the <wms:ContactPosition> of the <wms:ContactInformation> element
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check if each one of the Responsible Party Roles are valid
    Given the service's capabilities document
    And prefix wms is http://www.opengis.net/wms
    When there is a wms:ContactPosition node in the wms:ContactInformation section
    Then each node wms:ContactPosition in the wms:ContactInformation section should be in
      | resourceProvider      |
      | custodian             |
      | owner                 |
      | user                  |
      | distributor           |
      | originator            |
      | pointOfContact        |
      | principalInvestigator |
      | processor             |
      | publisher             |
      | author                |
