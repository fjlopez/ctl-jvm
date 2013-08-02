#language:es
Característica: Requisito 3
  La lista de los catálogos federados (en caso de existir), debe ser anunciada como resultado del
  metadato del servicio en respuesta a una petición del mismo (*GetCapabilities*).
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Descubrimiento Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  para **servicios de descubrimiento** basados en el estándar internacional **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que en el caso de que existan catálogos federados, éstos son servicios de Descubrimiento (CSW)
    Dado el documento de capabilities del servicio
    Cuando existan catálogos federados declarados
    Entonces cada uno de los catálogos federados debe responder correctamente a una petición GetCapabilities
