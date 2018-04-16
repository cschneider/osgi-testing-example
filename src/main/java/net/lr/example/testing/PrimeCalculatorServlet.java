package net.lr.example.testing;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * Servlet that displays the prime numbers until the given parameter max.
 */
@Component(
        service = Servlet.class,
        property = {
                HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN + "=" + "/prime",
        }
)
public class PrimeCalculatorServlet extends HttpServlet {
    private static final long serialVersionUID = 691594999516730176L;
    
    @Reference
    private PrimeCalculator calculator;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer max = new Integer(req.getParameter("max"));
        PrintWriter writer = resp.getWriter();
        calculator.until(max).forEach(writer::println);
    }
}
