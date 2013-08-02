package es.unizar.iaaa.ctl.protocols;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.core.io.ClassPathResource;

import es.unizar.iaaa.ctl.CTL;

/**
 * classpath: protocol URL connection
 * @author Francisco J. Lopez-Pellicer
 *
 */
public class ClasspathURLConnection extends URLConnection {
	
	private InputStream is;
	
	protected ClasspathURLConnection(URL url) {
		super(url);
	}

	@Override
	public void connect() throws IOException {
		if (!connected) {
			// Lookup CTL class
			Class<?> clazz = null;
			StackTraceElement ste[] = new Exception().getStackTrace();
			for(int i=0; i< ste.length; i++) {
				if (ste[i].getClassName().equals(CTL.class.getCanonicalName())) {
					try {
						clazz = Class.forName(ste[i+1].getClassName());
					} catch (ClassNotFoundException e) {
						throw new FileNotFoundException(getURL().toExternalForm() + " cannot be located because is relative to "+ste[i+1].getClassName()+" that cannot be locate too");
					}
					break;
				}
			}
			String path = getURL().getPath();
			if (clazz == null) {
				is =  new ClassPathResource(path).getInputStream();
			} else  {
				is =  new ClassPathResource(path, clazz).getInputStream();
			}
			if (is == null) {
				throw new FileNotFoundException(getURL().toExternalForm() + " don't exists or is not accessible");
			}
			connected = true;
		}
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if(!connected) {
			connect();
		}
		return is;
	}
}
