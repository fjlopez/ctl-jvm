#language:es
Característica: Requisito 26
  El rango de valores para el rol en el campo ResponsibleParty debe cumplir con la Regulación de Metadatos de INSPIRE
  **INS MD, Part D6**. Éste rol debe ser emparejado con el elemento <wms:ContactPosition> de la sección <wms:ContactInformation>
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
  para **servicios de visualización** basados en el estándar internacional **ISO 19128**
  (**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar si cada uno de los Roles declarados son válidos
    Dado el documento de capabilities del servicio
    Y la URI para el prefijo wms es http://www.opengis.net/wms
    Cuando exista un nodo wms:ContactPosition en la sección wms:ContactInformation
    Entonces el valor cada uno de los nodos wms:ContactPosition de las secciones wms:ContactInformation debe estar entre
      | resourceProvider      |
      | custodian             |
      | owner                 |
      | user                  |
      | distributor           |
      | originator            |
      | pointOfContact        |
      | principalInvestigator |
      | processor             |
      | publisher             |
      | author                |
