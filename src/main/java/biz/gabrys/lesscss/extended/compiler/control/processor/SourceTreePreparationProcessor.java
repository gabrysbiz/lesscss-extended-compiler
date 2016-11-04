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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import biz.gabrys.lesscss.extended.compiler.cache.SourceImportsCache;
import biz.gabrys.lesscss.extended.compiler.cache.SourceModificationDateCache;
import biz.gabrys.lesscss.extended.compiler.control.expiration.SourceExpirationChecker;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportOperation;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportResolver;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactory;

/**
 * Pre compilation processor responsible for preparing source file with imports (deep, imports of imports). It's saving
 * for all sources:
 * <ul>
 * <li>modification date</li>
 * <li>imports list</li>
 * </ul>
 * @since 1.0
 */
public class SourceTreePreparationProcessor extends AbstractSourceTreePreparationProcessor {

    /**
     * The cache responsible for storing source files modification dates.
     * @since 2.1.0
     */
    protected SourceModificationDateCache datesCache;
    /**
     * The resolver responsible for returning a list of import operations.
     * @since 2.1.0
     */
    protected LessImportResolver importResolver;

    /**
     * Constructs a new instance.
     * @param expirationChecker the checker responsible for determining if the source file expired.
     * @param datesCache the cache responsible for storing source files modification dates.
     * @param importsCache the cache responsible for storing source files imports lists.
     * @param importResolver the resolver responsible for returning a list of import operations.
     * @param sourceFactory the factory responsible for creating new instances of the {@link LessSource}.
     * @since 1.0
     */
    public SourceTreePreparationProcessor(final SourceExpirationChecker expirationChecker, final SourceModificationDateCache datesCache,
            final SourceImportsCache importsCache, final LessImportResolver importResolver, final SourceFactory sourceFactory) {
        super(expirationChecker, importsCache, sourceFactory);
        this.datesCache = datesCache;
        this.importResolver = importResolver;
    }

    @Override
    protected void prepareWhenExpired(final LessSource source, final Set<String> preparedSourcesPaths) {
        final String sourceCode = source.getContent();
        datesCache.saveSourceModificationDate(source, source.getLastModificationDate());
        final Collection<LessImportOperation> operations = importResolver.resolve(sourceCode);
        final List<String> imports = new ArrayList<String>(operations.size());
        for (final LessImportOperation operation : operations) {
            final LessSource importSource = sourceFactory.create(source, operation.getComputedPath());
            imports.add(operation.getComputedPath());
            prepare(importSource, preparedSourcesPaths);
        }
        importsCache.saveSourceImports(source, imports);
    }
}
