#language: es
Característica: Requisito 2
  El uso de **ISO 19128** como el estándar defacto para implementar un servicio INSPIRE de visualización
  implica que este servicio debe cumplir con la especificación básica para WMS tal y como se define en este estándard.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Asegurar que el servicio está declarado como ISO 19128
    Dado el documento de capabilities del servicio
    Entonces el nombre del nodo raíz es WMS_Capabilities
    Y la URI para el espacio de nombres del nodo raíz es http://www.opengis.net/wms
    Y el atributo version en el nodo raíz tiene valor 1.3.0
