Feature: Requirement 22
  The INSPIRE Metadata Regulation **INS MD** requires that metadata shall include information on
  the degree of conformity with the implementing rules provided in Art. 7.1 (Interoperability 
  of spatial data sets and services) of the INSPIRE Directive **Directive 2007/2/EC**.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check Capabilities conformance (provided Conformity section exists, it should )
    Given the service's capabilities document
    And prefix inspire_vs is http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    And prefix inspire_common is http://inspire.ec.europa.eu/schemas/common/1.0
    When there exists an inspire_common:Conformity node in the inspire_vs:ExtendedCapabilities section
    Then node inspire_common:Degree in the inspire_common:Conformity section should be in
      | notEvaluated  |
      | conformant    |
      | notConformant |
