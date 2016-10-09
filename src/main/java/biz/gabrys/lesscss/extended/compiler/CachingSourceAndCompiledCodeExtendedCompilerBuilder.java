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

import biz.gabrys.lesscss.compiler.LessCompiler;
import biz.gabrys.lesscss.compiler.LessCompilerImpl;
import biz.gabrys.lesscss.extended.compiler.cache.FullCache;
import biz.gabrys.lesscss.extended.compiler.control.expiration.CompiledSourceExpirationChecker;
import biz.gabrys.lesscss.extended.compiler.control.expiration.CompiledSourceExpirationCheckerImpl;
import biz.gabrys.lesscss.extended.compiler.control.expiration.SourceExpirationChecker;
import biz.gabrys.lesscss.extended.compiler.control.expiration.SourceModificationDateBasedExpirationChecker;
import biz.gabrys.lesscss.extended.compiler.control.processor.DoNothingPostCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.PostCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.PreCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.SourceTreeWithCodeCachingPreparationProcessorBuilder;
import biz.gabrys.lesscss.extended.compiler.control.provider.CachedSourceFileProvider;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportReplacer;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportReplacerImpl;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportResolver;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportResolverImpl;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactory;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactoryBuilder;

/**
 * Responsible for creating new instances of the {@link ExtendedCompiler} which cache source and compiled code.
 * @since 1.0
 */
public class CachingSourceAndCompiledCodeExtendedCompilerBuilder {

    private final FullCache cache;

    private LessCompiler compiler;
    private SourceExpirationChecker expirationChecker;
    private LessImportResolver importResolver;
    private LessImportReplacer importReplacer;
    private SourceFactory sourceFactory;
    private PostCompilationProcessor postProcessor;

    /**
     * Constructs a new instance.
     * @param cache the cache.
     * @throws IllegalArgumentException if cache is equal to {@code null}.
     * @since 1.0
     */
    public CachingSourceAndCompiledCodeExtendedCompilerBuilder(final FullCache cache) {
        if (cache == null) {
            throw new IllegalArgumentException("Cache cannot be null");
        }
        this.cache = cache;
    }

    /**
     * Sets native compiler. Default {@link LessCompilerImpl}.
     * @param compiler the <a href="http://lesscss.org/">Less</a> compiler.
     * @return {@code this} builder.
     * @since 1.0
     */
    public CachingSourceAndCompiledCodeExtendedCompilerBuilder withCompiler(final LessCompiler compiler) {
        this.compiler = compiler;
        return this;
    }

    /**
     * Sets expiration checker which is responsible for checking expiration of individual files. Default
     * {@link SourceModificationDateBasedExpirationChecker}.
     * @param expirationChecker the source expiration checker.
     * @return {@code this} builder.
     * @since 1.0
     */
    public CachingSourceAndCompiledCodeExtendedCompilerBuilder withExpirationChecker(final SourceExpirationChecker expirationChecker) {
        this.expirationChecker = expirationChecker;
        return this;

    }

    /**
     * Sets <a href="http://lesscss.org/">Less</a> import resolver which is responsible for returning a list of import
     * operations from the source code. Default {@link LessImportResolverImpl}.
     * @param importResolver the import resolver.
     * @return {@code this} builder.
     * @since 1.0
     */
    public CachingSourceAndCompiledCodeExtendedCompilerBuilder withImportResolver(final LessImportResolver importResolver) {
        this.importResolver = importResolver;
        return this;
    }

    /**
     * Sets <a href="http://lesscss.org/">Less</a> import replacer which is responsible for replacing import operations
     * in the source code with new values. Default {@link LessImportReplacerImpl}.
     * @param importReplacer the import replacer.
     * @return {@code this} builder.
     * @since 1.0
     */
    public CachingSourceAndCompiledCodeExtendedCompilerBuilder withImportReplacer(final LessImportReplacer importReplacer) {
        this.importReplacer = importReplacer;
        return this;
    }

    /**
     * Sets factory which is responsible for creating new instances of the
     * {@link biz.gabrys.lesscss.extended.compiler.source.LessSource LessSource}. Default {@link SourceFactory} created
     * by {@link SourceFactoryBuilder} with standard factories ( {@link SourceFactoryBuilder#withStandard()}).
     * @param sourceFactory the source factory.
     * @return {@code this} builder.
     * @since 1.0
     */
    public CachingSourceAndCompiledCodeExtendedCompilerBuilder withSourceFactory(final SourceFactory sourceFactory) {
        this.sourceFactory = sourceFactory;
        return this;
    }

    /**
     * Sets compilation processor which is responsible for preparing compiled code before returning it. Default
     * {@link DoNothingPostCompilationProcessor}.
     * @param postProcessor the post compilation processor.
     * @return {@code this} builder.
     * @since 1.0
     */
    public CachingSourceAndCompiledCodeExtendedCompilerBuilder withPostProcessor(final PostCompilationProcessor postProcessor) {
        this.postProcessor = postProcessor;
        return this;
    }

    /**
     * Crates a new instance of the {@link ExtendedCompiler}. If you do not set some specific implementations
     * explicitly, then the following default ones are used:
     * <ul>
     * <li>{@link LessCompilerImpl}</li>
     * <li>{@link SourceModificationDateBasedExpirationChecker}</li>
     * <li>{@link SourceFactory} created by {@link SourceFactoryBuilder} with standard factories (
     * {@link SourceFactoryBuilder#withStandard()})</li>
     * <li>{@link LessImportResolverImpl}</li>
     * <li>{@link LessImportReplacerImpl}</li>
     * <li>{@link DoNothingPostCompilationProcessor}</li>
     * </ul>
     * @return the new instance of the {@link ExtendedCompiler}.
     * @since 1.0
     */
    public ExtendedCompiler create() {
        final LessCompiler lessCompiler = compiler != null ? compiler : new LessCompilerImpl();

        final SourceExpirationChecker checker = expirationChecker != null ? expirationChecker
                : new SourceModificationDateBasedExpirationChecker(cache);
        final SourceTreeWithCodeCachingPreparationProcessorBuilder builder = new SourceTreeWithCodeCachingPreparationProcessorBuilder(
                cache);
        builder.withExpirationChecker(checker);
        builder.withImportResolver(importResolver != null ? importResolver : new LessImportResolverImpl());
        builder.withImportReplacer(importReplacer != null ? importReplacer : new LessImportReplacerImpl());
        builder.withSourceFactory(sourceFactory != null ? sourceFactory : new SourceFactoryBuilder().withStandard().create());
        final PreCompilationProcessor preProc = builder.create();

        final PostCompilationProcessor postProc = postProcessor != null ? postProcessor : new DoNothingPostCompilationProcessor();

        final ExtendedCompiler extendedCompiler = new SimpleExtendedCompiler(lessCompiler, preProc, new CachedSourceFileProvider(cache),
                postProc);

        final CompiledSourceExpirationChecker compiledSourceChecker = new CompiledSourceExpirationCheckerImpl(checker, cache, cache,
                sourceFactory);
        return new CachingCompiledCodeExtendedCompiler(extendedCompiler, compiledSourceChecker, cache, cache);
    }
}
