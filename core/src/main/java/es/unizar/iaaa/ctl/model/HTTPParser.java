package es.unizar.iaaa.ctl.model;


/**
 * HTTPParser is a parser that returns HTTP header information uses other
 * parser(s) to parse the content. It supports multipart messages. CTL built-in
 * parser
 * 
 * @author Francisco J. Lopez-Pellicer
 * 
 */
public interface HTTPParser extends Parser {

	/**
	 * Parser that applies only to standard MIME-type responses
	 * @param mimeType a mime type
	 * @param parser the parser
	 * @return this parser for chaining the configuration
	 */
	HTTPParser parse(String mimeType, Parser parser);

	/**
	 * Parser that applies only to a specific part number in a multipart MIME response
	 * @param part a part number starting with 1
	 * @param mimeType a matching mime type
	 * @param parser the parser
	 * @return this parser for chaining the configuration
	 */
	HTTPParser parse(int part, String mimeType, Parser parser);

}
