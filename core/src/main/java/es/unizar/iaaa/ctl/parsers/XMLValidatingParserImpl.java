package es.unizar.iaaa.ctl.parsers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.DocumentLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import static org.springframework.util.xml.XmlValidationModeDetector.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import es.unizar.iaaa.ctl.model.XMLValidatingParser;
import es.unizar.iaaa.ctl.xml.ResourceCatalogResolver;
import es.unizar.iaaa.ctl.xml.SaxErrorHandlerImpl;


/**
 * Default XML Validating Parser. Returns an XML representation of the content of an InputStream or null.
 * @author Francisco J. Lopez Pellicer
 */
@Component("XMLValidatingParser")
@Scope("prototype")
public class XMLValidatingParserImpl extends AbstractParser implements XMLValidatingParser {

	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private ResourceCatalogResolver rcr;

	@Autowired
	private DocumentLoader documentLoader;
	
	// Factory settings
	// TODO Add default schemas and DTDs
	private List<Resource> defaultSchemas = new ArrayList<Resource>();
	private List<Resource> defaultDtds = new ArrayList<Resource>();

	// Request settings
	// TODO Add request schemas, DTDs, ignore errors and ignore warnings
	private List<Resource> requestDtds = new ArrayList<Resource>();
	private List<Resource> requestSchemas = new ArrayList<Resource>();
	private boolean ignoreErrors = false;
	private boolean ignoreWarnings = true;
	private boolean validate = true;
	
	
	private static final Logger logger = Logger
			.getLogger(XMLValidatingParserImpl.class);

	@Override
	protected Node parse(URLConnection conn, InputStream xml) throws Exception {
		// Configure environment
		ArrayList<Resource> schemas = new ArrayList<Resource>();
		ArrayList<Resource> dtds = new ArrayList<Resource>();
		if (defaultSchemas != null) {
			schemas.addAll(defaultSchemas);
		}
		if (requestSchemas != null) {
			schemas.addAll(requestSchemas);
		}
		if (defaultDtds != null) {
			dtds.addAll(defaultDtds);
		}
		if (requestDtds != null) {
			dtds.addAll(requestDtds);
		}
		
        InputSource in = new InputSource(xml);
        in.setSystemId(conn.getURL().toString());
		SaxErrorHandlerImpl eh = new SaxErrorHandlerImpl("Parsing", ignoreErrors, ignoreWarnings);
		
        Document doc = documentLoader.loadDocument(in, rcr, eh, validate?VALIDATION_XSD:VALIDATION_DTD, true);
        
		if (doc != null && validate) {
			eh.setRole("Validation");
			validateSchemas(doc, conn.getURL().toString(), schemas, eh);
			validateDTDs(doc, conn.getURL().toString(), dtds, eh);
			int error_count = eh.getErrorCount();
			int warning_count = eh.getWarningCount();
			printErrors(error_count, warning_count);
			if (error_count > 0 && !ignoreErrors) {
				doc = null;
			}
			if (warning_count > 0 && !ignoreWarnings) {
				doc = null;
			}
		}
		return doc != null?doc.getFirstChild():null;
	}

	private void printErrors(int error_count,
			int warning_count) {
		if (error_count > 0 || warning_count > 0) {
			StringBuffer msg = new StringBuffer();
			if (error_count > 0) {
				msg.append(error_count).append(" validation error")
						.append(error_count == 1 ? "" : "s");
			}
			if (warning_count > 0) {
				if (msg.length() > 0)
					msg.append(" and ");
				msg.append(warning_count).append(" warning")
						.append(warning_count == 1 ? "" : "s");
			}
			msg.append(" detected.");
			logger.info(msg);
		}
	}

	private void validateSchemas(Node doc, String systemId, ArrayList<Resource> schemas,
			ErrorHandler eh) throws Exception {
		List<StreamSource> schemaSources = new ArrayList<StreamSource>();
		Exception processException = null;
		try {
			for (int i = 0; i < schemas.size(); i++) {
				schemaSources.add(new StreamSource(schemas.get(i)
						.getInputStream()));
			}
			SchemaFactory sf = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			sf.setResourceResolver(rcr);
			try {
				sf.setFeature(
						"http://apache.org/xml/features/validation/schema-full-checking",
						false);
			} catch (SAXNotRecognizedException e) {
				logger.error("Can't set schema-full-checking feature", e);
			} catch (SAXNotSupportedException e) {
				logger.error("Can't set schema-full-checking feature", e);
			}
			Schema schema = null;
			if (schemas.size()>0) {
				schema = sf.newSchema(schemaSources.toArray(new Source[0]));
			} else {
				schema = sf.newSchema();
			}
			Validator validator = schema.newValidator();
			validator.setErrorHandler(eh);
			validator.setResourceResolver(rcr);
			Source is = new DOMSource(doc);
			is.setSystemId(systemId);
			validator.validate(is);
		} catch (Exception anyException) {
			processException = anyException;
		} finally {
			closeResources(schemas, processException);
		}
	}

	private void closeResources(ArrayList<Resource> resources,
			Exception processException) throws Exception {
		for (Resource r : resources) {
			if (r.isOpen()) {
				try {
					r.getInputStream().close();
				} catch (IOException ioException) {
					if (processException == null) {
						processException = ioException;
					}
				}
			}
		}
		if (processException != null) {
			throw processException;
		}
	}

	private void validateDTDs(Node doc, String systemId, ArrayList<Resource> dtds,
			ErrorHandler eh) throws Exception {
		Exception processException = null;
		try {
			for (Resource dtd : dtds) {
				StringBuffer transform = new StringBuffer();
				transform
						.append("<xsl:transform xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"2.0\">");
				transform.append("<xsl:output doctype-system=\"");
				transform.append(IOUtils.toString(dtd.getInputStream()));
				transform.append("\"/>");
				transform.append("<xsl:template match=\"/\">");
				transform.append("<xsl:copy-of select=\"*\"/>");
				transform.append("</xsl:template>");
				transform.append("</xsl:transform>");
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer t = tf
						.newTransformer(new StreamSource(new CharArrayReader(
								transform.toString().toCharArray())));
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				Source source = new DOMSource(doc);
				source.setSystemId(systemId);  // TODO  Test this
				t.transform(source, new StreamResult(baos));
				
				InputSource in = new InputSource(new ByteArrayInputStream(baos.toByteArray()));
				in.setSystemId(systemId); // TODO  Test this
		        documentLoader.loadDocument(in, null, eh, VALIDATION_DTD, true);
			}
		} catch (Exception anyException) {
			processException = anyException;
		} finally {
			closeResources(dtds, processException);
		}
	}

	@Override
	public boolean isIgnoreErrors() {
		return ignoreErrors;
	}

	public void setIgnoreErrors(boolean ignoreErrors) {
		this.ignoreErrors = ignoreErrors;
	}

	@Override
	public boolean isIgnoreWarnings() {
		return ignoreWarnings;
	}

	public void setIgnoreWarnings(boolean ignoreWarnings) {
		this.ignoreWarnings = ignoreWarnings;
	}

	@Override
	public XMLValidatingParser ignoreErrors(boolean ignore) {
		setIgnoreErrors(ignore);
		return this;
	}

	@Override
	public XMLValidatingParser ignoreWarnings(boolean ignore) {
		setIgnoreErrors(ignore);
		return this;
	}

	@Override
	public XMLValidatingParser schema(String url) {
		requestSchemas.add(ctx.getResource(url));
		return this;
	}

	@Override
	public XMLValidatingParser dtd(String url) {
		requestDtds.add(ctx.getResource(url));
		return this;
	}

	@Override
	public XMLValidatingParser validate(boolean validate) {
		this.validate = validate;
		return this;
	}

	@Override
	public boolean validate(Node node) throws Exception {
		return validate(node, null);
	}

	@Override
	public boolean validate(Node node, String systemId) throws Exception {
		// Configure environment
		ArrayList<Resource> schemas = new ArrayList<Resource>();
		ArrayList<Resource> dtds = new ArrayList<Resource>();
		if (defaultSchemas != null) {
			schemas.addAll(defaultSchemas);
		}
		if (requestSchemas != null) {
			schemas.addAll(requestSchemas);
		}
		if (defaultDtds != null) {
			dtds.addAll(defaultDtds);
		}
		if (requestDtds != null) {
			dtds.addAll(requestDtds);
		}
		
		SaxErrorHandlerImpl eh = new SaxErrorHandlerImpl("Validation", ignoreErrors, ignoreWarnings);
		
		if (node != null && validate) {
			validateSchemas(node, systemId, schemas, eh);
			validateDTDs(node, systemId, dtds, eh);
			int error_count = eh.getErrorCount();
			int warning_count = eh.getWarningCount();
			printErrors(error_count, warning_count);
			if (error_count > 0 && !ignoreErrors) {
				node = null;
			}
			if (warning_count > 0 && !ignoreWarnings) {
				node = null;
			}
		}
		return node != null;
	}
}
