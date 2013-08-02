#language:es
Característica: Requisito 52
  El parámetro obligatorio LAYERS contiene la lista de capas que deben ser devueltas
  por esta petición *GetMap*.  El valor del parámetro LAYERS debe ser una lista de los
  nombres de las capas separadas por comas.  El nombre de estas capas debe corresponderse
  con nombres harmonizados según INSPIRE.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar si se recibe una excepción cuando no se utiliza el parámetro Layers
    Dado el documento de capabilities del servicio
    Entonces una petición sin layers debe devolver una excepción
