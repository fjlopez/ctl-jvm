package es.unizar.iaaa.ctl.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunction;

public class DefaultContext implements Context {

	private Context parent;
	private Object item;
	private Map<String,String> boundPrefixes = new HashMap<String,String>();
	private Map<String,Object> boundVariables = new HashMap<String,Object>();
	
	/* (non-Javadoc)
	 * @see com.geoslab.te.xml.Context#setPrefix(java.lang.String, java.lang.String)
	 */
	@Override
	public void setPrefix(String prefix, String namespaceURI) {
		if (prefix == null || namespaceURI == null) {
			throw new IllegalArgumentException();
		}
		if (prefix.equals(XMLConstants.XML_NS_PREFIX) || prefix.equals(XMLConstants.XMLNS_ATTRIBUTE)
			|| namespaceURI.equals(XMLConstants.XMLNS_ATTRIBUTE_NS_URI) || namespaceURI.equals(XMLConstants.XML_NS_URI))
			return;
		boundPrefixes.put(prefix, namespaceURI);
	}
	
	/* (non-Javadoc)
	 * @see com.geoslab.te.xml.Context#unsetPrefix(java.lang.String)
	 */
	@Override
	public void unsetPrefix(String prefix) {
		if (prefix == null || prefix.equals(XMLConstants.XML_NS_PREFIX) || prefix.equals(XMLConstants.XMLNS_ATTRIBUTE)) {
			throw new IllegalArgumentException();
		}
		boundPrefixes.remove(prefix);
	}
	
	/* (non-Javadoc)
	 * @see com.geoslab.te.xml.Context#setVariable(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setVariable(String variableName, Object value) {
		if (variableName == null) {
			throw new IllegalArgumentException();
		}
		boundVariables.put(variableName, value);
	}
	
	/* (non-Javadoc)
	 * @see com.geoslab.te.xml.Context#unsetVariable(java.lang.String)
	 */
	@Override
	public void unsetVariable(String variableName) {
		if (variableName == null) {
			throw new IllegalArgumentException();
		}
		boundVariables.remove(variableName);
	}
	
	@Override
	public XPathFunction resolveFunction(QName functionName, int arity) {
		// null
		if (functionName == null) 
			throw new IllegalArgumentException();
		return null;
	}

	@Override
	public Object resolveVariable(QName variableName) {
		// null
		if (variableName == null) 
			throw new IllegalArgumentException();
		String key = variableName.toString();
		// bound variable that matches [{namespaceURI}localName] or [localName] (if namespaceURI is "")
		if (boundVariables.containsKey(key))
			return boundVariables.get(key);
		if (variableName.getNamespaceURI().equals(XMLConstants.NULL_NS_URI)) {
			// bound variable that matches [prefix:localName]
			if (!variableName.getPrefix().equals(XMLConstants.DEFAULT_NS_PREFIX)) {
				key = variableName.getPrefix()+":"+variableName.getLocalPart();
				if (boundVariables.containsKey(key))
					return boundVariables.get(key);
			}
		} else {
			// bound variable that matches [prefix(namespaceURI):localName] or [localName] (if prefix(namespaceURI) is "")
			String prefix = getPrefix(variableName.getNamespaceURI());
			if (!prefix.equals(XMLConstants.DEFAULT_NS_PREFIX)) {
				key = prefix + ":" +variableName.getLocalPart();
			} else {
				key = variableName.getLocalPart();
			}
			if (boundVariables.containsKey(key))
				return boundVariables.get(key);
		}
		// bound variable that matches [{prefix(namespaceURI)}localName]
		if (boundVariables.containsKey(key))
			return boundVariables.get(key);
		// unbound variable
		return parent !=null?parent.resolveVariable(variableName):null;
	}

	@Override
	public String getNamespaceURI(String prefix) {
		// null
		if (prefix == null) 
			throw new IllegalArgumentException();
		// XML_NS_PREFIX
		if (prefix.equals(XMLConstants.XML_NS_PREFIX))
			return XMLConstants.XML_NS_URI;
		// XMLNS_ATTRIBUTE
		if (prefix.equals(XMLConstants.XMLNS_ATTRIBUTE))
			return XMLConstants.XMLNS_ATTRIBUTE_NS_URI;
		// DEFAULT_NS_PREFIX or bound prefix
		if (boundPrefixes.containsKey(prefix)) {
			return boundPrefixes.get(prefix);
		} 
		// unbound prefix
		return parent!=null?parent.getNamespaceURI(prefix):XMLConstants.NULL_NS_URI;
	}

	@Override
	public String getPrefix(String namespaceURI) {
		// null
		if (namespaceURI == null) 
			throw new IllegalArgumentException();
		// XML_NS_URI
		if (namespaceURI.equals(XMLConstants.XML_NS_URI))
			return XMLConstants.XML_NS_PREFIX;
		// XMLNS_ATTRIBUTE_NS_URI
		if (namespaceURI.equals(XMLConstants.XMLNS_ATTRIBUTE_NS_URI))
			return XMLConstants.XMLNS_ATTRIBUTE;
		// bound namespace
		if (boundPrefixes.containsValue(namespaceURI)) {
			for(Entry<String,String> e : boundPrefixes.entrySet()) {
				if (namespaceURI.equals(e.getValue()))
					return e.getKey();
			}
		}
		// unbound namespace
		return parent!=null?parent.getPrefix(namespaceURI):null;
	}

	@Override
	public Iterator<String> getPrefixes(String namespaceURI) {
		// null
		if (namespaceURI == null) 
			throw new IllegalArgumentException();
		List<String> l = new ArrayList<String>();
		// XML_NS_URI
		if (namespaceURI.equals(XMLConstants.XML_NS_URI))
			l.add(XMLConstants.XML_NS_PREFIX);
		// XMLNS_ATTRIBUTE_NS_URI
		if (namespaceURI.equals(XMLConstants.XMLNS_ATTRIBUTE_NS_URI))
			l.add(XMLConstants.XMLNS_ATTRIBUTE);
		if (l.size()>0) {
			return Collections.unmodifiableList(l).iterator();
		}
		// bound namespace
		if (boundPrefixes.containsValue(namespaceURI)) {
			for(Entry<String,String> e : boundPrefixes.entrySet()) {
				if (namespaceURI.equals(e.getValue()))
					l.add(e.getKey());
			}
		}
		// parent namespaces
		if (parent != null) {
			Iterator<String> it = parent.getPrefixes(namespaceURI);
			while(it.hasNext()) {
				String nx = it.next();
				if (!l.contains(nx)) {
					l.add(nx);
				}
			}
		}
		return Collections.unmodifiableList(l).iterator();
	}

	/* (non-Javadoc)
	 * @see com.geoslab.te.xml.Context#setParent(com.geoslab.te.xml.DefaultContext)
	 */
	@Override
	public void setParent(Context parent) {
		this.parent = parent;
	}

	/* (non-Javadoc)
	 * @see com.geoslab.te.xml.Context#getParent()
	 */
	@Override
	public Context getParent() {
		return parent;
	}

	/* (non-Javadoc)
	 * @see com.geoslab.te.xml.Context#getContextItem()
	 */
	@Override
	public Object getContextItem() {
		return item == null && parent != null?parent.getContextItem():item;
	}
	
	/* (non-Javadoc)
	 * @see com.geoslab.te.xml.Context#setContextItem(java.lang.Object)
	 */
	@Override
	public void setContextItem(Object item) {
		this.item = item;
	}

}
