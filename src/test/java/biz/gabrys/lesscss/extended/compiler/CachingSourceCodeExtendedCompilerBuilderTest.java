package biz.gabrys.lesscss.extended.compiler;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import biz.gabrys.lesscss.compiler.LessCompiler;
import biz.gabrys.lesscss.compiler.LessCompilerImpl;
import biz.gabrys.lesscss.extended.compiler.cache.FullCache;
import biz.gabrys.lesscss.extended.compiler.control.expiration.SourceExpirationChecker;
import biz.gabrys.lesscss.extended.compiler.control.expiration.SourceModificationDateBasedExpirationChecker;
import biz.gabrys.lesscss.extended.compiler.control.processor.DoNothingPostCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.PostCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.SourceTreeWithCodeCachingPreparationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.SourceTreeWithCodeCachingPreparationProcessorBuilder;
import biz.gabrys.lesscss.extended.compiler.control.provider.CachedSourceFileProvider;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportReplacer;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportReplacerImpl;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportResolver;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportResolverImpl;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactory;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactoryBuilder;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactoryImpl;

public final class CachingSourceCodeExtendedCompilerBuilderTest {

    @Test
    public void createSourceFactoryFromBuilder() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingSourceCodeExtendedCompilerBuilder(cache));

        final SourceFactoryBuilder sourceFactoryBuilder = Mockito.mock(SourceFactoryBuilder.class);
        final SourceFactoryImpl sourceFactory = Mockito.mock(SourceFactoryImpl.class);
        Mockito.when(sourceFactoryBuilder.withStandard()).thenReturn(sourceFactoryBuilder);
        Mockito.when(sourceFactoryBuilder.create()).thenReturn(sourceFactory);

        Assert.assertEquals("Source factory instance", sourceFactory, builder.createSourceFactoryFromBuilder(sourceFactoryBuilder));

        Mockito.verify(builder).createSourceFactoryFromBuilder(sourceFactoryBuilder);
        Mockito.verify(sourceFactoryBuilder).withStandard();
        Mockito.verify(sourceFactoryBuilder).create();
        Mockito.verifyNoMoreInteractions(builder, sourceFactoryBuilder);
        Mockito.verifyZeroInteractions(cache, sourceFactory);
    }

    @Test
    public void createSourceFactory_hasNotBeenSet_returnDefaultFactory() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingSourceCodeExtendedCompilerBuilder(cache));

        final SourceFactory sourceFactory = builder.createSourceFactory();
        Assert.assertNotNull("Source factory instance", sourceFactory);
        Assert.assertEquals("Source factory class", SourceFactoryImpl.class, sourceFactory.getClass());

        Mockito.verify(builder).createSourceFactory();
        Mockito.verify(builder).createSourceFactoryFromBuilder(Matchers.any(SourceFactoryBuilder.class));
        Mockito.verifyNoMoreInteractions(cache, builder);
    }

    @Test
    public void createSourceFactory_hasBeenSet_returnSetFactory() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingSourceCodeExtendedCompilerBuilder(cache));

        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);
        builder.withSourceFactory(sourceFactory);
        Assert.assertEquals("Source factory instance", sourceFactory, builder.createSourceFactory());

        Mockito.verify(builder).withSourceFactory(sourceFactory);
        Mockito.verify(builder).createSourceFactory();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache, sourceFactory);
    }

    @Test
    public void createPreProcessor_defaultValues() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingSourceCodeExtendedCompilerBuilder(cache));

        final SourceTreeWithCodeCachingPreparationProcessorBuilder sourceTreeBuilder = Mockito
                .mock(SourceTreeWithCodeCachingPreparationProcessorBuilder.class);
        final SourceTreeWithCodeCachingPreparationProcessor preProcessor = Mockito
                .mock(SourceTreeWithCodeCachingPreparationProcessor.class);

        Mockito.when(sourceTreeBuilder.create()).thenReturn(preProcessor);

        Assert.assertEquals("Pre processor instance", preProcessor, builder.createPreProcessor(sourceTreeBuilder));

        Mockito.verify(builder).createPreProcessor(sourceTreeBuilder);
        Mockito.verify(sourceTreeBuilder).withExpirationChecker(Matchers.any(SourceModificationDateBasedExpirationChecker.class));
        Mockito.verify(sourceTreeBuilder).withImportResolver(Matchers.any(LessImportResolverImpl.class));
        Mockito.verify(sourceTreeBuilder).withImportReplacer(Matchers.any(LessImportReplacerImpl.class));
        Mockito.verify(builder).createSourceFactory();
        Mockito.verify(builder).createSourceFactoryFromBuilder(Matchers.any(SourceFactoryBuilder.class));
        Mockito.verify(sourceTreeBuilder).withSourceFactory(Matchers.any(SourceFactoryImpl.class));
        Mockito.verify(sourceTreeBuilder).create();
        Mockito.verifyNoMoreInteractions(cache, builder, sourceTreeBuilder);
    }

    @Test
    public void createPreProcessor_customValues() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingSourceCodeExtendedCompilerBuilder(cache));

        final SourceExpirationChecker expirationChecker = Mockito.mock(SourceExpirationChecker.class);
        final LessImportResolver importResolver = Mockito.mock(LessImportResolver.class);
        final LessImportReplacer importReplacer = Mockito.mock(LessImportReplacer.class);
        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);
        builder.withExpirationChecker(expirationChecker);
        builder.withImportResolver(importResolver);
        builder.withImportReplacer(importReplacer);
        builder.withSourceFactory(sourceFactory);

        final SourceTreeWithCodeCachingPreparationProcessorBuilder sourceTreeBuilder = Mockito
                .mock(SourceTreeWithCodeCachingPreparationProcessorBuilder.class);
        final SourceTreeWithCodeCachingPreparationProcessor preProcessor = Mockito
                .mock(SourceTreeWithCodeCachingPreparationProcessor.class);

        Mockito.when(sourceTreeBuilder.create()).thenReturn(preProcessor);

        Assert.assertEquals("Pre processor instance", preProcessor, builder.createPreProcessor(sourceTreeBuilder));

        Mockito.verify(builder).withExpirationChecker(expirationChecker);
        Mockito.verify(builder).withImportResolver(importResolver);
        Mockito.verify(builder).withImportReplacer(importReplacer);
        Mockito.verify(builder).withSourceFactory(sourceFactory);
        Mockito.verify(builder).createPreProcessor(sourceTreeBuilder);
        Mockito.verify(sourceTreeBuilder).withExpirationChecker(expirationChecker);
        Mockito.verify(sourceTreeBuilder).withImportResolver(importResolver);
        Mockito.verify(sourceTreeBuilder).withImportReplacer(importReplacer);
        Mockito.verify(builder).createSourceFactory();
        Mockito.verify(sourceTreeBuilder).withSourceFactory(sourceFactory);
        Mockito.verify(sourceTreeBuilder).create();
        Mockito.verifyNoMoreInteractions(builder, sourceTreeBuilder);
        Mockito.verifyZeroInteractions(cache, expirationChecker, importResolver, importReplacer, sourceFactory);
    }

    @Test
    public void create_defaultValues() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingSourceCodeExtendedCompilerBuilder(cache));

        final ExtendedCompiler extendedCompiler = builder.create();
        Assert.assertNotNull("Extended compiler instance", extendedCompiler);
        Assert.assertEquals("Extended compiler class", SimpleExtendedCompiler.class, extendedCompiler.getClass());
        final SimpleExtendedCompiler simpleExtendedCompiler = (SimpleExtendedCompiler) extendedCompiler;

        Assert.assertNotNull("Compiler instance", simpleExtendedCompiler.compiler);
        Assert.assertEquals("Compiler class", LessCompilerImpl.class, simpleExtendedCompiler.compiler.getClass());
        Assert.assertNotNull("Pre processor instance", simpleExtendedCompiler.preProcessor);
        Assert.assertEquals("Pre processor class", SourceTreeWithCodeCachingPreparationProcessor.class,
                simpleExtendedCompiler.preProcessor.getClass());
        Assert.assertNotNull("File provider instance", simpleExtendedCompiler.fileProvider);
        Assert.assertEquals("File provider class", CachedSourceFileProvider.class, simpleExtendedCompiler.fileProvider.getClass());
        Assert.assertNotNull("Post processor instance", simpleExtendedCompiler.postProcessor);
        Assert.assertEquals("Post processor class", DoNothingPostCompilationProcessor.class,
                simpleExtendedCompiler.postProcessor.getClass());

        Mockito.verify(builder).create();
        Mockito.verify(builder).createPreProcessor(Matchers.any(SourceTreeWithCodeCachingPreparationProcessorBuilder.class));
        Mockito.verify(builder).createSourceFactory();
        Mockito.verify(builder).createSourceFactoryFromBuilder(Matchers.any(SourceFactoryBuilder.class));
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void create_customValues() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingSourceCodeExtendedCompilerBuilder(cache));

        final LessCompiler compiler = Mockito.mock(LessCompiler.class);
        final PostCompilationProcessor postProcessor = Mockito.mock(PostCompilationProcessor.class);
        builder.withCompiler(compiler);
        builder.withPostProcessor(postProcessor);

        final ExtendedCompiler extendedCompiler = builder.create();
        Assert.assertNotNull("Extended compiler instance", extendedCompiler);
        Assert.assertEquals("Extended compiler class", SimpleExtendedCompiler.class, extendedCompiler.getClass());
        final SimpleExtendedCompiler simpleExtendedCompiler = (SimpleExtendedCompiler) extendedCompiler;

        Assert.assertEquals("Compiler instance", compiler, simpleExtendedCompiler.compiler);
        Assert.assertNotNull("Pre processor instance", simpleExtendedCompiler.preProcessor);
        Assert.assertEquals("Pre processor class", SourceTreeWithCodeCachingPreparationProcessor.class,
                simpleExtendedCompiler.preProcessor.getClass());
        Assert.assertNotNull("File provider instance", simpleExtendedCompiler.fileProvider);
        Assert.assertEquals("File provider class", CachedSourceFileProvider.class, simpleExtendedCompiler.fileProvider.getClass());
        Assert.assertEquals("Post processor instance", postProcessor, simpleExtendedCompiler.postProcessor);

        Mockito.verify(builder).withCompiler(compiler);
        Mockito.verify(builder).withPostProcessor(postProcessor);
        Mockito.verify(builder).create();
        Mockito.verify(builder).createPreProcessor(Matchers.any(SourceTreeWithCodeCachingPreparationProcessorBuilder.class));
        Mockito.verify(builder).createSourceFactory();
        Mockito.verify(builder).createSourceFactoryFromBuilder(Matchers.any(SourceFactoryBuilder.class));
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }
}
