package net.lr.example.testing.osgi;

import static org.hamcrest.Matchers.notNullValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.awaitility.Awaitility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.osgi.service.cm.ConfigurationAdmin;

@RunWith(PaxExam.class)
public class PrimeServletTest extends BaseTest {
    @Inject
    ConfigurationAdmin configAdmin;

    @Configuration
    public Option[] configuration() {
        return new Option[] { 
                baseConfiguration(),
                httpService()
                };
    }

    @Test
    public void test() throws URISyntaxException, MalformedURLException, IOException {
        URL testUrl = new URL("http://localhost:8080/prime?max=10");
        /*
         *  Retry to get the content for up to 10 seconds by default. 
         *  Use this if you have timing issues that can not be avoided using explicit service dependencies.
         */
        String content = Awaitility.await().ignoreExceptions().until(() -> getContent(testUrl), notNullValue());
        System.out.println(content);
    }

    private String getContent(URL testUrl) throws IOException, MalformedURLException {
        URLConnection connection = testUrl.openConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
