import gherkin.formatter.PrettyFormatter
import gherkin.formatter.model.Feature;
import gherkin.parser.Parser

// keyword: name
// description

// keyword: name
// markdown


class MyPrettyFormatter extends PrettyFormatter {
	def featureDescription = {it}
	def app
	MyPrettyFormatter(Appendable app) {
		super(app, true, false)
		this.app = app
	}
    @Override
    public void feature(Feature feature) {
        super.printComments(feature.getComments(), "")
        super.printTags(feature.getTags(), "")
		app.append(feature.getKeyword() + ": " + feature.getName()+"\n")
		super.printDescription(featureDescription(feature.getDescription()), "  ", false)
    }
}

patch = { text, newText ->
	def prettyCode = new StringBuffer()
	def formatter = new MyPrettyFormatter(prettyCode)
	def apply = {oldText ->
		oldText.trim() + '\n!!!\n' + newText.trim()
	}
	formatter.featureDescription = apply
	new Parser(formatter).parse(text,"", 0)
	prettyCode.toString()
}

def markdownPatch(path, pattern, text, out) {
	def fout = new File(out)
	fout.mkdirs()
	new File(path).listFiles().findAll {it.name ==~ pattern}.each {
		new File(fout, it.name).write(patch.call(it.text, text))
	}	
}


println "Patcher"

markdownPatch('src/main/features/view31', /.*es\.feature/, """
      |Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
	  |de Visualización Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
      |para **servicios de visualización** basados en el estándar internacional **ISO 19128**
      |(**OGC WMS 1.3.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
      |de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).
      |""".stripMargin(), 'target/view31')

markdownPatch('src/main/features/view31', /.*en\.feature/, """
      |Requirement established in the guidance document [Technical Guidance for the
	  |implementation of INSPIRE View Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_ViewServices_v3.1.pdf) 
      |for **view services** based on the international standard **ISO 19128**
      |(**OGC WMS 1.3.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU)
	  |nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).
      |""".stripMargin(), 'target/view31')

markdownPatch('src/main/features/discovery31', /.*es\.feature/, """
      |Requisito establecido en el documento guía [Guía Técnica para la Implementación de los Servicios
	  |de Descubrimiento Versión 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
      |para **servicios de descubrimiento** basados en el estándar internacional **CSW ISO AP**
      |(**OGC CSW 2.0.2 - ISO AP 1.0.0**). La legislación aplicable es el [Reglamento (CE) nº 976/2009 de la Comisión (modificado por el Reglamento (UE) nº 1088/2010
      |de la Comisión)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:ES:HTML).
      |""".stripMargin(), 'target/discovery31')

markdownPatch('src/main/features/discovery31', /.*en\.feature/, """
      |Requirement established in the guidance document [Technical Guidance for the
	  |implementation of INSPIRE Discovery Services Version 3.1](http://inspire.jrc.ec.europa.eu/documents/Network_Services/TechnicalGuidance_DiscoveryServices_v3.1.pdf) 
      |for **discovery services** based on the international standard **CSW ISO AP**
      |(**OGC CSW 2.0.2 - ISO AP 1.0.0**). The applicable legislation is the [Commission Regulation (EC) nº 976/2009 (amended by Commission Regulation (EU) 
      |nº 1088/2010)](http://eur-lex.europa.eu/LexUriServ/LexUriServ.do?uri=CONSLEG:2009R0976:20101228:EN:HTML).
      |""".stripMargin(), 'target/discovery31')

println "Done"