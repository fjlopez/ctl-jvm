package es.unizar.iaaa.ctl.protocols;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * classpath: protocol URL connection
 * @author Francisco J. Lopez-Pellicer
 *
 */
public class TextURLConnection extends URLConnection {
	
	private InputStream is;
	private String text;
	private String encoding;
	
	public TextURLConnection(URL url, String text, String encoding) {
		super(url);
		this.text = text;
		this.encoding = encoding;
	}

	@Override
	public void connect() throws IOException {
		if (!connected) {
			is = new ByteArrayInputStream(text.getBytes(encoding));
			connected = true;
		}
	}
	
	@Override
	public String getContentEncoding() {
		return encoding;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if(!connected) {
			connect();
		}
		return is;
	}
}
