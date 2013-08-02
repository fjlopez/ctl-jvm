#language:es
Característica: Requisito 23
  El metadato de respuesta de un servicio en red debe contener una lista de idiomas naturales 
  soportados por el servicio.  Esta lista debe contenter uno o más idiomas que están soportados.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Descubrimiento Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  para **servicios de descubrimiento** basados en el estándar internacional **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que existe una lista de idiomas soportados
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
    Y la URI para el prefijo inspire_ds es http://inspire.ec.europa.eu/schemas/inspire_ds/1.0
    Entonces existe un nodo inspire_common:SupportedLanguages en la sección inspire_ds:ExtendedCapabilities
    Y existe un nodo inspire_common:SupportedLanguage en la sección inspire_common:SupportedLanguages
