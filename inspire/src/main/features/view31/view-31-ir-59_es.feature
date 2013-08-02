#language:es
Característica: Requisito 59
  El valor por defecto debe ser "XML", si este parámetro no se encuentra en la petición.  Otros
  valores válidos son "INIMAGE" y "BLANK".
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que se devuelve un XML cuando no está presente o se indica "XML" y que se devuelve una imagen con INIMAGE o BLANK
    Dado el documento de capabilities del servicio
    Entonces una petición sin request debe devolver una excepción
    Y una petición sin request y EXCEPTIONS=XML debe devolver una excepción en xml
    Y una petición sin request y EXCEPTIONS=INIMAGE debe devolver una excepción en imagen
    Y una petición sin request y EXCEPTIONS=BLANK debe devolver una imagen en blanco
