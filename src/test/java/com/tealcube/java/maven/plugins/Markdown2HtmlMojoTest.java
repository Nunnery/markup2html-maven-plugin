package com.tealcube.java.maven.plugins;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class Markdown2HtmlMojoTest {

    private static final String POM_FILE = "src/test/resources/pom.xml";
    private static final String MOJO_GOAL = "markdown2html";

    @Rule
    public MojoRule rule = new MojoRule() {
        @Override
        protected void before() throws Throwable {
        }

        @Override
        protected void after() {
        }
    };

    @Test
    public void testSomething() throws Exception {
        Mojo mojo = rule.lookupMojo(MOJO_GOAL, POM_FILE);
        Assert.assertNotNull(mojo);
        Markdown2HtmlMojo markdown2HtmlMojo = (Markdown2HtmlMojo) mojo;
        markdown2HtmlMojo.execute();
    }
}