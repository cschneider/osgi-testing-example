package net.lr.example.testing;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import net.lr.example.testing.PrimeCalculatorServlet.Config;

/**
 * Servlet that displays the prime numbers until the given parameter max.
 */
@Component(
        service = Servlet.class
)
@Designate(ocd=Config.class)
public class PrimeCalculatorServlet extends HttpServlet {
    private static final long serialVersionUID = 691594999516730176L;
    
    @Reference
    private PrimeCalculator calculator;

    private String title;
    
    @Activate
    public void activate(Config config) {
        this.title = config.title();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer max = new Integer(req.getParameter("max"));
        PrintWriter writer = resp.getWriter();
        writer.println(title);
        calculator.until(max).forEach(writer::println);
    }
    
    @ObjectClassDefinition(name = "Prime servlet config")
    public @interface Config {
        @AttributeDefinition
        String osgi_http_whiteboard_servlet_pattern() default "/prime";

        @AttributeDefinition
        String title() default "Prime numbers";
    }
}
