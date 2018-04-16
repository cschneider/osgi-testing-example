package net.lr.example.testing.osgi;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;

import net.lr.example.testing.PrimeCommand;

@RunWith(PaxExam.class)
public class PrimeCommandTest extends BaseTest {
    @Inject
    PrimeCommand command;

    @Configuration
    public Option[] configuration() {
        return new Option[] { 
                baseConfiguration()
                };
    }

    @Test
    public void test() {
        command.calc(100);
    }
}
