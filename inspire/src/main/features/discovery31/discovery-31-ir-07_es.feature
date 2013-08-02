#language:es
Característica: Requisito 7
  La respuesta debe incluir los parámetros del metadato del servicio de descubrimiento **INS NS** 
  implementado una de las opciones que se muestran a continuación:
  Opción 1: Referenciando una URL incluida en la respuesta *GetCapabilities* en el elemento	MetadataUrl
   en la sección ExtendedCapabilities del **CSW ISO AP**; los parámetros obligatorios **OGC CSW ISO AP**
   (ver Tabla 2) deben emparejarse con los elementos del metadatos INSPIRE para que la implementación
   del interfaz sea consistente.  
  Opción 2: Incluir todos los metadatos requeridos explícitamente en la respuesta *GetCapabilities* **CSW ISO AP**.
   Los elementos INSPIRE que no pueden ser mapeados a elementos **CSW ISO AP** se implementan como elementos
   de la sección ExtendedCapabilities.
  
  Para cumplir con los requisitos de idioma de la regulación de servicios en red de INSPIRE **INS NS**,
  una sección idioma debe ser añadida en la sección ExtendedCapabilities del servicio.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Descubrimiento Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  para **servicios de descubrimiento** basados en el estándar internacional **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Opción 1, existe un elemento MetadataURL apuntando al metadato de servicio
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
    Y la URI para el prefijo inspire_ds es http://inspire.ec.europa.eu/schemas/inspire_ds/1.0
    Cuando exista un nodo inspire_common:MetadataUrl en la sección inspire_ds:ExtendedCapabilities
    Entonces existe un nodo inspire_common:URL en la sección inspire_common:MetadataUrl

  Escenario: Opción 2, todos los metadatos requeridos se incluyen explícitamente en el la respuesta GetCapabilities
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo ows es http://www.opengis.net/ows
    Y la URI para el prefijo ogc es http://www.opengis.net/ogc
    Y la URI para el prefijo csw es http://www.opengis.net/cat/csw/2.0.2
    Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
    Y la URI para el prefijo inspire_ds es http://inspire.ec.europa.eu/schemas/inspire_ds/1.0
    Cuando no exista un nodo inspire_common:MetadataUrl en la sección inspire_ds:ExtendedCapabilities
    Entonces existe un nodo ows:ServiceType en la sección ows:ServiceIdentification
    Y existe un nodo ows:ServiceTypeVersion en la sección ows:ServiceIdentification
    Y el valor del nodo ows:ServiceTypeVersion en la sección ows:ServiceIdentification debe ser '2.0.2'
    Y existe un nodo ows:Title en la sección ows:ServiceIdentification
    Y existe un nodo ows:Abstract en la sección ows:ServiceIdentification
    Y existe un nodo ows:Keywords en la sección ows:ServiceIdentification
    Y existe un nodo ows:Fees en la sección ows:ServiceIdentification
    Y existe un nodo ows:AccessConstraints en la sección ows:ServiceIdentification
    Y existe un nodo ows:ProviderName en la sección ows:ServiceProvider
    Y existe un nodo ows:ProviderSite en la sección ows:ServiceProvider
    Y existe un nodo ows:ServiceContact en la sección ows:ServiceProvider
    Y existe un nodo inspire_common:ResourceType en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo inspire_common:ResourceLocator en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo inspire_common:SpatialDataServiceType en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo ows:Operation en la sección ows:OperationsMetadata
    Y existe un nodo ows:Parameter en la sección ows:OperationsMetadata
    Y existe un nodo ows:Constraint en la sección ows:OperationsMetadata
    Y existe un nodo inspire_ds:ExtendedCapabilities en la sección ows:OperationsMetadata
    Y existe un nodo inspire_common:MandatoryKeyword en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo inspire_common:TemporalReference en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo inspire_common:Conformity en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo inspire_common:MetadataPointOfContact en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo inspire_common:MetadataDate en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo inspire_common:SupportedLanguages en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo ogc:Filter_Capabilities en la sección csw:Capabilities
