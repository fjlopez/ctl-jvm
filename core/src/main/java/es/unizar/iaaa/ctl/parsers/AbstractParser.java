package es.unizar.iaaa.ctl.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import es.unizar.iaaa.ctl.model.Parser;


public abstract class AbstractParser implements Parser {

	public static Logger log = Logger.getLogger(AbstractParser.class);
	
	@Override
	public Node parse(URLConnection conn) throws Exception {
		if (conn instanceof HttpURLConnection) {
			return doParseHttp((HttpURLConnection)conn);
		}
		return doParse(conn);
	}

	private Node doParse(URLConnection conn) throws Exception, IOException {
		IOException processException = null;
		InputStream input = null;
		try {
			input = conn.getInputStream();
			return parse(conn, input);
		} catch (IOException e) {
			processException = e;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					if (processException != null) {
						throw processException;
					} else {
						throw e;
					}
				}
			}
			if (processException != null) {
				throw processException;
			}
		}
		return null;
	}

	private Node doParseHttp(final HttpURLConnection conn) throws Exception, IOException {
		IOException processException = null;
		InputStream input = null;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				log.info("Starting to disconnect "+conn.getURL());
				conn.disconnect();
				log.info("Disconnected "+conn.getURL());
			}
			
		}, 30000); // FIXME Hardcoded timeout limit
		try {
			input = conn.getInputStream();
			return parse(conn, input);
		} catch (IOException e) {
			processException = e;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					if (processException != null) {
						throw processException;
					} else {
						throw e;
					}
				}
			}
			if (processException != null) {
				throw processException;
			}
		}
		return null;
	}

	abstract protected Node parse(URLConnection conn, InputStream input) throws Exception;
}
