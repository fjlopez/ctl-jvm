package es.unizar.iaaa.ctl.protocols;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import org.springframework.stereotype.Component;

/**
 * classpath: protocol URL handler
 * @author Francisco J. Lopez-Pellicer
 *
 */
@Component("classpathProtocol")
public class ClasspathURLStreamHandler extends URLStreamHandler {

	public static final String PROTOCOL = "classpath";
	
	@Override
	protected URLConnection openConnection(URL spec) throws IOException {
		return new ClasspathURLConnection(spec);
	}
}
