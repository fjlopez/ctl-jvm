Feature: Requirement 19
  An INSPIRE discovery service shall support the queryables as indicated in Table 4: INSPIRE
  search criteria (queryables)
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE Discovery Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  for **discovery services** based on the international standard **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU) 
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check that all the queryables in Table 4 are listed in the Capabilities Document
    Given the service's capabilities document
    Then Subject is one of the values for Constraint SupportedISOQueryables in GetRecords operation
    And TopicCategory is one of the values for Constraint SupportedISOQueryables in GetRecords operation
    And ServiceType is one of the values for Constraint SupportedISOQueryables in GetRecords operation
    And SpatialResolution is one of the values for Constraint SupportedISOQueryables in GetRecords operation
    And BoundingBox is one of the values for Constraint SupportedISOQueryables in GetRecords operation
    And OrganisationName is one of the values for Constraint SupportedISOQueryables in GetRecords operation
    And Title is one of the values for Constraint SupportedISOQueryables in GetRecords operation
    And Abstract is one of the values for Constraint SupportedISOQueryables in GetRecords operation
    And Type is one of the values for Constraint SupportedISOQueryables in GetRecords operation
    And ResourceIdentifier is one of the values for Constraint SupportedISOQueryables in GetRecords operation
    And TemporalExtent is one of the values for Constraint SupportedISOQueryables in GetRecords operation
    And PublicationDate is one of the values for Constraint SupportedISOQueryables in GetRecords operation
    And RevisionDate is one of the values for Constraint SupportedISOQueryables in GetRecords operation
    And CreationDate is one of the values for Constraint SupportedISOQueryables in GetRecords operation
