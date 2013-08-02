package es.unizar.iaaa.ctl.hooks.inspire.view31;

import static es.unizar.iaaa.ctl.CTL.*;
import es.unizar.iaaa.ctl.model.XMLValidatingParser;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class IGNHooksEn {
	
	@Before
	public void beforeScenario() {
		set(request("http://www.ign.es/wms-inspire/ign-base?REQUEST=GetCapabilities&SERVICE=WMS",
				parser(XMLValidatingParser.class).validate(false)));
		//set("endpoint", "http://www.ign.es/wms-inspire/ign-base?REQUEST=GetCapabilities&SERVICE=WMS");
		set("INSPIRE.SpatialDataServicesClassification",request("classpath:/inspire/skos/INSPIRE_SpatialDataServicesClassification.skos.xml",
				parser(XMLValidatingParser.class).validate(false)));
		set("INSPIRE.Languages",request("classpath:/inspire/skos/INSPIRE_Languages.skos.xml",
				parser(XMLValidatingParser.class).validate(false)));
		set("serviceValidationLanguage", "en");
	}

	@After
	public void afterScenario() {
		unset();
	}
}
