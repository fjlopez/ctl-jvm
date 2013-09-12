package es.unizar.iaaa.ctl.tests.inspire;

import static es.unizar.iaaa.ctl.CTL.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import es.unizar.iaaa.ctl.model.XMLValidatingParser;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hook {
	
	@Before
	public void beforeScenario() throws IOException {
		new File("test.properties");
		Properties p = new Properties();
		InputStream is = new FileInputStream(new File("test.properties"));
		p.load(is);
		is.close();
		set(request(p.getProperty("ctl.sut"),
				parser(XMLValidatingParser.class).validate(false)));
		set("INSPIRE.SpatialDataServicesClassification",request(p.getProperty("ctl.inspire.classification"),
				parser(XMLValidatingParser.class).validate(false)));
		set("INSPIRE.Languages",request(p.getProperty("ctl.inspire.languages"),
				parser(XMLValidatingParser.class).validate(false)));
		set("serviceValidationLanguage", p.getProperty("ctl.language"));
	}

	@After
	public void afterScenario() {
		unset();
	}
}
