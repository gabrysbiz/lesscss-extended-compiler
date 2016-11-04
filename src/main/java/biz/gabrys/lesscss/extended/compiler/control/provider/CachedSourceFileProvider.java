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
package biz.gabrys.lesscss.extended.compiler.control.provider;

import java.io.File;

import biz.gabrys.lesscss.extended.compiler.cache.SourceCodeCache;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;

/**
 * Provider which returns files for sources from cache.
 * @since 1.0
 */
public class CachedSourceFileProvider implements SourceFileProvider {

    /**
     * The cache responsible for storing files with source code.
     * @since 2.1.0
     */
    protected SourceCodeCache cache;

    /**
     * Constructs a new instance.
     * @param cache the cache responsible for storing files with source code.
     * @since 1.0
     */
    public CachedSourceFileProvider(final SourceCodeCache cache) {
        this.cache = cache;
    }

    public File getFile(final LessSource source) {
        return cache.getSourceFile(source);
    }
}
