Feature: Requirement 21
  Table 5 identifies the additional queryables that are not supported by **CSW ISO AP**, but 
  required by **INS NS**. X-Path expression and data types are taken from **INS MDTG**.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE Discovery Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  for **discovery services** based on the international standard **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU) 
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check that queryables defined in Table 5 are listed in the capabilities document as AdditionalQueryables
    Given the service's capabilities document
    Then Degree is one of the values for Constraint AdditionalQueryables in GetRecords operation
    And ConditionApplyingToAccessAndUse is one of the values for Constraint AdditionalQueryables in GetRecords operation
    And Lineage is one of the values for Constraint AdditionalQueryables in GetRecords operation
    And ResponsiblePartyRole is one of the values for Constraint AdditionalQueryables in GetRecords operation
    And SpecificationTitle is one of the values for Constraint AdditionalQueryables in GetRecords operation
    And SpecificationDate is one of the values for Constraint AdditionalQueryables in GetRecords operation
    And SpecificationDateType is one of the values for Constraint AdditionalQueryables in GetRecords operation
    And AccessConstraints is one of the values for Constraint AdditionalQueryables in GetRecords operation
    And OtherConstraints is one of the values for Constraint AdditionalQueryables in GetRecords operation
    And Classification is one of the values for Constraint AdditionalQueryables in GetRecords operation
