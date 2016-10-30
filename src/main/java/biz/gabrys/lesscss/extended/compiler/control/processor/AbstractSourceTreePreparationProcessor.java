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
package biz.gabrys.lesscss.extended.compiler.control.processor;

import java.util.HashSet;
import java.util.Set;

import biz.gabrys.lesscss.extended.compiler.cache.SourceImportsCache;
import biz.gabrys.lesscss.extended.compiler.control.expiration.SourceExpirationChecker;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactory;

/**
 * Helps in implementing pre compilation processors responsible for preparing source file with imports (deep, imports of
 * imports). It executes preparation only for expired sources.
 * @since 1.0
 */
public abstract class AbstractSourceTreePreparationProcessor implements PreCompilationProcessor {

    /**
     * The checker responsible for determining if the source file expired.
     * @since 1.0
     */
    protected SourceExpirationChecker expirationChecker;
    /**
     * The cache responsible for storing source files imports lists.
     * @since 1.0
     */
    protected SourceImportsCache importsCache;
    /**
     * The factory responsible for creating new instances of the {@link LessSource}.
     * @since 1.0
     */
    protected SourceFactory sourceFactory;

    /**
     * Constructs a new instance.
     * @param expirationChecker the checker responsible for determining if the source file expired.
     * @param importsCache the cache responsible for storing source files imports lists.
     * @param sourceFactory the factory responsible for creating new instances of the {@link LessSource}.
     * @since 1.0
     */
    protected AbstractSourceTreePreparationProcessor(final SourceExpirationChecker expirationChecker, final SourceImportsCache importsCache,
            final SourceFactory sourceFactory) {
        this.expirationChecker = expirationChecker;
        this.importsCache = importsCache;
        this.sourceFactory = sourceFactory;
    }

    /**
     * {@inheritDoc} This method delegates work to the {@link #prepare(LessSource, Set)} (set is empty).
     * @since 1.0
     */
    public void prepare(final LessSource source) {
        prepare(source, new HashSet<String>());
    }

    /**
     * Prepares a source file to the compilation process. This method is called recursively for the source and all of
     * its imports. To avoid imports loop, it processes only sources whose paths are not contained by the set.
     * @param source the source file.
     * @param preparedSourcesPaths set with prepared sources path.
     * @since 1.0
     */
    protected void prepare(final LessSource source, final Set<String> preparedSourcesPaths) {
        final String path = source.getPath();
        if (preparedSourcesPaths.contains(path)) {
            return;
        }
        preparedSourcesPaths.add(path);
        if (expirationChecker.isExpired(source)) {
            prepareWhenExpired(source, preparedSourcesPaths);
        } else {
            for (final String importPath : importsCache.getSourceImports(source)) {
                final LessSource importedSource = sourceFactory.create(source, importPath);
                prepare(importedSource, preparedSourcesPaths);
            }
        }
    }

    /**
     * Prepares a source file to the compilation process. Called only when source expired (determines by checker). If
     * this method should prepare the source import, then you must call {@link #prepare(LessSource, Set)}.
     * @param source the source file.
     * @param preparedSourcesPaths set with prepared sources paths.
     * @since 1.0
     */
    protected abstract void prepareWhenExpired(LessSource source, final Set<String> preparedSourcesPaths);
}
