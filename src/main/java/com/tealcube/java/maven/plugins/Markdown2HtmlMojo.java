package com.tealcube.java.maven.plugins;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.pegdown.PegDownProcessor;

import java.io.File;
import java.io.IOException;

@Mojo(name = "markdown2html")
public class Markdown2HtmlMojo extends Markup2HtmlAbstractMojo {
    public static final String MARKDOWN_EXTENSION = ".md";
    public static final String HTML_EXTENSION = ".html";
    public static final String JS_EXTENSION = ".js";
    public static final String CSS_EXTENSION = ".css";

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (sourceDirectory == null) {
            throw new MojoExecutionException("sourceDirectory must be defined!");
        }
        if (outputDirectory == null) {
            throw new MojoExecutionException("outputDirectory must be defined!");
        }
        if (sourceDirectory.list() == null) {
            getLog().info("source directory is empty, not doing anything");
            return;
        }
        for (String filename : sourceDirectory.list()) {
            if (filename == null) {
                continue;
            }
            if (!filename.endsWith(MARKDOWN_EXTENSION)) {
                continue;
            }
            File markdownFile = new File(sourceDirectory, filename);
            File xhtmlFile = new File(outputDirectory, filename.replace(MARKDOWN_EXTENSION, HTML_EXTENSION));
            getLog().info("Parsing " + markdownFile.getAbsolutePath() + " into " + xhtmlFile.getAbsolutePath());

            PegDownProcessor processor = new PegDownProcessor();

            String markdown;
            try {
                markdown = FileUtils.readFileToString(markdownFile, UTF8);
            } catch (IOException e) {
                throw new MojoFailureException("Unable to read " + markdownFile.getName(), e);
            }

            String processed = processor.markdownToHtml(markdown);
            StringBuilder xhtmlBuilder = new StringBuilder(DOCUMENT_START).append(HEAD_START);
            if (jsDirectory != null) {
                for (String jsFilename : jsDirectory.list()) {
                    if (jsFilename == null) {
                        continue;
                    }
                    if (!jsFilename.endsWith(JS_EXTENSION)) {
                        continue;
                    }
                    xhtmlBuilder = xhtmlBuilder.append(String.format(JAVASCRIPT_TAG, jsDirectory.getName() + "/" + jsFilename));
                    try {
                        FileUtils.copyFileToDirectory(new File(jsDirectory, jsFilename), new File(outputDirectory,
                                                                                                  jsDirectory
                                                                                                          .getName()));
                    } catch (IOException e) {
                        throw new MojoFailureException("Unable to copy file to directory!", e);
                    }
                }
            }
            if (cssDirectory != null) {
                for (String cssFilename : cssDirectory.list()) {
                    if (cssFilename == null) {
                        continue;
                    }
                    if (!cssFilename.endsWith(CSS_EXTENSION)) {
                        continue;
                    }
                    xhtmlBuilder = xhtmlBuilder.append(String.format(CSS_TAG, cssDirectory.getName() + "/" + cssFilename));
                    try {
                        FileUtils.copyFileToDirectory(new File(cssDirectory, cssFilename), new File(outputDirectory,
                                                                                                    cssDirectory
                                                                                                            .getName()));
                    } catch (IOException e) {
                        throw new MojoFailureException("Unable to copy file to directory!", e);
                    }
                }
            }

            xhtmlBuilder = xhtmlBuilder.append(HEAD_END);
            xhtmlBuilder = xhtmlBuilder.append(BODY_START);
            xhtmlBuilder = xhtmlBuilder.append(processed);
            xhtmlBuilder = xhtmlBuilder.append(BODY_END);
            xhtmlBuilder = xhtmlBuilder.append(DOCUMENT_END);
            String xhtml = xhtmlBuilder.toString();
            try {
                FileUtils.writeStringToFile(xhtmlFile, xhtml, UTF8, false);
            } catch (IOException e) {
                throw new MojoFailureException("Unable to write " + xhtmlFile.getName(), e);
            }
        }
    }
}
