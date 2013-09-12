#language:es
Característica: Requisito 25
  El nombre de este parámetro debe ser “LANGUAGE”. Los valores del parámetro deben estar basados en códigos
  ISO 639-2/B alpha 3, como los usuados en **INS MDTG**.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Descubrimiento Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  para **servicios de descubrimiento** basados en el estándar internacional **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Lanzar un GetCapabilities por su idioma por defecto
    Dado el documento de capabilities del servicio
    Entonces una respuesta GetCapabilities sin especificar un idioma es la misma que la recibida con el idioma por defecto
