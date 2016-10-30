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
package biz.gabrys.lesscss.extended.compiler.control.expiration;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import biz.gabrys.lesscss.extended.compiler.cache.SourceImportsCache;
import biz.gabrys.lesscss.extended.compiler.cache.SourceModificationDateCache;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactory;

/**
 * Checker which marks compiled code of a source file as expired when the file or one of its imports expired (deep -
 * imports of imports etc.). Expiration of individual files are determined by an other checker (set in the constructor).
 * @since 1.0
 */
public class CompiledSourceExpirationCheckerImpl implements CompiledSourceExpirationChecker {

    /**
     * The individual source files expiration checker.
     * @since 2.1.0
     */
    protected SourceExpirationChecker expirationChecker;
    /**
     * The cache responsible for storing source files dates.
     * @since 2.1.0
     */
    protected SourceModificationDateCache datesCache;
    /**
     * The cache responsible for storing source files imports lists.
     * @since 2.1.0
     */
    protected SourceImportsCache importsCache;
    /**
     * The factory responsible for creating new instances of the {@link LessSource}.
     * @since 2.1.0
     */
    protected SourceFactory sourceFactory;

    /**
     * Constructs a new instance.
     * @param expirationChecker the individual source files expiration checker.
     * @param datesCache the cache responsible for storing source files dates.
     * @param importsCache the cache responsible for storing source files imports lists.
     * @param sourceFactory the factory responsible for creating new instances of the {@link LessSource}.
     * @since 1.0
     */
    public CompiledSourceExpirationCheckerImpl(final SourceExpirationChecker expirationChecker,
            final SourceModificationDateCache datesCache, final SourceImportsCache importsCache, final SourceFactory sourceFactory) {
        this.expirationChecker = expirationChecker;
        this.datesCache = datesCache;
        this.importsCache = importsCache;
        this.sourceFactory = sourceFactory;
    }

    /**
     * {@inheritDoc} This method delegates work to the {@link #isExpired(LessSource, Date, Set)} (set is empty).
     * @since 1.0
     */
    public boolean isExpired(final LessSource source, final Date lastCompilationDate) {
        return isExpired(source, lastCompilationDate, new HashSet<String>());
    }

    /**
     * Tests whether a compiled code for a source file expired. This method is called recursively for the source and all
     * of its imports (until find the first expired file). To avoid imports loop, it processes only sources whose paths
     * are not contained by the set.
     * @param source the source file.
     * @param lastCompilationDate the date of last compilation.
     * @param checkedSourcesPaths set with checked sources paths.
     * @return {@code true} whether the compiled code expired, otherwise {@code false}.
     * @since 1.0
     */
    protected boolean isExpired(final LessSource source, final Date lastCompilationDate, final Set<String> checkedSourcesPaths) {
        if (expirationChecker.isExpired(source) || isModifiedAfterLastCompilation(source, lastCompilationDate)) {
            return true;
        }
        checkedSourcesPaths.add(source.getPath());
        return checkImports(source, lastCompilationDate, checkedSourcesPaths);
    }

    /**
     * Tests whether a source has been modified after last compilation.
     * @param source the source file.
     * @param lastCompilationDate the date of last compilation.
     * @return {@code true} whether the source has been modified after last compilation, otherwise {@code false}.
     * @since 1.0
     */
    protected boolean isModifiedAfterLastCompilation(final LessSource source, final Date lastCompilationDate) {
        final Date modificationDate = datesCache.getSourceModificationDate(source);
        return modificationDate.after(lastCompilationDate);
    }

    private boolean checkImports(final LessSource source, final Date lastCompilationDate, final Set<String> checkedSourcesPaths) {
        for (final String importPath : importsCache.getSourceImports(source)) {
            final LessSource importedSource = sourceFactory.create(source, importPath);
            if (checkedSourcesPaths.contains(importedSource.getPath())) {
                continue;
            }
            if (isExpired(importedSource, lastCompilationDate, checkedSourcesPaths)) {
                return true;
            }
        }
        return false;
    }
}
