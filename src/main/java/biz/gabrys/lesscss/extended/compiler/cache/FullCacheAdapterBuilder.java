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
        if (cache == null) {
            throw new IllegalArgumentException("Full cache cannot be null");
        }
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
        if (cache == null) {
            throw new IllegalArgumentException("Source modification dates cannot be null");
        }
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
        if (cache == null) {
            throw new IllegalArgumentException("Source imports paths cannot be null");
        }
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
        if (cache == null) {
            throw new IllegalArgumentException("Source code cache cannot be null");
        }
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
        if (cache == null) {
            throw new IllegalArgumentException("Compilation dates cache cannot be null");
        }
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
        if (cache == null) {
            throw new IllegalArgumentException("Compiled code cache cannot be null");
        }
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
