#language: es
Característica: Requisito 57
  El parámetro obligatorio FORMAT indica el formato deseado para el mapa.  En **INS NS, Annex III, Part B, Section 2** el formato
  de imagen indica que al menos uno entre “image/png” o “image/gif” debe ser soportado y por lo tanto indicado en la operación
  *GetCapabilities*.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Esquema del escenario: La operación GetCapabilties debe soportar los formatos deseados
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo wms es http://www.opengis.net/wms
    Cuando se mira la lista de valores en <path>
    Entonces <format> está presente cuando el nodo raíz es <root>
    Y una petición sin format debe devolver una excepción

    Ejemplos: 
      | root                 | path                           | format    |
      | wms:WMS_Capabilities | //wms:GetMap/wms:Format/text() | image/png |
      | wms:WMS_Capabilities | //wms:GetMap/wms:Format/text() | image/gif |
