#language:es
Característica: Requisito 29
	Debido a que **ISO 19128** - WMS 1.3.0 no da soporte a un elemento de tipo fecha, debe utilizarse la extensión
	<inspire_common:MetadataDate> dentro del elemento <inspire_vs:ExtendedCapabilities>.  Las fechas deben ser 
	expresadas en conformidad con **INS MD**.
    !!!
    Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
    de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
    para **servicios de visualización** basados en el estándar internacional **ISO 19128**
    (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
    de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).
	
	Escenario:  comprobar que el elemento inspire_common:Conformity ha sido correctamente utilizado en el caso de ser necesario
	Dado el documento de capabilities del servicio
	Y la URI para el prefijo wms es http://www.opengis.net/wms
	Y la URI para el prefijo inspire_vs es http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
	Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
	Cuando no exista un nodo inspire_common:MetadataUrl en la sección inspire_vs:ExtendedCapabilities
	Entonces existe un nodo inspire_common:MetadataDate en la sección inspire_vs:ExtendedCapabilities 
	
	