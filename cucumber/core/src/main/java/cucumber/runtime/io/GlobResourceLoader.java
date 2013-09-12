package cucumber.runtime.io;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GlobResourceLoader implements ResourceLoader {

	@Override
	public Iterable<Resource> resources(String path, String suffix) {
		Matcher m = Pattern.compile("([^;]*);glob:(.*)").matcher(path);
		if (m.find()) {
	        File root = new File(m.group(1));
	        return new GlobResourceIterable(root, root, Pattern.compile(convertGlobToRegex(m.group(2))), suffix);
		} else {
	        File root = new File(path);
	        return new FileResourceIterable(root, root, suffix);
		}
	}

	public  final String convertGlobToRegex(String pattern) {
	    StringBuilder sb = new StringBuilder(pattern.length());
	    int inGroup = 0;
	    int inClass = 0;
	    int firstIndexInClass = -1;
	    char[] arr = pattern.toCharArray();
	    for (int i = 0; i < arr.length; i++) {
	        char ch = arr[i];
	        switch (ch) {
	            case '\\':
	                if (++i >= arr.length) {
	                    sb.append('\\');
	                } else {
	                    char next = arr[i];
	                    switch (next) {
	                        case ',':
	                            // escape not needed
	                            break;
	                        case 'Q':
	                        case 'E':
	                            // extra escape needed
	                            sb.append('\\');
	                        default:
	                            sb.append('\\');
	                    }
	                    sb.append(next);
	                }
	                break;
	            case '*':
	                if (inClass == 0)
	                    sb.append(".*");
	                else
	                    sb.append('*');
	                break;
	            case '?':
	                if (inClass == 0)
	                    sb.append('.');
	                else
	                    sb.append('?');
	                break;
	            case '[':
	                inClass++;
	                firstIndexInClass = i+1;
	                sb.append('[');
	                break;
	            case ']':
	                inClass--;
	                sb.append(']');
	                break;
	            case '.':
	            case '(':
	            case ')':
	            case '+':
	            case '|':
	            case '^':
	            case '$':
	            case '@':
	            case '%':
	                if (inClass == 0 || (firstIndexInClass == i && ch == '^'))
	                    sb.append('\\');
	                sb.append(ch);
	                break;
	            case '!':
	                if (firstIndexInClass == i)
	                    sb.append('^');
	                else
	                    sb.append('!');
	                break;
	            case '{':
	                inGroup++;
	                sb.append('(');
	                break;
	            case '}':
	                inGroup--;
	                sb.append(')');
	                break;
	            case ',':
	                if (inGroup > 0)
	                    sb.append('|');
	                else
	                    sb.append(',');
	                break;
	            default:
	                sb.append(ch);
	        }
	    }
	    return sb.toString();
	}
}
