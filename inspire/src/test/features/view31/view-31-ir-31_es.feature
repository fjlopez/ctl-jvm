#language:es
Característica: Requisito 31
  La información acerca de la operación *GetMap* debe incluirse en el elemento <wms:GetMap>. O bien el formato
  PNG o el formato GIF (sin compresión LZW) con transparencia se deben soportar en el servicio de visualización
  **INS NS, Annex III, Part B**.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar si los formatos PNG o GIF están soportados
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo wms es http://www.opengis.net/wms
    Entonces existe un nodo wms:GetMap en la sección wms:Request
    Y existe un nodo wms:Format en la sección wms:GetMap
    Y el valor del nodo wms:Format en la sección wms:GetMap debe estar entre
      | image/png |
      | image/gif |
