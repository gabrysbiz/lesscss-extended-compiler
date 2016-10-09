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
import biz.gabrys.lesscss.extended.compiler.control.processor.PreCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.SourceTreeWithCodeCachingPreparationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.SourceTreeWithCodeCachingPreparationProcessorBuilder;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportReplacer;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportReplacerImpl;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportResolver;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportResolverImpl;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactory;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactoryBuilder;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactoryImpl;

public final class CachingSourceAndCompiledCodeExtendedCompilerBuilderTest {

    @Test
    public void createLessCompiler_hasNotBeenSet_returnDefaultCompiler() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceAndCompiledCodeExtendedCompilerBuilder builder = Mockito
                .spy(new CachingSourceAndCompiledCodeExtendedCompilerBuilder(cache));

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
        final CachingSourceAndCompiledCodeExtendedCompilerBuilder builder = Mockito
                .spy(new CachingSourceAndCompiledCodeExtendedCompilerBuilder(cache));

        final LessCompiler compiler = Mockito.mock(LessCompiler.class);
        builder.withCompiler(compiler);

        Assert.assertEquals("Should return set compiler", compiler, builder.createLessCompiler());

        Mockito.verify(builder).withCompiler(compiler);
        Mockito.verify(builder).createLessCompiler();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createExpirationChecker_hasNotBeenSet_returnDefaultChecker() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceAndCompiledCodeExtendedCompilerBuilder builder = Mockito
                .spy(new CachingSourceAndCompiledCodeExtendedCompilerBuilder(cache));

        final SourceExpirationChecker checker = builder.createExpirationChecker();
        Assert.assertNotNull("Expiration checker instance", checker);
        Assert.assertEquals("Expiration checker class", SourceModificationDateBasedExpirationChecker.class, checker.getClass());

        Mockito.verify(builder).createExpirationChecker();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createExpirationChecker_hasBeenSet_returnSetChecker() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceAndCompiledCodeExtendedCompilerBuilder builder = Mockito
                .spy(new CachingSourceAndCompiledCodeExtendedCompilerBuilder(cache));

        final SourceExpirationChecker checker = Mockito.mock(SourceExpirationChecker.class);
        builder.withExpirationChecker(checker);

        Assert.assertEquals("Should return set expiration checker", checker, builder.createExpirationChecker());

        Mockito.verify(builder).withExpirationChecker(checker);
        Mockito.verify(builder).createExpirationChecker();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createSourceFactory_hasNotBeenSet_returnDefaultFactory() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceAndCompiledCodeExtendedCompilerBuilder builder = Mockito
                .spy(new CachingSourceAndCompiledCodeExtendedCompilerBuilder(cache));

        final SourceFactory factory = builder.createSourceFactory();
        Assert.assertNotNull("Source factory instance", factory);

        Mockito.verify(builder).createSourceFactory();
        Mockito.verify(builder).createSourceFactoryFromBuilder(Matchers.any(SourceFactoryBuilder.class));
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createSourceFactoryFromBuilder_withStandard_returnSourceFactory() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceAndCompiledCodeExtendedCompilerBuilder builder = Mockito
                .spy(new CachingSourceAndCompiledCodeExtendedCompilerBuilder(cache));

        final SourceFactoryBuilder sourceBuilder = Mockito.mock(SourceFactoryBuilder.class);
        Mockito.when(sourceBuilder.withStandard()).thenReturn(sourceBuilder);
        final SourceFactoryImpl factory = Mockito.mock(SourceFactoryImpl.class);
        Mockito.when(sourceBuilder.create()).thenReturn(factory);

        Assert.assertEquals("Source factory instance", factory, builder.createSourceFactoryFromBuilder(sourceBuilder));

        Mockito.verify(builder).createSourceFactoryFromBuilder(sourceBuilder);
        Mockito.verify(sourceBuilder).withStandard();
        Mockito.verify(sourceBuilder).create();
        Mockito.verifyNoMoreInteractions(builder, sourceBuilder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createSourceFactory_hasBeenSet_returnSetFactory() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceAndCompiledCodeExtendedCompilerBuilder builder = Mockito
                .spy(new CachingSourceAndCompiledCodeExtendedCompilerBuilder(cache));

        final SourceFactory factory = Mockito.mock(SourceFactory.class);
        builder.withSourceFactory(factory);

        Assert.assertEquals("Should return set source factory", factory, builder.createSourceFactory());

        Mockito.verify(builder).withSourceFactory(factory);
        Mockito.verify(builder).createSourceFactory();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createImportResolver_hasNotBeenSet_returnDefaultResolver() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceAndCompiledCodeExtendedCompilerBuilder builder = Mockito
                .spy(new CachingSourceAndCompiledCodeExtendedCompilerBuilder(cache));

        final LessImportResolver resolver = builder.createImportResolver();
        Assert.assertNotNull("Import resolver instance", resolver);
        Assert.assertEquals("Import resolver class", LessImportResolverImpl.class, resolver.getClass());

        Mockito.verify(builder).createImportResolver();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createImportResolver_hasBeenSet_returnSetResolver() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceAndCompiledCodeExtendedCompilerBuilder builder = Mockito
                .spy(new CachingSourceAndCompiledCodeExtendedCompilerBuilder(cache));

        final LessImportResolver resolver = Mockito.mock(LessImportResolver.class);
        builder.withImportResolver(resolver);

        Assert.assertEquals("Should return set import resolver", resolver, builder.createImportResolver());

        Mockito.verify(builder).withImportResolver(resolver);
        Mockito.verify(builder).createImportResolver();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createImportReplacer_hasNotBeenSet_returnDefaultReplacer() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceAndCompiledCodeExtendedCompilerBuilder builder = Mockito
                .spy(new CachingSourceAndCompiledCodeExtendedCompilerBuilder(cache));

        final LessImportReplacer replacer = builder.createImportReplacer();
        Assert.assertNotNull("Import replacer instance", replacer);
        Assert.assertEquals("Import replacer class", LessImportReplacerImpl.class, replacer.getClass());

        Mockito.verify(builder).createImportReplacer();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createImportReplacer_hasBeenSet_returnSetReplacer() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceAndCompiledCodeExtendedCompilerBuilder builder = Mockito
                .spy(new CachingSourceAndCompiledCodeExtendedCompilerBuilder(cache));

        final LessImportReplacer replacer = Mockito.mock(LessImportReplacer.class);
        builder.withImportReplacer(replacer);

        Assert.assertEquals("Should return set import replacer", replacer, builder.createImportReplacer());

        Mockito.verify(builder).withImportReplacer(replacer);
        Mockito.verify(builder).createImportReplacer();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createPreProcessor_valuesHaveBeenSet_returnProcessor() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceAndCompiledCodeExtendedCompilerBuilder builder = Mockito
                .spy(new CachingSourceAndCompiledCodeExtendedCompilerBuilder(cache));

        final SourceExpirationChecker checker = Mockito.mock(SourceExpirationChecker.class);
        builder.withExpirationChecker(checker);
        final LessImportResolver resolver = Mockito.mock(LessImportResolver.class);
        builder.withImportResolver(resolver);
        final LessImportReplacer replacer = Mockito.mock(LessImportReplacer.class);
        builder.withImportReplacer(replacer);
        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);
        builder.withSourceFactory(sourceFactory);

        final SourceTreeWithCodeCachingPreparationProcessorBuilder sourceTreeBuilder = Mockito
                .mock(SourceTreeWithCodeCachingPreparationProcessorBuilder.class);
        final SourceTreeWithCodeCachingPreparationProcessor processor = Mockito.mock(SourceTreeWithCodeCachingPreparationProcessor.class);
        Mockito.when(sourceTreeBuilder.create()).thenReturn(processor);

        Assert.assertEquals("Pre processor instance", processor, builder.createPreProcessor(sourceTreeBuilder, checker, sourceFactory));

        Mockito.verify(builder).withExpirationChecker(checker);
        Mockito.verify(builder).withImportResolver(resolver);
        Mockito.verify(builder).withImportReplacer(replacer);
        Mockito.verify(builder).withSourceFactory(sourceFactory);
        Mockito.verify(builder).createPreProcessor(sourceTreeBuilder, checker, sourceFactory);
        Mockito.verify(sourceTreeBuilder).withExpirationChecker(checker);
        Mockito.verify(builder).createImportResolver();
        Mockito.verify(sourceTreeBuilder).withImportResolver(resolver);
        Mockito.verify(builder).createImportReplacer();
        Mockito.verify(sourceTreeBuilder).withImportReplacer(replacer);
        Mockito.verify(sourceTreeBuilder).withSourceFactory(sourceFactory);
        Mockito.verify(sourceTreeBuilder).create();
        Mockito.verifyNoMoreInteractions(builder, sourceTreeBuilder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createPostProcessor_hasNotBeenSet_returnDefaultProcessor() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceAndCompiledCodeExtendedCompilerBuilder builder = Mockito
                .spy(new CachingSourceAndCompiledCodeExtendedCompilerBuilder(cache));

        final PostCompilationProcessor processor = builder.createPostProcessor();
        Assert.assertNotNull("Post processor instance", processor);
        Assert.assertEquals("Post processor class", DoNothingPostCompilationProcessor.class, processor.getClass());

        Mockito.verify(builder).createPostProcessor();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createPostProcessor_hasBeenSet_returnSetProcessor() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceAndCompiledCodeExtendedCompilerBuilder builder = Mockito
                .spy(new CachingSourceAndCompiledCodeExtendedCompilerBuilder(cache));

        final PostCompilationProcessor processor = Mockito.mock(PostCompilationProcessor.class);
        builder.withPostProcessor(processor);

        Assert.assertEquals("Should return set post processor", processor, builder.createPostProcessor());

        Mockito.verify(builder).withPostProcessor(processor);
        Mockito.verify(builder).createPostProcessor();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void create_defaultValues() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final CachingSourceAndCompiledCodeExtendedCompilerBuilder builder = Mockito
                .spy(new CachingSourceAndCompiledCodeExtendedCompilerBuilder(cache));

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
        Mockito.verify(builder).createExpirationChecker();
        Mockito.verify(builder).createSourceFactory();
        Mockito.verify(builder).createSourceFactoryFromBuilder(Matchers.any(SourceFactoryBuilder.class));
        Mockito.verify(builder).createPreProcessor(Matchers.any(SourceTreeWithCodeCachingPreparationProcessorBuilder.class),
                Matchers.any(SourceExpirationChecker.class), Matchers.any(SourceFactory.class));
        Mockito.verify(builder).createImportResolver();
        Mockito.verify(builder).createImportReplacer();
        Mockito.verify(builder).createPostProcessor();
        Mockito.verify(builder).createSimpleExtendedCompiler(Matchers.any(LessCompilerImpl.class),
                Matchers.any(PreCompilationProcessor.class), Matchers.any(PostCompilationProcessor.class));
        Mockito.verify(builder).createCompiledSourceExpirationChecker(Matchers.any(SourceExpirationChecker.class),
                Matchers.any(SourceFactory.class));
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }
}
