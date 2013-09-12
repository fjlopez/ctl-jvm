#language:es
Característica: Requisito 4
  Los parámetros de respuesta deben ser proporcionados a través de la descripción del servicio (Capabilities),
  tal y como se define en el Estándar WMS **ISO 19128, Sección 7.2.4**.  Estas características son obligatorias
  y definnidas cuando se configura un WMS.  La descripción de un servicio consiste en información del servicio,
  operaciones soportadas y valores para los parámetros.  La sección "extendedCapabilities" de usarse para 
  cumplir con los requisitos que INSPIRE establece para servicios de tipo visualización (ver sección 4.2.3.3.1).
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Asegurar que la respuesta a la petición GetCapabilities cumple con el XMLSchema esperado
    Dado el documento de capabilities del servicio
    Entonces la URI del esquema declarada es http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    Y la localización del esquema declarada es http://inspire.ec.europa.eu/schemas/inspire_vs/1.0/inspire_vs.xsd
    Y la descripción del servicio debe cumplir con el XMLSchema de INSPIRE
