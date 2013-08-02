Feature: Requirement 38
  To be able to map the concept of a responsible body/codeSpace and local identifier/code to **ISO 19128**), 
  AuthorityURL and Identifier elements shall be used. The authority name and explanatory URL shall be 
  defined in a separate AuthorityURL element, which may be defined once and inherited by subsidiary layers. 
  Identifiers themselves are not inherited.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check if each Layer has an Identifier section and each Identifier has a declared Authority
    Given the service's capabilities document
    And prefix wms is http://www.opengis.net/wms
    Then there is a wms:Identifier node in each wms:Layer section
    And every used Authority has been previouly declared just once
    And pairs identifier/authority are unique
