package es.unizar.iaaa.ctl.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class XPathHelper {
	/**
	 * Select returns a {@List<Node>} that can be empty.
	 * @param expr the expression
	 * @param context the context provider
	 * @return a list of nodes
	 * @throws XPathExpressionException
	 */
	public List<Node> select(String expr, final Context context) throws XPathExpressionException {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		xpath.setNamespaceContext(context);
		xpath.setXPathVariableResolver(context);
		XPathExpression xpe = xpath.compile(expr);
		NodeList ns = (NodeList) xpe.evaluate(context.getContextItem(), XPathConstants.NODESET);
		if (ns == null) {
			return Collections.unmodifiableList(new ArrayList<Node>());
		}
		List<Node> ln = new ArrayList<Node>(ns.getLength());
		for(int i = 0;i<ns.getLength();i++) {
			ln.add(ns.item(i));
		}
		return Collections.unmodifiableList(ln);
	}
	/**
	 * Select returns a {@List<Node>} that can be empty.
	 * @param expr the expression
	 * @param context the context provider
	 * @return a list of nodes
	 * @throws XPathExpressionException
	 */
	@SuppressWarnings("unchecked")
	public <T> T select(String expr, final Context context, Class<T> clazz) throws XPathExpressionException {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		xpath.setNamespaceContext(context);
		xpath.setXPathVariableResolver(context);
		XPathExpression xpe = xpath.compile(expr);
		if (Boolean.class.isAssignableFrom(clazz)) {
			return (T) xpe.evaluate(context.getContextItem(), XPathConstants.BOOLEAN);
		}
		if (Number.class.isAssignableFrom(clazz)) {
			return (T) xpe.evaluate(context.getContextItem(), XPathConstants.NUMBER);
		}
		if (String.class.isAssignableFrom(clazz)) {
			return (T) xpe.evaluate(context.getContextItem(), XPathConstants.STRING);
		}
		if (Node.class.isAssignableFrom(clazz)) {
			return (T) xpe.evaluate(context.getContextItem(), XPathConstants.NODE);
		}
		if (List.class.isAssignableFrom(clazz)) {
			NodeList ns = (NodeList) xpe.evaluate(context.getContextItem(), XPathConstants.NODESET);
			if (ns == null) {
				return (T) Collections.unmodifiableList(new ArrayList<Node>());
			}
			List<Node> ln = new ArrayList<Node>(ns.getLength());
			for(int i = 0;i<ns.getLength();i++) {
				ln.add(ns.item(i));
			}
			return (T) Collections.unmodifiableList(ln);
		}
		return null;
	}
}
