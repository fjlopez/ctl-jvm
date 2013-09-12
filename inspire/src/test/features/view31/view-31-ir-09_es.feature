#language:es
Característica: Requisito 9
  Independientemente del escenario escogido (Requisito 6 o Requisito 7), el Metadato del Servicio de Visualización
  debe estar publicado en un servicio de Descubrimiento INSPIRE.  Esto se requiere para dar soporte a:
  a) Al enlace al servicio INSPIRE de Visualización,
  b) Descubrimiento de servicios de visualización por apliaciones cliente como podría ser el geoportal de INSPIRE.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Existe una sección MetadataUrl
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo inspire_vs es http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
    Cuando exista un nodo inspire_common:MetadataUrl en la sección inspire_vs:ExtendedCapabilities
    Entonces la sección MetadataUrl debe contener una consulta getRecordById
    Y el nodo MetadataUrl debe apuntar a una dirección válida

  Escenario: No existe una sección MetadataUrl
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo inspire_vs es http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
    Cuando no exista un nodo inspire_common:MetadataUrl en la sección inspire_vs:ExtendedCapabilities
    Entonces debe existir un Servicio de Catálogo que sirva este metadato de servicio
