#language:es
Característica: Requisito 16
  La Regulación de Metadatos de INSPIRE **INS MD** indica que en el caso de un servicio de datos 
  espaciales, al menos, una palabra clave debe provenir de la Clasificación de Servicios de datos
  espaciales de INSPIRE (Part D.4 de **INS MD**)
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que al menos una palabra clave proviene de la "Classification of Spatial data Services"
    Dado el documento de capabilities del servicio
    Entonces existe al menos una palabra clave proveniente de la "Classification of Spatial data Services"
