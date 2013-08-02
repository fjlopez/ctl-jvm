package es.unizar.iaaa.ctl.parsers;

import org.w3c.dom.Node;

import static es.unizar.iaaa.ctl.CTL.*;

public class HTTPParserResponse {

	public static Integer statusCode(Node response) {
		Double value = select(response, "/response/status/@code", Double.class);
		return value!=null?value.intValue():-1;
	}	

	public static String contentType(Node response) {
		return select(response, "/response/headers/header[@name='Content-Type']/text()", String.class);
	}	

}
