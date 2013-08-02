Feature: Requirement 5
  The operation for implementing INSPIRE *Get View Service Metadata* operation is the *GetCapabilities* operation. 
  The parameters defined within the **ISO 19128** standard shall be used to convey relevant information in order to 
  get the expected responses as described in **INS NS, Annex III, Section 2.2** of the Regulation on INSPIRE Network 
  Services.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check if with parameters request=GetCapabilities and service=WMS, a service description is obtained
    Given the service's capabilities document
    Then a query with parameters request=GetCapabilities&service=WMS should return a service description
