package com.tealcube.java.maven.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.nio.charset.Charset;

public abstract class Markup2HtmlAbstractMojo extends AbstractMojo {

    protected static final Charset UTF8 = Charset.forName("UTF-8");
    protected static final String DOCUMENT_START = "<!DOCTYPE HTML>\n<html>";
    protected static final String DOCUMENT_END = "\n</html>\n";
    protected static final String HEAD_START = "<head>\n";
    protected static final String HEAD_END = "</head>\n";
    protected static final String BODY_START = "<body>\n";
    protected static final String BODY_END = "</body>\n";
    protected static final String JAVASCRIPT_TAG = "<script src=\"%s\"></script>\n";
    protected static final String CSS_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\" />";
    
    @Parameter(property = Markup2HtmlPlugin.PREFIX + "sourceDirectory", required = true, defaultValue = "${project.basedir}/src/main/markup")
    protected File sourceDirectory;

    @Parameter(property = Markup2HtmlPlugin.PREFIX + "outputDirectory", required = true, defaultValue = "${project.build.directory}/markup")
    protected File outputDirectory;

    @Parameter(property = Markup2HtmlPlugin.PREFIX + "jsDirectory", required = false, defaultValue = "${project.basedir}/src/main/js")
    protected File jsDirectory;

    @Parameter(property = Markup2HtmlPlugin.PREFIX + "cssDirectory", required = false, defaultValue = "${project.basedir}/src/main/css")
    protected File cssDirectory;

    @Parameter(property = Markup2HtmlPlugin.PREFIX + "fontsDirectory", required = false, defaultValue = "${project.basedir}/src/main/fonts")
    protected File fontsDirectory;

    @Parameter(property = Markup2HtmlPlugin.PREFIX + "skip", required = false)
    protected boolean skip = false;

}
