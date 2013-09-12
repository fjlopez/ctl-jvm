Feature: Requirement 30
	*GetCapabilities* operation metadata shall be mapped to the <wms:GetCapabilities> element.
    !!!
    Requirement established in the guidance document [Technical Guidance for the
    implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
    for **view services** based on the international standard **ISO 19128**
    (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
    nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).
	
	Scenario: Asegurarse que las operaciones GetCapabilities y GetMap están declaradas
	Given the service's capabilities document
	And prefix wms is http://www.opengis.net/wms
	Then there is a wms:GetCapabilities node in the wms:Request section
	