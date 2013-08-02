#language: es
Característica: Requisito 3
  Las operaciones *GetCapabilities* y *GetMap* descritas en el estándar **ISO 19128** deben estar implementadas.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Asegurarse que las operaciones GetCapabilities y GetMap están declaradas
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo wms es http://www.opengis.net/wms
    Entonces existe un nodo wms:GetCapabilities en la sección wms:Request
    Y existe un nodo wms:GetMap en la sección wms:Request
    Y una petición GetMap debe ser respondida satisfactoriamente
