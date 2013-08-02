#language:es
Característica: Requisito 14
  Cada uno de los elementos MetadataURL deben estar poblados con una URL que permita el acceso
  unívoco a un registro de metadatos.  La URL debe ser una llamada HTTP/GET de tipo GetRecordById
  a un servicio de Descubrimiento o un enlace directo al documento del metadato ISO 19139.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que cada uno de los posibles MetadataURL en las secciones Layer contienen un link válido en el subnodo OnlineResource
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo wms es http://www.opengis.net/wms
    Entonces existe un nodo wms:OnlineResource en cada sección wms:MetadataURL
    Y cada href en los wms:OnlineResource de las secciones wms:MetadataURL son enlaces válidos
