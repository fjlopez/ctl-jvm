package cucumber.runtime.io;

import java.io.File;
import java.util.Iterator;
import java.util.regex.Pattern;

public class GlobResourceIterable implements Iterable<Resource> {

	    private final File root;
	    private final File file;
	    private final String suffix;
	    private final Pattern regex;

	    public GlobResourceIterable(File root, File file, Pattern regex, String suffix) {
	        this.root = root;
	        this.file = file;
	        this.suffix = suffix;
	        this.regex = regex;
	    }

	    @Override
	    public Iterator<Resource> iterator() {
	        return new GlobResourceIterator(root, file, regex, suffix);
	    }

}
