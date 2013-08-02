#language:es
Característica: Requisito 58
  El parámetro opcional TRANSPARENT especifica si el fondo del mapa debe ser transparente o no.  Se requiere
  que el servicio implemente esta funcionalidad.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar si se admite el uso del parámetro TRANSPARENT
    Dado el documento de capabilities del servicio
    Entonces una petición con transparent=TRUE debe devolver una imagen
    Y una petición con transparent=FALSE debe devolver una imagen
