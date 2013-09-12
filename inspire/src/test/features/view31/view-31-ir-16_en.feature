Feature: Requirement 16
  The INSPIRE Metadata Regulation **INS MD** mandates that in the case of spatial data services 
  at least one keyword from the "Classification of Spatial data Services" (Part D.4 from 
  **INS MD**) shall be provided.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check that at least there is a keyword from "Classification of Spatial data Services"
    Given the service's capabilities document
    Then there is at least one keyword from the INSPIRE "Classification of Spatial data Services" list
