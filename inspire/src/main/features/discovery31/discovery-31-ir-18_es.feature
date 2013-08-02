#language:es
Característica: Requisito 18
  **CSW ISO AP** como especificación base para el servicio de Descubrimiento de INSPIRE, está basado
  en el modelo de información de ISO 19115/19119.  Como tal, los elementos de los metadatos de 
  INSPIRE (revisar **INS MD**) deben ser pedidos a través del servicio de Descubrimiento de INSPIRE
  dentro de una consulta
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Descubrimiento Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  para **servicios de descubrimiento** basados en el estándar internacional **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que el perfil ISO está declarado
    Dado el documento de capabilities del servicio
    Entonces el perfil ISO está declarado como una Constraint en la sección OperationsMetadata
    Y http://www.isotc211.org/2005/gmd es uno de los valores para el parámetro outputSchema en la operación GetRecords
    Y gmd:MD_Metadata es uno de los valores para el parámetro typeNames en la operación GetRecords
