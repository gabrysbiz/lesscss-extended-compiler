package biz.gabrys.lesscss.extended.compiler.control.processor;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import biz.gabrys.lesscss.extended.compiler.cache.FullCache;
import biz.gabrys.lesscss.extended.compiler.control.expiration.SourceAlwaysExpiredChecker;
import biz.gabrys.lesscss.extended.compiler.control.expiration.SourceExpirationChecker;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportResolver;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportResolverImpl;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactory;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactoryBuilder;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactoryImpl;

public final class SourceTreePreparationProcessorBuilderTest {
    @Test
    public void createExpirationChecker_hasNotBeenSet_returnSourceAlwaysExpiredChecker() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final SourceTreePreparationProcessorBuilder builder = Mockito.spy(new SourceTreePreparationProcessorBuilder(cache));

        final SourceExpirationChecker expirationChecker = builder.createExpirationChecker();

        Assert.assertNotNull("Checker instance", expirationChecker);
        Assert.assertEquals("Checker class", SourceAlwaysExpiredChecker.class, expirationChecker.getClass());
        Mockito.verify(builder).createExpirationChecker();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createExpirationChecker_hasBeenSet_returnSetExpirationChecker() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final SourceTreePreparationProcessorBuilder builder = Mockito.spy(new SourceTreePreparationProcessorBuilder(cache));

        final SourceExpirationChecker expirationChecker = Mockito.mock(SourceExpirationChecker.class);
        final SourceExpirationChecker expirationChecker2 = builder.withExpirationChecker(expirationChecker).createExpirationChecker();

        Assert.assertEquals("Checker instance", expirationChecker, expirationChecker2);
        Mockito.verify(builder).withExpirationChecker(expirationChecker);
        Mockito.verify(builder).createExpirationChecker();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createImportResolver_hasNotBeenSet_returnLessImportResolverImpl() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final SourceTreePreparationProcessorBuilder builder = Mockito.spy(new SourceTreePreparationProcessorBuilder(cache));

        final LessImportResolver importResolver = builder.createImportResolver();

        Assert.assertNotNull("Resolver instance", importResolver);
        Assert.assertEquals("Resolver class", LessImportResolverImpl.class, importResolver.getClass());
        Mockito.verify(builder).createImportResolver();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createImportResolver_hasBeenSet_returnSetResolver() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final SourceTreePreparationProcessorBuilder builder = Mockito.spy(new SourceTreePreparationProcessorBuilder(cache));

        final LessImportResolver importResolver = Mockito.mock(LessImportResolver.class);
        final LessImportResolver importResolver2 = builder.withImportResolver(importResolver).createImportResolver();

        Assert.assertEquals("Resolver instance", importResolver, importResolver2);
        Mockito.verify(builder).withImportResolver(importResolver);
        Mockito.verify(builder).createImportResolver();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createSourceFactory_hasNotBeenSet_returnDefaultSourceFactory() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final SourceTreePreparationProcessorBuilder builder = Mockito.spy(new SourceTreePreparationProcessorBuilder(cache));

        final SourceFactory sourceFactory = builder.createSourceFactory();

        Assert.assertNotNull("Source factory instance", sourceFactory);
        Assert.assertEquals("Source factory class", SourceFactoryImpl.class, sourceFactory.getClass());

        Mockito.verify(builder).createSourceFactory();
        Mockito.verify(builder).createSourceFactoryFromBuilder(Matchers.any(SourceFactoryBuilder.class));
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }

    @Test
    public void createSourceFactory_hasBeenSet_returnSetSourceFactory() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final SourceTreePreparationProcessorBuilder builder = Mockito.spy(new SourceTreePreparationProcessorBuilder(cache));

        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);
        final SourceFactory sourceFactory2 = builder.withSourceFactory(sourceFactory).createSourceFactory();

        Assert.assertEquals("Source factory instance", sourceFactory, sourceFactory2);
        Mockito.verify(builder).withSourceFactory(sourceFactory);
        Mockito.verify(builder).createSourceFactory();
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache, sourceFactory);
    }

    @Test
    public void createSourceFactoryFromBuilder_withStandard_returnSourceFactory() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final SourceTreePreparationProcessorBuilder builder = Mockito.spy(new SourceTreePreparationProcessorBuilder(cache));

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
    public void create_defaultValues() {
        final FullCache cache = Mockito.mock(FullCache.class);
        final SourceTreePreparationProcessorBuilder builder = Mockito.spy(new SourceTreePreparationProcessorBuilder(cache));

        final SourceTreePreparationProcessor processor = builder.create();
        Assert.assertNotNull("Processor instance", processor);
        Assert.assertNotNull("Expiration checker instance", processor.expirationChecker);
        Assert.assertEquals("Expiration checker class", SourceAlwaysExpiredChecker.class, processor.expirationChecker.getClass());
        Assert.assertEquals("Imports cache instance", cache, processor.importsCache);
        Assert.assertNotNull("Source factory instance", processor.sourceFactory);
        Assert.assertEquals("Source factory class", SourceFactoryImpl.class, processor.sourceFactory.getClass());
        Assert.assertEquals("Dates cache instance", cache, processor.datesCache);
        Assert.assertNotNull("Import resolver instance", processor.importResolver);
        Assert.assertEquals("Import resolver class", LessImportResolverImpl.class, processor.importResolver.getClass());

        Mockito.verify(builder).create();

        Mockito.verify(builder).createExpirationChecker();
        Mockito.verify(builder).createImportResolver();
        Mockito.verify(builder).createSourceFactory();
        Mockito.verify(builder).createSourceFactoryFromBuilder(Matchers.any(SourceFactoryBuilder.class));
        Mockito.verifyNoMoreInteractions(builder);
        Mockito.verifyZeroInteractions(cache);
    }
}
