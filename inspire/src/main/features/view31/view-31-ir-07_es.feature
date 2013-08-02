#language:es
Característica: Requisitos 7
  Los elementos INSPIRE se encuentran completamente emparejados con elementos del documento de 
  Capabilities de los WMS. Es obligatorio usar el emparejamiento proporcionado en esta
  guía técnica (descrito desde la sección 4.2.3.3.1.1 a la 4.2.3.3.1.16).  Los elementos
  INSPIRE que no pueden emparejarse con campos disponibles de **ISO 19128** – WMS1.3.0 
  están implementados en la sección Extended Capabilities. Los metadatos son publicados 
  a través del documento de capabilities del servicio y pueden ser obtenidos mediante 
  mecanismo de **harvest** por un servicio INSPIRE de Descubrimiento.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Segunda opción, utilizar las propiedades extendidas para emparejar todos los elementos de INSPIRE con los elementos de [ISO 19128] – WMS1.3.0,
    algunos de estos elementos de INSPIRE están emparejados con elementos del documento Capabilities de WMS. Es obligatorio utilizar el emparejamiento
    proporcionado en la guía técnica de INSPIRE (descrito en las secciones 4.2.3.3.1.1 a la 4.2.3.3.1.16. Los elementos de los metadatos de INSPIRE
    que no pueden ser emparejados con elementos [ISO 19128] – WMS1.3.0 disponibles serán implementados en la sección extendedCapabilities. Los metadatos
    serán publicados a través de la descripción del servicio y pueden ser obtenidos a través de un servicio de descubrimiento INSPIRE.

    Dado el documento de capabilities del servicio
    Y la URI para el prefijo wms es http://www.opengis.net/wms
    Y la URI para el prefijo inspire_vs es http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
    Cuando no exista un nodo inspire_common:MetadataUrl en la sección inspire_vs:ExtendedCapabilities
    Entonces existe un nodo wms:Title en la sección wms:Service
    Y existe un nodo wms:Abstract en la sección wms:Service
    Y existe un nodo inspire_common:ResourceType en la sección inspire_vs:ExtendedCapabilities
    Y existe un nodo inspire_common:ResourceLocator en la sección inspire_vs:ExtendedCapabilities
    Y existe un nodo wms:MetadataURL en cada sección wms:Layer
    Y existe un nodo inspire_common:SpatialDataServiceType en la sección inspire_vs:ExtendedCapabilities
    Y existe un nodo wms:KeywordList en la sección wms:Service
    Y existe un nodo inspire_common:MandatoryKeyword en la sección inspire_vs:ExtendedCapabilities
    Y existe un nodo wms:EX_GeographicBoundingBox en cada sección wms:Layer
    Y existe un nodo inspire_common:TemporalReference en la sección inspire_vs:ExtendedCapabilities
    Y existe un nodo inspire_common:Conformity en la sección inspire_vs:ExtendedCapabilities
    Y existe un nodo wms:Fees en la sección wms:Service
    Y existe un nodo wms:AccessConstraints en la sección wms:Service
    Y existe un nodo wms:ContactInformation en la sección wms:Service
    Y existe un nodo inspire_common:MetadataPointOfContact en la sección inspire_vs:ExtendedCapabilities
    Y existe un nodo inspire_common:MetadataDate en la sección inspire_vs:ExtendedCapabilities
    Y existe un nodo inspire_common:SupportedLanguages en la sección inspire_vs:ExtendedCapabilities
