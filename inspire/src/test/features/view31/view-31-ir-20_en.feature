Feature: Requirement 20
  To be compliant with the INSPIRE Metadata Regulation **INS MD** and with **ISO 19115** one 
  of following dates shall be used: date of publication, date of last revision, or the date 
  of creation. Date of last revision is preferred. The date shall be expressed in conformity 
  with the **INS MD**.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check that there exists at least one date (provided there is a TemporalReference section and no DateOfCreation and DateOfPublication are available)
    Given the service's capabilities document
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    And prefix inspire_vs is http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    When there exists an inspire_common:TemporalReference node in the inspire_vs:ExtendedCapabilities section
    And there is not a inspire_common:DateOfCreation node in the inspire_common:TemporalReference section
    And there is not a inspire_common:DateOfPublication node in the inspire_common:TemporalReference section
    Then there is a inspire_common:DateOfLastRevision node in the inspire_common:TemporalReference section

  Scenario: Check that there exists at least one date (provided there is a TemporalReference section and no DateOfPublication and DateOfLastRevision are available)
    Given the service's capabilities document
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    And prefix inspire_vs is http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    When there exists an inspire_common:TemporalReference node in the inspire_vs:ExtendedCapabilities section
    And there is not a inspire_common:DateOfLastRevision node in the inspire_common:TemporalReference section
    And there is not a inspire_common:DateOfPublication node in the inspire_common:TemporalReference section
    Then there is a inspire_common:DateOfCreation node in the inspire_common:TemporalReference section

  Scenario: Check that there exists at least one date (provided there is a TemporalReference section and no DateOfCreation and DateOfLastRevision are available)
    Given the service's capabilities document
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    And prefix inspire_vs is http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    When there exists an inspire_common:TemporalReference node in the inspire_vs:ExtendedCapabilities section
    And there is not a inspire_common:DateOfCreation node in the inspire_common:inspire_common:TemporalReference section
    And there is not a inspire_common:DateOfLastRevision node in the inspire_common:TemporalReference section
    Then there is a inspire_common:DateOfPublication node in the inspire_common:TemporalReference section
