package net.lr.example.testing;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.osgi.util.converter.Converters;

import net.lr.example.testing.PrimeCalculatorServlet.Config;

public class PrimeCalculatorServletTest {

    @Mock
    private HttpServletRequest req;
    
    @Mock
    private HttpServletResponse resp;
    
    @Spy
    private PrimeCalculator calculator = new PrimeCalculator();

    @InjectMocks
    private PrimeCalculatorServlet servlet;
    
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() throws ServletException, IOException {
        when(req.getParameter(Mockito.eq("max"))).thenReturn("10");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(resp.getWriter()).thenReturn(writer);
        
        Config config = createConfig();
        servlet.activate(config);
        servlet.doGet(req, resp);
        
        assertThat(stringWriter.toString(),
                equalTo("My numbers\n2\n3\n5\n7\n"));
    }

    /**
     * Before OSGi R7 it was hard to mock a type safe config as each attribute
     * had to be populated with behaviour and the default were lost.
     * 
     * Now there is the converter spec which allows to convert a map to a 
     * type safe config while retaining default values.
     */
    private Config createConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put("title", "My numbers");
        return Converters.standardConverter().convert(props).to(Config.class);
    }
}
