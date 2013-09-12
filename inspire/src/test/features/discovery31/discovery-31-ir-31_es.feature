#language:es
Característica: Requisito 30
  Una petición GetRecords CSW que contiene un idioma específico debe devolver metadatos que cumplen
  con la restricción de idioma.  Si ningún metadato cumple con las restricciones, se debe devolver
  un conjunto vacío (sin lanzar ninguna excepción)
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Descubrimiento Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  para **servicios de descubrimiento** basados en el estándar internacional **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Antecedentes: 
    Dado el documento de capabilities del servicio
    Y la descripción del servicio tiene declarado un idioma por defecto
    Y la descripción del servicio tiene declarados opcionalmente una lista de idiomas soportados

  Escenario: Comprobar que todos los metadatos devueltos para una consulta GetRecords con idioma, se corresponden con el idioma indicado
    Entonces el contenido de una consulta GetRecords por cada uno de los idiomas soportados debe devolver metadatos en el idioma indicado
