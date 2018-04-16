package net.lr.example.testing;

import static java.util.stream.IntStream.rangeClosed;

import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * Calculate prime numbers
 */
@Component(
        service = PrimeCalculator.class
        )
public class PrimeCalculator {
    
    public Stream<Integer> until(int max) {
        return rangeClosed(2, max)
                .filter(this::isPrime)
                .mapToObj(Integer::new);
    }
    
    public boolean isPrime(Integer number) {
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

}
