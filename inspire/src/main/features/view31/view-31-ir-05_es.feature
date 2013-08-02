#language:es
Característica: Requisito 5
  La operación descrita en el estándar de INSPIRE como *Get View Service Metadata* debe ser implementada a través
  de la operación *GetCapabilities*. 
  Los parámetros definidos en el estándar **ISO 19128** deben ser usados para transmitir información relevante con el
  propósito de obtener la respuesta esperada tal y como se describe en **INS NS, Anexo III, Sección 2.2** de la regulación
  de Servicios en red de INSPIRE.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar si al realizar una petición con los parámetros request=GetCapabilities y service=WMS se obtiene la descripción del servicio
    Dado el documento de capabilities del servicio
    Entonces una consulta con los parámetros request=GetCapabilities&service=WMS debe devolver la descripción del servicio
