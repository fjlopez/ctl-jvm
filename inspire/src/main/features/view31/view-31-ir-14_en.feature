Feature: Requirement 14
  Each of the <MetadataURL> elements shall be populated with a URL that allows access to an 
  unambiguous metadata record. The URL shall be either an HTTP/GET call on the GetRecordById 
  operation of the Discovery Service or a direct link to the ISO 19139 metadata document.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check if each of the possible MetadataURL in Layer sections contains a valid link in OnlineResource subnode
    Given the service's capabilities document
    And prefix wms is http://www.opengis.net/wms
    Then there is an wms:OnlineResource node in each wms:MetadataURL section
    And each href in the wms:OnlineResource of wms:MetadataURL sections is a valid link
