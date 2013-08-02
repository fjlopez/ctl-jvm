#language:es
Característica: Requisito 32
  Si un cliente envía una petición CSW GetRecords inválida (que no cumple con
  CSW ISO AP) conteniendo un filter con un idioma específico causará una 
  excepción en el servicio.  La excepción debe ser respondida en el idioma
  por defecto o en uno de los idiomas soportados indicados en la petición.
  El uso de un filtro con un idioma específico no debe lanzar una excepción,
  incluso si el idioma requerido no está soportado.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Descubrimiento Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  para **servicios de descubrimiento** basados en el estándar internacional **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).
