package es.unizar.iaaa.ctl.parsers;

import static es.unizar.iaaa.ctl.CTL.parser;
import static es.unizar.iaaa.ctl.CTL.request;
import static es.unizar.iaaa.ctl.CTL.validator;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

import es.unizar.iaaa.ctl.model.XMLValidatingParser;
import es.unizar.iaaa.ctl.parsers.XMLValidatingParserImpl;

public class XMLValidatingParserImplTest {

	private XMLValidatingParserImpl VALIDATOR; 
	
	@Before
	public void setup() {
		VALIDATOR = (XMLValidatingParserImpl) validator(XMLValidatingParserImpl.class).
		schema("classpath:/schemas/w3/xml.xsd").
		schema("classpath:/schemas/w3/xlink.xsd").
		schema("classpath:/schemas/opengis/wms_capabilities_1_3_0.xsd");
	}
	
	@Test
	public void testValidatingParserOk() throws Exception {
		Node node = request("classpath:/schemas/xml/wms_server_ok.xml",
				parser(XMLValidatingParser.class).validate(false));
		assertTrue(VALIDATOR.validate(node));
	}

	@Test
	public void testValidatingParserWrong() throws Exception {
		Node node = request("classpath:/schemas/xml/wms_server_wrong.xml",
				parser(XMLValidatingParser.class).validate(false));
		assertFalse(VALIDATOR.validate(node));
	}
}
