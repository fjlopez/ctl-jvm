#language:es
Característica: Requisito 28
  Debido a que sólamente está permitida una instancia del elemento <wms:ContactInformation> en **ISO 19128** – WMS 1.3.0
  (en el cual se encuentra mapeada el elemento ResponsibleParty), debe utilizarse la extensión <inspire_common:MetadataPointOfContact>
  dentro de la sección <inspire_vs:ExtendedCapabilities>.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar si el contenido de MetadataPointOfContact está bien formado
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo inspire_vs es http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
    Cuando exista un nodo inspire_common:MetadataPointOfContact en la sección inspire_vs:ExtendedCapabilities
    Entonces existe un nodo inspire_common:OrganisationName en la sección inspire_common:MetadataPointOfContact
    Y existe un nodo inspire_common:EmailAddress en la sección inspire_common:MetadataPointOfContact
