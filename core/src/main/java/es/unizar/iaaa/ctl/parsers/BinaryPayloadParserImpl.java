package es.unizar.iaaa.ctl.parsers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.net.URLConnection;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Reads a response message and produces a String representation of its content.
 * 
 */
@Component("BinaryPayloadParser")
@Scope("prototype")
public class BinaryPayloadParserImpl extends AbstractParser {

	// TODO Configurable document builder
	private DocumentBuilder builder;
	
	public BinaryPayloadParserImpl() throws ParserConfigurationException {
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	}
	
	@Override
	protected Node parse(URLConnection conn, InputStream input)
			throws Exception {
        byte[] bytes = parse(input);
        Document doc = builder.newDocument();
        Element root = doc.createElement("payload");
        root.appendChild(doc.createTextNode(DatatypeConverter.printBase64Binary(bytes)));
        doc.appendChild(root);
        return root;
	}

	private byte[] parse(InputStream input) throws IOException,
			UnsupportedEncodingException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
        int numread = input.read(buf);
        baos.write(buf, 0, numread);
        while (numread >= 0) {
            numread = input.read(buf);
            if (numread > 0) {
                baos.write(buf, 0, numread);
            }
        }
		return baos.toByteArray();
	}
}
