#language:es
Característica: Requisito 67
  Un cliente puede especificar el idioma en una petición.  Si el idioma está contenido en la lista
  de idiomas soportados, los campos de texto en idioma natural de la respuesta deben recibirse en
  el idioma de la petición.  Si el idioma no está soportado por el servicio, entonces el parámetro
  será ignorado.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que las respuestas de GetCapabilities son diferentes por cada uno de los idiomas soportados y la misma cuando se especifica el idioma por defecto o un idioma no soportado
    Dado el documento de capabilities del servicio
    Entonces una respuesta GetCapabilities en el idioma por defecto es diferente de las obtenidas en cada uno de los idiomas soportados
    Y una respuesta GetCapabilities en un idioma no soportado es la misma que la recibida con el idioma por defecto
    Y una respuesta GetCapabilities sin especificar un idioma es la misma que la recibida con el idioma por defecto
