Feature: Requirement 48
	In other cases such as time and elevation, <wms:Dimension> shall be used according 
	to **INS NS**.
    !!!
    Requirement established in the guidance document [Technical Guidance for the
    implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
    for **view services** based on the international standard **ISO 19128**
    (**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
    nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).
	
	Scenario: check, if any, that every dimension elements in Layer section have name and units attributes
	Given the service's capabilities document
	And prefix wms is http://www.opengis.net/wms
	Then there is an attribute name in each wms:Dimension section
	And there is an attribute units in each wms:Dimension section
	
	
	
	