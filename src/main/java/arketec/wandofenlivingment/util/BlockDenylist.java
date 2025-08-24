package arketec.wandofenlivingment.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class BlockDenylist {
    private final List<String> literalNames;
    private final List<Pattern> regexPatterns;

    private BlockDenylist(List<String> literals, List<Pattern> regexes) {
        this.literalNames = literals;
        this.regexPatterns = regexes;
    }

    public static BlockDenylist fromConfig(List<? extends String> configValues) {
        List<String> literals = new ArrayList<>();
        List<Pattern> regexes = new ArrayList<>();

        for (String raw : configValues) {
            if (looksLikeRegex(raw)) {
                regexes.add(Pattern.compile(raw));
            } else {
                literals.add(raw);
            }
        }
        return new BlockDenylist(literals, regexes);
    }

    private static boolean looksLikeRegex(String s) {
        return s.contains(".*") || s.contains("(") || s.contains("[") || s.startsWith("^");
    }

    public boolean matches(String blockName) {
        if (literalNames.contains(blockName)) return true;
        for (Pattern p : regexPatterns) {
            if (p.matcher(blockName).matches()) return true;
        }
        return false;
    }
}
