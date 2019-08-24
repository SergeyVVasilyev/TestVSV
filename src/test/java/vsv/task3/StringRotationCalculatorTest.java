package vsv.task3;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringRotationCalculatorTest {

    @Test
    public void testEmpty() {
        assertEquals(-1, (long) StringRotationCalculator.calculate(null, null));
        assertEquals(-1, (long) StringRotationCalculator.calculate("", ""));
    }

//    "coffee", "eecoff" => 2 "eecoff", "coffee" => 4 "moose", "Moose" => - 1 "isn't", "'tisn" => 2 "Esham", "Esham" => 0 "dog", "god" => - 1

    @Test
    public void testCalculate() {
        assertEquals(2, (long) StringRotationCalculator.calculate("coffee", "eecoff"));
        assertEquals(4, (long) StringRotationCalculator.calculate("eecoff", "coffee"));
        assertEquals(-1, (long) StringRotationCalculator.calculate("moose", "Moose"));
        assertEquals(2, (long) StringRotationCalculator.calculate("isn't", "'tisn"));
        assertEquals(0, (long) StringRotationCalculator.calculate("Esham", "Esham"));
        assertEquals(-1, (long) StringRotationCalculator.calculate("dog", "god"));
    }

}