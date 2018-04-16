package net.lr.example.testing;

import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.assertThat;

import java.util.stream.Stream;

import org.junit.Test;

/**
 * Simple test without any magic. Use this for classes that do not need special dependencies
 */
public class PrimeCalculatorTest {

    @Test
    public void test() {
        Stream<Integer> primes = new PrimeCalculator().until(3);
        assertThat(primes.toArray(), arrayContaining(2,3));
    }
}
