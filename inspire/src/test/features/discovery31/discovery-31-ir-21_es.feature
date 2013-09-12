#language:es
Característica: Requisito 21
  La Tabla 5 identifica las propiedades consultables adicionales que no están soportadas por
  **CSW ISO AP**, pero que son requeridas por **INS NS**. Las expresiones X-Path y los
  tipos de datos están tomados de **INS MDTG**.
  !!!
  Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
  de Descubrimiento Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
  para **servicios de descubrimiento** basados en el estándar internacional **CSW ISO AP**
  (**OGC CSW 2.0.2 - ISO AP 1.0.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
  de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).

  Escenario: Comprobar que las propiedades consultables definidas en la Table 5 están listadas en el documento de Capabilities del servicio como AdditionalQueryables
    Dado el documento de capabilities del servicio
    Entonces Degree es uno de los valores de la sección Constraint con nombre AdditionalQueryables en la operación GetRecords
    Y ConditionApplyingToAccessAndUse es uno de los valores de la sección Constraint con nombre AdditionalQueryables en la operación GetRecords
    Y Lineage es uno de los valores de la sección Constraint con nombre AdditionalQueryables en la operación GetRecords
    Y ResponsiblePartyRole es uno de los valores de la sección Constraint con nombre AdditionalQueryables en la operación GetRecords
    Y SpecificationTitle es uno de los valores de la sección Constraint con nombre AdditionalQueryables en la operación GetRecords
    Y SpecificationDate es uno de los valores de la sección Constraint con nombre AdditionalQueryables en la operación GetRecords
    Y SpecificationDateType es uno de los valores de la sección Constraint con nombre AdditionalQueryables en la operación GetRecords
    Y AccessConstraints es uno de los valores de la sección Constraint con nombre AdditionalQueryables en la operación GetRecords
    Y OtherConstraints es uno de los valores de la sección Constraint con nombre AdditionalQueryables en la operación GetRecords
    Y Classification es uno de los valores de la sección Constraint con nombre AdditionalQueryables en la operación GetRecords
