#language:es
Característica: Requisito 15
  Para indicar el tipo de servicio espacial, tal y como se define en la Regulación de Metadatos
  de INSPIRE **INS MD**, debe utilizarse una extensión para emparejar este elemento con
  <inspire_common:SpatialDataServiceType> dentro de la sección <inspire_vs:ExtendedCapabilities>.
  Para un servicio de Visualización de INSPIRE, este tipo debe tener el valor 'view' de 
  acuerdo con la Regulación de Metadatos de INSPIRE **INS MD Part 3**.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar el valor de SpatialDataServiceType (en el caso de que exista)
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo inspire_vs es http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
    Cuando exista un nodo inspire_common:SpatialDataServiceType en la sección inspire_vs:ExtendedCapabilities
    Entonces el valor del nodo inspire_common:SpatialDataServiceType en la sección inspire_vs:ExtendedCapabilities debe ser 'view'
