#language:es
Característica: Requisito 42
  Debe ser definido un estilo <inspire_common:DEFAULT> por cada tema, tal y como está definido en la sección 
  "Portrayal" del **INS DS, Article 14**.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comporbar que cada capa tiene <inspire_common:DEFAULT> como uno de sus estilos
    Dado el documento de capabilities del servicio
    Entonces cada Capa tiene <inspire_common:DEFAULT> como uno de sus estilos
