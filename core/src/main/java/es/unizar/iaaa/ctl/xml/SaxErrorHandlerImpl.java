package es.unizar.iaaa.ctl.xml;

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/**
 * Handles errors arising while processing XML resources and records the numbers
 * of error and warning notifications received.
 * 
 */
public class SaxErrorHandlerImpl implements ErrorHandler {
    private int ErrorCount = 0;

    private boolean ignoreErrors = false;
    
    private boolean ignoreWarnings = false;
    
    private int WarningCount = 0;

    private String Prefix = "";

	private static final Logger logger = Logger
			.getLogger(SaxErrorHandlerImpl.class);

	public SaxErrorHandlerImpl(String role, boolean ignoreErrors, boolean ignoreWarnings) {
		this.ignoreErrors = ignoreErrors;
		this.ignoreWarnings = ignoreWarnings;
        setRole(role);
    }

    public void setRole(String role) {
        if (role != null) {
            Prefix = role + " ";
        }
    }

    public String getErrorCounts() {
        String msg = "";
        if (ErrorCount > 0 || WarningCount > 0) {
            if (ErrorCount > 0) {
                msg += ErrorCount + " error" + (ErrorCount == 1 ? "" : "s");
                if (WarningCount > 0)
                    msg += " and ";
            }
            if (WarningCount > 0) {
                msg += WarningCount + " warning"
                        + (WarningCount == 1 ? "" : "s");
            }
        } else {
            msg = "No errors or warnings";
        }
        msg += " detected.";
        return msg;
    }

    private void error(String type, SAXParseException e) {
    	StringBuffer sb = new StringBuffer();
    	sb.append(type);
        if (e.getLineNumber() >= 0) {
            sb.append(" at line " + e.getLineNumber());
            if (e.getColumnNumber() >= 0) {
            	sb.append(", column " + e.getColumnNumber());
            }
            if (e.getSystemId() != null) {
            	sb.append(" of " + e.getSystemId());
            }
        } else {
            if (e.getSystemId() != null) {
            	sb.append(" in " + e.getSystemId());
            }
        }
        sb.append(":");
        sb.append("  " + e.getMessage());
        if (logger.isDebugEnabled()) {
        	logger.debug(sb);
        }
    }

    public int getErrorCount() {
        return ErrorCount;
    }

    public int getWarningCount() {
        return WarningCount;
    }

    @Override
	public void error(SAXParseException exception) {
    	if (!ignoreErrors) {
    		error(Prefix + "error", exception);
    		ErrorCount++;
    	}
    }

    @Override
	public void fatalError(SAXParseException exception) {
    	if (!ignoreErrors) {
    		error("Fatal " + Prefix + "error", exception);
    		ErrorCount++;
    	}
    }

    @Override
	public void warning(SAXParseException exception) {
    	if (!ignoreWarnings) {
    		error(Prefix + "warning", exception);
    		WarningCount++;
    	}
    }
}
