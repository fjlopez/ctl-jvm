#language:es
Característica: Requisito 55
  El parámetro obligatorio BBOX permite a un cliente pedir un rectángulo particular.  El valor del
  parámetro BBOX en una petición *GetMap* debe ser una lista de números reales separados por coma en
  la forma de "minx,miny,maxx,maxy".  Estos valores especifican los valores X mínimo, Y mínimo, X máximo
  e Y máximo.  Las unidades deben figurar en la petición ordenadas y en dirección de incremento de
  los ejes X e Y tal y como se definen en el Sistema de Referencia. Los cuatro valores de la caja 
  envolvente indican los límites exteriores de la región requerida.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que se lanza una excepción cuando no se utiliza bbox
    Dado el documento de capabilities del servicio
    Entonces una petición sin bbox debe devolver una excepción
