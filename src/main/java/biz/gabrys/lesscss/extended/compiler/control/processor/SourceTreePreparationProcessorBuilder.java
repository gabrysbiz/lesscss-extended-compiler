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
import biz.gabrys.lesscss.extended.compiler.cache.SourceImportsCache;
import biz.gabrys.lesscss.extended.compiler.cache.SourceModificationDateCache;
import biz.gabrys.lesscss.extended.compiler.control.expiration.SourceAlwaysExpiredChecker;
import biz.gabrys.lesscss.extended.compiler.control.expiration.SourceExpirationChecker;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportResolver;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportResolverImpl;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactory;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactoryBuilder;

/**
 * Responsible for creating new instances of the {@link SourceTreePreparationProcessor}.
 * @since 1.0
 */
public class SourceTreePreparationProcessorBuilder {

    private final SourceModificationDateCache datesCache;
    private final SourceImportsCache importsCache;

    private SourceExpirationChecker expirationChecker;
    private LessImportResolver importResolver;
    private SourceFactory sourceFactory;

    /**
     * Constructs a new instance and sets cache.
     * @param cache the cache responsible for storing data.
     * @throws IllegalArgumentException if cache is equal to {@code null}.
     * @since 1.0
     */
    public SourceTreePreparationProcessorBuilder(final FullCache cache) {
        if (cache == null) {
            throw new IllegalArgumentException("Cache cannot be null");
        }
        datesCache = cache;
        importsCache = cache;
    }

    /**
     * Constructs a new instance and sets caches.
     * @param datesCache the cache responsible for storing source files modification dates.
     * @param importsCache the cache responsible for storing source files imports list.
     * @throws IllegalArgumentException if at least one cache is equal to {@code null}.
     * @since 1.0
     */
    public SourceTreePreparationProcessorBuilder(final SourceModificationDateCache datesCache, final SourceImportsCache importsCache) {
        if (datesCache == null) {
            throw new IllegalArgumentException("Dates cache cannot be null");
        }
        this.datesCache = datesCache;
        if (importsCache == null) {
            throw new IllegalArgumentException("Imports cache cannot be null");
        }
        this.importsCache = importsCache;
    }

    /**
     * Sets specified {@link LessImportResolver}.
     * @param importResolver the import resolver.
     * @return {@code this} builder.
     * @since 1.0
     */
    public SourceTreePreparationProcessorBuilder withImportResolver(final LessImportResolver importResolver) {
        this.importResolver = importResolver;
        return this;
    }

    /**
     * Sets specified {@link SourceFactory}.
     * @param sourceFactory the source factory.
     * @return {@code this} builder.
     * @since 1.0
     */
    public SourceTreePreparationProcessorBuilder withSourceFactory(final SourceFactory sourceFactory) {
        this.sourceFactory = sourceFactory;
        return this;
    }

    /**
     * Crates a new instance of the {@link SourceTreePreparationProcessor}. If you do not set some specific
     * implementations explicitly, then the following default ones are used:
     * <ul>
     * <li>{@link SourceAlwaysExpiredChecker}</li>
     * <li>{@link LessImportResolverImpl}</li>
     * <li>{@link SourceFactory} created by {@link SourceFactoryBuilder} with standard factories (
     * {@link SourceFactoryBuilder#withStandard()})</li>
     * </ul>
     * @return the new instance of the {@link SourceTreePreparationProcessor}.
     * @since 1.0
     */
    public SourceTreePreparationProcessor create() {
        final SourceExpirationChecker checker = expirationChecker != null ? expirationChecker : new SourceAlwaysExpiredChecker();
        final LessImportResolver resolver = importResolver != null ? importResolver : new LessImportResolverImpl();
        final SourceFactory factory = sourceFactory != null ? sourceFactory : new SourceFactoryBuilder().withStandard().create();
        return new SourceTreePreparationProcessor(checker, datesCache, importsCache, resolver, factory);
    }
}
