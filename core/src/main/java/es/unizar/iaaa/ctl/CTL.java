package es.unizar.iaaa.ctl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import es.unizar.iaaa.ctl.model.Parser;
import es.unizar.iaaa.ctl.model.Validator;
import es.unizar.iaaa.ctl.protocols.TextURLConnection;
import es.unizar.iaaa.ctl.xml.DefaultContext;
import es.unizar.iaaa.ctl.xml.XPathHelper;

public class CTL {

	private static final ApplicationContext ctx;

	private static final Logger logger = Logger.getLogger(CTL.class);

	private static ThreadLocal<Map<String,Object>> tprops = new ThreadLocal<Map<String,Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<String,Object>();
		}
	};

	static {
		ctx = new ClassPathXmlApplicationContext(
				"classpath:META-INF/spring/ctl-context.xml");
	}

	public static void boundPrefix(String prefix, String namespaceURI) {
		defaultContext().setPrefix(prefix, namespaceURI);
	}

	public static String boundNamespaceURI(String prefix) {
		return defaultContext().getNamespaceURI(prefix);
	}

	private static  DefaultContext defaultContext() {
		Map<String,Object> m = tprops.get();
		if (!m.containsKey("_defaultContext")) {
			m.put("_defaultContext", new DefaultContext());
		}
		return (DefaultContext) m.get("_defaultContext");
	}
	
	public static void set(Node node) {
		tprops.get().put("_context", node);
	}
	
	public static boolean isset(String name) {
		return tprops.get().get(name)!=null;
	}

	public static void set(String name, Object value) {
		if (name != null && (!name.startsWith("_"))) {
			tprops.get().put(name, value);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(String name, Class<T> clazz) {
		try {
			return (T) tprops.get().get(name);
		} catch (ClassCastException e) {
			return null;
		}
	}

	public static void unset() {
		tprops.remove();
	}
	
	public static Node context() {
		return (Node) tprops.get().get("_context");
	}

	/**
	 * The request element returns an XML representation of the response if the
	 * response is well-formed XML. Otherwise, null is returned.
	 * 
	 * @param url
	 *            the url
	 * @return an XML representation of the response
	 */
	public static Node request(String url) {
		return request(url, new Object[0]);
	}


	/**
	 * The request element returns an XML representation of the response if the
	 * response is well-formed XML. Otherwise, null is returned.
	 * 
	 * @param conn
	 *            the url connection
	 * @return an XML representation of the response
	 */
	public static Node request(URLConnection conn) {
		return request(conn, new Object[0]);
	}

	/**
	 * The request element returns an XML representation of the response if the
	 * response is well-formed XML. Otherwise, null is returned.
	 * 
	 * @param url
	 *            the url
	 * @return an XML representation of the response
	 */
	public static Node request(String url, Object... parameters) {
		Parser parser = null;
		for (Object o : parameters) {
			if (o instanceof Parser) {
				if (parser == null) {
					parser = (Parser) o;
				} else {
					logger.info("Multiple parsers. Only the first parser will be used.");
				}
			}
		}
		try {
			if (parser == null) {
				parser = ctx.getBean("defaultParser", Parser.class);
			}
			URLConnection conn = createConnection(url);

			return parser.parse(conn);
		} catch (Exception e) {
			logger.error("Request to [" + url + "] failed", e);
		}
		return null;
	}

	/**
	 * The request element returns an XML representation of the response if the
	 * response is well-formed XML. Otherwise, null is returned.
	 * 
	 * @param conn
	 *            the URL connection
	 * @return an XML representation of the response
	 */
	public static Node request(URLConnection conn, Object... parameters) {
		Parser parser = null;
		for (Object o : parameters) {
			if (o instanceof Parser) {
				if (parser == null) {
					parser = (Parser) o;
				} else {
					logger.info("Multiple parsers. Only the first parser will be used.");
				}
			}
		}
		try {
			if (parser == null) {
				parser = ctx.getBean("defaultParser", Parser.class);
			}

			return parser.parse(conn);
		} catch (Exception e) {
			logger.error("Request to [" + conn.getURL() + "] failed", e);
		}
		return null;
	}

	public static List<Node> select(Node node, String expr) {
		XPathHelper helper = ctx.getBean(XPathHelper.class);
		defaultContext().setContextItem(node);
		try {
			return helper.select(expr, defaultContext());
		} catch (XPathExpressionException e) {
			logger.error("Expression [" + expr + "] failed with node " + node,
					e);
			return new ArrayList<Node>();
		}
	}

	public static <T> T select(Node node, String expr, Class<T> clazz) {
		XPathHelper helper = ctx.getBean(XPathHelper.class);
		defaultContext().setContextItem(node);
		try {
			return helper.select(expr, defaultContext(), clazz);
		} catch (XPathExpressionException e) {
			logger.error("Expression [" + expr + "] failed with node " + node,
					e);
			return null;
		}
	}

	/**
	 * Create an URLConnection. The classpath protocol is supported.
	 * 
	 * @param url
	 *            a string url
	 * @return an URL Connection
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private static URLConnection createConnection(String url)
			throws MalformedURLException, IOException {
		if (url.startsWith("classpath:")) {
			
			return new URL(null, url, ctx.getBean("classpathProtocol",
					URLStreamHandler.class)).openConnection();
		}
		return new URL(url).openConnection();
	}

	/**
	 * Returns a parser with such name. If the parser is not found the test has
	 * an error.
	 * 
	 * @param name
	 *            the name
	 * @return a parser with such name or null.
	 */
	public static Parser parser(String name) {
		if (!ctx.containsBean(name)) {
			logger.error("Parser named [" + name + "] not found");
		}
		try {
			return ctx.getBean(name, Parser.class);
		} catch (Exception exception) {
			logger.error("Parser named [" + name + "] can be retrieved",
					exception);
		}
		return null;
	}

	/**
	 * Returns a parser with such name. If the parser is not found the test has
	 * an error.
	 * 
	 * @param name
	 *            the name
	 * @return a parser with such name or null.
	 */
	public static <T extends Parser> T parser(String name, Class<T> clazz) {
		if (!ctx.containsBean(name)) {
			logger.error("Parser named [" + name + "] not found");
		}
		try {
			return ctx.getBean(name, clazz);
		} catch (Exception exception) {
			logger.error("Parser named [" + name + "] can be retrieved",
					exception);
		}
		return null;
	}

	/**
	 * Returns a parser of such class. If the parser is not found the test has
	 * an error.
	 * 
	 * @return a parser with such name or null.
	 */
	public static <T extends Parser> T parser(Class<T> clazz) {
		try {
			return ctx.getBean(clazz);
		} catch (Exception exception) {
			logger.error("Parser named [" + clazz.getCanonicalName()
					+ "] can be retrieved", exception);
			return null;
		}
	}
	
	/**
	 * Returns a validator with such name. If the validator is not found the test has
	 * an error.
	 * 
	 * @param name
	 *            the name
	 * @return a parser with such name or null.
	 */
	public static Validator validator(String name) {
		if (!ctx.containsBean(name)) {
			logger.error("Validator named [" + name + "] not found");
		}
		try {
			return ctx.getBean(name, Validator.class);
		} catch (Exception exception) {
			logger.error("Validator named [" + name + "] can be retrieved",
					exception);
		}
		return null;
	}

	/**
	 * Returns a validator with such name. If the validator is not found the test has
	 * an error.
	 * 
	 * @param name
	 *            the name
	 * @return a parser with such name or null.
	 */
	public static <T extends Validator> T validator(String name, Class<T> clazz) {
		if (!ctx.containsBean(name)) {
			logger.error("Validator named [" + name + "] not found");
		}
		try {
			return ctx.getBean(name, clazz);
		} catch (Exception exception) {
			logger.error("Validator named [" + name + "] can be retrieved",
					exception);
		}
		return null;
	}

	/**
	 * Returns a validator of such class. If the validator is not found the test has
	 * an error.
	 * 
	 * @return a validator with such name or null.
	 */
	public static <T extends Validator> T validator(Class<T> clazz) {
		try {
			return ctx.getBean(clazz);
		} catch (Exception exception) {
			logger.error("Parser named [" + clazz.getCanonicalName()
					+ "] can be retrieved", exception);
			return null;
		}
	}
	
	public static URLConnection connection(URL url, String text, String encoding) {
		return new TextURLConnection(url, text, encoding);
	}

	///////////////////////////////////////////////////////////////////////////////
	// DEPRECATED CODE
	///////////////////////////////////////////////////////////////////////////////

	@Deprecated
	public static String createXML(Document xmlDoc) {
		StringWriter strWriter = null;
		XMLSerializer xmlSerializer = null;
		OutputFormat outFormat = null;
		try {
			xmlSerializer = new XMLSerializer();
			strWriter = new StringWriter();
			outFormat = new OutputFormat();

			// outFormat.setEncoding(XML_ENCODING);
			// outFormat.setVersion(XML_VERSION);
			outFormat.setIndenting(true);
			outFormat.setIndent(4);
			// outFormat.setOmitXMLDeclaration(true);

			xmlSerializer.setOutputCharStream(strWriter);
			xmlSerializer.setOutputFormat(outFormat);
			xmlSerializer.serialize(xmlDoc);
			strWriter.close();
		} catch (Exception ioEx) {
			System.out.println("Error : " + ioEx);
		}
		return strWriter.toString();
	}

	@Deprecated
	public static String createXML(Node node) {
		StringWriter strWriter = null;
		XMLSerializer xmlSerializer = null;
		OutputFormat outFormat = null;
		try {
			xmlSerializer = new XMLSerializer();
			strWriter = new StringWriter();
			outFormat = new OutputFormat();

			// outFormat.setEncoding(XML_ENCODING);
			// outFormat.setVersion(XML_VERSION);
			outFormat.setIndenting(true);
			outFormat.setIndent(4);
			// outFormat.setOmitXMLDeclaration(true);

			xmlSerializer.setOutputCharStream(strWriter);
			xmlSerializer.setOutputFormat(outFormat);
			xmlSerializer.serialize((Element) node);
			strWriter.close();
		} catch (Exception ioEx) {
			System.out.println("Error : " + ioEx);
		}
		return strWriter.toString();
	}

	/**
	 * 
	 */
	@Deprecated
	public static Node requestPost(String url, String query) {
		query = query.trim();
		HttpClient client = new HttpClient();
		HttpMethodBase http = null;
		http = new PostMethod(url);
		String contentType = "";
		InputStream response = null;
		StringRequestEntity sre = null;
		try {
			sre = new StringRequestEntity(query, "application/xml", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			System.out.println(e1);
		}
		((PostMethod) http).setRequestEntity(sre);
		((PostMethod) http).addRequestHeader("charset", "UTF-8");

		if (System.getProperty("http.proxyHost") != null) {

			String proxyHost = System.getProperty("http.proxyHost");
			int proxyPort = Integer.parseInt(System
					.getProperty("http.proxyPort"));
			client.getHostConfiguration().setProxy(proxyHost, proxyPort);
			// Comprobamos si está http.proxyUserName
			if (System.getProperty("http.proxyUserName") != null) {
				String proxyUsername = System.getProperty("http.proxyUserName");
				String proxyPassword = System.getProperty("http.proxyPassword");
				// Asumimos que la autenticacion es BASIC
				client.getState().setProxyCredentials(
						AuthScope.ANY,
						new UsernamePasswordCredentials(proxyUsername,
								proxyPassword));
				// Autentificación preventiva. LÍNEA NECESARIA TAN SOLO PARA LOS
				// WPS (Web
				// Proccesing Services)
				client.getParams().setAuthenticationPreemptive(true);
			}
		}
		try {
			client.executeMethod(http);
		} catch (HttpException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		
		try {
			return createNodeFromString(http.getResponseBodyAsStream());
		} catch (Exception e) {
			// return http;
			return null;
		}

	}
	
	@Deprecated
	public static Node createNodeFromString(InputStream s){
		try {
			InputSource in = new InputSource(s);
			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			domFactory.setNamespaceAware(true);
			DocumentBuilder builder = null;
			builder = domFactory.newDocumentBuilder();
			return builder.parse(in);
		} catch (Exception e) {
			// return http;
			return null;
		}
	}
}
