/*
 * Copyright 2007-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.tasks.compile;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import org.gradle.api.Incubating;
import org.gradle.api.Nullable;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Compilation options to be passed to the Groovy compiler.
 */
public class GroovyCompileOptions extends AbstractOptions {
    private static final long serialVersionUID = 0;
    private static final ImmutableSet<String> EXCLUDE_FROM_ANT_PROPERTIES =
            ImmutableSet.of("forkOptions", "optimizationOptions", "stubDir", "keepStubs", "fileExtensions");

    private boolean failOnError = true;

    private boolean verbose;

    private boolean listFiles;

    private String encoding = "UTF-8";

    private boolean fork = true;

    private boolean keepStubs;

    private List<String> fileExtensions = ImmutableList.of("java", "groovy");

    private GroovyForkOptions forkOptions = new GroovyForkOptions();

    private Map<String, Boolean> optimizationOptions = Maps.newHashMap();

    private File stubDir;

    private File configurationScript;

    private boolean javaAnnotationProcessing;

    /**
     * Tells whether the compilation task should fail if compile errors occurred. Defaults to {@code true}.
     */
    public boolean isFailOnError() {
        return failOnError;
    }

    /**
     * Sets whether the compilation task should fail if compile errors occurred. Defaults to {@code true}.
     */
    public void setFailOnError(boolean failOnError) {
        this.failOnError = failOnError;
    }

    /**
     * Tells whether to turn on verbose output. Defaults to {@code false}.
     */
    public boolean isVerbose() {
        return verbose;
    }

    /**
     * Sets whether to turn on verbose output. Defaults to {@code false}.
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * Tells whether to print which source files are to be compiled. Defaults to {@code false}.
     */
    public boolean isListFiles() {
        return listFiles;
    }

    /**
     * Sets whether to print which source files are to be compiled. Defaults to {@code false}.
     */
    public void setListFiles(boolean listFiles) {
        this.listFiles = listFiles;
    }

    /**
     * Tells the source encoding. Defaults to {@code UTF-8}.
     */
    @Input
    public String getEncoding() {
        return encoding;
    }

    /**
     * Sets the source encoding. Defaults to {@code UTF-8}.
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * Tells whether to run the Groovy compiler in a separate process. Defaults to {@code true}.
     */
    public boolean isFork() {
        return fork;
    }

    /**
     * Sets whether to run the Groovy compiler in a separate process. Defaults to {@code true}.
     */
    public void setFork(boolean fork) {
        this.fork = fork;
    }

    /**
     * A Groovy script file that configures the compiler, allowing extensive control over how the code is compiled.
     * <p>
     * The script is executed as Groovy code, with the following context:
     * <ul>
     * <li>The instance of <a href="http://docs.groovy-lang.org/latest/html/gapi/org/codehaus/groovy/control/CompilerConfiguration.html">CompilerConfiguration</a> available as the {@code configuration} variable.</li>
     * <li>All static members of <a href="http://docs.groovy-lang.org/latest/html/gapi/org/codehaus/groovy/control/customizers/builder/CompilerCustomizationBuilder.html">CompilerCustomizationBuilder</a> pre imported.</li>
     * </ul>
     * </p>
     * <p>
     * This facilitates the following pattern:
     * <pre>
     * withConfig(configuration) {
     *   // use compiler configuration DSL here
     * }
     * </pre>
     * </p>
     * <p>
     * For example, to activate type checking for all Groovy classes…
     * <pre>
     * import groovy.transform.TypeChecked
     *
     * withConfig(configuration) {
     *     ast(TypeChecked)
     * }
     * </pre>
     * </p>
     * <p>
     * Please see <a href="http://groovy.codehaus.org/Advanced+compiler+configuration#Advancedcompilerconfiguration-Thecustomizationbuilder">the Groovy compiler customization builder documentation</a>
     * for more information about the compiler configuration DSL.
     * </p>
     * <p>
     * <b>This feature is only available if compiling with Groovy 2.1 or later.</b>
     * </p>
     * @see <a href="http://docs.groovy-lang.org/latest/html/gapi/org/codehaus/groovy/control/CompilerConfiguration.html">CompilerConfiguration</a>
     * @see <a href="http://docs.groovy-lang.org/latest/html/gapi/org/codehaus/groovy/control/customizers/builder/CompilerCustomizationBuilder.html">CompilerCustomizationBuilder</a>
     */
    @InputFile
    @Incubating
    @Optional
    public File getConfigurationScript() {
        return configurationScript;
    }

    /**
     * Sets the path to the groovy configuration file.
     *
     * @see #getConfigurationScript()
     */
    @Incubating
    public void setConfigurationScript(@Nullable File configurationFile) {
        this.configurationScript = configurationFile;
    }

    /**
     * Tells whether Java annotation processors should process annotations on stubs. When set to <code>true</code>, stubs will be unconditionnaly generated
     * for all Groovy sources, and Java annotations processors will be executed on those stubs. This allow some annotation processors normally designed to
     * work on Java sources to be able to process Groovy sources too.
     *
     * When this option is set to <code>false</code>, the annotation processors found on classpath are only going to process Java sources of the Groovy source set.
     * It is different from using the compiler option <code>-proc:none</code> that will disable annotation processing altogether, be it on Java sources or Groovy stubs.
     *
     * Defaults to {@code false}.
     */
    @Incubating
    @Input
    public boolean isJavaAnnotationProcessing() {
        return javaAnnotationProcessing;
    }

    /**
     * Sets whether Java annotation processors should process annotations on stubs. Defaults to {@code false}.
     */
    @Incubating
    public void setJavaAnnotationProcessing(boolean javaAnnotationProcessing) {
        this.javaAnnotationProcessing = javaAnnotationProcessing;
    }

    /**
     * Returns options for running the Groovy compiler in a separate process. These options only take effect
     * if {@code fork} is set to {@code true}.
     */
    public GroovyForkOptions getForkOptions() {
        return forkOptions;
    }

    /**
     * Sets options for running the Groovy compiler in a separate process. These options only take effect
     * if {@code fork} is set to {@code true}.
     */
    public void setForkOptions(GroovyForkOptions forkOptions) {
        this.forkOptions = forkOptions;
    }

    /**
     * Returns optimization options for the Groovy compiler. Allowed values for an option are {@code true} and {@code false}.
     * Only takes effect when compiling against Groovy 1.8 or higher.
     *
     * <p>Known options are:
     *
     * <dl>
     *     <dt>indy
     *     <dd>Use the invokedynamic bytecode instruction. Requires JDK7 or higher and Groovy 2.0 or higher. Disabled by default.
     *     <dt>int
     *     <dd>Optimize operations on primitive types (e.g. integers). Enabled by default.
     *     <dt>all
     *     <dd>Enable or disable all optimizations. Note that some optimizations might be mutually exclusive.
     * </dl>
     */
    public Map<String, Boolean> getOptimizationOptions() {
        return optimizationOptions;
    }

    /**
     * Sets optimization options for the Groovy compiler. Allowed values for an option are {@code true} and {@code false}.
     * Only takes effect when compiling against Groovy 1.8 or higher.
     */
    public void setOptimizationOptions(Map<String, Boolean> optimizationOptions) {
        this.optimizationOptions = optimizationOptions;
    }

    /**
     * Returns the directory where Java stubs for Groovy classes will be stored during Java/Groovy joint
     * compilation. Defaults to {@code null}, in which case a temporary directory will be used.
     */
    public File getStubDir() {
        return stubDir;
    }

    /**
     * Sets the directory where Java stubs for Groovy classes will be stored during Java/Groovy joint
     * compilation. Defaults to {@code null}, in which case a temporary directory will be used.
     */
    public void setStubDir(File stubDir) {
        this.stubDir = stubDir;
    }

    /**
     * Returns the list of acceptable source file extensions. Only takes effect when compiling against
     * Groovy 1.7 or higher. Defaults to {@code ImmutableList.of("java", "groovy")}.
     */
    @Input
    @Incubating
    public List<String> getFileExtensions() {
        return fileExtensions;
    }

    /**
     * Sets the list of acceptable source file extensions. Only takes effect when compiling against
     * Groovy 1.7 or higher. Defaults to {@code ImmutableList.of("java", "groovy")}.
     */
    @Incubating
    public void setFileExtensions(List<String> fileExtensions) {
        this.fileExtensions = fileExtensions;
    }

    /**
     * Tells whether Java stubs for Groovy classes generated during Java/Groovy joint compilation
     * should be kept after compilation has completed. Useful for joint compilation debugging purposes.
     * Defaults to {@code false}.
     */
    public boolean isKeepStubs() {
        return keepStubs;
    }

    /**
     * Sets whether Java stubs for Groovy classes generated during Java/Groovy joint compilation
     * should be kept after compilation has completed. Useful for joint compilation debugging purposes.
     * Defaults to {@code false}.
     */
    public void setKeepStubs(boolean keepStubs) {
        this.keepStubs = keepStubs;
    }

    /**
     * Convenience method to set {@link GroovyForkOptions} with named parameter syntax.
     * Calling this method will set {@code fork} to {@code true}.
     */
    public GroovyCompileOptions fork(Map<String, Object> forkArgs) {
        fork = true;
        forkOptions.define(forkArgs);
        return this;
    }

    @Override
    protected boolean excludeFromAntProperties(String fieldName) {
        return EXCLUDE_FROM_ANT_PROPERTIES.contains(fieldName);
    }

    /**
     * Internal method.
     */
    public Map<String, Object> optionMap() {
        Map<String, Object> map = super.optionMap();
        map.putAll(forkOptions.optionMap());
        if (optimizationOptions.containsKey("indy")) {
            map.put("indy", optimizationOptions.get("indy"));
        }
        return map;
    }
}
