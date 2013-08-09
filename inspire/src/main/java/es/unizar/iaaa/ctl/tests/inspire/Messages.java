package es.unizar.iaaa.ctl.tests.inspire;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private ResourceBundle resourceBundle;

	/**
	 * Wraps the corresponding {@code ResourceBundle} of {@code object} class in the desired {@code language}.
	 * @param object an object
	 * @param language a language identifier
	 * @author Francisco J. Lopez-Pellicer
	 */
	public Messages(Object object, String language) {
		resourceBundle=ResourceBundle.getBundle(object.getClass().getCanonicalName(), new Locale(language));
	}

	/**
	 * See {@link java.util.ResourceBundle#getString(String)}.
	 * @author Francisco J. Lopez-Pellicer
	 */
	public String getString(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return "{missing key:" + key + "}";
        }
    }
	
	/**
	 * Gets a string for the given key from this resource bundle or one of its parents with parameter substitution.
	 * @param key the key
	 * @param params the parameters
	 * @author Francisco J. Lopez-Pellicer
	 * @return an interpolated string
	 */
    public String getString(String key, Object... params  ) {
        try {
            return MessageFormat.format(resourceBundle.getString(key), params);
        } catch (MissingResourceException e) {
            return "{missing key:" + key + "}";
        }
    }
}
