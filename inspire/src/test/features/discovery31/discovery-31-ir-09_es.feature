#language:es
Característica: Requisito 9
  De acuerdo con **INS NS, Annex II, Sección 3.1** el servicio debe soportar dos parámetros: 
  Language y Query.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Descubrimiento Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  para **servicios de descubrimiento** basados en el estándar internacional **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Una consulta GetRecords con anytext con valor comodín y el parámetro language con el valor del idioma por defecto debería devolver algunos metadatos
    Dado el documento de capabilities del servicio
    Entonces una consulta GetRecords con anytext puesta a * y language puesto al idioma por defecto debería devolver algún metadato
