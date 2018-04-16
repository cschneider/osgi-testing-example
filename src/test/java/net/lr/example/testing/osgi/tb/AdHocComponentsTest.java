package net.lr.example.testing.osgi.tb;

import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.assertThat;

import java.util.stream.Stream;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.tinybundles.core.TinyBundles;

import net.lr.example.testing.IntStreamProcessing;
import net.lr.example.testing.PrimeCalculator;
import net.lr.example.testing.osgi.BaseTest;

/**
 * Sometimes you want to create some DS components just for the test.
 * This is easy as TinyBundles now allows to create bundles with DS components on the fly.
 */
@RunWith(PaxExam.class)
public class AdHocComponentsTest extends BaseTest {
    @Inject
    PrimeCalculator calculator;
    
    @Inject
    IntStreamProcessing doubler;

    @Configuration
    public Option[] configuration() {
        return new Option[] { 
                baseConfiguration(),
                
                // Create bundle with DS component on the fly
                BndDSOptions.dsBundle("doubling", 
                        TinyBundles.bundle().add(DoublingComponent.class)
                        )
                };
    }

    @Test
    public void test() {
        Stream<Integer> doubleStream = doubler.process(calculator.until(10));
        assertThat(doubleStream.toArray(), arrayContaining(4, 6, 10, 14));
    }
}
