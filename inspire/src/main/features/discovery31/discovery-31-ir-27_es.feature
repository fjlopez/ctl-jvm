#language: es
Característica: Requisito 27
  La sección de ExtendedCapabilities debe indicar el idioma de la respuesta usado:
  Dependiendo del idioma indicado en la petición, el valor de <inspire_common:ResponseLanguage> se corresponderá con un valor diferente según las siguientes
  situaciones:
  Si se realiza la petición con uno de los idiomas soportados, entonces <inspire_common:ResponseLanguage> debe corresponderse con el idioma requerido. 
  Si se realiza la petición con un idioma no soportado o no se indica ningún idioma en la petición, entonces <inspire_common:ResponseLanguage> debe
  corresponderse con el idioma por defecto del servicio <inspire_common:DefaultLanguage>.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Descubrimiento Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  para **servicios de descubrimiento** basados en el estándar internacional **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Antecedentes: 
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
    Y la descripción del servicio tiene declarado un idioma por defecto
    Y la descripción del servicio tiene declarados opcionalmente una lista de idiomas soportados

  Escenario: El idioma de las respuestas se corresponde con el idioma de la petición si éste se encuentra entre los idiomas soportados
    Cuando se pide el documento GetCapabilities para cada uno de los idiomas soportados del servicio
    Y se pide el documento GetCapabilities en el idioma por defecto del servicio
    Entonces cada respuesta debe corresponderse con el idioma en el que fue solicitada

  Escenario: El idioma de las respuestas se corresponde con el idioma por defecto del servicio si se indica un idioma no soportado en la petición
    Dado el idioma xyz no soportado por el servicio
    Cuando se pide el documento GetCapabilities en el idioma xyz al servicio
    Entonces el idioma de la respuesta debe corresponderse con el idioma por defecto del servicio

  Escenario: El idioma de las respuestas se corresponde con el idioma por defecto del servicio si no se indica ningún idioma en la petición
    Cuando se pide el documento GetCapabilities sin especificar ningún idioma al servicio
    Entonces el idioma de la respuesta debe corresponderse con el idioma por defecto del servicio
