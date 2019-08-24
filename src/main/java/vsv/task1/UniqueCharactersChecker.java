package vsv.task1;

public class UniqueCharactersChecker {
    private final static int MAX_LENGTH = 128;

    public static boolean isUnique(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        if (str.length() > MAX_LENGTH) {
            return false;
        }

        boolean[] chars = new boolean[MAX_LENGTH];

        for (int i = 0; i < str.length(); i++) {
            int index = str.charAt(i);
            if (chars[index]) {
                return false;
            }
            chars[index] = true;
        }

        return true;
    }

    public static boolean isUniqueByStream(String str) {
        return str == null || str.isEmpty() || str.length() == str.chars().distinct().count();
    }
}
