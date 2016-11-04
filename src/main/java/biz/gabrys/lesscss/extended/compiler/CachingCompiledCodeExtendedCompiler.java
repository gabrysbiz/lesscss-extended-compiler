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

import java.util.Date;

import biz.gabrys.lesscss.compiler.CompilerException;
import biz.gabrys.lesscss.compiler.CompilerOptions;
import biz.gabrys.lesscss.extended.compiler.cache.CompilationDateCache;
import biz.gabrys.lesscss.extended.compiler.cache.CompiledCodeCache;
import biz.gabrys.lesscss.extended.compiler.control.expiration.CompiledSourceExpirationChecker;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;

/**
 * Compiler which wraps other extended compiler expanding it with caching compiled code.
 * @since 1.0
 */
public class CachingCompiledCodeExtendedCompiler implements ExtendedCompiler {

    /**
     * The extended compiler responsible for compiling source files.
     * @since 2.1.0
     */
    protected ExtendedCompiler compiler;
    /**
     * The checker responsible for determining if the compiled code expired.
     * @since 2.1.0
     */
    protected CompiledSourceExpirationChecker expirationChecker;
    /**
     * The cache that holds compilation dates.
     * @since 2.1.0
     */
    protected CompilationDateCache datesCache;
    /**
     * The cache that holds compiled code.
     * @since 2.1.0
     */
    protected CompiledCodeCache codeCache;

    /**
     * Constructs a new instance.
     * @param compiler the extended compiler responsible for compiling source files.
     * @param expirationChecker the checker responsible for determining if the compiled code expired.
     * @param datesCache the cache that holds compilation dates.
     * @param codeCache the cache that holds compiled code.
     * @since 1.0
     */
    public CachingCompiledCodeExtendedCompiler(final ExtendedCompiler compiler, final CompiledSourceExpirationChecker expirationChecker,
            final CompilationDateCache datesCache, final CompiledCodeCache codeCache) {
        this.compiler = compiler;
        this.expirationChecker = expirationChecker;
        this.datesCache = datesCache;
        this.codeCache = codeCache;
    }

    public String compile(final LessSource source) throws CompilerException {
        if (isExpired(source)) {
            final String compiledCode = compiler.compile(source);
            datesCache.saveCompilationDate(source, new Date());
            codeCache.saveCompiledCode(source, compiledCode);
            return compiledCode;
        }
        return codeCache.getCompiledCode(source);
    }

    public String compile(final LessSource source, final CompilerOptions options) throws CompilerException {
        if (isExpired(source)) {
            final String compiledCode = compiler.compile(source, options);
            datesCache.saveCompilationDate(source, new Date());
            codeCache.saveCompiledCode(source, compiledCode);
            return compiledCode;
        }
        return codeCache.getCompiledCode(source);
    }

    /**
     * Tests whether source file requires compilation.
     * @param source the source file.
     * @return {@code true} whether source file requires compilation, otherwise {@code false}.
     * @since 1.0
     */
    protected boolean isExpired(final LessSource source) {
        if (!datesCache.hasCompilationDate(source)) {
            return true;
        }
        final Date compilationDate = datesCache.getCompilationDate(source);
        return expirationChecker.isExpired(source, compilationDate);
    }
}
