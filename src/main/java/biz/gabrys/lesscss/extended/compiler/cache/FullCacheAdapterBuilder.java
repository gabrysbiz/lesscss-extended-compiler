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

import biz.gabrys.lesscss.extended.compiler.util.ParameterUtils;

/**
 * Responsible for creating new instances of the {@link FullCacheAdapter}.
 * @since 1.0
 */
public class FullCacheAdapterBuilder {

    private SourceModificationDateCache modificationDatesCache;
    private SourceImportsCache importsCache;
    private SourceCodeCache sourceCache;
    private CompilationDateCache compilationDatesCache;
    private CompiledCodeCache compiledCache;

    /**
     * Constructs a new instance.
     * @param cache the full cache.
     * @throws IllegalArgumentException if cache is equal to {@code null}.
     * @since 1.0
     */
    public FullCacheAdapterBuilder(final FullCache cache) {
        ParameterUtils.verifyNotNull("full cache", cache);
        modificationDatesCache = cache;
        importsCache = cache;
        sourceCache = cache;
        compilationDatesCache = cache;
        compiledCache = cache;
    }

    /**
     * Sets cache responsible for storing source files modification dates.
     * @param cache the cache.
     * @return {@code this} builder.
     * @throws IllegalArgumentException if cache is equal to {@code null}.
     * @since 1.0
     */
    public FullCacheAdapterBuilder withSourceModificationDateCache(final SourceModificationDateCache cache) {
        ParameterUtils.verifyNotNull("source modification dates", cache);
        modificationDatesCache = cache;
        return this;
    }

    /**
     * Sets cache responsible for storing source files imports paths.
     * @param cache the cache.
     * @return {@code this} builder.
     * @throws IllegalArgumentException if cache is equal to {@code null}.
     * @since 1.0
     */
    public FullCacheAdapterBuilder withSourceImportsPathsCache(final SourceImportsCache cache) {
        ParameterUtils.verifyNotNull("source imports paths", cache);
        importsCache = cache;
        return this;
    }

    /**
     * Sets cache responsible for storing source files code.
     * @param cache the cache.
     * @return {@code this} builder.
     * @throws IllegalArgumentException if cache is equal to {@code null}.
     * @since 1.0
     */
    public FullCacheAdapterBuilder withSourceCodeCache(final SourceCodeCache cache) {
        ParameterUtils.verifyNotNull("source code cache", cache);
        sourceCache = cache;
        return this;
    }

    /**
     * Sets cache responsible for storing sources compilation dates.
     * @param cache the cache.
     * @return {@code this} builder.
     * @throws IllegalArgumentException if cache is equal to {@code null}.
     * @since 1.0
     */
    public FullCacheAdapterBuilder withCompilationDateCache(final CompilationDateCache cache) {
        ParameterUtils.verifyNotNull("compilation dates cache", cache);
        compilationDatesCache = cache;
        return this;
    }

    /**
     * Sets cache responsible for storing compiled code.
     * @param cache the cache.
     * @return {@code this} builder.
     * @throws IllegalArgumentException if cache is equal to {@code null}.
     * @since 1.0
     */
    public FullCacheAdapterBuilder withCompiledCodeCache(final CompiledCodeCache cache) {
        ParameterUtils.verifyNotNull("compiled code cache", cache);
        compiledCache = cache;
        return this;
    }

    /**
     * Crates a new instance of the {@link FullCacheAdapter}.
     * @return the new instance of the {@link FullCacheAdapter}.
     * @since 1.0
     */
    public FullCacheAdapter create() {
        return new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache, compiledCache);
    }
}
