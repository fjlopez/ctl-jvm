package es.unizar.iaaa.ctl.model;

import org.w3c.dom.Node;

/**
 * A validator of XML.
 * @author Francisco J. Lopez-Pellicer
 *
 */
public interface Validator {

	Validator ignoreErrors(boolean ignore);
	
	Validator ignoreWarnings(boolean ignore);

	Validator schema(String url);

	Validator dtd(String url);

	boolean isIgnoreErrors();

	boolean isIgnoreWarnings();

	Validator validate(boolean validate);

	boolean validate(Node node) throws Exception;

	boolean validate(Node node, String systemId) throws Exception;
}
