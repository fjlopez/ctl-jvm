#language:es
Característica: Requisito 6
  El elemento <inspire_common:MetadataURL> dentro de la sección ExtendedCapabilities de INSPIRE 
  debe ser usado para referenciar el metadato de servicio (a través de un servicio INSPIRE de 
  descubrimiento).
  Los campos obligatorios de **ISO 19128** – WMS 1.3.0 deben estar emparejados con los campos INSPIRE
  de forma que se pueda implementar un interfaz consistente.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Primera opción, publicación de los metadatos de un servicio de visualización a través de un servicio de descubrimiento.
    Requisito 6 - El elemento <inspire_common:MetadataURL>, dentro de la sección extendedCapabilities para un servicio [ISO 19128] – WMS 1.3.0 
    debe ser usado para referenciar al metadato de servicio INSPIRE, el cual estará disponible a través de un servicio de descubrimiento INSPIRE.
    Los elementos obligatorios de este metadato de servicio deben estar emparejados con los elementos  de los metadatos de INSPIRE, de esta forma
    es posible implementar un interfaz consistente.

    Dado el documento de capabilities del servicio
    Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
    Y la URI para el prefijo inspire_vs es http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    Cuando exista un nodo inspire_common:MetadataUrl en la sección inspire_vs:ExtendedCapabilities
    Entonces el link del elemento MetadataUrl debe ser un metadato INSPIRE válido
