#language:es
Característica: Requisito 8
  Independientemente del escenario escogido (Requisito 6 o Requisito 7), debe añadirse una sección de idioma
  en la sección **ExtendedCapabilities** del servicio para satisfacer los requisitos de idioma de la regulación
  de los Servicios en Red de INSPIRE **INS NS**.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Asegurar que existen las secciones SupportedLanguages y ResponseLanguage
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo inspire_vs es http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
    Entonces existe un nodo inspire_common:SupportedLanguages en la sección inspire_vs:ExtendedCapabilities
    Y existe un nodo inspire_common:ResponseLanguage en la sección inspire_vs:ExtendedCapabilities
