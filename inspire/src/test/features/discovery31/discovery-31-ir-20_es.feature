#language:es
Característica: Requisito 20
  La única propiedad consultable que no está definida arriba (Tabla 4), pero es necesaria
  para cumplir con **INS MDTG** es “Metadata language”. Esta propiedad es obligatoria para
  que un servicio de Descubrimiento INSPIRE pueda dar soporte al parámetro “Language” tal 
  y como se define en **INS NS, Anexo II, Parte B, Sección 3.1**.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Descubrimiento Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  para **servicios de descubrimiento** basados en el estándar internacional **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que el idioma (Language) está listado como propiedad consultable ISO (SupportedISOQueryable)
    Dado el documento de capabilities del servicio
    Entonces Language es uno de los valores de la sección Constraint con nombre SupportedISOQueryables en la operación GetRecords
