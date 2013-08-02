package es.unizar.iaaa.ctl.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLParserErrorHandler implements ErrorHandler {

	@Override
	public void error(SAXParseException exception) throws SAXException {
		SAXException saxe=new SAXException(exception);
		throw saxe;
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		SAXException saxe=new SAXException(exception);
		throw saxe;
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		SAXException saxe=new SAXException(exception);
		throw saxe;
	}

}
