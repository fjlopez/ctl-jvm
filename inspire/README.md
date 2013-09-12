## INSPIRE test suites

They are based in the latest versions of the [Technical Guidance for the implementation](http://inspire.jrc.ec.europa.eu/index.cfm/pageid/5) documents.

These test suites are available for Discovery and View services in:
* English
* Spanish

### Ant

Create a file named `test.properties` with the following content

```properties
# The instance under test
ctl.sut=http://www.ign.es/wms-inspire/ign-base?REQUEST=GetCapabilitie&SERVICE=WMS

# Select the test
# The folder where the abstract tests are stored
ctl.features.path=src/test/features/view31/
# The tests to be executed (supports globbling patterns, e.g. * and ?)
ctl.features.glob=view-31-ir-0*_en.feature
# The language for extra messages. Note that feature language and this property must be the same
ctl.language=en

# Abstract test suite support
# Service classification. Required by the implementation of some tests
ctl.inspire.classification=classpath:/inspire/skos/INSPIRE_SpatialDataServicesClassification.skos.xml
# Languages supported in INSPIRE. Required by the implementation of some tests
ctl.inspire.languages=classpath:/inspire/skos/INSPIRE_Languages.skos.xml
```

Then, simply run:

```sh
ant download
ant runcukes
```

This runs Cucumber features using the Command Line Interface (CLI) runner and generates in ``target`` folder:
* A HTML report in ``cucumber-html-report``
* A JSON dump of the results (``cucumber-junit-report.json``)
* A Junit report in XML (``cucumber-junit-report.xml``) 

