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

import biz.gabrys.lesscss.extended.compiler.cache.FullCache;
import biz.gabrys.lesscss.extended.compiler.cache.SourceCodeCache;
import biz.gabrys.lesscss.extended.compiler.cache.SourceImportsCache;
import biz.gabrys.lesscss.extended.compiler.cache.SourceModificationDateCache;
import biz.gabrys.lesscss.extended.compiler.control.expiration.SourceAlwaysExpiredChecker;
import biz.gabrys.lesscss.extended.compiler.control.expiration.SourceExpirationChecker;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportReplacer;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportReplacerImpl;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportResolver;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportResolverImpl;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactory;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactoryBuilder;

/**
 * Responsible for creating new instances of the {@link SourceTreeWithCodeCachingPreparationProcessor}.
 * @since 1.0
 */
public class SourceTreeWithCodeCachingPreparationProcessorBuilder {

    private final SourceModificationDateCache datesCache;
    private final SourceImportsCache importsCache;
    private final SourceCodeCache codeCache;

    private SourceExpirationChecker expirationChecker;
    private LessImportResolver importResolver;
    private LessImportReplacer importReplacer;
    private SourceFactory sourceFactory;

    /**
     * Constructs a new instance and sets cache.
     * @param cache the cache responsible for storing data.
     * @throws IllegalArgumentException if cache is equal to {@code null}.
     * @since 1.0
     */
    public SourceTreeWithCodeCachingPreparationProcessorBuilder(final FullCache cache) {
        if (cache == null) {
            throw new IllegalArgumentException("Cache cannot be null");
        }
        datesCache = cache;
        importsCache = cache;
        codeCache = cache;
    }

    /**
     * Constructs a new instance and sets caches.
     * @param datesCache the cache responsible for storing source files modification dates.
     * @param importsCache the cache responsible for storing source files imports list.
     * @param codeCache the cache responsible for storing source files code.
     * @throws IllegalArgumentException if at least one cache is equal to {@code null}.
     * @since 1.0
     */
    public SourceTreeWithCodeCachingPreparationProcessorBuilder(final SourceModificationDateCache datesCache,
            final SourceImportsCache importsCache, final SourceCodeCache codeCache) {
        if (datesCache == null) {
            throw new IllegalArgumentException("Dates cache cannot be null");
        }
        this.datesCache = datesCache;
        if (importsCache == null) {
            throw new IllegalArgumentException("Imports cache cannot be null");
        }
        this.importsCache = importsCache;
        if (codeCache == null) {
            throw new IllegalArgumentException("Code cache cannot be null");
        }
        this.codeCache = codeCache;
    }

    /**
     * Sets specified {@link SourceExpirationChecker}.
     * @param expirationChecker the source expiration checker.
     * @return {@code this} builder.
     * @since 1.0
     */
    public SourceTreeWithCodeCachingPreparationProcessorBuilder withExpirationChecker(final SourceExpirationChecker expirationChecker) {
        this.expirationChecker = expirationChecker;
        return this;
    }

    /**
     * Sets specified {@link LessImportResolver}.
     * @param importResolver the import resolver.
     * @return {@code this} builder.
     * @since 1.0
     */
    public SourceTreeWithCodeCachingPreparationProcessorBuilder withImportResolver(final LessImportResolver importResolver) {
        this.importResolver = importResolver;
        return this;
    }

    /**
     * Sets specified {@link LessImportReplacer}.
     * @param importReplacer the import replacer.
     * @return {@code this} builder.
     * @since 1.0
     */
    public SourceTreeWithCodeCachingPreparationProcessorBuilder withImportReplacer(final LessImportReplacer importReplacer) {
        this.importReplacer = importReplacer;
        return this;
    }

    /**
     * Sets specified {@link SourceFactory}.
     * @param sourceFactory the source factory.
     * @return {@code this} builder.
     * @since 1.0
     */
    public SourceTreeWithCodeCachingPreparationProcessorBuilder withSourceFactory(final SourceFactory sourceFactory) {
        this.sourceFactory = sourceFactory;
        return this;
    }

    /**
     * Crates a new instance of the {@link SourceTreeWithCodeCachingPreparationProcessor}. If you do not set some
     * specific implementations explicitly, then the following default ones are used:
     * <ul>
     * <li>{@link SourceAlwaysExpiredChecker}</li>
     * <li>{@link LessImportResolverImpl}</li>
     * <li>{@link LessImportReplacerImpl}</li>
     * <li>{@link SourceFactory} created by {@link SourceFactoryBuilder} with standard factories (
     * {@link SourceFactoryBuilder#withStandard()})</li>
     * </ul>
     * @return the new instance of the {@link SourceTreeWithCodeCachingPreparationProcessor}.
     * @since 1.0
     */
    public SourceTreeWithCodeCachingPreparationProcessor create() {
        final SourceExpirationChecker checker = expirationChecker != null ? expirationChecker : new SourceAlwaysExpiredChecker();
        final LessImportResolver resolver = importResolver != null ? importResolver : new LessImportResolverImpl();
        final LessImportReplacer replacer = importReplacer != null ? importReplacer : new LessImportReplacerImpl();
        final SourceFactory factory = sourceFactory != null ? sourceFactory : new SourceFactoryBuilder().withStandard().create();
        return new SourceTreeWithCodeCachingPreparationProcessor(checker, datesCache, importsCache, codeCache, resolver, replacer, factory);
    }
}
