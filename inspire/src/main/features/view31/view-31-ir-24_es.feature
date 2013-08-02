#language:es
Característica: Requisito 24
  Los metadatos concernientes a las condiciones de acceso y uso deben ser registrados en el elemento <wms:Fees>
  del documento Capabilities.  Si no existen condiciones aplicables al acceso o uso del recurso, debe utilizarse
  "no conditions apply". Si las condiciones son desconocidas debe utilizarse "conditions unknown".
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar el campo Fees
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo wms es http://www.opengis.net/wms
    Entonces existe un nodo wms:Fees en la sección wms:Service
