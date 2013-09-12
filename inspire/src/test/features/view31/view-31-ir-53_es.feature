#language:es
Característica: Requisito 53
  El parámetro obligatorio STYLES contiene una lista de estilos en los cuales cada capa
  debe ser renderizada.  El valor del parámetro STYLES debe ser una lista de nombres de
  estilos separados por comas.  Un cliente puede realizar una petición en el estilo por
  defecto utilizando un valor nulo ("STYLES=").
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que el parámetro Styles con valor vacío es aceptada y que se lanza una excepción cuando no se incluye
    Dado el documento de capabilities del servicio
    Entonces una petición con styles= debe devolver una imagen
    Y una petición sin styles debe devolver una excepción
