/*
 * Extended LessCSS Compiler
 * http://lesscss-extended-compiler.projects.gabrys.biz/
 *
 * Copyright (c) 2015 Adam Gabryś
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 *  - a copy of the License at project page
 *  - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
package biz.gabrys.lesscss.extended.compiler;

import biz.gabrys.lesscss.compiler.LessCompiler;
import biz.gabrys.lesscss.compiler.LessCompilerImpl;
import biz.gabrys.lesscss.extended.compiler.control.processor.DoNothingPostCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.DoNothingPreCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.PostCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.PreCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.provider.SourceFileProvider;
import biz.gabrys.lesscss.extended.compiler.control.provider.SourcePathFileProvider;

/**
 * Responsible for creating new instances of the {@link ExtendedCompiler} which do not cache any data.
 * @since 1.0
 */
public class NonCachingExtendedCompilerBuilder {

    private LessCompiler compiler;
    private PreCompilationProcessor preProcessor;
    private SourceFileProvider fileProvider;
    private PostCompilationProcessor postProcessor;

    /**
     * Constructs a new instance.
     * @since 1.0
     */
    public NonCachingExtendedCompilerBuilder() {
        // do nothing
    }

    /**
     * Sets native compiler. Default {@link LessCompilerImpl}.
     * @param compiler the <a href="http://lesscss.org/">Less</a> compiler.
     * @return {@code this} builder.
     * @since 1.0
     */
    public NonCachingExtendedCompilerBuilder withCompiler(final LessCompiler compiler) {
        this.compiler = compiler;
        return this;
    }

    /**
     * Sets compilation processor which is responsible for preparing source files to the compilation process. Default
     * {@link DoNothingPreCompilationProcessor}.
     * @param preProcessor the pre compilation processor.
     * @return {@code this} builder.
     * @since 1.0
     */
    public NonCachingExtendedCompilerBuilder withPreProcessor(final PreCompilationProcessor preProcessor) {
        this.preProcessor = preProcessor;
        return this;
    }

    /**
     * Sets source file provider which is responsible for returning file representation of the
     * {@link biz.gabrys.lesscss.extended.compiler.source.LessSource LessSource}. Default {@link SourcePathFileProvider}
     * .
     * @param fileProvider the source file provider.
     * @return {@code this} builder.
     * @since 1.0
     */
    public NonCachingExtendedCompilerBuilder withFileProvider(final SourceFileProvider fileProvider) {
        this.fileProvider = fileProvider;
        return this;
    }

    /**
     * Sets compilation processor which is responsible for preparing compiled code before returning it. Default
     * {@link DoNothingPostCompilationProcessor}.
     * @param postProcessor the post compilation processor.
     * @return {@code this} builder.
     * @since 1.0
     */
    public NonCachingExtendedCompilerBuilder withPostProcessor(final PostCompilationProcessor postProcessor) {
        this.postProcessor = postProcessor;
        return this;
    }

    /**
     * Crates a new instance of the {@link ExtendedCompiler}. If you do not set some specific implementations
     * explicitly, then the following default ones are used:
     * <ul>
     * <li>{@link LessCompilerImpl}</li>
     * <li>{@link DoNothingPreCompilationProcessor}</li>
     * <li>{@link SourcePathFileProvider}</li>
     * <li>{@link DoNothingPostCompilationProcessor}</li>
     * </ul>
     * @return the new instance of the {@link ExtendedCompiler}.
     * @since 1.0
     */
    public ExtendedCompiler create() {
        final LessCompiler lessCompiler = compiler != null ? compiler : new LessCompilerImpl();
        final PreCompilationProcessor preProc = preProcessor != null ? preProcessor : new DoNothingPreCompilationProcessor();
        final SourceFileProvider provider = fileProvider != null ? fileProvider : new SourcePathFileProvider();
        final PostCompilationProcessor postProc = postProcessor != null ? postProcessor : new DoNothingPostCompilationProcessor();

        return new SimpleExtendedCompiler(lessCompiler, preProc, provider, postProc);
    }
}
