#language:es
Característica: Requisito 20
  Para cumplir con la Regulación de Metadatos de INSPIRE **INS MD** y con **ISO 19115**, al menos
  una de las siguientes tipos de fecha debe ser indicada: fecha de publicación, fecha de  la
  última revisión o fecha de creación.  De todas, es preferible la fecha de última revisión.
  Las fechas deben ser expresadas en conformidad con **INS MD**.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que existe al menos una fecha (asumiendo que existe una sección TemporalReference y que tanto DateOfCreation como DateOfPublication no están disponibles)
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
    Y la URI para el prefijo inspire_vs es http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    Cuando exista un nodo inspire_common:TemporalReference en la sección inspire_vs:ExtendedCapabilities
    Y no exista un nodo inspire_common:DateOfCreation en la sección inspire_common:TemporalReference
    Y no exista un nodo inspire_common:DateOfPublication en la sección inspire_common:TemporalReference
    Entonces existe un nodo inspire_common:DateOfLastRevision en la sección inspire_common:TemporalReference

  Escenario: Comprobar que existe al menos una fecha (asumiendo que existe una sección TemporalReference y que tanto DateOfPublication como DateOfLastRevision no están disponibles)
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
    Y la URI para el prefijo inspire_vs es http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    Cuando exista un nodo inspire_common:TemporalReference en la sección inspire_vs:ExtendedCapabilities
    Y no exista un nodo inspire_common:DateOfLastRevision en la sección inspire_common:TemporalReference
    Y no exista un nodo inspire_common:DateOfPublication en la sección inspire_common:TemporalReference
    Entonces existe un nodo inspire_common:DateOfCreation en la sección inspire_common:TemporalReference

  Escenario: Comprobar que existe al menos una fecha (asumiendo que existe una sección TemporalReference y que tanto DateOfCreation como DateOfLastRevision no están disponibles)
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo inspire_common es http://inspire.ec.europa.eu/schemas/common/1.0
    Y la URI para el prefijo inspire_vs es http://inspire.ec.europa.eu/schemas/inspire_vs/1.0
    Cuando exista un nodo inspire_common:TemporalReference en la sección inspire_vs:ExtendedCapabilities
    Y no existe un nodo inspire_common:DateOfCreation en la sección inspire_common:TemporalReference
    Y no existe un nodo inspire_common:DateOfLastRevision en la sección inspire_common:TemporalReference
    Entonces existe un nodo inspire_common:DateOfPublication en la sección inspire_common:TemporalReference
