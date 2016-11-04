package biz.gabrys.lesscss.extended.compiler;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import biz.gabrys.lesscss.compiler.LessCompiler;
import biz.gabrys.lesscss.compiler.LessCompilerImpl;
import biz.gabrys.lesscss.extended.compiler.cache.FullCache;
import biz.gabrys.lesscss.extended.compiler.control.expiration.CompiledSourceExpirationCheckerImpl;
import biz.gabrys.lesscss.extended.compiler.control.expiration.SourceExpirationChecker;
import biz.gabrys.lesscss.extended.compiler.control.expiration.SourceModificationDateBasedExpirationChecker;
import biz.gabrys.lesscss.extended.compiler.control.processor.DoNothingPostCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.PostCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.SourceTreePreparationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.SourceTreePreparationProcessorBuilder;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportResolver;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportResolverImpl;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactory;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactoryBuilder;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactoryImpl;

public final class CachingCompiledCodeExtendedCompilerBuilderTest {

    @Test
    public void createLessCompiler_hasNotBeenSet_returnDefaultCompiler() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingCompiledCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingCompiledCodeExtendedCompilerBuilder(cache));

        final LessCompiler compiler = builder.createLessCompiler();
        Assert.assertNotNull("Compiler instance", compiler);
        Assert.assertEquals("Compiler class", LessCompilerImpl.class, compiler.getClass());

        Mockito.verify(builder).createLessCompiler();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createLessCompiler_hasBeenSet_returnSetCompiler() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingCompiledCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingCompiledCodeExtendedCompilerBuilder(cache));

        final LessCompiler compiler = Mockito.mock(LessCompiler.class);
        builder.withCompiler(compiler);

        Assert.assertEquals("Should return set compiler", compiler, builder.createLessCompiler());

        Mockito.verify(builder).withCompiler(compiler);
        Mockito.verify(builder).createLessCompiler();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createSourceFactory_hasNotBeenSet_returnDefaultSourceFactory() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingCompiledCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingCompiledCodeExtendedCompilerBuilder(cache));

        final LessCompiler compiler = Mockito.mock(LessCompiler.class);
        final SourceFactory sourceFactory = builder.createSourceFactory(compiler);
        Assert.assertNotNull("Source factory instance", sourceFactory);
        Assert.assertEquals("Source factory class", SourceFactoryImpl.class, sourceFactory.getClass());

        Mockito.verify(builder).createSourceFactory(compiler);
        Mockito.verify(builder).createSourceFactoryFromBuilder(Matchers.any(LessCompiler.class), Matchers.any(SourceFactoryBuilder.class));
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache, compiler);
    }

    @Test
    public void createSourceFactory_hasBeenSet_returnSetSourceFactory() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingCompiledCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingCompiledCodeExtendedCompilerBuilder(cache));

        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);
        builder.withSourceFactory(sourceFactory);

        final LessCompiler compiler = Mockito.mock(LessCompiler.class);
        Assert.assertEquals("Should return set source factory", sourceFactory, builder.createSourceFactory(compiler));

        Mockito.verify(builder).withSourceFactory(sourceFactory);
        Mockito.verify(builder).createSourceFactory(compiler);
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache, compiler, sourceFactory);
    }

    @Test
    public void createSourceFactoryFromBuilder_compilerClassIsLessCompilerImpl_createFactoryWithLocal() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingCompiledCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingCompiledCodeExtendedCompilerBuilder(cache));

        final SourceFactoryBuilder sourceFactoryBuilder = Mockito.mock(SourceFactoryBuilder.class);
        final SourceFactoryImpl sourceFactory = Mockito.mock(SourceFactoryImpl.class);
        Mockito.when(sourceFactoryBuilder.create()).thenReturn(sourceFactory);
        final LessCompiler compiler = Mockito.spy(new LessCompilerImpl());

        Assert.assertEquals("Source factory instance", sourceFactory,
                builder.createSourceFactoryFromBuilder(compiler, sourceFactoryBuilder));

        Mockito.verify(builder).createSourceFactoryFromBuilder(compiler, sourceFactoryBuilder);
        Mockito.verify(sourceFactoryBuilder).withLocal();
        Mockito.verify(sourceFactoryBuilder).create();
        Mockito.verifyNoMoreInteractions(builder, sourceFactoryBuilder);
        Mockito.verifyZeroInteractions(cache, compiler);
    }

    @Test
    public void createSourceFactoryFromBuilder_compilerClassIsNotLessCompilerImpl_createFactoryWithStandard() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingCompiledCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingCompiledCodeExtendedCompilerBuilder(cache));

        final SourceFactoryBuilder sourceFactoryBuilder = Mockito.mock(SourceFactoryBuilder.class);
        final SourceFactoryImpl sourceFactory = Mockito.mock(SourceFactoryImpl.class);
        Mockito.when(sourceFactoryBuilder.create()).thenReturn(sourceFactory);
        final LessCompiler compiler = Mockito.mock(LessCompiler.class);

        Assert.assertEquals("Source factory instance", sourceFactory,
                builder.createSourceFactoryFromBuilder(compiler, sourceFactoryBuilder));

        Mockito.verify(builder).createSourceFactoryFromBuilder(compiler, sourceFactoryBuilder);
        Mockito.verify(sourceFactoryBuilder).withStandard();
        Mockito.verify(sourceFactoryBuilder).create();
        Mockito.verifyNoMoreInteractions(builder, sourceFactoryBuilder);
        Mockito.verifyZeroInteractions(cache, compiler);
    }

    @Test
    public void createPreProcessor_importResolverHasNotBeenSet_returnPreProcessorWithDefaultResolver() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingCompiledCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingCompiledCodeExtendedCompilerBuilder(cache));

        final SourceTreePreparationProcessorBuilder sourceTreeBuilder = Mockito.mock(SourceTreePreparationProcessorBuilder.class);
        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);

        builder.createPreProcessor(sourceTreeBuilder, sourceFactory);

        Mockito.verify(builder).createPreProcessor(sourceTreeBuilder, sourceFactory);
        Mockito.verify(builder).createImportResolver();
        Mockito.verify(sourceTreeBuilder).withImportResolver(Matchers.any(LessImportResolverImpl.class));
        Mockito.verify(sourceTreeBuilder).withSourceFactory(sourceFactory);
        Mockito.verify(sourceTreeBuilder).create();
        Mockito.verifyNoMoreInteractions(builder, sourceTreeBuilder);
        Mockito.verifyZeroInteractions(cache, sourceFactory);
    }

    @Test
    public void createPreProcessor_importResolverHasBeenSet_returnPreProcessorWithDefaultResolver() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingCompiledCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingCompiledCodeExtendedCompilerBuilder(cache));

        final LessImportResolver importResolver = Mockito.mock(LessImportResolver.class);
        builder.withImportResolver(importResolver);

        final SourceTreePreparationProcessorBuilder sourceTreeBuilder = Mockito.mock(SourceTreePreparationProcessorBuilder.class);
        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);
        builder.createPreProcessor(sourceTreeBuilder, sourceFactory);

        Mockito.verify(builder).withImportResolver(importResolver);
        Mockito.verify(builder).createPreProcessor(sourceTreeBuilder, sourceFactory);
        Mockito.verify(builder).createImportResolver();
        Mockito.verify(sourceTreeBuilder).withImportResolver(importResolver);
        Mockito.verify(sourceTreeBuilder).withSourceFactory(sourceFactory);
        Mockito.verify(sourceTreeBuilder).create();
        Mockito.verifyNoMoreInteractions(builder, sourceTreeBuilder);
        Mockito.verifyZeroInteractions(cache, sourceFactory);
    }

    @Test
    public void createPostProcessor_hasNotBeenSet_returnDefaultPostProcessor() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingCompiledCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingCompiledCodeExtendedCompilerBuilder(cache));

        final PostCompilationProcessor postProcessor = builder.createPostProcessor();
        Assert.assertNotNull("Post compilation processor instance", postProcessor);
        Assert.assertEquals("Post compilation processor class", DoNothingPostCompilationProcessor.class, postProcessor.getClass());

        Mockito.verify(builder).createPostProcessor();

        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createPostProcessor_hasBeenSet_returnSetPostProcessor() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingCompiledCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingCompiledCodeExtendedCompilerBuilder(cache));

        final PostCompilationProcessor postProcessor = Mockito.mock(PostCompilationProcessor.class);
        builder.withPostProcessor(postProcessor);

        Assert.assertEquals("Post compilation processor instance", postProcessor, builder.createPostProcessor());

        Mockito.verify(builder).withPostProcessor(postProcessor);
        Mockito.verify(builder).createPostProcessor();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createSourceExpirationChecker_hasNotBeenSet_returnDefaultChecker() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingCompiledCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingCompiledCodeExtendedCompilerBuilder(cache));

        final SourceExpirationChecker checker = builder.createSourceExpirationChecker();
        Assert.assertNotNull("Source expiration checker instance", checker);
        Assert.assertEquals("Source expiration checker class", SourceModificationDateBasedExpirationChecker.class, checker.getClass());

        Mockito.verify(builder).createSourceExpirationChecker();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createSourceExpirationChecker_hasBeenSet_returnSetChecker() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingCompiledCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingCompiledCodeExtendedCompilerBuilder(cache));

        final SourceExpirationChecker expirationChecker = Mockito.mock(SourceExpirationChecker.class);
        builder.withExpirationChecker(expirationChecker);

        Assert.assertEquals("Source expiration checker instance", expirationChecker, builder.createSourceExpirationChecker());

        Mockito.verify(builder).withExpirationChecker(expirationChecker);
        Mockito.verify(builder).createSourceExpirationChecker();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache, expirationChecker);
    }

    @Test
    public void create_defaultValues() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingCompiledCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingCompiledCodeExtendedCompilerBuilder(cache));

        final ExtendedCompiler extendedCompiler = builder.create();
        Assert.assertNotNull("Extended compiler instance", extendedCompiler);
        Assert.assertEquals("Extended compiler class", CachingCompiledCodeExtendedCompiler.class, extendedCompiler.getClass());
        final CachingCompiledCodeExtendedCompiler cashingExtendedCompiler = (CachingCompiledCodeExtendedCompiler) extendedCompiler;

        Assert.assertNotNull("Compiler instance", cashingExtendedCompiler.compiler);
        Assert.assertEquals("Compiler class", SimpleExtendedCompiler.class, cashingExtendedCompiler.compiler.getClass());
        Assert.assertNotNull("Expiration checker instance", cashingExtendedCompiler.expirationChecker);
        Assert.assertEquals("Expiration checker class", CompiledSourceExpirationCheckerImpl.class,
                cashingExtendedCompiler.expirationChecker.getClass());
        Assert.assertEquals("Dates cache instance", cache, cashingExtendedCompiler.datesCache);
        Assert.assertEquals("Code cache instance", cache, cashingExtendedCompiler.codeCache);

        Mockito.verify(builder).create();

        Mockito.verify(builder).createLessCompiler();
        Mockito.verify(builder).createSourceFactory(Matchers.any(LessCompilerImpl.class));
        Mockito.verify(builder).createSourceFactoryFromBuilder(Matchers.any(LessCompilerImpl.class),
                Matchers.any(SourceFactoryBuilder.class));
        Mockito.verify(builder).createPreProcessor(Matchers.any(SourceTreePreparationProcessorBuilder.class),
                Matchers.any(SourceFactoryImpl.class));
        Mockito.verify(builder).createImportResolver();
        Mockito.verify(builder).createPostProcessor();
        Mockito.verify(builder).createSimpleExtendedCompiler(Matchers.any(LessCompilerImpl.class),
                Matchers.any(SourceTreePreparationProcessor.class), Matchers.any(DoNothingPostCompilationProcessor.class));
        Mockito.verify(builder).createCompiledSourceExpirationChecker(Matchers.any(SourceFactoryImpl.class));
        Mockito.verify(builder).createSourceExpirationChecker();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void create_customSourceFactory() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingCompiledCodeExtendedCompilerBuilder builder = Mockito.spy(new CachingCompiledCodeExtendedCompilerBuilder(cache));

        final SourceFactory sourceFactory = new SourceFactoryDummy();
        builder.withSourceFactory(sourceFactory);

        final ExtendedCompiler extendedCompiler = builder.create();
        Assert.assertNotNull("Extended compiler instance", extendedCompiler);
        Assert.assertEquals("Extended compiler class", CachingCompiledCodeExtendedCompiler.class, extendedCompiler.getClass());
        final CachingCompiledCodeExtendedCompiler cashingExtendedCompiler = (CachingCompiledCodeExtendedCompiler) extendedCompiler;

        Assert.assertNotNull("Compiler instance", cashingExtendedCompiler.compiler);
        Assert.assertEquals("Compiler class", SimpleExtendedCompiler.class, cashingExtendedCompiler.compiler.getClass());
        Assert.assertNotNull("Expiration checker instance", cashingExtendedCompiler.expirationChecker);
        Assert.assertEquals("Expiration checker class", CompiledSourceExpirationCheckerImpl.class,
                cashingExtendedCompiler.expirationChecker.getClass());
        Assert.assertEquals("Dates cache instance", cache, cashingExtendedCompiler.datesCache);
        Assert.assertEquals("Code cache instance", cache, cashingExtendedCompiler.codeCache);

        Mockito.verify(builder).withSourceFactory(sourceFactory);
        Mockito.verify(builder).create();

        Mockito.verify(builder).createLessCompiler();
        Mockito.verify(builder).createSourceFactory(Matchers.any(LessCompilerImpl.class));
        Mockito.verify(builder).createPreProcessor(Matchers.any(SourceTreePreparationProcessorBuilder.class),
                Matchers.any(SourceFactoryDummy.class));
        Mockito.verify(builder).createImportResolver();
        Mockito.verify(builder).createPostProcessor();
        Mockito.verify(builder).createSimpleExtendedCompiler(Matchers.any(LessCompilerImpl.class),
                Matchers.any(SourceTreePreparationProcessor.class), Matchers.any(DoNothingPostCompilationProcessor.class));
        Mockito.verify(builder).createCompiledSourceExpirationChecker(Matchers.any(SourceFactoryDummy.class));
        Mockito.verify(builder).createSourceExpirationChecker();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    private static class SourceFactoryDummy implements SourceFactory {

        public LessSource create(final LessSource source, final String importPath) {
            return null;
        }
    }
}
