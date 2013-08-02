#language:es
Característica: Requisito 40
  Es obligatorio utilizar sistemas de coordenadas basadas en ETRS89 en el continente Europeo e ITRS fuera
  de Europa.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar si cada capa soporta EPSG:4258, EPSG:4326 o CRS:84
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo wms es http://www.opengis.net/wms
    Entonces un nodo wms:CRS en cada una de las secciones wms:Layer dentro de la sección wms:Capability debe estar entre
      | EPSG:4258 |
      | EPSG:4326 |
      | CRS:84    |
