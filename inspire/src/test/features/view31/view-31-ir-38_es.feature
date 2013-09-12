#language:es
Característica: Requisito 38
  Para hacer posible el emparejamiento entre organizaciones/espacio de códigos e identificadores/códigos
  deben utilizarse los elementos **ISO 19128** (AuthorityURL e Identifier).  El nombre de la autoridad y la
  URL deben ser definidos en un elemento AuthorityURL separado, el cual debe ser definido una vez y 
  heredado por sus capas hijas.  Los Identificadores no son heredables.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que cada Capa tiene un Identificador y que cada Identificador tiene una Autoridad declarada
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo wms es http://www.opengis.net/wms
    Entonces existe un nodo wms:Identifier en cada sección wms:Layer
    Y cada una de las Authoridades usadas ha sido declarada sólamente una vez
    Y los pares identifier/authority son únicos
