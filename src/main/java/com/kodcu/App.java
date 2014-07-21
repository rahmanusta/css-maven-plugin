package com.kodcu;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by usta on 15.07.2014.
 */
@Mojo(name = "minify", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class App extends AbstractMojo {

    private ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");

    @Parameter
    private String inputPath;

    @Parameter
    private List<String> styles;

    @Parameter
    private String outputPath;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        try {
            startExecution();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws ScriptException, IOException, URISyntaxException {

        new App().startExecution();
    }

    private void startExecution() throws IOException, ScriptException {

        engine.eval("var YAHOO = {}");
        engine.eval("var window = {}");
        engine.eval(IOHelper.toString("/cssmin.js"));

        getLog().info("Started Minifying CSS Files");

        StringBuffer buffer = new StringBuffer();

        styles
                .stream()
                .map(jss -> Paths.get(inputPath).resolve(jss))
                .map(IOHelper::readAllBytes)
                .map(bayt -> new String(bayt, Charset.forName("UTF-8")))
                .forEach(buffer::append);

        String concanatedStyleSheets = buffer.toString();
        engine.put("cssInput", concanatedStyleSheets);

        String cssOutput = (String) engine.eval("YAHOO.compressor.cssmin(cssInput)");

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(outputPath));) {

            bufferedWriter.write(cssOutput);
        }
        getLog().info("Completed Minifying CSS Files");
    }

}
