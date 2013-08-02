package es.unizar.iaaa.ctl.model;

import java.net.URLConnection;

import org.w3c.dom.Node;

public interface Parser {

	Node parse(URLConnection conn) throws Exception;

}
