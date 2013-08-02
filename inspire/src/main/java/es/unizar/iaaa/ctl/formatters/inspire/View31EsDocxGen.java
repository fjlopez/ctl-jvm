package es.unizar.iaaa.ctl.formatters.inspire;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Text;

import gherkin.parser.Parser;

public class View31EsDocxGen {
	
	
	public static void main(String args[]) throws IOException, Docx4JException {
		new View31EsDocxGen().run();
	}

	private void run() throws IOException, Docx4JException {
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
		MainDocumentPart mainPart = wordMLPackage.getMainDocumentPart();
		File dir = new File("src/main/features/view31");
		for(File file: dir.listFiles()) {
			if (file.getPath().endsWith("es.feature")) {
			    ObjectFactory factory = Context.getWmlObjectFactory();
				Text text = factory.createText();
		        text.setValue("Fichero '"+file.getName()+"'");
		        R run = factory.createR();
		        run.getContent().add(text);
		        RPr runProperties = factory.createRPr();
    	        BooleanDefaultTrue b = new BooleanDefaultTrue();
    	        b.setVal(true);
    	        runProperties.setB(b);
		        run.setRPr(runProperties);
		        P paragraph = factory.createP();
		        paragraph.getContent().add(run);
		        mainPart.addObject(paragraph);
				try {
					new Parser(new DocxFormatter(mainPart)).parse(FileUtils.readFileToString(file), "", 0);
				} catch (Exception e) {
					mainPart.addParagraphOfText(e.getMessage());
				}
				mainPart.addParagraphOfText("");
			}
		}
		File out = new File("target/View31EsDocxGen.docx");
		wordMLPackage.save(out);
	}

}
