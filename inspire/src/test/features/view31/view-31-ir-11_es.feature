#language:es
Característica: Requisito 11
  Dentro de del ámbito definido por la directiva de INSPIRE, el valor para el tipo de Recurso
  debe ser *service* para servicios de datos espaciales.  Debido a que el tipo de recurso no está
  soportado por **ISO 19128** - WMS 1.3.0, debe usarse la extensión <inspire_common:ResourceType> dentro
  de la sección <inspire_vs:ExtendedCapabilities> para este propósito.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar el valor de ResourceType en el caso de que exista
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo inspire_vs es http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
    Cuando exista un nodo inspire_common:ResourceType en la sección inspire_vs:ExtendedCapabilities
    Entonces el valor del nodo inspire_common:ResourceType en la sección inspire_vs:ExtendedCapabilities debe ser 'service'
