#language:es
Característica: Requisito 8
  **CSW ISO AP** especifica una operación *GetCapabilities* con ciertas secciones de metadatos.  El
  metadato de servicio en los documentos de capabilities debe ser conforme con los requisitos
  de los metadatos de servicio de INSPIRE **INS NS**.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Descubrimiento Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  para **servicios de descubrimiento** basados en el estándar internacional **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Todos los metadatos requeridos deben estar incluidos en la respuesta GetGetCapabilities (no existe MetadataUrl)
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo ows es http://www.opengis.net/ows
    Y la URI para el prefijo ogc es http://www.opengis.net/ogc
    Y la URI para el prefijo csw es http://www.opengis.net/cat/csw/2.0.2
    Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
    Y la URI para el prefijo inspire_ds es http://inspire.ec.europa.eu/schemas/inspire_ds/1.0
    Cuando no exista un nodo inspire_common:MetadataUrl en la sección inspire_ds:ExtendedCapabilities
    Entonces existe un nodo ows:Title en la sección ows:ServiceIdentification
    Y existe un nodo ows:Abstract en la sección ows:ServiceIdentification
    Y existe un nodo inspire_common:ResourceType en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo inspire_common:ResourceLocator en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo inspire_common:SpatialDataServiceType en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo inspire_common:MandatoryKeyword en la sección inspire_ds:ExtendedCapabilities
    Y hay al menos en la sección inspire_ds:ExtendedCapabilities un nodo de
      | inspire_common:TemporalExtent     |
      | inspire_common:DateOfPublication  |
      | inspire_common:DateOfLastRevision |
      | inspire_common:DateOfCreation     |
    Y existe un nodo inspire_common:Conformity en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo inspire_common:Specification en la sección inspire_common:Conformity
    Y existe un nodo inspire_common:Degree en la sección inspire_common:Conformity
    Y existe un nodo ows:Fees en la sección ows:ServiceIdentification
    Y existe un nodo ows:AccessConstraints en la sección ows:ServiceIdentification
    Y existe un nodo ows:ServiceProvider en la sección csw:Capabilities
    Y existe un nodo ows:ProviderName en la sección ows:ServiceProvider
    Y existe un nodo ows:ServiceContact en la sección ows:ServiceProvider
    Y existe un nodo ows:ContactInfo en la sección ows:ServiceContact
    Y existe un nodo ows:Address en la sección ows:ContactInfo
    Y existe un nodo ows:ElectronicMailAddress en la sección ows:Address
    Y existe un nodo ows:Role en la sección ows:ServiceContact
    Y existe un nodo inspire_common:MetadataPointOfContact en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo inspire_common:MetadataDate en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo inspire_common:ResponseLanguage en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo inspire_common:Language en la sección inspire_common:ResponseLanguage
