#language:es
Característica: Requisito 19
  Un servicio de Descubrimiento de INSPIRE debe soportar las propiedades de consulta indicadas
  en la Tabla 4: Criterios de búsqueda de INSPIRE.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Descubrimiento Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  para **servicios de descubrimiento** basados en el estándar internacional **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que todas las propiedades consultables de la tabla 4 están listadas en el documento de Capabilities
    Dado el documento de capabilities del servicio
    Entonces Subject es uno de los valores de la sección Constraint con nombre SupportedISOQueryables en la operación GetRecords
    Y TopicCategory es uno de los valores de la sección Constraint con nombre SupportedISOQueryables en la operación GetRecords
    Y ServiceType es uno de los valores de la sección Constraint con nombre SupportedISOQueryables en la operación GetRecords
    Y SpatialResolution es uno de los valores de la sección Constraint con nombre SupportedISOQueryables en la operación GetRecords
    Y BoundingBox es uno de los valores de la sección Constraint con nombre SupportedISOQueryables en la operación GetRecords
    Y OrganisationName es uno de los valores de la sección Constraint con nombre SupportedISOQueryables en la operación GetRecords
    Y Title es uno de los valores de la sección Constraint con nombre SupportedISOQueryables en la operación GetRecords
    Y Abstract es uno de los valores de la sección Constraint con nombre SupportedISOQueryables en la operación GetRecords
    Y Type es uno de los valores de la sección Constraint con nombre SupportedISOQueryables en la operación GetRecords
    Y ResourceIdentifier es uno de los valores de la sección Constraint con nombre SupportedISOQueryables en la operación GetRecords
    Y TemporalExtent es uno de los valores de la sección Constraint con nombre SupportedISOQueryables en la operación GetRecords
    Y PublicationDate es uno de los valores de la sección Constraint con nombre SupportedISOQueryables en la operación GetRecords
    Y RevisionDate es uno de los valores de la sección Constraint con nombre SupportedISOQueryables en la operación GetRecords
    Y CreationDate es uno de los valores de la sección Constraint con nombre SupportedISOQueryables en la operación GetRecords
