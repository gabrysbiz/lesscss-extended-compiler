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

import java.io.File;

import biz.gabrys.lesscss.compiler.CompilerException;
import biz.gabrys.lesscss.compiler.CompilerOptions;
import biz.gabrys.lesscss.compiler.LessCompiler;
import biz.gabrys.lesscss.extended.compiler.control.processor.PostCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.PreCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.provider.SourceFileProvider;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;

/**
 * Compiler which wraps the native {@link LessCompiler Less compiler} expanding it with preparing source files before
 * &amp; compiled code after the compilation process.
 * @since 1.0
 */
public class SimpleExtendedCompiler implements ExtendedCompiler {

    private final LessCompiler compiler;
    private final PreCompilationProcessor preProcessor;
    private final SourceFileProvider fileProvider;
    private final PostCompilationProcessor postProcessor;

    /**
     * Constructs a new instance.
     * @param compiler the native compiler.
     * @param preProcessor the pre compilation processor.
     * @param fileProvider the provider responsible for returning file representation of the {@link LessSource}.
     * @param postProcessor the post compilation processor.
     * @since 1.0
     */
    public SimpleExtendedCompiler(final LessCompiler compiler, final PreCompilationProcessor preProcessor,
            final SourceFileProvider fileProvider, final PostCompilationProcessor postProcessor) {
        this.compiler = compiler;
        this.preProcessor = preProcessor;
        this.fileProvider = fileProvider;
        this.postProcessor = postProcessor;
    }

    public String compile(final LessSource source) throws CompilerException {
        preProcessor.prepare(source);
        final File sourceFile = fileProvider.getFile(source);
        final String compiledCode = compiler.compile(sourceFile);
        return postProcessor.prepare(source, compiledCode);
    }

    public String compile(final LessSource source, final CompilerOptions options) throws CompilerException {
        preProcessor.prepare(source);
        final File sourceFile = fileProvider.getFile(source);
        final String compiledCode = compiler.compile(sourceFile, options);
        return postProcessor.prepare(source, compiledCode);
    }
}
