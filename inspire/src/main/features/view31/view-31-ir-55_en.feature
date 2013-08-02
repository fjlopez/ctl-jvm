Feature: Requirement 55
  The mandatory BBOX parameter allows a Client to request a particular Bounding Box. The value of the 
  BBOX parameter in a *GetMap* request shall be a list of comma-separated real numbers in the form 
  "minx,miny,maxx,maxy". These values specify the minimum X, minimum Y, maximum X, and maximum Y values 
  of a region in the Layer CRS of the request. The units, ordering and direction of increment of the X 
  and Y axes shall be as defined by the Layer CRS. The four bounding box values indicate the outside 
  limits of the region.
  !!!
  Requirement established in the guidance document [Technical Guidance for the
  implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  for **view services** based on the international standard **ISO 19128**
  (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
  nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).

  Scenario: Check that an exception is thrown when no parameter bbox is used
    Given the service's capabilities document
    Then a request with no bbox should return an exception
