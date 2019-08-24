package vsv.task2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownHeaderTransformer {
    private static final Pattern PATTERN = Pattern.compile("^\\s*(#{1,6})\\s*(.*?)\\s*$");

    public static String transform(String line) {
        if (line == null || line.isEmpty() || !line.trim().startsWith("#")) {
            return line;
        }

        Matcher matcher = PATTERN.matcher(line);
        if (matcher.matches()) {
            String hashes = matcher.group(1);
            String header = matcher.group(2);
            int level = hashes.length();
            String tag = "h" + level;
            return "<" + tag + ">" + header + "</" + tag + ">";
        }

        return line;
    }
}
