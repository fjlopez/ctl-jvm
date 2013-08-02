#language:es
Característica: Requisito 12
  Los metadatos de respuesta deben, al menos, contener los elementos INSPIRE. **INS NS, Anexo II, 
  Sección 3.2.1**.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Descubrimiento Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  para **servicios de descubrimiento** basados en el estándar internacional **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que el primer metadato de una respuesta GetRecords es válido respecto a la validación de metadatos INPSIRE
    Dado el documento de capabilities del servicio
    Entonces un metadato resultante de una consulta GetRecords debe ser válido respecto a las normas de INSPIRE
