package net.lr.example.testing.osgi;
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import static org.ops4j.pax.exam.CoreOptions.bundle;
import static org.ops4j.pax.exam.CoreOptions.composite;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;

import javax.inject.Inject;

import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.osgi.framework.BundleContext;

public class BaseTest {
    @Inject
    BundleContext context;

    public Option baseConfiguration() {
        return CoreOptions.composite(
                // This gives a fast fail when any bundle is unresolved
                systemProperty("pax.exam.osgi.unresolved.fail").value("true"),
                logback(),
                junit(),
                mavenBundle("org.apache.felix", "org.apache.felix.scr", "2.0.14"),
                mavenBundle("org.apache.felix", "org.apache.felix.configadmin", "1.8.16"),
                httpService(),
                bundle("reference:file:target/classes/")
        );
    }

    /**
     * Shows how to use logback in pax exam. 
     * This only works when the default logging support is disabled in the exam.properties file
     */
    public Option logback() {
        return composite(systemProperty("logback.configurationFile").value("src/test/resources/logback.xml"),
                mavenBundle().groupId("org.slf4j").artifactId("slf4j-api").version("1.7.6"),
                mavenBundle().groupId("ch.qos.logback").artifactId("logback-core").version("1.0.13"),
                mavenBundle().groupId("ch.qos.logback").artifactId("logback-classic").version("1.0.13"));
    }

    /**
     * We create our own junit option to also provide hamcrest and Awaitility support 
     */
    public Option junit() {
        return composite(systemProperty("pax.exam.invoker").value("junit"),
                bundle("link:classpath:META-INF/links/org.ops4j.pax.tipi.junit.link"),
                bundle("link:classpath:META-INF/links/org.ops4j.pax.exam.invoker.junit.link"),
                mavenBundle().groupId("org.apache.servicemix.bundles")
                        .artifactId("org.apache.servicemix.bundles.hamcrest").version("1.3_1"),
                mavenBundle().groupId("org.awaitility").artifactId("awaitility").version("3.1.0"));
    }
    
    public Option httpService() {
        return CoreOptions.composite(
                mavenBundle("org.apache.felix", "org.apache.felix.http.servlet-api", "1.1.2"),
                mavenBundle("org.apache.felix", "org.apache.felix.http.jetty", "3.4.8")
                );
    }

}
