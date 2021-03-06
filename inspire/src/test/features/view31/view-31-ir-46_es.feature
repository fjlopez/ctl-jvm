#language:es
Característica: Requisito 46
	Los estilos se encuentran emparejados en el elemento <wms:Style>.  El nombre legible
	para humanos se encuentra en el elemento <wms:Title> y el identificador único se encuentra
	en el elemento <wms:Name>. 
    !!!
    Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
    de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
    para **servicios de visualización** basados en el estándar internacional **ISO 19128**
    (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
    de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).
	
	Escenario: Comprobar si cada estilo tiene un título 
	Dado el documento de capabilities del servicio
	Y la URI para el prefijo wms es http://www.opengis.net/wms	
	Entonces existe un nodo wms:Name en cada sección wms:Style
	Y existe un nodo wms:Title en cada sección wms:Style
	
	
	