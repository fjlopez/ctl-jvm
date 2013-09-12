#language:es
Característica: Requisito 22
  Todas las propiedades consultables ISO deben ser anunciadas como tal en el metadato de descripción
  de los Servicios de Descubrimiento INSPIRE, además, todos los criterios de búsqueda deben estar 
  listados  en la sección “AdditionalQueryables”.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Descubrimiento Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  para **servicios de descubrimiento** basados en el estándar internacional **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que las propiedades IsoQueryables y AdditionalQueryables se encuentran en el documento de Capabilities
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo ows es http://www.opengis.net/ows
    Entonces existe un nodo ows:Constraint en la operación GetRecords con el atributo name con valor SupportedISOQueryables
    Entonces existe un nodo ows:Constraint en la operación GetRecords con el atributo name con valor AdditionalQueryables
