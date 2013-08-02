package es.unizar.iaaa.ctl.xml;

import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunction;

public class DefaultContextProvider implements ContextProvider {

	private Context root = new DefaultContext();
	@Override
	public XPathFunction resolveFunction(QName functionName, int arity) {
		return root.resolveFunction(functionName, arity);
	}
	@Override
	public Object resolveVariable(QName variableName) {
		return root.resolveVariable(variableName);
	}
	@Override
	public String getNamespaceURI(String prefix) {
		return root.getNamespaceURI(prefix);
	}
	@Override
	public String getPrefix(String namespaceURI) {
		return root.getPrefix(namespaceURI);
	}
	@Override
	public Iterator<String> getPrefixes(String namespaceURI) {
		return root.getPrefixes(namespaceURI);
	}

	/* (non-Javadoc)
	 * @see com.geoslab.te.xml.ContextProvider#push()
	 */
	@Override
	public void push() {
		DefaultContext ctx = new DefaultContext();
		ctx.setParent(root);
		root = ctx;
	}
	
	/* (non-Javadoc)
	 * @see com.geoslab.te.xml.ContextProvider#pop()
	 */
	@Override
	public void pop() {
		Context old = root;
		root = root.getParent();
		old.setParent(null);
	}
	@Override
	public void setPrefix(String prefix, String namespaceURI) {
		root.setPrefix(prefix, namespaceURI);
	}
	@Override
	public void unsetPrefix(String prefix) {
		root.unsetPrefix(prefix);
	}
	@Override
	public void setVariable(String variableName, Object value) {
		root.setVariable(variableName, value);
	}
	@Override
	public void unsetVariable(String variableName) {
		root.unsetVariable(variableName);
	}
	@Override
	public void setParent(Context parent) {
		root.setParent(parent);
	}
	@Override
	public Context getParent() {
		return root.getParent();
	}
	@Override
	public Object getContextItem() {
		return root.getContextItem();
	}
	@Override
	public void setContextItem(Object item) {
		root.setContextItem(item);
	}
}
