package es.unizar.iaaa.ctl.tests.inspire;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Pattern;

import static es.unizar.iaaa.ctl.parsers.HTTPParserResponse.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.xerces.parsers.SAXParser;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import es.unizar.iaaa.ctl.model.HTTPParser;
import es.unizar.iaaa.ctl.model.XMLValidatingParser;
import es.unizar.iaaa.ctl.xml.XMLParserErrorHandler;

import static org.junit.Assert.assertTrue;

import static es.unizar.iaaa.ctl.CTL.*;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dada;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import cucumber.runtime.PassVerdictException;

public class TestSteps {

	ResourceBundle rb=null;
	
	@Dado("^el documento de capabilities del servicio$")
	@Given("^the service's capabilities document$")
	public void servicesDocument(){
		String lang=get("serviceValidationLanguage",String.class);
		rb=ResourceBundle.getBundle(this.getClass().getCanonicalName(), new Locale(lang));
	}
	
	
	@Dado("^la URI para el prefijo ([^\\s]+) es ([^\\s]+)$")
	@Given("^prefix ([^\\s]+) is ([^\\s]+)$")
	public void registerPrefix(String prefix, String namespaceURI)
			throws Throwable {
		boundPrefix(prefix, namespaceURI);
	}

	@Dada("^la descripción del servicio tiene declarados opcionalmente una lista de idiomas soportados$")
	@Given("^the service description has optionally a declaration of supported languages$")
	public void extractAdditionalSupportedLanguages() throws Throwable {
		List<String> supportedLanguages = new ArrayList<String>();
		for (Node n : select(context(),
				"//*[local-name()='SupportedLanguage']/*[local-name()='Language']/text()")) {
			supportedLanguages.add(n.getNodeValue());
		}
		set("supportedLanguages", supportedLanguages);
	}

	@Dada("^la descripción del servicio tiene declarado un idioma por defecto$")
	@Given("^the service description has a declaration of default language$")
	public void extractDefaultLanguage() throws Throwable {
		String defaultLanguage = select(context(),
				"//*[local-name()='DefaultLanguage']/*[local-name()='Language']",
				String.class);
		assertTrue("Service description must have a default language", defaultLanguage != null);
		assertTrue("Service description must have a default language", defaultLanguage.length() >= 0);
		set("defaultLanguage", defaultLanguage);
	}

	@Dado("^el idioma (\\w+) no soportado por el servicio$")
	@Given("^the language (\\w+) is unsupported by the service$")
	public void checkUnsupportedLanguage(String lang) throws Throwable {
		String mens=rb.getString("checkUnsupportedLanguage");
		String err=MessageFormat.format(mens, new Object[]{lang});
		assertTrue(err, !lang.equals(get("defaultLanguage",String.class)) && !get("supportedLanguages", List.class).contains(lang));
	}
	

	@Cuando("^se pide el documento GetCapabilities para cada uno de los idiomas soportados del servicio$")
	@When("^I request a GetCapabilities document for each supported language to the service$")
	public void requestGetCapabilitiesDocumentForEachSupportedLanguage()
			throws Throwable {
		@SuppressWarnings("unchecked")
		List<String> supportedLanguages = get("supportedLanguages", List.class);
		for (String lang : supportedLanguages) {
			requestForLanguage(lang);
		}
		requestForLanguage(get("defaultLanguage",String.class));
	}

	@Cuando("^se pide el documento GetCapabilities en el idioma por defecto del servicio$")
	@When("^I request a GetCapabilities document in the default language to the service$")
	public void requestGetCapabilitiesDocumentInTheDefaultLanguage()
			throws Throwable {
		requestForLanguage(get("defaultLanguage",String.class));
	}


	@Cuando("^se pide el documento GetCapabilities sin especificar ningún idioma al servicio$")
	@When("^I request a GetCapabilities document with no specific language to the service$")
	public void requestGetCapabilitiesDocumentWithNoSpecificLanguage()
			throws Throwable {
		String serviceType=select(context(), "//*[local-name()='ServiceIdentification']/*[local-name()='ServiceType']",String.class);
		if((serviceType==null)||(serviceType.trim().length()==0)){
			serviceType=select(context(), "//*[local-name()='Service']/*[local-name()='Name']",String.class);
		}
		requestGetCapabilitiesDocumentWithNoSpecificLanguage(serviceType);
	}
	
	public void requestGetCapabilitiesDocumentWithNoSpecificLanguage(String serviceType)
				throws Throwable {
		List<String> retrievedLanguages = new ArrayList<String>();
		String capabilitiesURL=getGetCapabilitiesURL(serviceType);
		String mens1=rb.getString("launchcapabilitiesQuery1");
		String err1=MessageFormat.format(mens1, new Object[]{} );
		assertTrue(err1,capabilitiesURL.trim().length()>0);
		if(!capabilitiesURL.contains("?")){
			capabilitiesURL=capabilitiesURL+"?";
		}
		Node requestedCapabilities = request(capabilitiesURL+"request=GetCapabilities&service="+serviceType,
				parser(XMLValidatingParser.class).validate(false));
		String mens2=rb.getString("requestGetCapabilitiesDocumentWithNoSpecificLanguage1");
		String err2=MessageFormat.format(mens2, new Object[]{capabilitiesURL+"request=GetCapabilities&service="+serviceType});
		assertTrue(err2, requestedCapabilities!=null);
		String rlang = select(requestedCapabilities,
				"//inspire_common:ResponseLanguage/inspire_common:Language",
				String.class);
		String mens3=rb.getString("requestGetCapabilitiesDocumentWithNoSpecificLanguage2");
		String err3=MessageFormat.format(mens3, new Object[]{capabilitiesURL+"request=GetCapabilities&service="+serviceType});
		assertTrue(err3,rlang != null);
		retrievedLanguages.add(rlang);
		set("retrievedLanguages", retrievedLanguages);
	}

	@SuppressWarnings("unchecked")
	@Entonces("^cada respuesta debe corresponderse con el idioma en el que fue solicitada$")
	@Then("^each response shall correspond to that requested language$")
	public void eachResponseShallCorrespondToThatRequestedLanguage()
			throws Throwable {
		List<Object> request=get("requestedLanguages", List.class);
		List<Object> response=get("retrievedLanguages", List.class);
		String mens=rb.getString("eachResponseShallCorrespondToThatRequestedLanguage");
		String err=MessageFormat.format(mens, new Object[]{request,response});
		assertTrue(err,request.equals(response));
	}

	@Cuando("^se pide el documento GetCapabilities en el idioma (\\w+) al servicio$")
	@When("^I request a GetCapabilities document with the language (\\w+) to the service$")
	public void requestGetCapabilitiesDocumentWithAnUnsupportedLanguage(String language) throws Throwable{
		String serviceType=select(context(), "//*[local-name()='ServiceIdentification']/*[local-name()='ServiceType']",String.class);
		if((serviceType==null)||(serviceType.trim().length()==0)){
			serviceType=select(context(), "//*[local-name()='Service']/*[local-name()='Name']",String.class);
		}
		requestGetCapabilitiesDocumentWithAnUnsupportedLanguage(language,serviceType);
	}
			
	public void requestGetCapabilitiesDocumentWithAnUnsupportedLanguage(
			String language, String serviceType) throws Throwable {
		List<String> retrievedLanguages = new ArrayList<String>();
		String capabilitiesURL=getGetCapabilitiesURL(serviceType);
		String mens1=rb.getString("launchcapabilitiesQuery1");
		String err1=MessageFormat.format(mens1, new Object[]{} );
		assertTrue(err1,capabilitiesURL.trim().length()>0);
		if(!capabilitiesURL.contains("?")){
			capabilitiesURL=capabilitiesURL+"?";
		}
		capabilitiesURL = capabilitiesURL+"SERVICE="+serviceType+ "&REQUEST=GetCapabilities" + "&LANGUAGE=" + language;
		Node requestedCapabilities = request(capabilitiesURL,
				parser(XMLValidatingParser.class).validate(false));
		String mens2=rb.getString("requestGetCapabilitiesDocumentWithAnUnsupportedLanguage1");
		String err2=MessageFormat.format(mens2, new Object[]{capabilitiesURL});
		assertTrue(err2, requestedCapabilities!=null);
		String rlang = select(requestedCapabilities,
				"//inspire_common:ResponseLanguage/inspire_common:Language",
				String.class);
		String mens3=rb.getString("requestGetCapabilitiesDocumentWithAnUnsupportedLanguage2");
		String err3=MessageFormat.format(mens3, new Object[]{capabilitiesURL});
		assertTrue(err3, rlang != null);
		retrievedLanguages.add(rlang);
		set("retrievedLanguages", retrievedLanguages);
	}

	@Entonces("^el idioma de la respuesta debe corresponderse con el idioma por defecto del servicio$")
	@Then("^the response shall correspond to the service default language$")
	public void responseShallCorrespondToServiceDefaultLanguage()
			throws Throwable {
		List<String> requestedLanguages = new ArrayList<String>();		
		requestedLanguages.add(get("defaultLanguage",String.class));
		set("requestedLanguages", requestedLanguages);
		List<?> retrievedLanguages=get("retrievedLanguages", List.class);
		String mens=rb.getString("responseShallCorrespondToServiceDefaultLanguage");
		String err=MessageFormat.format(mens, new Object[]{requestedLanguages,retrievedLanguages});
		assertTrue(err,requestedLanguages.equals(get("retrievedLanguages", List.class)));
	}

	@Entonces("^se mira la lista de valores en (.*)$")
	@When("^I look the list of values in (.*)$")
	public void getXPathExpression(String xpath) throws Throwable {
		set("nodes",select(context(), xpath));
	}

	@Entonces("^([^\\s]+) está presente cuando el nodo raíz es ([^\\s]+)$")
	@Then("^([^\\s]+) is present when the root node is ([^\\s]+)$")
	public void testPresenceWithGuard(String value, String guard)
			throws Throwable {
		String[] s = guard.split(":");
		if (s.length == 1) {
			if (!s[0].equals(context().getNodeName()))
				return;
			if (context().getNamespaceURI() != null)
				return;
		} else {
			if (!boundNamespaceURI(s[0]).equals(context().getNamespaceURI()))
				return;
			if (!s[1].equals(context().getNodeName()))
				return;
		}
		@SuppressWarnings("unchecked")
		List<Node> nodes = get("nodes", List.class);
		for (Node n : nodes) {
			if (value.equals(n.getNodeValue()))
				return;
		}
		String mens=rb.getString("testPresenceWithGuard");
		String err=MessageFormat.format(mens, new Object[]{value});
		throw new Exception(err);
	}

	@Entonces("^el nombre del nodo raíz es (.+)$")
	@Then("^the root node name is (.+)$")
	public void rootNodeName(String name) throws Throwable {
		String mens=rb.getString("rootNodeName");
		String err=MessageFormat.format(mens, new Object[]{name});
		assertTrue(err, name.equals(
					context().getNodeName()));
	}

	@Entonces("^la URI para el espacio de nombres del nodo raíz es (.+)")
	@Then("^the root node namespace URI is (.+)$")
	public void rootNodeNamespace(String namespaceURI) throws Throwable {
		String mens=rb.getString("rootNodeNamespace");
		String err=MessageFormat.format(mens, new Object[]{namespaceURI});
		assertTrue(err, namespaceURI.equals(context().getNamespaceURI()));
	}

	@Entonces("^el atributo (\\w+) en el nodo raíz tiene valor (.+)$")
	@Then("^the attribute (\\w+) in root node has value (.+)$")
	public void rootNodeAttribute(String attr, String value) throws Throwable {
		Node at = context().getAttributes().getNamedItem(attr);
		String mens1=rb.getString("rootNodeAttribute1");
		String err1=MessageFormat.format(mens1, new Object[]{attr});
		assertTrue(err1, at != null);
		String mens2=rb.getString("rootNodeAttribute2");
		String err2=MessageFormat.format(mens2, new Object[]{attr,value});
		assertTrue(err2, value.equals(at.getNodeValue()));
	}

	@Entonces("^el valor de \"([^\"]*)\" en \"([^\"]*)\" tiene valor \"([^\"]*)\"$")
	@Then("^the value of \"([^\"]*)\" in \"([^\"]*)\" has value \"([^\"]*)\"$")
	public void valuePresent(String tag, String parent, String value)
			throws Throwable {
		String v = select(context(), "//" + parent + "/" + tag + "/text()",
				String.class);
		assertTrue("Tag [" + parent + "\\" + tag + "] has value [" + value
				+ "]", value.equals(v));
	}

	@Cuando("^no exist[ae] un nodo (.+) en la sección (.+)$")
	@When("^there is not an? (.+) node in the (.+) section")
	public void thereIsNotANodeInAGivenSection(String node, String section)
			throws Throwable {
		if (testSectionNode(section, node)) {
			String mens=rb.getString("thenThereIsANodeInAGivenSection");
			String err=MessageFormat.format(mens, new Object[]{node, section});
			throw new PassVerdictException(err);
		}
	}

	@Cuando("^exista un nodo (.+) en la sección (.+)$")
	@When("^there exists an? (.+) node in the (.+) section$")
	public void whenThereIsANodeInAGivenSection(String node, String section)
			throws Throwable {
		if (!testSectionNode(section, node)) {
			String mens=rb.getString("thereIsNotANodeInAGivenSection");
			String err=MessageFormat.format(mens, new Object[]{node, section});
			throw new PassVerdictException(err);
		}
	}

		
	@Entonces("^existe un nodo (.+) en la sección (.+)$")
	@Then("^there is an? (.+) node in the (.+) section$")
	public void thenThereIsANodeInAGivenSection(String node, String section)
			throws Throwable {
		String mens=rb.getString("thereIsNotANodeInAGivenSection");
		String err=MessageFormat.format(mens, new Object[]{section,node});
		assertTrue(err, testSectionNode(section, node));
	}

	private boolean testSectionNode(String section, String node) {
		if (!node.contains(":")) {
			node = "*[local-name()='" + node + "']";
		}
		if (!section.contains(":")) {
			section = "*[local-name()='" + section + "']";
		}
		return select(context(), "//" + section + "/" + node).size() >0;
	}

	@Entonces("^existe un nodo (.+) en cada sección ([^\\s]+)$")
	@Then("^there is an? (.+) node in each (.+) section$")
	public void thereIsANodeInEachOfTheGivenSection(String node, String section)
			throws Throwable {
		if (!node.contains(":")) {
			node = "*[local-name()='" + node + "']";
		}
		if (!section.contains(":")) {
			section = "*[local-name()='" + section + "']";
		}
		List<Node> sections = select(context(), "//" + section);
			for (int i = 0; i < sections.size(); i++) {
				List<Node> nl2 = select(sections.get(i), node);
				String mens1=rb.getString("thereIsANodeInEachOfTheGivenSection");
				String err1=MessageFormat.format(mens1, new Object[]{section,String.valueOf(i+1),node,sections.get(i).getFirstChild().getNextSibling()
								.getFirstChild().getTextContent()});
				assertTrue(err1, nl2.size() > 0);
			}
	}

	@Entonces("^existe un atributo (.+) en cada sección ([^\\s]+)$")
	@Then("^there is an attribute (.+) in each (.+) section$")
	public void thereIsAnAttributeInEachOfTheGivenSection(String attribute, String section)
			throws Throwable {
		if (!attribute.contains(":")) {
			attribute = "*[local-name()='" + attribute + "']";
		}
		if (!section.contains(":")) {
			section = "*[local-name()='" + section + "']";
		}
		List<Node> sections = select(context(), "//" + section);
			for (int i = 0; i < sections.size(); i++) {
				String s = select(sections.get(i), "@"+attribute, String.class);
				String mens1=rb.getString("thereIsAnAttributeInEachOfTheGivenSection");
				String err1=MessageFormat.format(mens1, new Object[]{section,attribute});
				assertTrue(err1, s!=null&&s.trim().length()>0);
			}
	}
	
	@Entonces("^una petición GetMap debe ser respondida satisfactoriamente$")
	@Then("^a GetMap request is succesfully answered$")
	public void launchGetMapRequest() {
		launchGetMapRequest(null, null);
	}

	public void launchGetMapRequest(String paramName, String paramValue) {
		String baseRequest = "";
		String url = select(
				context(),
				"//*[local-name()='Capability']/*[local-name()='Request']/*[local-name()='GetMap']/*[local-name()='DCPType']/*[local-name()='HTTP']/*[local-name()='Get']/*[local-name()='OnlineResource']/@*[local-name()='href']",
				String.class);
		String floatingParam = "";
		if ((paramName != null) && (paramValue != null)) {
			floatingParam = paramName + "=" + paramValue;
			baseRequest = floatingParam;
		}

		if (!"request".equalsIgnoreCase(paramName)) {
			if (paramName == null) {
				baseRequest = "request=GetMap";
			} else {
				baseRequest = baseRequest + "&request=GetMap";
			}
		}

		if (!"width".equalsIgnoreCase(paramName)) {
			baseRequest = baseRequest + "&WIDTH=100";
		}

		if (!"height".equalsIgnoreCase(paramName)) {
			baseRequest = baseRequest + "&HEIGHT=100";
		}

		if (!"version".equalsIgnoreCase(paramName)) {
			String version = select(context(),
					"/*/@*[local-name()='version']", String.class);
			baseRequest = baseRequest + "&VERSION=" + version;
		}
		String format = "";
		if (!"format".equalsIgnoreCase(paramName)) {
			format = select(
					context(),
					"//*[local-name()='Capability']/*[local-name()='Request']/*[local-name()='GetMap']/*[local-name()='Format'][1]",
					String.class);
			baseRequest = baseRequest + "&FORMAT=" + format;
		} else {
			format = paramValue;
		}
		if (!"layers".equalsIgnoreCase(paramName)) {
			String layer = select(
					context(),
					//"//*[local-name()='Capability']/*[local-name()='Layer'][*[local-name()='Style']]/*[local-name()='Layer'][1]/*[local-name()='Name']",
					"//*[local-name()='Layer'][*[local-name()='Style']]/*[local-name()='Name']",
					String.class);
			baseRequest = baseRequest + "&LAYERS=" + layer;
		}
		Node parentWithBBox = null;
		if ("bbox".equalsIgnoreCase("bbox")) {
			String minx = select(
					context(),
					"//*[local-name()='Capability']/*[local-name()='Layer']/*[local-name()='Layer'][*[local-name()='Style']][1]/*[local-name()='BoundingBox'][1]/@*[local-name()='minx']",
					String.class);
			if (minx.equalsIgnoreCase("")) {
				List<Node> parent = select(
						context(),
						"//*[local-name()='Capability']/*[local-name()='Layer']/*[local-name()='Layer'][*[local-name()='Style']][1]");
				Node n = parent.get(0).getParentNode();
				if (n.getNodeName().equalsIgnoreCase("Layer")) {
					parentWithBBox = retrieveParentWithBBox(n);
					if (parentWithBBox == null) {
						throw new RuntimeException("Unable to find a suitable bbox for a layer");
					} else {
						minx = select(
								parentWithBBox,
								"*[local-name()='BoundingBox'][1]/@*[local-name()='minx']",
								String.class);
					}
				}
			}
			String miny = null;
			if (parentWithBBox == null) {
				miny = select(
						context(),
						"//*[local-name()='Capability']/*[local-name()='Layer']/*[local-name()='Layer'][*[local-name()='Style']][1]/*[local-name()='BoundingBox'][1]/@*[local-name()='miny']",
						String.class);
			} else {
				miny = select(
						parentWithBBox,
						"*[local-name()='BoundingBox'][1]/@*[local-name()='miny']",
						String.class);
			}
			String maxx = null;
			if (parentWithBBox == null) {
				maxx = select(
						context(),
						"//*[local-name()='Capability']/*[local-name()='Layer']/*[local-name()='Layer'][*[local-name()='Style']][1]/*[local-name()='BoundingBox'][1]/@*[local-name()='maxx']",
						String.class);
			} else {
				maxx = select(
						parentWithBBox,
						"*[local-name()='BoundingBox'][1]/@*[local-name()='maxx']",
						String.class);
			}
			String maxy = null;
			if (parentWithBBox == null) {
				maxy = select(
						context(),
						"//*[local-name()='Capability']/*[local-name()='Layer']/*[local-name()='Layer'][*[local-name()='Style']][1]/*[local-name()='BoundingBox'][1]/@*[local-name()='maxy']",
						String.class);
			} else {
				maxy = select(
						parentWithBBox,
						"*[local-name()='BoundingBox'][1]/@*[local-name()='maxy']",
						String.class);
			}
			baseRequest = baseRequest + "&BBOX=" + minx.trim() + ","
					+ miny.trim() + "," + maxx.trim() + "," + maxy.trim();
		}
		if (!"crs".equalsIgnoreCase(paramName)) {
			String crs = null;
			if (parentWithBBox == null) {
				crs = select(
						context(),
						"//*[local-name()='Capability']/*[local-name()='Layer']/*[local-name()='Layer'][*[local-name()='Style']][1]/*[local-name()='BoundingBox'][1]/@*[local-name()='CRS']",
						String.class);
			} else {
				crs = select(
						parentWithBBox,
						"*[local-name()='BoundingBox'][1]/@*[local-name()='CRS']",
						String.class);
			}
			baseRequest = baseRequest + "&CRS=" + crs;
		}
		if ("style".equalsIgnoreCase(paramName)) {
			String style = select(
					context(),
					"//*[local-name()='Capability']/*[local-name()='Layer'][*[local-name()='Style']]/*[local-name()='Layer'][1]/*[local-name()='Style']/*[local-name()='Name']",
					String.class);
			baseRequest = baseRequest + "&STYLE=" + style;
		}
		if (!url.contains("?")) {
			url = url + "?";
		}
		String contentType = contentType(request(url + "&" + baseRequest, parser(HTTPParser.class)));
		String mens=rb.getString("launchGetMapRequest");
		String err=MessageFormat.format(mens, new Object[]{url,baseRequest,contentType});
		assertTrue(err, contentType.toLowerCase().equals(format.toLowerCase()));
	}

	@Entonces("^la URI del esquema declarada es (.+)$")
	@Then("^schema URI is set to (.+)$")
	public void schemaURIIs(String schemaUri){
		NamedNodeMap nnm = context().getAttributes();
		Node n = nnm.getNamedItemNS(
				"http://www.w3.org/2001/XMLSchema-instance",
				"schemaLocation");
		String mens1=rb.getString("schemaURIIs1");
		String err1=MessageFormat.format(mens1, new Object[]{});
		assertTrue(err1, n != null);
		String schema = n.getNodeValue().trim();
		String mens2=rb.getString("schemaURIIs2");
		String err2=MessageFormat.format(mens2, new Object[]{schema, schemaUri});
		assertTrue(err2,schema.trim().startsWith(schemaUri));
	}
	
	@Entonces("^la localización del esquema declarada es (.+)$")
	@Then("^schema location points to (.+)$")
	public void schemaLocationIs(String schemaLocation){
		NamedNodeMap nnm = context().getAttributes();
		Node n = nnm.getNamedItemNS(
				"http://www.w3.org/2001/XMLSchema-instance",
				"schemaLocation");
		String mens1=rb.getString("schemaLocationIs1");
		String err1=MessageFormat.format(mens1, new Object[]{});
		assertTrue(err1, n != null);
		String schema = n.getNodeValue().trim();
		String mens2=rb.getString("schemaLocationIs2");
		String err2=MessageFormat.format(mens2, new Object[]{schema, schemaLocation});
		assertTrue(err2,schema.trim().endsWith(schemaLocation));
	}
	
	@Entonces("^la descripción del servicio debe cumplir con el XMLSchema de INSPIRE$")
	@Then("^the service description must comply with the INSPIRE XMLSchema$")
	public void checkAgainstXMLSCHEMA() {
		String serviceType=select(context(), "//*[local-name()='ServiceIdentification']/*[local-name()='ServiceType']",String.class);
		if((serviceType==null)||(serviceType.trim().length()==0)){
			serviceType=select(context(), "//*[local-name()='Service']/*[local-name()='Name']",String.class);
		}
		if(serviceType.equalsIgnoreCase("CSW")){
			checkCSWschema();
		}else if(serviceType.equalsIgnoreCase("WMS")){
			checkWMSWMSTschema(context());
		}
	}

	public void checkCSWschema() {
		NamedNodeMap nnm = context().getAttributes();
		if (nnm != null) {
			Node n = nnm.getNamedItemNS(
					"http://www.w3.org/2001/XMLSchema-instance",
					"schemaLocation");
			assertTrue("Unable to find schemaLocation", n!=null);

			String schema = n.getNodeValue().trim();

			String cswNS1 = Pattern.quote("http://www.opengis.net/cat/csw/2.0.2");
			String cswSL1 = Pattern
					.quote("http://schemas.opengis.net/csw/2.0.2/CSW-discovery.xsd");
			String cswNS2 = Pattern
					.quote("http://inspire.ec.europa.eu/schemas/inspire_ds/1.0");
			String cswSL2 = Pattern
					.quote("http://inspire.ec.europa.eu/schemas/inspire_ds/1.0/inspire_ds.xsd");
			boolean match = schema.matches(cswNS1 + "\\s+" + cswSL1 + "\\s+" + cswNS2 + "\\s+" + cswSL2);
			
			if(!match){
				match = schema.matches(cswNS2 + "\\s+" + cswSL2 + "\\s+" + cswNS1 + "\\s+" + cswSL1);
			}
			String mens1=rb.getString("checkCSWschema1");
			String err1=MessageFormat.format(mens1, new Object[]{});
			assertTrue(err1,match);
		}

		SAXParser parser = new SAXParser();
		try {
			// Turn schema validation on
			parser.setFeature("http://xml.org/sax/features/validation", true);
			parser.setFeature(
					"http://apache.org/xml/features/validation/schema", true);
			parser.setFeature(
					"http://apache.org/xml/features/validation/schema-full-checking",
					true);

		} catch (SAXException e) {
			String mens2=rb.getString("checkCSWschema2");
			String err2=MessageFormat.format(mens2, new Object[]{});
			throw new RuntimeException(err2);
		}
		String cap = createXML(context().getOwnerDocument());
		InputSource iss = null;
		try {
			iss = new InputSource(new ByteArrayInputStream(
					cap.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		parser.setErrorHandler(new XMLParserErrorHandler());
		try {
			parser.parse(iss);
		} catch (Exception e) {
			String mens3=rb.getString("checkCSWschema3");
			String err3=MessageFormat.format(mens3, new Object[]{e.getMessage()});
			throw new RuntimeException(err3);

		}
	}

	public void checkWMSWMSTschema(Node document) {
		NamedNodeMap nnm = document.getAttributes();
		if (nnm != null) {
			Node n = nnm.getNamedItemNS(
					"http://www.w3.org/2001/XMLSchema-instance",
					"schemaLocation");
			assertTrue("schemaLocation is present", n != null);
			if (n != null) {
				String schema = n.getNodeValue().trim();
				String wmsNS = Pattern
						.quote("http://inspire.ec.europa.eu/schemas/inspire_vs/1.0");
				String wmsSL = Pattern
						.quote("http://inspire.ec.europa.eu/schemas/inspire_vs/1.0/inspire_vs.xsd");
				boolean match = schema.matches(wmsNS + "\\s+" + wmsSL);
				if (!match) {
					String wmstNS1 = Pattern
							.quote("http://www.opengis.net/wmts/1.0");
					String wmstSL1 = Pattern
							.quote("http://schemas.opengis.net/wmts/1.0/wmtsGetCapabilities_response.xsd");
					String wmstNS2 = Pattern
							.quote("http://inspire.ec.europa.eu/schemas/inspire_vs_ows11/1.0");
					String wmstSL2 = Pattern
							.quote("http://inspire.ec.europa.eu/schemas/inspire_vs_ows11/1.0/inspire_vs_ows_11.xsd");
					match = schema.matches(wmstNS1 + "\\s+" + wmstSL1 + "\\s+"
							+ wmstNS2 + "\\s+" + wmstSL2);
				}
				String mens=rb.getString("checkWMSWMSTschema1");
				String err=MessageFormat.format(mens, new Object[]{});
				assertTrue(err,	match);
			}
		}
		if (document.getOwnerDocument().getDoctype() != null) {
			// comprobaciones v?a DTD
		}
		SAXParser parser = new SAXParser();
		try {
			// Turn schema validation on
			parser.setFeature("http://xml.org/sax/features/validation", true);
			parser.setFeature(
					"http://apache.org/xml/features/validation/schema", true);
			parser.setFeature(
					"http://apache.org/xml/features/validation/schema-full-checking",
					true);

		} catch (SAXException e) {
			throw new RuntimeException("error turning on validation");
		}
		String cap = createXML(document.getOwnerDocument());
		InputSource iss = null;
		try {
			iss = new InputSource(new ByteArrayInputStream(
					cap.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		parser.setErrorHandler(new XMLParserErrorHandler());
		try {
			parser.parse(iss);
		} catch (Exception e) {
			String mens=rb.getString("checkWMSWMSTschema2");
			String err=MessageFormat.format(mens, new Object[]{});
			throw new RuntimeException(err+"\n"+e.getMessage());

		}
	}

	@Entonces("^una consulta con los parámetros (.)+ debe devolver la descripción del servicio$")
	@Then("^a query with parameters (.+) should return a service description$")
	public void launchcapabilitiesQuery(String params) {
		String capabilitiesURL=select(context(),"//*[local-name()='Capability']/*[local-name()='Request']/*[local-name()='GetCapabilities']/*[local-name()='DCPType']/*[local-name()='HTTP']/*[local-name()='Get']/*[local-name()='OnlineResource']/@*[local-name()='href']",String.class);
		String mens1=rb.getString("launchcapabilitiesQuery1");
		String err1=MessageFormat.format(mens1, new Object[]{} );
		assertTrue(err1,capabilitiesURL.trim().length()>0);
		if(!capabilitiesURL.contains("?")){
			capabilitiesURL=capabilitiesURL+"?";
		}
		Node capabilities = request(capabilitiesURL + "?" + params,
				parser(XMLValidatingParser.class).validate(false));
		String mens2=rb.getString("launchcapabilitiesQuery2");
		String err2=MessageFormat.format(mens2, new Object[]{});
		assertTrue(err2, capabilities!=null);
		checkWMSWMSTschema(capabilities);

	}

	@Entonces("^la sección MetadataUrl debe contener una consulta getRecordById$")
	@Then("^MetadataUrl section should contain a getRecordById query$")
	public void metadataUrlSectionShouldContainAGetRecordByIdQuery() {
		String getRecById = select(
				context(),
				"//*[local-name()='ExtendedCapabilities']/*[local-name()='MetadataUrl']/*[local-name()='URL']",
				String.class);
		if ("".equals(getRecById)) {
			getRecById = null;
		}
		String mens=rb.getString("metadataUrlSectionShouldContainAGetRecordByIdQuery1");
		String err=MessageFormat.format(mens, new Object[]{});
		assertTrue(err,getRecById!=null);

		if (!getRecById.toLowerCase().contains("request=getrecordbyid")) {
			String mens2=rb.getString("metadataUrlSectionShouldContainAGetRecordByIdQuery2");
			String err2=MessageFormat.format(mens2, new Object[]{});
			throw new RuntimeException(err2);
		}
		if (!getRecById.toLowerCase().contains("id=")) {
			String mens3=rb.getString("metadataUrlSectionShouldContainAGetRecordByIdQuery3");
			String err3=MessageFormat.format(mens3, new Object[]{});
			throw new RuntimeException(err3);
		}
	}


	@Entonces("^el nodo MetadataUrl debe apuntar a una dirección válida$")
	@Then("^MetadataUrl reference should point to an existing location")
	public void metadataURLShouldPointToAnExistingLocation() {
		String getRecById = select(
				context(),
				"//*[local-name()='ExtendedCapabilities']/*[local-name()='MetadataUrl']/*[local-name()='URL']",
				String.class);
		if ("".equals(getRecById)) {
			getRecById = null;
		}
		String mens=rb.getString("metadataURLShouldPointToAnExistingLocation1");
		String err=MessageFormat.format(mens, new Object[]{});
		assertTrue(
				err,
				getRecById!=null);

		int respCode = statusCode(request(getRecById, parser(HTTPParser.class)));
		String mens2=rb.getString("metadataURLShouldPointToAnExistingLocation2");
		String err2=MessageFormat.format(mens2, new Object[]{respCode});
		assertTrue(err2,
				respCode == 200);

	}

	@Entonces("^debe existir un Servicio de Catálogo que sirva este metadato de servicio$")
	@Then("^there should be a Discovery Service Catalog serving its metadata$")
	public void there_should_be_a_Discovery_Service_Catalog_serving_its_metadata()
			throws Throwable {
		String mens=rb.getString("there_should_be_a_Discovery_Service_Catalog_serving_its_metadata");
		String err=MessageFormat.format(mens, new Object[]{});
		throw new RuntimeException(err);
	}

	@Entonces("^el valor del nodo (.+) en la sección (.+) debe ser '(.+)'$")
	@Then("^node (.+) in the (.+) section should be set to '(.+)'$")
	public void node_in_section_should_be_set_to(String node, String section,
			String value) {
		if (!node.contains(":")) {
			node = "*[local-name()='" + node + "']";
		}
		if (!section.contains(":")) {
			section = "*[local-name()='" + section + "']";
		}
		String result = select(context(), "//" + section + "//" + node
				+ "[text()='" + value + "']", String.class);
		String mens1=rb.getString("node_in_section_should_be_set_to1");
		String err1=MessageFormat.format(mens1, new Object[]{section,node});
		assertTrue(err1, result!=null);
		String mens2=rb.getString("node_in_section_should_be_set_to2");
		String err2=MessageFormat.format(mens2, new Object[]{section,node,value});
		assertTrue(err2,result.equals(value));
	}

	@Entonces("^un nodo (.+) en cada una de las secciones (.+) dentro de la sección (.+) debe estar entre$")
	@Then("^node (.+) in each (.+) section of (.+) node should be in$")
	public void node_in_each_section_should_be_in(String node,
			String subsection, String section, List<String> values) {
		List<String> newvalues = new ArrayList<String>();
		for (int i = 0; i < values.size(); i++) {
			newvalues.add(values.get(i).toLowerCase());
		}
		values = newvalues;
		if (!node.contains(":")) {
			node = "*[local-name()='" + node + "']";
		}
		if (!subsection.contains(":")) {
			subsection = "*[local-name()='" + subsection + "']";
		}
		if (!section.contains(":")) {
			section = "*[local-name()='" + section + "']";
		}

		List<Node> subsections = select(context(), "//" + section + "/"
				+ subsection);
		for (int i = 0; i < subsections.size(); i++) {
			boolean found = false;
			Node subsect = subsections.get(i);
			List<Node> nodes = select(subsect, node);
			for (int j = 0; (j < nodes.size() && !found); j++) {
				Node nod = nodes.get(j);
				String value = nod.getTextContent();
				if (values.contains(value.toLowerCase())) {
					found = true;
				}
			}
			String mens=rb.getString("node_in_each_section_should_be_in");
			String err=MessageFormat.format(mens, new Object[]{values, nodes});
			assertTrue(err, found);
		}
	}

	@Entonces("^el valor cada uno de los nodos (.+) de las secciones (.+) debe estar entre$")
	@Then("^each node (.+) in the (.+) section should be in$")
	public void each_node_in_section_should_be_in(String node, String section,
			List<String> values) {
		List<String> newvalues = new ArrayList<String>();
		for (int i = 0; i < values.size(); i++) {
			newvalues.add(values.get(i).toLowerCase());
		}
		values = newvalues;
		if (!node.contains(":")) {
			node = "*[local-name()='" + node + "']";
		}
		if (!section.contains(":")) {
			section = "*[local-name()='" + section + "']";
		}
		List<Node> nodes = select(context(), "//" + section);
		for (int i = 0; i < nodes.size(); i++) {
			String value = select(nodes.get(i), node, String.class);
			if (!values.contains(value.toLowerCase())) {
				String mens=rb.getString("each_node_in_section_should_be_in");
				String err=MessageFormat.format(mens, new Object[]{section,i,node,value, values});
				throw new RuntimeException(err);
			}
		}
	}

	@Entonces("^el valor del nodo (.+) en la sección (.+) debe estar entre$")
	@Then("^node (.+) in the (.+) section should be in$")
	public void node_in_section_should_be_in(String node, String section,
			List<String> values) {
		if (!node.contains(":")) {
			node = "*[local-name()='" + node + "']";
		}
		if (!section.contains(":")) {
			section = "*[local-name()='" + section + "']";
		}
		boolean found = false;
		Iterator<String> valuesIt = values.iterator();
		while ((valuesIt.hasNext()) && (!found)) {
			String value = valuesIt.next();
			String result = select(context(), "//" + section + "//" + node
					+ "[text()='" + value + "']", String.class);
			if ((result != null) && (result.equals(value))) {
				found = true;
			}
		}
		String mens=rb.getString("node_in_section_should_be_in");
		String err=MessageFormat.format(mens, new Object[]{section, node, values});
		assertTrue(err, found);
	}

	@Entonces("^cada (.+) en los (.+) de las secciones (.+) son enlaces válidos$")
	@Then("^each (.+) in the (.+) of (.+) sections is a valid link$")
	public void node_each_linkage_in_node_is_valid(String attributeName,
			String node, String sections) {
		if (!node.contains(":")) {
			node = "*[local-name()='" + node + "']";
		}
		if (!sections.contains(":")) {
			sections = "*[local-name()='" + sections + "']";
		}
		if (!attributeName.contains(":")) {
			attributeName = "*[local-name()='" + attributeName + "']";
		}
		List<Node> nl = select(context(), "//" + sections + "/" + node
				+ "/@" + attributeName);
		for (int i = 0; i < nl.size(); i++) {
			Node n = nl.get(i);
			String url = n.getNodeValue();
			int code = statusCode(request(url, parser(HTTPParser.class)));
			String mens=rb.getString("node_each_linkage_in_node_is_valid");
			String err=MessageFormat.format(mens, new Object[]{url});
			assertTrue(err, code == 200);
		}
	}

	@Entonces("^existe al menos una palabra clave proveniente de la \"Classification of Spatial data Services\"$")
	@Then("^there is at least one keyword from the INSPIRE \"Classification of Spatial data Services\" list$")
	public void check_keywords() {
		Node thesaurus = get("INSPIRE.SpatialDataServicesClassification", Node.class);
		List<Node> keywords = select(
				context(),
				"//*[local-name()='Service']/*[local-name()='KeywordList']/*[local-name()='Keyword']");
		boolean found = false;
		String mens1=rb.getString("check_keywords");
		String err1=MessageFormat.format(mens1, new Object[]{});
		assertTrue(err1,keywords.size() > 0);
		for (int i = 0; (i < keywords.size()) && (!found); i++) {
			String keyword = keywords.get(i).getFirstChild().getNodeValue();
			String exists = select(
					thesaurus,
					"/*[local-name()='RDF']/*[local-name()='Description']/*[local-name()='prefLabel'][text()='"
							+ keyword.toUpperCase() + "']", String.class);
			if ((exists != null) && (exists.trim().length() > 0)) {
				found = true;
			}
		}
		assertTrue(err1,found);
	}

	@Entonces("^existe un nodo (.+) en cada una de las secciones (.+) pertenecientes a la sección (.+)$")
	@Then("^there is an? (.+) node in each (.+) section of the (.+) node$")
	public void there_is_a_node_in_each_section_of_the_root_node(String node,
			String subSection, String section) {
		if (!node.contains(":")) {
			node = "*[local-name()='" + node + "']";
		}
		if (!section.contains(":")) {
			section = "*[local-name()='" + section + "']";
		}
		if (!subSection.contains(":")) {
			subSection = "*[local-name()='" + subSection + "']";
		}
		/*
		 * Object nl = select(context(), "//" + section + "/" + node); if (nl
		 * instanceof Collection<?>) { if (((Collection<?>) nl).size() == 0) {
		 * nl = null; } } else { nl = null; } assertThat("Node [" + section +
		 * "\\" + node + "] exists", nl, is(notNullValue()));
		 */
		List<Node> sections = select(context(), "//" + section + "/"
				+ subSection);
		try {
			for (int i = 0; i <= sections.size(); i++) {
				List<Node> nl2 = select(sections.get(i), node);
				String mens=rb.getString("there_is_a_node_in_each_section_of_the_root_node");
				String err=MessageFormat.format(mens, new Object[]{section,i,node,sections.get(i).getTextContent()});
				assertTrue(err, nl2 != null);
				assertTrue(err + sections.get(i).getTextContent(),
						nl2.size()>0);
			}

		} catch (Exception e) {
			// there are no sections
		}
	}

	@Entonces("^existe un nodo wms\\:BoundingBox en cada sección wms\\:Layer section para cada wms\\:CRS declarado$")
	@Then("^there is a wms\\:BoundingBox node in each wms\\:Layer section for each wms\\:CRS declared$")
	public void testBoundingBoxes() {
		// every Layers in the context() nodes are obtained
		List<Node> layers = select(context(), "//*[local-name()='Layer']");
		for (int i = 0; i < layers.size(); i++) {
			Node layer = layers.get(i);
			List<Node> crss = select(layer, "*[local-name()='CRS']");
			Iterator<Node> crsIt = crss.iterator();
			while (crsIt.hasNext()) {
				String crs = crsIt.next().getFirstChild().getNodeValue();
				List<Node> bbox = select(layer,
						"*[local-name()='BoundingBox'][@*[local-name()='CRS']='"
								+ crs + "']");
				if ((bbox.size() == 0)
						&& (layer.getParentNode().getNodeName().equals("Layer"))) {
					testBoundingBox(layer.getParentNode(), crs);
				} else if ((bbox.size() == 0)
						&& (!layer.getParentNode().getNodeName()
								.equals("Layer"))) {
					String title = select(layer, "*[local-name()='Title']",
							String.class);
					String mens=rb.getString("testBoundingBoxes");
					String err=MessageFormat.format(mens, new Object[]{title,crs});
					throw new RuntimeException(err);
				}
			}
		}
	}

	public void testBoundingBox(Node layer, String crs) {
		List<Node> bbox = select(layer,
				"*[local-name()='BoundingBox'][@*[local-name()='CRS']='" + crs
						+ "']");
		if ((bbox.size() == 0)
				&& (layer.getParentNode().getNodeName().equals("Layer"))) {
			testBoundingBox(layer.getParentNode(), crs);
		} else if ((bbox.size() == 0)
				&& (!layer.getParentNode().getNodeName().equals("Layer"))) {
			throw new RuntimeException(layer.getBaseURI()
					+ " does not contain a BoundingBox node for " + crs
					+ " reference system");
		}
	}

	@Entonces("^cada una de las Authoridades usadas ha sido declarada sólamente una vez$")
	@Then("^every used Authority has been previouly declared just once$")
	public void testDeclaredAuthorities() {
		List<Node> identifiers = select(context(),
				"//*[local-name()='Layer']/*[local-name()='Identifier']");
		for (int i = 0; i < identifiers.size(); i++) {
			Node identifier = identifiers.get(i);
			String authority = select(identifier,
					"@*[local-name()='authority']", String.class);
			String mens1=rb.getString("testDeclaredAuthorities1");
			String err1=MessageFormat.format(mens1, new Object[]{identifier.getTextContent()});
			assertTrue(err1, (authority != null) & (authority.trim().length() > 0));
			Node layer = identifier.getParentNode();
			List<Node> auth = select(layer,
					"*[local-name()='AuthorityURL'][@*[local-name()='name']='"
							+ authority + "']");
			if ((auth.size() == 0)
					&& (layer.getParentNode().getNodeName().equals("Layer"))) {
				testAuthorities(layer.getParentNode(), authority);
			} else if (auth.size() == 0) {
				String mens2=rb.getString("testDeclaredAuthorities2");
				String err2=MessageFormat.format(mens2, new Object[]{authority});
				throw new RuntimeException(err2);
			} else if (layer.getParentNode().getNodeName().equals("Layer")) {
				try {
					testAuthorities(layer.getParentNode(), authority);
				} catch (Throwable t) {
					// if a failure is thrown means that this authority haven't
					// been
					// declared in any upper Layer,
					// success
				}
				String mens3=rb.getString("testDeclaredAuthorities3");
				String err3=MessageFormat.format(mens3, new Object[]{authority});
				throw new RuntimeException(err3);
			}
		}
	}

	@Entonces("^los pares identifier/authority son únicos$")
	@Then("^pairs identifier/authority are unique$")
	public void testUniqueIdentifiers() {
		List<Node> identifiers = select(context(),
				"//*[local-name()='Identifier']");
		for (int i = 0; i < identifiers.size(); i++) {
			Node identifier = identifiers.get(i);
			String authority = select(identifier,
					"@*[local-name()='authority']", String.class);
			String value = select(identifier, "text()", String.class);
			List<Node> duplicated = select(context(),
					"//*[local-name()='Identifier'][@*[local-name()='authority']='"
							+ authority + "'][text()='" + value + "']");
			if (duplicated.size() > 1) {
				String mens=rb.getString("testUniqueIdentifiers");
				String err=MessageFormat.format(mens, new Object[]{value, authority});
				throw new RuntimeException(err);
			}
		}
	}

	@Entonces("^el valor de (.+) es único dentro de las secciones (.+)$")
	@Then("^(.+) value is unique within (.+) sections$")
	public void value_is_unique_in_a_context(String node, String section) {
		if (!node.contains(":")) {
			node = "*[local-name()='" + node + "']";
		}
		if (!section.contains(":")) {
			section = "*[local-name()='" + section + "']";
		}
		List<Node> nodes = select(context(), "//" + section + "/" + node);
		List<String> values = new LinkedList<String>();
		for (int i = 0; i < nodes.size(); i++) {
			String value = nodes.get(i).getTextContent();
			String mens=rb.getString("value_is_unique_in_a_context");
			String err=MessageFormat.format(mens, new Object[]{value});
			assertTrue(err, !values.contains(value.toLowerCase()));
			values.add(value.toLowerCase());
		}
	}

	private void testAuthorities(Node layer, String authority) {
		List<Node> auth = select(layer,
				"*[local-name()='AuthorityURL'][@*[local-name()='name']='"
						+ authority + "']");
		if ((auth.size() == 0)
				&& (layer.getParentNode().getNodeName().equals("Layer"))) {
			testAuthorities(layer.getParentNode(), authority);
		} else if (auth.size() == 0) {
			String mens1=rb.getString("testAuthorities1");
			String err1=MessageFormat.format(mens1, new Object[]{authority});
			throw new RuntimeException(err1);
		} else if (layer.getParentNode().getNodeName().equals("Layer")) {
			try {
				testAuthorities(layer.getParentNode(), authority);
			} catch (Throwable t) {
				// if a failure is thrown means that this authority haven't been
				// declared in any upper Layer,
				// success
			}
			String mens2=rb.getString("testAuthorities2");
			String err2=MessageFormat.format(mens2, new Object[]{authority});
			throw new RuntimeException(err2);
		}
	}

	@Entonces("^cada Capa tiene <inspire_common:DEFAULT> como uno de sus estilos$")
	@Then("^each Layer has an <inspire_common:DEFAULT> as one of its styles$")
	public void each_layer_has_a_default_style() {
		List<Node> layers = select(context(), "//*[local-name()='Layer']");
		for (int i = 0; i < layers.size(); i++) {
			Node layer = layers.get(i);
			String style = select(
					layer,
					"*[local-name()='Style']/*[local-name()='Name'][text()='inspire_common:DEFAULT']",
					String.class);
			if ((style == null) || (style.trim().length() == 0)) {
				if (layer.getParentNode().getNodeName().equals("Layer")) {
					try {
						testDefaultStyle(layer.getParentNode());
					} catch (Throwable e) {
						String mens1=rb.getString("each_layer_has_a_default_style1");
						String err1=MessageFormat.format(mens1,new Object[]{select(layer, "*[local-name()='Name']",
								String.class)});
						throw new RuntimeException(err1);
					}
				} else {
					String mens1=rb.getString("each_layer_has_a_default_style1");
					String err1=MessageFormat.format(mens1, new Object[]{select(layer, "*[local-name()='Name']",
									String.class)});
					throw new RuntimeException(err1);
				}
			}
		}
	}

	private void testDefaultStyle(Node parentNode) {
		String style = select(
				parentNode,
				"*[local-name()='Style']/*[local-name()='Name'][text()='inspire_common:DEFAULT']",
				String.class);
		if ((style == null) || (style.trim().length() == 0)) {
			if (parentNode.getParentNode().getNodeName().equals("Layer")) {
				testDefaultStyle(parentNode.getParentNode());
			} else {
				String mens=rb.getString("testDefaultStyle");
				String err=MessageFormat.format(mens, new Object[]{parentNode.getTextContent()});
				throw new RuntimeException(err);
			}
		}

	}

	@Entonces("^cada estilo tiene una leyenda válida en cada uno de los idiomas soportados$")
	@Then("^every Style has a valid legend in any of the supported languages$")
	public void testLegendsInAnyLanguage() {
		List<Node> supportedLanguages = select(
				context(),
				"//*[local-name()='SupportedLanguages']/*[local-name()='SupportedLanguage']/*[local-name()='Language']");
		List<Node> defaultLanguage = select(
				context(),
				"//*[local-name()='SupportedLanguages']/*[local-name()='DefaultLanguage']/*[local-name()='Language']");
		ArrayList<Node> languages = new ArrayList<Node>();
		languages.addAll(supportedLanguages);
		languages.addAll(defaultLanguage);
		for (int i = 0; i < supportedLanguages.size(); i++) {
			Node language = supportedLanguages.get(i);
			String lang = language.getTextContent();
			String urlCap = select(
					context(),
					"//*[local-name()='Request']/*[local-name()='GetCapabilities']/*[local-name()='DCPType']/*[local-name()='HTTP']/*[local-name()='Get']/*[local-name()='OnlineResource']/@*[local-name()='href']",
					String.class);
			String mens1=rb.getString("testLegendsInAnyLanguage1");
			String err1=MessageFormat.format(mens1, new Object[]{});
			assertTrue(err1,urlCap != null);
			assertTrue(err1,urlCap.trim().length()>0);
			Node capabilitiesInALang = request(urlCap
					+ "&request=GetCapabilities&service=WMS&language=" + lang,
					parser(XMLValidatingParser.class).validate(false));
			
			testLegendsInALanguage(capabilitiesInALang, lang);
		}
	}

	private void testLegendsInALanguage(Node capabilitiesInALang, String lang) {
		List<Node> styles = select(capabilitiesInALang,
				"//*[local-name()='Style']");
		for (int i = 0; i < styles.size(); i++) {
			Node style = styles.get(i);
			String legend = select(
					style,
					"/*[local-name()='LegendURL']/*[local-name()='OnlineResource']/@*[local-name()='href']",
					String.class);
			if ((legend == null) || (legend.trim().length() == 0)) {
				String styleName = select(style, "*[local-name()='Name']",
						String.class);
				String mens1=rb.getString("testLegendsInALanguage1");
				String err1=MessageFormat.format(mens1, new Object[]{styleName,lang});
				throw new RuntimeException(err1);
			} else {
				int responseCode = statusCode(request(legend, parser(HTTPParser.class)));
				if (responseCode<0 || responseCode > 400) {
					String styleName = select(style, "*[local-name()='Name']",
							String.class);
					String mens2=rb.getString("testLegendsInALanguage2");
					String err2=MessageFormat.format(mens2, new Object[]{legend,styleName,lang,responseCode});
					throw new RuntimeException(err2);
				}
			}
		}
	}

	@Entonces("^una petición con (.+)=(.+)? debe devolver una imagen$")
	@Then("^a request with (.+)=(.+)? should return an image$")
	public void testARequestWithACertainParamPreSet(String paramName,
			String paramValue) {
		if (paramValue == null) {
			paramValue = "";
		}
		launchGetMapRequest(paramName, paramValue);
	}

	@Entonces("^una petición sin (.+) debe devolver una excepción$")
	@Then("^a request with no (.+) should return an exception$")
	public void testARequestWithoutAParam(String paramName) {
		testARequestWithoutAParam(paramName, null, null);

	}

	@Entonces("^una petición sin (.+) y EXCEPTIONS=(.+) debe devolver una excepción en (.+)$")
	@Then("^a request with no (.+) and EXCEPTIONS=(.+) should return an exception (.+)$")
	public void testARequestWithoutAParam(String paramName, String exception,
			String result) {
		if ((result == null) || (result.trim().length() == 0)
				|| (result.equalsIgnoreCase("xml"))) {
			Node request = launchGetMapWithoutAParam(paramName, "EXCEPTIONS="
					+ exception);
			List<Node> serviceException = select(request,
					"//*[local-name()='ServiceException']");
			if ((serviceException == null) || (serviceException.size() == 0)) {
				String mens1=rb.getString("testARequestWithoutAParam1");
				String err1=MessageFormat.format(mens1, new Object[]{paramName});
				throw new RuntimeException(err1);
			}
		} else if (result.equalsIgnoreCase("image")) {
			String mens2=rb.getString("testARequestWithoutAParam2");
			String err2=MessageFormat.format(mens2, new Object[]{exception});
			assertTrue(
					"Exception should be included in an image with EXCEPTIONS="
							+ exception,
					launchGetMapWithoutAParamReturnsAnImage(paramName,
							"EXCEPTIONS=" + exception));
		}
	}

	@Entonces("^una petición sin (.+) y EXCEPTIONS=(.+) debe devolver una? (.+) en blanco$")
	@Then("^a request with no (.+) and EXCEPTIONS=(.+) should return a blank (.+)$")
	public void testARequestWithoutAParamBlank(String paramName,
			String exception, String result) {
		testARequestWithoutAParam(paramName, exception, result);
	}

	public Node launchGetMapWithoutAParam(String paramName) {
		return launchGetMapWithoutAParam(paramName, null);
	}

	public boolean launchGetMapWithoutAParamReturnsAnImage(String paramName,
			String exceptionParam) {
		String baseRequest = "";
		String url = select(
				context(),
				"//*[local-name()='Capability']/*[local-name()='Request']/*[local-name()='GetMap']/*[local-name()='DCPType']/*[local-name()='HTTP']/*[local-name()='Get']/*[local-name()='OnlineResource']/@*[local-name()='href']",
				String.class);
		if (!paramName.toLowerCase().equals("request")) {
			baseRequest = "request=GetMap";
		}

		if (!paramName.toLowerCase().equals("width")) {
			if (baseRequest.equals("")) {
				baseRequest = baseRequest + "width=100";
			} else {
				baseRequest = baseRequest + "&width=100";
			}
		}
		if (!paramName.toLowerCase().equals("height")) {
			baseRequest = baseRequest + "&height=100";
		}
		if (!paramName.toLowerCase().equals("version")) {
			String version = select(context(),
					"/*/@*[local-name()='version']", String.class);
			baseRequest = baseRequest + "&VERSION=" + version.trim();
		}
		String format = "";
		if (!paramName.toLowerCase().equals("format")) {
			format = select(
					context(),
					"//*[local-name()='Capability']/*[local-name()='Request']/*[local-name()='GetMap']/*[local-name()='Format'][1]",
					String.class);
			baseRequest = baseRequest + "&FORMAT=" + format.trim();
		}
		if (!paramName.toLowerCase().equals("layers")) {
			String layer = select(
					context(),
					"//*[local-name()='Capability']/*[local-name()='Layer']/*[local-name()='Layer'][*[local-name()='Style']][1]/*[local-name()='Name']",
					String.class);
			baseRequest = baseRequest + "&LAYERS=" + layer;
		}
		Node parentWithBBox = null;
		if (!paramName.toLowerCase().equals("bbox")) {
			String minx = select(
					context(),
					"//*[local-name()='Capability']/*[local-name()='Layer']/*[local-name()='Layer'][*[local-name()='Style']][1]/*[local-name()='BoundingBox'][1]/@*[local-name()='minx']",
					String.class);
			if (minx.equalsIgnoreCase("")) {
				List<Node> parent = select(
						context(),
						"//*[local-name()='Capability']/*[local-name()='Layer']/*[local-name()='Layer'][*[local-name()='Style']][1]");
				Node n = parent.get(0).getParentNode();
				if (n.getNodeName().equalsIgnoreCase("Layer")) {
					parentWithBBox = retrieveParentWithBBox(n);
					if (parentWithBBox == null) {
						throw new RuntimeException("Unable to find a suitable bbox for a layer");
					} else {
						minx = select(
								parentWithBBox,
								"*[local-name()='BoundingBox'][1]/@*[local-name()='minx']",
								String.class);
					}
				}
			}
			String miny = null;
			if (parentWithBBox == null) {
				miny = select(
						context(),
						"//*[local-name()='Capability']/*[local-name()='Layer'][*[local-name()='Style']]/*[local-name()='Layer'][1]/*[local-name()='BoundingBox'][1]/@*[local-name()='miny']",
						String.class);
			} else {
				miny = select(
						parentWithBBox,
						"*[local-name()='BoundingBox'][1]/@*[local-name()='miny']",
						String.class);
			}
			String maxx = null;
			if (parentWithBBox == null) {
				maxx = select(
						context(),
						"//*[local-name()='Capability']/*[local-name()='Layer'][*[local-name()='Style']]/*[local-name()='Layer'][1]/*[local-name()='BoundingBox'][1]/@*[local-name()='maxx']",
						String.class);
			} else {
				maxx = select(
						parentWithBBox,
						"*[local-name()='BoundingBox'][1]/@*[local-name()='maxx']",
						String.class);
			}
			String maxy = null;
			if (parentWithBBox == null) {
				maxy = select(
						context(),
						"//*[local-name()='Capability']/*[local-name()='Layer'][*[local-name()='Style']]/*[local-name()='Layer'][1]/*[local-name()='BoundingBox'][1]/@*[local-name()='maxy']",
						String.class);
			} else {
				maxy = select(
						parentWithBBox,
						"*[local-name()='BoundingBox'][1]/@*[local-name()='maxy']",
						String.class);
			}
			baseRequest = baseRequest + "&BBOX=" + minx.trim() + ","
					+ miny.trim() + "," + maxx.trim() + "," + maxy.trim();
		}
		if (!paramName.toLowerCase().equals("crs")) {
			String crs = null;
			if (parentWithBBox == null) {
				crs = select(
						context(),
						"//*[local-name()='Capability']/*[local-name()='Layer'][*[local-name()='Style']]/*[local-name()='Layer'][1]/*[local-name()='BoundingBox'][1]/@*[local-name()='CRS']",
						String.class);
			} else {
				crs = select(
						parentWithBBox,
						"*[local-name()='BoundingBox'][1]/@*[local-name()='CRS']",
						String.class);
			}
			baseRequest = baseRequest + "&CRS=" + crs;
		}
		if (!paramName.toLowerCase().equals("style")) {
			String style = select(
					context(),
					"//*[local-name()='Capability']/*[local-name()='Layer'][*[local-name()='Style']]/*[local-name()='Layer'][1]/*[local-name()='Style']/*[local-name()='Name']",
					String.class);
			baseRequest = baseRequest + "&STYLE=" + style;
		}
		if (!url.contains("?")) {
			url = url + "?";
		}
		if (exceptionParam != null) {
			baseRequest = baseRequest + "&" + exceptionParam;
		}

		String contentType = contentType(request(url + "&" + baseRequest, parser(HTTPParser.class)));
		return contentType.equalsIgnoreCase(format);
	}

	public Node launchGetMapWithoutAParam(String paramName,
			String exceptionParam) {
		String baseRequest = "";
		String url = select(
				context(),
				"//*[local-name()='Capability']/*[local-name()='Request']/*[local-name()='GetMap']/*[local-name()='DCPType']/*[local-name()='HTTP']/*[local-name()='Get']/*[local-name()='OnlineResource']/@*[local-name()='href']",
				String.class);
		if (!paramName.toLowerCase().equals("request")) {
			baseRequest = "request=GetMap";
		}
		if (!paramName.toLowerCase().equals("width")) {
			if (baseRequest.equals("")) {
				baseRequest = baseRequest + "width=100";
			} else {
				baseRequest = baseRequest + "&width=100";
			}
		}
		if (!paramName.toLowerCase().equals("height")) {
			baseRequest = baseRequest + "&height=100";
		}
		if (!paramName.toLowerCase().equals("version")) {
			String version = select(context(),
					"/*/@*[local-name()='version']", String.class);
			baseRequest = baseRequest + "&VERSION=" + version.trim();
		}
		if (!paramName.toLowerCase().equals("format")) {
			String format = select(
					context(),
					"//*[local-name()='Capability']/*[local-name()='Request']/*[local-name()='GetMap']/*[local-name()='Format'][1]",
					String.class);
			baseRequest = baseRequest + "&FORMAT=" + format.trim();
		}
		if (!paramName.toLowerCase().equals("layers")) {
			String layer = select(
					context(),
					"//*[local-name()='Capability']/*[local-name()='Layer']/*[local-name()='Layer'][*[local-name()='Style']][1]/*[local-name()='Name']",
					String.class);
			baseRequest = baseRequest + "&LAYERS=" + layer;
		}
		Node parentWithBBox = null;
		if (!paramName.toLowerCase().equals("bbox")) {
			String minx = select(
					context(),
					"//*[local-name()='Capability']/*[local-name()='Layer']/*[local-name()='Layer'][*[local-name()='Style']][1]/*[local-name()='BoundingBox'][1]/@*[local-name()='minx']",
					String.class);
			if (minx.equalsIgnoreCase("")) {
				List<Node> parent = select(
						context(),
						"//*[local-name()='Capability']/*[local-name()='Layer']/*[local-name()='Layer'][*[local-name()='Style']][1]");
				Node n = parent.get(0).getParentNode();
				if (n.getNodeName().equalsIgnoreCase("Layer")) {
					parentWithBBox = retrieveParentWithBBox(n);
					if (parentWithBBox == null) {
						throw new RuntimeException("Unable to find a suitable bbox for a layer");
					} else {
						minx = select(
								parentWithBBox,
								"*[local-name()='BoundingBox'][1]/@*[local-name()='minx']",
								String.class);
					}
				}
			}
			String miny = null;
			if (parentWithBBox == null) {
				miny = select(
						context(),
						"//*[local-name()='Capability']/*[local-name()='Layer'][*[local-name()='Style']]/*[local-name()='Layer'][1]/*[local-name()='BoundingBox'][1]/@*[local-name()='miny']",
						String.class);
			} else {
				miny = select(
						parentWithBBox,
						"*[local-name()='BoundingBox'][1]/@*[local-name()='miny']",
						String.class);
			}
			String maxx = null;
			if (parentWithBBox == null) {
				maxx = select(
						context(),
						"//*[local-name()='Capability']/*[local-name()='Layer'][*[local-name()='Style']]/*[local-name()='Layer'][1]/*[local-name()='BoundingBox'][1]/@*[local-name()='maxx']",
						String.class);
			} else {
				maxx = select(
						parentWithBBox,
						"*[local-name()='BoundingBox'][1]/@*[local-name()='maxx']",
						String.class);
			}
			String maxy = null;
			if (parentWithBBox == null) {
				maxy = select(
						context(),
						"//*[local-name()='Capability']/*[local-name()='Layer'][*[local-name()='Style']]/*[local-name()='Layer'][1]/*[local-name()='BoundingBox'][1]/@*[local-name()='maxy']",
						String.class);
			} else {
				maxy = select(
						parentWithBBox,
						"*[local-name()='BoundingBox'][1]/@*[local-name()='maxy']",
						String.class);
			}
			baseRequest = baseRequest + "&BBOX=" + minx.trim() + ","
					+ miny.trim() + "," + maxx.trim() + "," + maxy.trim();
		}
		if (!paramName.toLowerCase().equals("crs")) {
			String crs = null;
			if (parentWithBBox == null) {
				crs = select(
						context(),
						"//*[local-name()='Capability']/*[local-name()='Layer'][*[local-name()='Style']]/*[local-name()='Layer'][1]/*[local-name()='BoundingBox'][1]/@*[local-name()='CRS']",
						String.class);
			} else {
				crs = select(
						parentWithBBox,
						"*[local-name()='BoundingBox'][1]/@*[local-name()='CRS']",
						String.class);
			}
			baseRequest = baseRequest + "&CRS=" + crs;
		}
		if (!paramName.toLowerCase().equals("style")) {
			String style = select(
					context(),
					"//*[local-name()='Capability']/*[local-name()='Layer'][*[local-name()='Style']]/*[local-name()='Layer'][1]/*[local-name()='Style']/*[local-name()='Name']",
					String.class);
			baseRequest = baseRequest + "&STYLE=" + style;
		}
		if (!url.contains("?")) {
			url = url + "?";
		}

		Node response = request(url + "&" + baseRequest,
				parser(XMLValidatingParser.class).validate(false));
		return response;
	}

	private Node retrieveParentWithBBox(Node layer) {
		String minx = select(layer,
				"*[local-name()='BoundingBox'][1]/@*[local-name()='minx']",
				String.class);
		if (minx.equalsIgnoreCase("")) {
			List<Node> parent = select(
					context(),
					"//*[local-name()='Capability']/*[local-name()='Layer']/*[local-name()='Layer'][*[local-name()='Style']][1]");
			Node n = parent.get(0).getParentNode();
			if (n.getNodeName().equalsIgnoreCase("Layer")) {
				return retrieveParentWithBBox(n);
			} else {
				return null;
			}
		} else {
			return layer;
		}
	}

	@Entonces("^los idiomas declarados en (.+) son idiomas reconocidos por INSPIRE$")
	@Then("^languages declared in (.+) are INSPIRE recognized languages$")
	public void validateDeclaredLanguagesInASection(String node) {
		if (!node.contains(":")) {
			node = "*[local-name()='" + node + "']";
		}
		Node thesaurus = get("INSPIRE.Languages", Node.class);
		List<Node> languages = select(context(), "//" + node
				+ "//*[local-name()='Language']");
		for (int i = 0; i < languages.size(); i++) {
			Node language = languages.get(i);
			String lang = language.getTextContent();
			String official = select(
					thesaurus,
					"/*[local-name()='RDF']/*[local-name()='Description']/*[local-name()='prefLabel'][@*[local-name()='lang']='zxx'][text()='"
							+ lang + "']", String.class);
			if ((official == null) || (official.trim().length() == 0)) {
				String mens=rb.getString("validateDeclaredLanguagesInASection");
				String err=MessageFormat.format(mens, new Object[]{lang});
				throw new RuntimeException(err);
			}
		}
	}

	@Entonces("^una respuesta GetCapabilities en el idioma por defecto es diferente de las obtenidas en cada uno de los idiomas soportados$")
	@Then("^GetCapabilities response in default language is different from the response in the other supported languages$")
	public void responseIsDifferentForEverySupportedLanguage() {
		String serviceType=select(context(), "//*[local-name()='ServiceIdentification']/*[local-name()='ServiceType']",String.class);
		if((serviceType==null)||(serviceType.trim().length()==0)){
			serviceType=select(context(), "//*[local-name()='Service']/*[local-name()='Name']",String.class);
		}
		responseIsDifferentForEverySupportedLanguage(serviceType);
	}
	
	public void responseIsDifferentForEverySupportedLanguage(String serviceType) {
		List<Node> languages = select(context(),
				"//*[local-name()='ExtendedCapabilities']//*[local-name()='Language']");
		HashMap<String, String> receivedcapabilities = new HashMap<String, String>();
		String defaultLang = select(
				context(),
				"//*[local-name()='ExtendedCapabilities']//*[local-name()='DefaultLanguage']/*[local-name()='Language']",
				String.class);
		if (defaultLang == null) {
			defaultLang = "";
		}

		String cap = context().getTextContent().trim();
		cap = cap.replaceAll("\\s+", " ").toLowerCase();
		receivedcapabilities.put(cap, defaultLang + " - (defaultLanguage)");

		for (int i = 0; i < languages.size(); i++) {
			Node language = languages.get(i);
			String lang = language.getTextContent();
			if (!lang.equalsIgnoreCase(defaultLang)) {
				String capabilitiesURL=getGetCapabilitiesURL(serviceType);
				String mens1=rb.getString("launchcapabilitiesQuery1");
				String err1=MessageFormat.format(mens1, new Object[]{} );
				assertTrue(err1,capabilitiesURL.trim().length()>0);
				if(!capabilitiesURL.contains("?")){
					capabilitiesURL=capabilitiesURL+"?";
				}
				Node received = request(capabilitiesURL+"request=GetCapabilities&service="+serviceType + "&language=" + lang,
						parser(XMLValidatingParser.class).validate(false));
				String content = received.getTextContent().trim();
				content = content.replaceAll("\\s+", " ").toLowerCase();
				if (receivedcapabilities.containsKey(content)) {
					String prevLang = receivedcapabilities.get(content);
					String mens=rb.getString("responseIsDifferentForEverySupportedLanguage");
					String err=MessageFormat.format(mens, new Object[]{prevLang});
					throw new RuntimeException(err);
				}
				receivedcapabilities.put(lang, content);
			}
		}
	}

	@Entonces("^una respuesta GetCapabilities en un idioma no soportado es la misma que la recibida con el idioma por defecto$")
	@Then("^GetCapabilities response is the same as with default language response when language is not suppoted$")
	public void responseIsTheSameWithNotSuportedLanguage() {
		String serviceType=select(context(), "//*[local-name()='ServiceIdentification']/*[local-name()='ServiceType']",String.class);
		if((serviceType==null)||(serviceType.trim().length()==0)){
			serviceType=select(context(), "//*[local-name()='Service']/*[local-name()='Name']",String.class);
		}
		responseIsTheSameWithNotSuportedLanguage(serviceType);
	}
	
	public void responseIsTheSameWithNotSuportedLanguage(String serviceType) {
		String defaultLang = select(
				context(),
				"//*[local-name()='ExtendedCapabilities']//*[local-name()='DefaultLanguage']/*[local-name()='Language']",
				String.class);
		String capabilitiesURL=getGetCapabilitiesURL(serviceType);
		String mens1=rb.getString("launchcapabilitiesQuery1");
		String err1=MessageFormat.format(mens1, new Object[]{} );
		assertTrue(err1,capabilitiesURL.trim().length()>0);
		if(!capabilitiesURL.contains("?")){
			capabilitiesURL=capabilitiesURL+"?";
		}
		String unsupportedLang = UUID.randomUUID().toString();
		Node receivedUns = request(capabilitiesURL+"request=GetCapabilities&service="+serviceType + "&language=" + unsupportedLang,
				parser(XMLValidatingParser.class).validate(false));
		Node receivedDef = request(capabilitiesURL+"request=GetCapabilities&service="+serviceType + "&language=" + defaultLang,
				parser(XMLValidatingParser.class).validate(false));
		String receivedStrUns = receivedUns.getTextContent().trim();
		receivedStrUns = receivedStrUns.replaceAll("\\s+", " ").toLowerCase();
		String receivedStrDef = receivedDef.getTextContent().trim();
		receivedStrDef = receivedStrDef.replaceAll("\\+", " ").toLowerCase();
		String mens=rb.getString("responseIsTheSameWithNotSuportedLanguage");
		String err=MessageFormat.format(mens, new Object[]{unsupportedLang});
		assertTrue(	err, !receivedStrDef.equals(receivedStrUns));

	}

	@Entonces("^una respuesta GetCapabilities sin especificar un idioma es la misma que la recibida con el idioma por defecto$")
	@Then("^GetCapabilities response is the same as with default language response when no language is specified$")
	public void responseIsTheSameWithNotSpecifiedLanguage() {
		String serviceType=select(context(), "//*[local-name()='ServiceIdentification']/*[local-name()='ServiceType']",String.class);
		if((serviceType==null)||(serviceType.trim().length()==0)){
			serviceType=select(context(), "//*[local-name()='Service']/*[local-name()='Name']",String.class);
		}
		responseIsTheSameWithNotSpecifiedLanguage(serviceType);
	}
	
	public String getGetCapabilitiesURL(String serviceType){
		if(serviceType.trim().equalsIgnoreCase("WMS")){
			String capabilitiesURL=select(context(),"//*[local-name()='Capability']/*[local-name()='Request']/*[local-name()='GetCapabilities']/*[local-name()='DCPType']/*[local-name()='HTTP']/*[local-name()='Get']/*[local-name()='OnlineResource']/@*[local-name()='href']",String.class);
			return capabilitiesURL;
		}else if(serviceType.trim().equalsIgnoreCase("CSW")){
			String capabilitiesURL=select(context(),"//*[local-name()='OperationsMetadata']/*[local-name()='Operation'][@*[local-name()='name']='GetCapabilities']/*[local-name()='DCP']/*[local-name()='HTTP']/*[local-name()='Get']/@*[local-name()='href']",String.class);
			return capabilitiesURL;
		}
		return null;
	}
	
	public void responseIsTheSameWithNotSpecifiedLanguage(String serviceType) {
		String defaultLang = select(
				context(),
				"//*[local-name()='ExtendedCapabilities']//*[local-name()='DefaultLanguage']/*[local-name()='Language']",
				String.class);
		String capabilitiesURL=getGetCapabilitiesURL(serviceType);
		String mens1=rb.getString("launchcapabilitiesQuery1");
		String err1=MessageFormat.format(mens1, new Object[]{} );
		assertTrue(err1,capabilitiesURL.trim().length()>0);
		if(!capabilitiesURL.contains("?")){
			capabilitiesURL=capabilitiesURL+"?";
		}
		Node receivedDef = request(capabilitiesURL+"request=GetCapabilities&service="+serviceType+"&language=" + defaultLang,
				parser(XMLValidatingParser.class).validate(false));
		String receivedStrNoLang = context().getTextContent().trim();
		receivedStrNoLang = receivedStrNoLang.replaceAll("\\s+", " ")
				.toLowerCase();
		String receivedStrDef = receivedDef.getTextContent().trim();
		receivedStrDef = receivedStrDef.replaceAll("\\+", " ").toLowerCase();
		String mens2=rb.getString("responseIsTheSameWithNotSpecifiedLanguage");
		String err2=MessageFormat.format(mens2, new Object[]{});
		assertTrue(err2,
				!receivedStrDef.equals(receivedStrNoLang));

	}

	/*
	 * rules for CSW services validation
	 */
	@Cuando("^existan catálogos federados declarados$")
	@When("^there are federated catalogues declared$")
	public void thereAreFederatedCatalogues() {
		List<Node> constraints = select(
				context(),
				"//*[local-name()='Constraint'][@*[local-name()='name']='FederatedCatalogues']/*[local-name()='Value']");
		if(constraints.size()<=0){
			throw new PassVerdictException("There are no Federated Catalogues declared");
		}
	}

	@Entonces("^cada uno de los catálogos federados debe responder correctamente a una petición GetCapabilities$")
	@Then("^each federated catalogue should reply correctly to a GetCapabilities query$")
	public void eachFederatedCatalogueReturnsOk() {
		List<Node> constraints = select(
				context(),
				"//*[local-name()='Constraint'][@*[local-name()='name']='FederatedCatalogues']/*[local-name()='Value']");
		for (int i = 0; i < constraints.size(); i++) {
			Node fedCat = constraints.get(i);
			String url = fedCat.getTextContent();
			if (!url.toLowerCase().contains("GetCapabilities")) {
				if (url.contains("?")) {
					url = url + "&";
				} else {
					url = url + "?";
				}
				url = url + "request=GetCapabilities&service=CSW";
			}
			try {
				Node response = request(url, parser(XMLValidatingParser.class)
						.validate(false));
				String mens1=rb.getString("eachFederatedCatalogueReturnsOk1");
				String err1=MessageFormat.format(mens1, new Object[]{url});
				assertTrue(err1,
						response.getLocalName()
								.equalsIgnoreCase("context()"));
			} catch (Exception e) {
				String mens2=rb.getString("eachFederatedCatalogueReturnsOk2");
				String err2=MessageFormat.format(mens2, new Object[]{url});
				throw new RuntimeException(err2);
			}
		}
	}

	@Entonces("^hay al menos en la sección (.+) un nodo de$")
	@Then("^there is at least in the (.+) section one of$")
	public void thereIsAtLeastOneOf(String node, List<String> values) {
		if (!node.contains(":")) {
			node = "*[local-name()='" + node + "']";
		}
		boolean found = false;
		for (int i = 0; (i < values.size()) && !found; i++) {
			String value = values.get(i);
			if (!value.contains(":")) {
				value = "*[local-name()='" + value + "']";
			}
			List<Node> result = select(context(), "//" + node + "//" + value);
			if (result.size() > 0) {
				found = true;
			}
		}
		assertTrue("None of the subnodes provided was found in the " + node
				+ " section", found);
	}

	@Entonces("^una consulta GetRecords con anytext puesta a \\* y language puesto al idioma por defecto debería devolver algún metadato$")
	@Then("^a GetRecords query with anytext set to \\* and language set to default should return some metadata$")
	public void sendGetRecordsQuery() {
		String getRecordsUrl = select(
				context(),
				"//*[local-name()='Operation'][@*[local-name()='name']='GetRecords']/*[local-name()='DCP']/*[local-name()='HTTP']/*[local-name()='Post']/@*[local-name()='href']",
				String.class);
		String defaultLanguage = select(
				context(),
				"//*[local-name()='ExtendedCapabilities']/*[local-name()='SupportedLanguages']/*[local-name()='DefaultLanguage']/*[local-name()='Language']",
				String.class);
		String getRecordsSqueleton = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<csw:GetRecords xmlns:csw=\"http://www.opengis.net/cat/csw/2.0.2\" "
				+ "xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\" "
				+ "xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:gmd=\"http://www.isotc211.org/2005/gmd\" "
				+ "service=\"CSW\" resultType=\"results\" "
				+ "outputFormat=\"application/xml\" outputSchema=\"http://www.isotc211.org/2005/gmd\" "
				+ "startPosition=\"1\" maxRecords=\"10\">"
				+ "	<csw:Query typeNames=\"gmd:MD_Metadata\">"
				+ "		<csw:ElementSetName typeNames=\"gmd:MD_Metadata\">full</csw:ElementSetName>"
				+ "		<csw:Constraint version=\"1.1.0\">"
				+ "			<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\">"
				+ "				<ogc:And>"
				+ "					<ogc:PropertyIsEqualTo>"
				+ "						<ogc:PropertyName>apiso:Language</ogc:PropertyName>"
				+ "						<ogc:Literal>$LANGUAGE$</ogc:Literal>"
				+ "					</ogc:PropertyIsEqualTo>"
				+ "					<ogc:PropertyIsLike wildCard=\"*\" singleChar=\"?\" escapeChar=\"/\">"
				+ "						<ogc:PropertyName>anyText</ogc:PropertyName>"
				+ "						<ogc:Literal>*</ogc:Literal>"
				+ "					</ogc:PropertyIsLike>"
				+ "				</ogc:And>"
				+ "			</ogc:Filter>"
				+ "		</csw:Constraint>"
				+ "	</csw:Query>"
				+ "</csw:GetRecords>";
		if ((defaultLanguage == null) || (defaultLanguage.trim().length() == 0)) {
			defaultLanguage = "eng";
		}
		String getRec = getRecordsSqueleton.replace("$LANGUAGE$",
				defaultLanguage);
		Node n = requestPost(getRecordsUrl, getRec);
		String mens1=rb.getString("sendGetRecordsQuery1");
		String err1=MessageFormat.format(mens1, new Object[]{});
		assertTrue(err1, n!=null);
		String numberOfRecordsMatched = select(
				n,
				"//*[local-name()='GetRecordsResponse']/*[local-name()='SearchResults']/@*[local-name()='numberOfRecordsMatched']",
				String.class);
		try {
			int norm = Integer.parseInt(numberOfRecordsMatched);
			String mens2=rb.getString("sendGetRecordsQuery2");
			String err2=MessageFormat.format(mens2, new Object[]{});
			assertTrue(err2,
					norm > 0);
		} catch (Exception e) {
			String mens3=rb.getString("sendGetRecordsQuery1");
			String err3=MessageFormat.format(mens3, new Object[]{});
			throw new RuntimeException(err3);
		}
	}

	@Entonces("^un metadato resultante de una consulta GetRecords debe ser válido respecto a las normas de INSPIRE$")
	@Then("^a metadata resulting from a GetRecords query should be a valid INSPIRE metadata$")
	public void checkMetadataINSPIRECompliance() {
		String getRecordsUrl = select(
				context(),
				"//*[local-name()='Operation'][@*[local-name()='name']='GetRecords']/*[local-name()='DCP']/*[local-name()='HTTP']/*[local-name()='Post']/@*[local-name()='href']",
				String.class);
		// inicio temporal
		//getRecordsUrl = "http://www.ign.es/csw-inspire/srv/es/csw";
		// fin temporal
		String getRecords = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<GetRecords xmlns=\"http://www.opengis.net/cat/csw/2.0.2\" "
				+ "xmlns:dc=\"http://purl.org/dc/elements/1.1/\" "
				+ "xmlns:ogc=\"http://www.opengis.net/ogc\" "
				+ "xmlns:ows=\"http://www.opengis.net/ows\" "
				+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
				+ "xmlns:gml=\"http://www.opengis.net/gml\" "
				+ "xsi:schemaLocation=\"http://www.opengis.net/cat/csw/2.0.2 http://schemas.opengis.net/csw/2.0.2/CSW-discovery.xsd\" "
				+ "xmlns:gmd=\"http://www.isotc211.org/2005/gmd\" service=\"CSW\" version=\"2.0.2\" "
				+ "resultType=\"results\" outputFormat=\"application/xml\" outputSchema=\"http://www.isotc211.org/2005/gmd\">"
				+ "	<Query typeNames=\"gmd:MD_Metadata\">"
				+ "		<ElementSetName>full</ElementSetName>"
				+ "		<Constraint version=\"1.1.0\">"
				+ "			<ogc:Filter>"
				+ "				<ogc:PropertyIsLike wildCard=\"*\" singleChar=\"?\" escapeChar=\"/\">"
				+ "					<ogc:PropertyName>anyText</ogc:PropertyName>"
				+ "					<ogc:Literal>*</ogc:Literal>"
				+ "				</ogc:PropertyIsLike>" + "			</ogc:Filter>"
				+ "		</Constraint>" + "	</Query>" + "</GetRecords>";
		Node n = requestPost(getRecordsUrl, getRecords);
		List<Node> records = select(
				n,
				"/*[local-name()='GetRecordsResponse']/*[local-name()='SearchResults']/*[local-name()='MD_Metadata'][1]");
		String mens1=rb.getString("checkMetadataINSPIRECompliance1");
		String err1=MessageFormat.format(mens1, new Object[]{});
		assertTrue(err1,
				records.size() > 0);
		String record = createXML(records.get(0));

		/*---Launch an INSPIRE compliance with the metadata returned from getRecords response---*/
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(
					"http://idee.unizar.es/INSPIREValidatorService/resources/validation/inspire");
			httpPost.addHeader("Accept", "text/html");

			File f = File.createTempFile("inspireValidation", "insVal");
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(record.getBytes("UTF-8"));
			fos.flush();
			fos.close();

			FileBody dataFile = new FileBody(f);
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("dataFile", dataFile);
			httpPost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			String mens2=rb.getString("checkMetadataINSPIRECompliance2");
			String err2=MessageFormat.format(mens2, new Object[]{statusCode});
			assertTrue(err2,
					statusCode < 400);

			InputStream is = response.getEntity().getContent();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			String aux = null;
			while ((aux = br.readLine()) != null) {
				s = s + "\n" + aux;
			}
			s = s.trim();

			s = "<wrapper>" + s + "</wrapper>";

			Node validation = createNodeFromString(new ByteArrayInputStream(
					s.getBytes("UTF-8")));

			String incorrectElements = select(
					validation,
					"/*[local-name()='wrapper']/*[local-name()='div'][@*[local-name()='id']='totalIncorrectEle']",
					String.class);
			String incorrectElementsContent = select(
					validation,
					"/*[local-name()='wrapper']/*[local-name()='div'][@*[local-name()='id']='incorrectList']/*[local-name()='ul']",
					String.class);
			String mens3=rb.getString("checkMetadataINSPIRECompliance3");
			String err3=MessageFormat.format(mens3, new Object[]{});
			assertTrue(
					err3,
					incorrectElements!=null);
			String mens4=rb.getString("checkMetadataINSPIRECompliance4");
			String err4=MessageFormat.format(mens4, new Object[]{incorrectElements, incorrectElementsContent});
			assertTrue(
					err4,
					incorrectElements
							.equalsIgnoreCase("Number of incorrect elements found: 0"));

		} catch (Exception e) {
			String mens5=rb.getString("checkMetadataINSPIRECompliance5");
			String err5=MessageFormat.format(mens5, new Object[]{e});
			throw new RuntimeException(err5);
		}

	}

	@Entonces("^el perfil ISO está declarado como una Constraint en la sección OperationsMetadata$")
	@Then("^ISO profile is declared as a Constraint in the OperationsMetadata section$")
	public void isoProfileIsDeclared() {
		String s = select(
				context(),
				"//*[local-name()='OperationsMetadata']/*[local-name()='Constraint'][@*[local-name()='name']='IsoProfiles']/*[local-name()='Value']",
				String.class);
		String mens1=rb.getString("isoProfileIsDeclared1");
		String err1=MessageFormat.format(mens1, new Object[]{});
		assertTrue(err1, s!=null);
		String mens2=rb.getString("isoProfileIsDeclared2");
		String err2=MessageFormat.format(mens2, new Object[]{});
		assertTrue(
				err2,
				s.equals("http://www.isotc211.org/2005/gmd"));
	}

	@Entonces("^(.+) es uno de los valores para el parámetro (.+) en la operación (.+)$")
	@Then("^(.+) (.+) is supported in (.+) operation$")
	public void checkParamValueInOperation(String value, String paramName,
			String operation) {
		List<Node> values = select(
				context(),
				"//*[local-name()='OperationsMetadata']/*[local-name()='Operation'][@*[local-name()='name']='"
						+ operation
						+ "']/*[local-name()='Parameter'][@*[local-name()='name']='"
						+ paramName
						+ "']/*[local-name()='Value'][text()='"
						+ value + "']");
		String mens=rb.getString("checkParamValueInOperation");
		String err=MessageFormat.format(mens, new Object[]{value, paramName, operation});
		assertTrue(err,
				values.size() > 0);

	}

	@Entonces("^(.+) es uno de los valores de la sección Constraint con nombre (.+) en la operación (.+)$")
	@Then("^(.+) is one of the values for Constraint (.+) in (.+) operation$")
	public void checkConstraintValueInOperation(String value, String constName,
			String operation) {
		List<Node> values = select(
				context(),
				"//*[local-name()='OperationsMetadata']/*[local-name()='Operation'][@*[local-name()='name']='"
						+ operation
						+ "']/*[local-name()='Constraint'][@*[local-name()='name']='"
						+ constName
						+ "']/*[local-name()='Value'][text()='"
						+ value + "']");
		String mens=rb.getString("checkConstraintValueInOperation");
		String err=MessageFormat.format(mens, new Object[]{value,constName,operation});
		assertTrue(err, values.size() > 0);
	}

	@Entonces("^existe un nodo (.+) en la operación (.+) con el atributo (.+) con valor (.+)$")
	@Then("^there is a (.+) node in the operation (.+) with the attribute (.+) set to (.+)$")
	public void checkAttributeValueOnANode(String node, String operation,
			String attributeName, String attributeValue) {
		String originalAttributeName=attributeName;
		if (!node.contains(":")) {
			node = "*[local-name()='" + node + "']";
		}
		if (!attributeName.contains(":")) {
			attributeName = "*[local-name()='" + attributeName + "']";
		}
		List<Node> values = select(context(),
				"//*[local-name()='Operation'][@*[local-name()='name']='"
						+ operation + "']/" + node + "[@" + attributeName
						+ "='" + attributeValue + "']");
		String mens=rb.getString("checkAttributeValueOnANode");
		String err=MessageFormat.format(mens, new Object[]{node,operation,originalAttributeName,attributeValue});
		assertTrue(err, values.size() > 0);
	}
	
	@SuppressWarnings("unchecked")
	@Entonces("^el contenido de una consulta GetRecords por cada uno de los idiomas soportados debe devolver metadatos en el idioma indicado$")
	@Then("^the metadata returned by getRecords query by every supported language correspond to de specified language$")
	public void checkGetRecordsWithLanguage(){
		List<String> languagesToQuery=new ArrayList<String>();
		languagesToQuery.addAll(get("supportedLanguages", List.class));
		if(!languagesToQuery.contains(get("defaultLanguage",String.class))){
			languagesToQuery.add(get("defaultLanguage",String.class));
		}
		
		String getRecordsUrl = select(
				context(),
				"//*[local-name()='Operation'][@*[local-name()='name']='GetRecords']/*[local-name()='DCP']/*[local-name()='HTTP']/*[local-name()='Post']/@*[local-name()='href']",
				String.class);
		// inicio temporal
		getRecordsUrl = "http://www.ign.es/csw-inspire/srv/es/csw";
		String getRecordsSqueleton = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<csw:GetRecords xmlns:csw=\"http://www.opengis.net/cat/csw/2.0.2\" "
				+ "xmlns:apiso=\"http://www.opengis.net/cat/csw/apiso/1.0\" "
				+ "xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:gmd=\"http://www.isotc211.org/2005/gmd\" "
				+ "service=\"CSW\" resultType=\"results\" "
				+ "outputFormat=\"application/xml\" outputSchema=\"http://www.isotc211.org/2005/gmd\" "
				+ "startPosition=\"1\" maxRecords=\"10\">"
				+ "	<csw:Query typeNames=\"gmd:MD_Metadata\">"
				+ "		<csw:ElementSetName typeNames=\"gmd:MD_Metadata\">full</csw:ElementSetName>"
				+ "		<csw:Constraint version=\"1.1.0\">"
				+ "			<ogc:Filter xmlns:ogc=\"http://www.opengis.net/ogc\">"
				+ "				<ogc:PropertyIsEqualTo>"
				+ "					<ogc:PropertyName>apiso:Language</ogc:PropertyName>"
				+ "					<ogc:Literal>$LANGUAGE$</ogc:Literal>"
				+ "				</ogc:PropertyIsEqualTo>"
				+ "			</ogc:Filter>"
				+ "		</csw:Constraint>"
				+ "	</csw:Query>"
				+ "</csw:GetRecords>";
		for(int i=0;i<languagesToQuery.size();i++){
			String lang=languagesToQuery.get(i);
			String getRecQ=getRecordsSqueleton.replace("$LANGUAGE$", lang);
			Node response=requestPost(getRecordsUrl, getRecQ);
			List<Node> languagesReturned=select(response,"//*[local-name()='MD_Metadata']/*[local-name()='language']/*");
			for(int j=0;j<languagesReturned.size();j++){
				Node langret=languagesReturned.get(j);
				String mens=rb.getString("checkGetRecordsWithLanguage");
				String err=MessageFormat.format(mens, new Object[]{String.valueOf(i+1),lang,getRecQ});
				assertTrue(err,langret.getTextContent().equals(lang));
			}
		}
		
		
	}

	//
	//	Utility functions
	//
	
	private void requestForLanguage(String lang) {
		String serviceType=select(context(), "//*[local-name()='ServiceIdentification']/*[local-name()='ServiceType']",String.class);
		if((serviceType==null)||(serviceType.trim().length()==0)){
			serviceType=select(context(), "//*[local-name()='Service']/*[local-name()='Name']",String.class);
		}
		requestForLanguage(lang, serviceType);
	}
	
	private void requestForLanguage(String lang,String serviceType) {
		if (!isset("requestedLanguages")) {
			set("requestedLanguages", new ArrayList<String>());
		}
		get("requestedLanguages", List.class).add(lang);
		String capabilitiesURL=getGetCapabilitiesURL(serviceType);
		String mens1=rb.getString("launchcapabilitiesQuery1");
		String err1=MessageFormat.format(mens1, new Object[]{} );
		assertTrue(err1,capabilitiesURL.trim().length()>0);
		if(!capabilitiesURL.contains("?")){
			capabilitiesURL=capabilitiesURL+"?";
		}
		String ureq = capabilitiesURL+"&request=GetCapabilities&service="+serviceType + "&LANGUAGE=" + lang;
		Node requestedCapabilities = request(ureq,
				parser(XMLValidatingParser.class).validate(false));
		String mens=rb.getString("requestForLanguage1");
		String err=MessageFormat.format(mens, new Object[]{ureq});
		assertTrue(err,
				requestedCapabilities !=null);
		String rlang = select(requestedCapabilities,
				"//inspire_common:ResponseLanguage/inspire_common:Language",
				String.class);
		String mens2=rb.getString("requestForLanguage2");
		String err2=MessageFormat.format(mens2, new Object[]{ureq});
		assertTrue(err2,
				rlang != null);
		if (!isset("retrievedLanguages")) {
			set("retrievedLanguages", new ArrayList<String>());
		}
		get("retrievedLanguages", List.class).add(lang);
	}
}
