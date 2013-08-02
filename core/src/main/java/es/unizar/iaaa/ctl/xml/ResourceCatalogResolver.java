package es.unizar.iaaa.ctl.xml;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.xerces.dom.DOMInputImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.EntityResolver2;

@Component
public class ResourceCatalogResolver implements EntityResolver2,
		LSResourceResolver {

	private static final Pattern PROTOCOL_DETECTOR = Pattern.compile("^[a-z][a-z0-9+\\-\\.]*:", Pattern.CASE_INSENSITIVE);
	
	private static final Logger logger = Logger
			.getLogger(ResourceCatalogResolver.class);

	@Autowired
	private ApplicationContext ctx;

	@Override
	public InputSource getExternalSubset(String name, String baseURI)
			throws SAXException, IOException {
		if ("xs:schema".equals(name)) {
			return resolveEntity(name, "classpath:com/geoslab/te/dtd/XMLSchema.dtd");
		}
        if (logger.isDebugEnabled()) {
    		logger.info("External subset with root name [" + name
					+ "] not provided to document [" + baseURI + "]" );
        }
		return null;
	}

	@Override
	public InputSource resolveEntity(String name, String publicId,
			String baseURI, String systemId) throws SAXException, IOException {
		return resolveEntity(publicId, computeAbsoluteURI(baseURI, systemId));
	}

	private String computeAbsoluteURI(String baseURI, String systemId)
			throws IOException {
		String response = systemId;
			if (baseURI != null
					&& systemId!=null && !PROTOCOL_DETECTOR.matcher(systemId).find()) {
				try {
					response = ctx.getResource(baseURI).createRelative(systemId).getURL().toString();
				} catch (IOException ioException) {
					logger.error("Although systemId:["+systemId+"] is relative, we cannot build an absolute URI with baseURI:[" + baseURI+"]", ioException);
					throw ioException;
				}
			}
		if (logger.isDebugEnabled()) {
			logger.debug("baseURI:[" + baseURI
				+ "] and systemId:[" + systemId + "] -> location:["+response+"]" );
		}
		return response;
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {
		InputSource is = null;
		Resource res = ctx.getResource(systemId);
		try {
			is = new InputSource(res.getInputStream());
			is.setPublicId(publicId);
			is.setSystemId(systemId);
		} catch (IOException ioException) {
			logger.error("Can't create a fresh stream to publicId:[" + publicId
					+ "] and systemID:[" + systemId+"]");
			throw ioException;
		}
		return is;
	}

	@Override
	public LSInput resolveResource(String type, String namespaceURI,
			String publicId, String systemId, String baseURI) {
		try {
			String location = computeAbsoluteURI(baseURI, systemId);
			if (location == null) {
				return null;
			}
			if (!location.equals(systemId)) {
				logger.info("Resolving systemId:[" + systemId
						+ "] to [" + location+"]");
			} else {
				logger.info("Resolved systemId:[" + location+"]");
			}
			Resource res = ctx.getResource(location);
			return new DOMInputImpl(publicId, location, location, res.getInputStream(), null);
		} catch (IOException ioException) {
			logger.error("Can't create a fresh stream to publicId:[" + publicId
					+ "] and systemID:[" + systemId+"]", ioException);
			return null;
		}
	}

}
