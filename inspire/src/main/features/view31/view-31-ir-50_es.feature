#language:es
Característica: Requisito 50
  El parámetro VERSION es obligatorio.  El valor "1.3.0" debe usarse para las peticiones
  *GetMap* que cumplen con el estándar **ISO 19128**.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que se acepta el parámetro VERSION con el valor 1.3.0 y que se lanza una excepción si no se incluye este parámetro
    Dado el documento de capabilities del servicio
    Entonces una petición con VERSION=1.3.0 debe devolver una imagen
    Y una petición sin version debe devolver una excepción
