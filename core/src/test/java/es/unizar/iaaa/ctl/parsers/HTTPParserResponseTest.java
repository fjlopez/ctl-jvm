package es.unizar.iaaa.ctl.parsers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.w3c.dom.Node;

import es.unizar.iaaa.ctl.model.HTTPParser;
import static es.unizar.iaaa.ctl.CTL.*;
import static es.unizar.iaaa.ctl.parsers.HTTPParserResponse.*;

public class HTTPParserResponseTest {

	@Test
	public void testStatusCode() {
		Node result = request("http://www.google.es", parser(HTTPParser.class));
		assertNotNull(statusCode(result));
		assertEquals(200, (int)statusCode(result));
	}

	@Test
	public void testContentType() {
		Node result = request("http://www.google.es", parser(HTTPParser.class));
		assertNotNull(contentType(result));
		assertTrue(contentType(result).startsWith("text/html"));
	}

}
