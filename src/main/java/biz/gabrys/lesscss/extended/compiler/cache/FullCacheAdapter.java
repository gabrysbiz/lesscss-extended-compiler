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
package biz.gabrys.lesscss.extended.compiler.cache;

import java.io.File;
import java.util.Date;
import java.util.List;

import biz.gabrys.lesscss.extended.compiler.source.LessSource;
import biz.gabrys.lesscss.extended.compiler.util.ParameterUtils;

/**
 * Allows you to create an instance of {@link FullCache} which delegates execution of each method to concrete caches.
 * @since 1.0
 */
public class FullCacheAdapter implements FullCache {

    /**
     * The cache responsible for storing source files modification dates.
     * @since 2.1.0
     */
    protected SourceModificationDateCache modificationDatesCache;
    /**
     * The cache responsible for storing source files imports paths.
     * @since 2.1.0
     */
    protected SourceImportsCache importsCache;
    /**
     * The cache responsible for storing source files code.
     * @since 2.1.0
     */
    protected SourceCodeCache sourceCache;
    /**
     * The cache responsible for storing sources compilation dates.
     * @since 2.1.0
     */
    protected CompilationDateCache compilationDatesCache;
    /**
     * The cache responsible for storing compiled code.
     * @since 2.1.0
     */
    protected CompiledCodeCache compiledCache;

    /**
     * Constructs a new instance.
     * @param modificationDatesCache the cache responsible for storing source files modification dates.
     * @param importsCache the cache responsible for storing source files imports paths.
     * @param sourceCache the cache responsible for storing source files code.
     * @param compilationDatesCache the cache responsible for storing sources compilation dates.
     * @param compiledCache the cache responsible for storing compiled code.
     * @throws IllegalArgumentException if at least one cache is equal to {@code null}.
     * @since 1.0
     */
    public FullCacheAdapter(final SourceModificationDateCache modificationDatesCache, final SourceImportsCache importsCache,
            final SourceCodeCache sourceCache, final CompilationDateCache compilationDatesCache, final CompiledCodeCache compiledCache) {

        ParameterUtils.verifyNotNull("source modification dates cache", modificationDatesCache);
        ParameterUtils.verifyNotNull("source imports paths cache", importsCache);
        ParameterUtils.verifyNotNull("source code cache", sourceCache);
        ParameterUtils.verifyNotNull("compilation dates cache", compilationDatesCache);
        ParameterUtils.verifyNotNull("compiled code cache", compiledCache);

        this.modificationDatesCache = modificationDatesCache;
        this.importsCache = importsCache;
        this.sourceCache = sourceCache;
        this.compilationDatesCache = compilationDatesCache;
        this.compiledCache = compiledCache;
    }

    public void saveSourceModificationDate(final LessSource source, final Date modificationDate) {
        modificationDatesCache.saveSourceModificationDate(source, modificationDate);
    }

    public boolean hasSourceModificationDate(final LessSource source) {
        return modificationDatesCache.hasSourceModificationDate(source);
    }

    public Date getSourceModificationDate(final LessSource source) {
        return modificationDatesCache.getSourceModificationDate(source);
    }

    public void saveSourceImports(final LessSource source, final List<String> imports) {
        importsCache.saveSourceImports(source, imports);
    }

    public boolean hasSourceImports(final LessSource source) {
        return importsCache.hasSourceImports(source);
    }

    public List<String> getSourceImports(final LessSource source) {
        return importsCache.getSourceImports(source);
    }

    public void saveSourceCode(final LessSource source, final String sourceCode) {
        sourceCache.saveSourceCode(source, sourceCode);
    }

    public boolean hasSourceCode(final LessSource source) {
        return sourceCache.hasSourceCode(source);
    }

    public File getSourceFile(final LessSource source) {
        return sourceCache.getSourceFile(source);
    }

    public String getSourceRelativePath(final LessSource source) {
        return sourceCache.getSourceRelativePath(source);
    }

    public void saveCompilationDate(final LessSource source, final Date compilationDate) {
        compilationDatesCache.saveCompilationDate(source, compilationDate);
    }

    public boolean hasCompilationDate(final LessSource source) {
        return compilationDatesCache.hasCompilationDate(source);
    }

    public Date getCompilationDate(final LessSource source) {
        return compilationDatesCache.getCompilationDate(source);
    }

    public void saveCompiledCode(final LessSource source, final String compiledCode) {
        compiledCache.saveCompiledCode(source, compiledCode);
    }

    public boolean hasCompiledCode(final LessSource source) {
        return compiledCache.hasCompiledCode(source);
    }

    public String getCompiledCode(final LessSource source) {
        return compiledCache.getCompiledCode(source);
    }
}
