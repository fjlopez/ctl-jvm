package es.unizar.iaaa.ctl.formatters.inspire;

import gherkin.formatter.Argument;
import gherkin.formatter.Format;
import gherkin.formatter.Formats;
import gherkin.formatter.Formatter;
import gherkin.formatter.StepPrinter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.BasicStatement;
import gherkin.formatter.model.CellResult;
import gherkin.formatter.model.Comment;
import gherkin.formatter.model.DescribedStatement;
import gherkin.formatter.model.DocString;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Row;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;
import gherkin.formatter.model.Tag;
import gherkin.formatter.model.TagStatement;
import gherkin.util.Mapper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.docx4j.jaxb.Context;
import org.docx4j.model.properties.paragraph.Indent;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.ParaRPr;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Text;

import static gherkin.util.FixJava.join;
import static gherkin.util.FixJava.map;

/**
 * This class pretty prints feature files like they were in the source, only
 * prettier. That is, with consistent indentation. This class is also a {@link Reporter},
 * which means it can be used to print execution results - highlighting arguments,
 * printing source information and exception information.
 */
public class DocxFormatter implements Formatter {
    private final MainDocumentPart out;

    private Mapper tagNameMapper = new Mapper() {
        public String map(Object tag) {
            return ((Tag) tag).getName();
        }
    };
    private int[][] cellLengths;
    private int[] maxLengths;
    private int rowIndex;
    private List<? extends Row> rows;
    private Integer rowHeight = null;
    private boolean rowsAbove = false;
    ObjectFactory factory = Context.getWmlObjectFactory();

    //private List<Step> steps = new ArrayList<Step>();
    private List<Integer> indentations = new ArrayList<Integer>();

    public DocxFormatter(MainDocumentPart out) {
        this.out = out;
    }

    public void uri(String uri) {
    }

    public void feature(Feature feature) {
        P paragraph = factory.createP();
		addBoldText(paragraph, feature.getKeyword());
		addText(paragraph, ": "+clean(feature.getName()));
        out.addObject(paragraph);
        printDescription(clean(feature.getDescription()), "  ", false);
    }

    public void background(Background background) {
        printStatement(background);
    }

    public void scenario(Scenario scenario) {
        printStatement(scenario);
    }

    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        printStatement(scenarioOutline);
    }

    private  void addWhiteStyle(RPr runProperties) {
        org.docx4j.wml.Color col = factory.createColor();
        col.setVal("White");
        runProperties.setColor(col);
    }

    private  void addBoldStyle(RPr runProperties) {
    	        BooleanDefaultTrue b = new BooleanDefaultTrue();
    	        b.setVal(true);
    	        runProperties.setB(b);
   }

    private void printStatement(DescribedStatement statement) {
        if (statement == null) {
            return;
        }
        calculateLocationIndentations();
        P paragraph = factory.createP();
		addWhiteText(paragraph, "___");
		addBoldText(paragraph, statement.getKeyword());
		addText(paragraph, ": "+clean(statement.getName()));
        out.addObject(paragraph);
        printDescription(clean(statement.getDescription()), "    ", true);
        statement = null;
    }

	private void addText(P paragraph, String msg) {
		Text text = factory.createText();
        text.setValue(msg);
        R run = factory.createR();
        run.getContent().add(text);
        paragraph.getContent().add(run);
	}

	private void addBoldText(P paragraph, String msg) {
		Text text = factory.createText();
        text.setValue(msg);
        R run = factory.createR();
        run.getContent().add(text);
        RPr runProperties = factory.createRPr();
        addBoldStyle(runProperties);
        run.setRPr(runProperties);
        paragraph.getContent().add(run);
	}

	private void addWhiteText(P paragraph, String msg) {
		Text text = factory.createText();
        text.setValue(msg);
        R run = factory.createR();
        run.getContent().add(text);
        RPr runProperties = factory.createRPr();
        addWhiteStyle(runProperties);
        run.setRPr(runProperties);
        paragraph.getContent().add(run);
	}

	public void examples(Examples examples) {
        P paragraph = factory.createP();
		addWhiteText(paragraph, "_____");
		addBoldText(paragraph, examples.getKeyword());
		addText(paragraph, ": "+clean(examples.getName()));
        out.addObject(paragraph);
        printDescription(examples.getDescription(), "      ", true);
        table(examples.getRows());
    }

    public void step(Step step) {
    	try {
        printStep(step, "skipped", Collections.<Argument>emptyList(), null, true);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }


    public void writeStep(StringBuffer out, Format textFormat, Format argFormat, String stepName, List<Argument> arguments) {
        int textStart = 0;
        for (Argument argument : arguments) {
            // can be null if the argument is missing.
            if (argument.getOffset() != null) {
                String text = stepName.substring(textStart, argument.getOffset());
                out.append(textFormat.text(text));
            }
            // val can be null if the argument isn't there, for example @And("(it )?has something")
            if (argument.getVal() != null) {
                out.append(argFormat.text(argument.getVal()));
                textStart = argument.getOffset() + argument.getVal().length();
            }
        }
        if (textStart != stepName.length()) {
            String text = stepName.substring(textStart, stepName.length());
            out.append(text);
        }
    }


    private void printStep(Step step, String status, List<Argument> arguments, String location, boolean proceed) {

        //printComments(step.getComments(), "    ");
        P paragraph = factory.createP();
		addWhiteText(paragraph, "_____");
		addBoldText(paragraph, step.getKeyword());
		addWhiteText(paragraph, "_");
    	StringBuffer sb = new StringBuffer();
        writeStep(sb, null, null, step.getName(), arguments);
		addText(paragraph, sb.toString());
        out.addObject(paragraph);
        if (step.getRows() != null) {
            table(step.getRows());
        } else if (step.getDocString() != null) {
            docString(step.getDocString());
        }
    }

    public void table(List<? extends Row> rows) {
        prepareTable(rows);
        for (Row row : rows) {
        	row(row.createResults("skipped"));
        	nextRow();
        }
    }

    private void prepareTable(List<? extends Row> rows) {
        this.rows = rows;
        int columnCount = rows.get(0).getCells().size();
        cellLengths = new int[rows.size()][columnCount];
        maxLengths = new int[columnCount];
        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            Row row = rows.get(rowIndex);
            List<String> cells = row.getCells();
            for (int colIndex = 0; colIndex < columnCount; colIndex++) {
                String cell = cells.get(colIndex);
                int length = escapeCell(cell).length();
                cellLengths[rowIndex][colIndex] = length;
                maxLengths[colIndex] = Math.max(maxLengths[colIndex], length);
            }
        }
        rowIndex = 0;
    }

    public void row(List<CellResult> cellResults) {
        Row row = rows.get(rowIndex);
        if (rowsAbove) {
        	for(int i = 0; i<rowHeight; i++) {
        		out.addParagraphOfText("");
        	}
        } else {
            rowsAbove = true;
        }
        StringBuffer sb = new StringBuffer();
        rowHeight = 1;

        for (Comment comment : row.getComments()) {
            sb.append("      ");
            sb.append(comment.getValue());
            out.addParagraphOfText(sb.toString());
            sb = new StringBuffer();
            rowHeight++;
        }
        switch (row.getDiffType()) {
            case NONE:
                sb.append("      | ");
                break;
            case DELETE:
                sb.append("    ").append("-").append(" | ");
                break;
            case INSERT:
                sb.append("    ").append("+").append(" | ");
                break;
        }
        for (int colIndex = 0; colIndex < maxLengths.length; colIndex++) {
            String cellText = escapeCell(row.getCells().get(colIndex));
            String status = null;
            switch (row.getDiffType()) {
                case NONE:
                    status = cellResults.get(colIndex).getStatus();
                    break;
                case DELETE:
                    status = "skipped";
                    break;
                case INSERT:
                    status = "comment";
                    break;
            }
            sb.append(cellText);
            int padding = maxLengths[colIndex] - cellLengths[rowIndex][colIndex];
            padSpace(sb, padding);
            if (colIndex < maxLengths.length - 1) {
                sb.append(" | ");
            } else {
                sb.append(" |");
            }
        }
        out.addParagraphOfText(sb.toString());
        rowHeight++;
        Set<Result> seenResults = new HashSet<Result>();
        for (CellResult cellResult : cellResults) {
            for (Result result : cellResult.getResults()) {
                if (result.getErrorMessage() != null && !seenResults.contains(result)) {
                    printError(result);
                    rowHeight += result.getErrorMessage().split("\n").length;
                    seenResults.add(result);
                }
            }
        }
    }

    private void printError(Result result) {
        out.addParagraphOfText(indent(result.getErrorMessage(), "      "));
    }

    public void nextRow() {
        rowIndex++;
        rowsAbove = false;
    }

    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {
        throw new UnsupportedOperationException();
    }

    public void done() {
        // replay();
        // We're *not* closing the stream here.
        // https://github.com/cucumber/gherkin/issues/151
        // https://github.com/cucumber/cucumber-jvm/issues/96
    }

    public void close() {
        //replay();
    }

    private String escapeCell(String cell) {
        return cell.replaceAll("\\\\(?!\\|)", "\\\\\\\\").replaceAll("\\n", "\\\\n").replaceAll("\\|", "\\\\|");
    }

    public void docString(DocString docString) {
        out.addParagraphOfText("      \"\"\"");
        StringBuffer sb = new StringBuffer();
        sb.append(escapeTripleQuotes(indent(docString.getValue(), "      ")));
        sb.append("\n      \"\"\"");
        out.addParagraphOfText(sb.toString());
    }

    public void eof() {
    }

    private void calculateLocationIndentations() {
        int[] lineWidths = new int[1];
        int i = 0;

        List<BasicStatement> statements = new ArrayList<BasicStatement>();
        int maxLineWidth = 0;
        for (BasicStatement statement : statements) {
            int stepWidth = statement.getKeyword().length() + statement.getName().length();
            lineWidths[i++] = stepWidth;
            maxLineWidth = Math.max(maxLineWidth, stepWidth);
        }
        for (int lineWidth : lineWidths) {
            indentations.add(maxLineWidth - lineWidth);
        }
    }

    private void padSpace(StringBuffer buff, int indent) {
        for (int i = 0; i < indent; i++) {
            buff.append(" ");
        }
    }

    private void printDescription(String description, String indentation, boolean newline) {
        if (!"".equals(description)) {
            out.addParagraphOfText(indent(description, indentation));
            if (newline) out.addParagraphOfText("");
        }
    }

    private static final Pattern START = Pattern.compile("^", Pattern.MULTILINE);

    private static String indent(String s, String indentation) {
        return START.matcher(s).replaceAll(indentation);
    }

    private static final Pattern TRIPLE_QUOTES = Pattern.compile("\"\"\"", Pattern.MULTILINE);
    private static final Pattern WHITESPACES = Pattern.compile("\\s+");
    private static final String ESCAPED_TRIPLE_QUOTES = "\\\\\"\\\\\"\\\\\"";

    private static String escapeTripleQuotes(String s) {
        return TRIPLE_QUOTES.matcher(s).replaceAll(ESCAPED_TRIPLE_QUOTES);
    }

    private static String clean(String s) {
        return WHITESPACES.matcher(s).replaceAll(" ");
    }


}
