package es.unizar.iaaa.ctl.xml;

import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;

public interface Context extends NamespaceContext, XPathVariableResolver, XPathFunctionResolver{

	public abstract void setPrefix(String prefix, String namespaceURI);

	public abstract void unsetPrefix(String prefix);

	public abstract void setVariable(String variableName, Object value);

	public abstract void unsetVariable(String variableName);

	public abstract void setParent(Context parent);

	public abstract Context getParent();

	public abstract Object getContextItem();

	public abstract void setContextItem(Object item);

	@Override
	public Iterator<String> getPrefixes(String namespaceURI);
}