#language:es
Característica: Requisito 51
  El parámetro REQUEST definido en **ISO 19128, Sección 6.9.2** es obligatorio.  Para invocar
  la operación *GetMap*, el valor "GetMap" debe utilizarse para cumplir con el estándar **ISO 19128**.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar si el parámetro REQUEST con valor "GetMap" es aceptado y que se recibe una excepción cuando no se especifica
    Dado el documento de capabilities del servicio
    Entonces una petición con request=GetMap debe devolver una imagen
    Y una petición sin request debe devolver una excepción
