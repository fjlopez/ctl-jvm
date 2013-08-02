package es.unizar.iaaa.ctl.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import es.unizar.iaaa.ctl.model.CDataParser;

/**
 * Reads a response message and produces a String representation of its content.
 * 
 */
@Component("CDataParser")
@Scope("prototype")
public class CDataParserImpl extends AbstractParser implements CDataParser {

	// TODO Configurable charset
	private String charsetName = Charset.defaultCharset().name();
	
	// TODO Configurable document builder
	private DocumentBuilder builder;
	
	public CDataParserImpl() throws ParserConfigurationException {
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	}
	
	@Override
	protected Node parse(URLConnection conn, InputStream input)
			throws Exception {
        StringBuffer sb = parse(input);
        Document doc = builder.newDocument();		
        return doc.createCDATASection(sb.toString());
	}

	private StringBuffer parse(InputStream input) throws IOException,
			UnsupportedEncodingException {
		byte[] buf = new byte[1024];
        int numread = input.read(buf);
        StringBuffer sb = new StringBuffer(numread);
        sb.append(new String(buf, 0, numread, charsetName));
        while (numread >= 0) {
            numread = input.read(buf);
            if (numread > 0) {
                sb.append(new String(buf, 0, numread, charsetName));
            }
        }
		return sb;
	}
}
