package net.lr.example.testing.osgi;

import javax.inject.Inject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import net.lr.example.testing.osgi.before.AfterOsgi;
import net.lr.example.testing.osgi.before.BeforeOsgi;
import net.lr.example.testing.osgi.before.ExtPaxExam;

@RunWith(ExtPaxExam.class)
public class HooksTest extends BaseTest {
    
    @Inject
    Runnable ru;

    @Configuration
    public Option[] configuration() {
        return new Option[] { 
                baseConfiguration()
                };
    }
    
    /**
     * Works because of our ExtPaxExam runner. Will be executed outside OSGi in the process
     * that runs junit (not in the forked process).
     */
    @BeforeOsgi
    public static void startServers() {
        System.out.println("Start servers");
        // A typical case is to start other docker or OSGi containers needed for a test here
    }
    
    /**
     * Will be executed inisde OSGi but before @Inject so we can register services that are needed for @Inject
     * at this point
     */
    @BeforeClass
    public static void beforeClass() {
        BundleContext bContext = FrameworkUtil.getBundle(HooksTest.class).getBundleContext();
        bContext.registerService(Runnable.class, () -> {}, null);
        System.out.println("beforeClass");
    }
    
    /**
     * Executed inside OSGi before each test method 
     */
    @Before
    public void before() {
        System.out.println("before");
    }
    
    @Test
    public void test() {
        System.out.println("In test");
    }

    /**
     * Executed inside OSGi after each test method (even if test fails).
     * Will not be executed if @Inject times out
     */
    @After
    public void after() {
        System.out.println("after");
    }

    /**
     * Will be executed inside OSGi after the whole class. Will also be executed if @Inject times out.
     */
    @AfterClass
    public static void afterClass() {
        System.out.println("afterClass");
    }
    
    /**
     * Works because of our ExtPaxExam runner. Will be executed outside OSGi in the process
     * that runs junit (not in the forked process).
     */
    @AfterOsgi
    public static void stopServers() {
        System.out.println("Stop servers");
    }
}
