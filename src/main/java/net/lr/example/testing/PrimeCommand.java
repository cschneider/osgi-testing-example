package net.lr.example.testing;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Command to print prime numbers on the felix gogo console
 */
@Component(
        service = PrimeCommand.class,
        property = {
                "osgi.command.scope=prime", //
                "osgi.command.function=calc"
        }
        )
public class PrimeCommand {
    @Reference
    private PrimeCalculator calculator;
    
    public void calc(int maxNum) {
        calculator.until(maxNum).forEach(System.out::println);
    }
}
