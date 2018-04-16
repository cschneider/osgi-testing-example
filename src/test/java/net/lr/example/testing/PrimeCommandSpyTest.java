package net.lr.example.testing;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 * Shows how to inject private service dependencies using mockito.
 * In this case we inject the actual calculator using the @Spy annotation
 */
public class PrimeCommandSpyTest {

    @InjectMocks
    PrimeCommand command;
    
    @Spy
    PrimeCalculator calculator = new PrimeCalculator();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void test() {
        command.calc(3);
    }
}
