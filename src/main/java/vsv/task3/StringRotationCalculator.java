package vsv.task3;

public class StringRotationCalculator {

    public static Integer calculate(String line1, String line2) {
        if (line1 == null || line1.isEmpty() || line2 == null || line2.isEmpty()
                || line1.length() != line2.length()) {
            return -1;
        }
        if (line1.equals(line2)) {
            return 0;
        }
        for (int i = 0; i < line1.length(); i++) {
            line1 = line1.charAt(line1.length() - 1) + line1.substring(0, line1.length() - 1);
            if (line1.equals(line2)) {
                return i + 1;
            }
        }
        return -1;
    }
}
