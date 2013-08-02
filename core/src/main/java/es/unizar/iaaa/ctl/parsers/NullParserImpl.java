package es.unizar.iaaa.ctl.parsers;

import java.io.InputStream;
import java.net.URLConnection;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

/**
 * Null parser.
 * 
 */
@Component("NullParser")
public class NullParserImpl extends AbstractParser {

	
	@Override
	protected Node parse(URLConnection conn, InputStream input)
			throws Exception {
		return null;
	}

}
