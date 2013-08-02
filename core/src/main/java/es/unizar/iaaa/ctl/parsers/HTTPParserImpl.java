package es.unizar.iaaa.ctl.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import org.javatuples.Pair;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.unizar.iaaa.ctl.model.HTTPParser;
import es.unizar.iaaa.ctl.model.Parser;

/**
 * Implementation of the HTTPParser. This version support the use of wilcards
 * (*) and regex in MIME types.
 * TODO test multimime
 * @author Francisco J. Lopez-Pellicer
 * 
 */
@Component("HTTPParser")
public class HTTPParserImpl extends AbstractParser implements HTTPParser {

	private Map<Integer, List<Pair<Pattern, Parser>>> parsers = new HashMap<Integer, List<Pair<Pattern, Parser>>>();

	@Override
	protected Node parse(URLConnection conn, InputStream input) throws Exception {
		String mime = conn.getContentType();
		boolean multipart = (mime != null && mime.startsWith("multipart"));

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		Element root = doc.createElement(multipart ? "multipart-response"
				: "response");

		if (conn.getHeaderFieldKey(0) == null) {
			String status_line = conn.getHeaderField(0);
			String status_array[] = status_line.split("\\s");
			Element status = doc.createElement("status");
			if (status_array.length > 0) {
				status.setAttribute("protocol", status_array[0]);
			}
			if (status_array.length > 1) {
				status.setAttribute("code", status_array[1]);
			}
			if (status_array.length > 2) {
				status.appendChild(doc.createTextNode(status_array[2]));
			}
			root.appendChild(status);
		}

		appendHeaders(conn, root);

		Transformer t = TransformerFactory.newInstance().newTransformer();

		if (multipart) {
			String mime2 = mime + ";";
			int start = mime2.indexOf("boundary=") + 9;
			char endchar = ';';
			if (mime2.charAt(start) == '"') {
				start++;
				endchar = '"';
			}
			int end = mime2.indexOf(endchar, start);
			String boundary = mime2.substring(start, end);
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			File temp = createPartFile(in, boundary);
			temp.delete();
			String line = in.readLine();
			int num = 1;
			while (!line.endsWith("--")) {
				String contentType = "text/plain";
				Element part = doc.createElement("part");
				part.setAttribute("num", Integer.toString(num));
				Element headers = doc.createElement("headers");
				line = in.readLine();
				while (line.length() > 0) {
					Element header = doc.createElement("header");
					int colon = line.indexOf(":");
					String name = line.substring(0, colon);
					String value = line.substring(colon + 1).trim();
					if (name.toLowerCase().equals("content-type")) {
						contentType = value;
					}
					header.setAttribute("name", name);
					header.appendChild(doc.createTextNode(value));
					headers.appendChild(header);
					line = in.readLine();
				}
				part.appendChild(headers);
				temp = createPartFile(in, boundary);
				URLConnection pc = temp.toURI().toURL().openConnection();
				pc.setRequestProperty("Content-type", mime);
				Parser parser = selectParser(num, contentType);
				if (parser != null) {
					Node content = parser.parse(pc);
					t.transform(new DOMSource(content), new DOMResult(part));
					temp.delete();
					root.appendChild(part);
				}
				line = in.readLine();
				num++;
			}
		} else {
			Parser parser = selectParser(0, conn.getContentType());
			if (parser != null) {
				Node content = parser.parse(conn);
				Element tcontent = doc.createElement("content");
				root.appendChild(tcontent);
				t.transform(new DOMSource(content), new DOMResult(tcontent));
			}
		}
		doc.appendChild(root);
		return root;
	}

	private void appendHeaders(URLConnection conn, Element e) {
		Document doc = e.getOwnerDocument();
		Element headers = doc.createElement("headers");
		e.appendChild(headers);

		for (int i = 0;; i++) {
			String headerKey = conn.getHeaderFieldKey(i);
			String headerValue = conn.getHeaderField(i);
			if (headerKey == null) {
				if (headerValue == null)
					break;
			} else {
				Element header = doc.createElement("header");
				headers.appendChild(header);
				header.setAttribute("name", headerKey);
				header.appendChild(doc.createTextNode(headerValue));
			}
		}
	}

	private static File createPartFile(Reader in, String boundary)
			throws Exception {
		File temp = File.createTempFile("$te_", ".tmp");
		RandomAccessFile raf = new RandomAccessFile(temp, "rw");
		int qLen = boundary.length() + 2;
		int[] boundary_queue = new int[qLen];
		boundary_queue[0] = '-';
		boundary_queue[1] = '-';
		for (int i = 0; i < boundary.length(); i++) {
			boundary_queue[i + 2] = boundary.charAt(i);
		}
		int[] queue = new int[qLen];
		for (int i = 0; i < qLen; i++) {
			queue[i] = in.read();
			if (queue[i] == -1) {
				break;
			}
		}
		int qPos = 0;
		while (!queueEquals(queue, qPos, qLen, boundary_queue)) {
			raf.write(queue[qPos]);
			queue[qPos] = in.read();
			if (queue[qPos] == -1) {
				raf.close();
				throw new Exception(
						"Error in multipart stream. End of stream reached and with no closing boundary delimiter line");
			}
			qPos = (qPos + 1) % qLen;
		}
		raf.close();
		return temp;
	}

	private Parser selectParser(int num, String mime) {
		if (mime == null || !parsers.containsKey(num))
			return null;
		List<Pair<Pattern, Parser>> list = parsers.get(num);
		String[] parts = mime.split(";\\s*");
		for (Pair<Pattern, Parser> p : list) {
			if (!p.getValue0().matcher(parts[0]).matches())
				continue;
			return p.getValue1();
		}
		return null;
	}

	@Override
	public HTTPParser parse(String mimeType, Parser parser) {
		if (!parsers.containsKey(0)) {
			parsers.put(0, new ArrayList<Pair<Pattern, Parser>>());
		}
		parsers.get(0).add(
				new Pair<Pattern, Parser>(Pattern.compile(mimeType.replaceAll(
						"\\*", "\\.\\*")), parser));
		return this;
	}

	@Override
	public HTTPParser parse(int part, String mimeType, Parser parser) {
		if (part < 1) {
			throw new IllegalArgumentException(
					"Part in multipart parsers must be >=1");
		}
		if (!parsers.containsKey(part)) {
			parsers.put(part, new ArrayList<Pair<Pattern, Parser>>());
		}
		parsers.get(part).add(
				new Pair<Pattern, Parser>(Pattern.compile(mimeType.replaceAll(
						"\\*", "\\.\\*")), parser));
		return this;
	}

	private static boolean queueEquals(int[] queue, int qPos, int qLen,
			int[] value) {
		for (int i = 0; i < qLen; i++) {
			if (queue[(i + qPos) % qLen] != value[i]) {
				return false;
			}
		}
		return true;
	}
}
