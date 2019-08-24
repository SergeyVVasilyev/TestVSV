package vsv.task1;

import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UniqueCharactersCheckerTest {
    private static final String ALL_128_ASCII = IntStream.rangeClosed(0, 127).mapToObj(i -> (char) i).map(String::valueOf).collect(Collectors.joining());
    private static final String ALL_128_ASCII_DOUBLE = ALL_128_ASCII + ALL_128_ASCII;
    private static final String STR_POSITIVE = "aAbBcC012";
    private static final String STR_NEGATIVE_DOUBLE_A = "aAbBcC01A2";

    @Test
    public void testEmpty() {
        assertTrue(UniqueCharactersChecker.isUnique(null));
        assertTrue(UniqueCharactersChecker.isUnique(""));
        assertTrue(UniqueCharactersChecker.isUniqueByStream(null));
        assertTrue(UniqueCharactersChecker.isUniqueByStream(""));
    }

    @Test
    public void testIsUnique() {
        assertTrue(UniqueCharactersChecker.isUnique(ALL_128_ASCII));
        assertTrue(UniqueCharactersChecker.isUnique(STR_POSITIVE));
        assertFalse(UniqueCharactersChecker.isUnique(ALL_128_ASCII_DOUBLE));
        assertFalse(UniqueCharactersChecker.isUnique(STR_NEGATIVE_DOUBLE_A));
    }

    @Test
    public void testIsUniqueByStream() {
        assertTrue(UniqueCharactersChecker.isUniqueByStream(ALL_128_ASCII));
        assertTrue(UniqueCharactersChecker.isUniqueByStream(STR_POSITIVE));
        assertFalse(UniqueCharactersChecker.isUnique(ALL_128_ASCII_DOUBLE));
        assertFalse(UniqueCharactersChecker.isUniqueByStream(STR_NEGATIVE_DOUBLE_A));
    }
}