package biz.gabrys.lesscss.extended.compiler.control.processor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import biz.gabrys.lesscss.extended.compiler.cache.SourceImportsCache;
import biz.gabrys.lesscss.extended.compiler.cache.SourceModificationDateCache;
import biz.gabrys.lesscss.extended.compiler.control.expiration.SourceExpirationChecker;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportOperation;
import biz.gabrys.lesscss.extended.compiler.imports.LessImportResolver;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactory;

public final class SourceTreePreparationProcessorTest {

    @Test
    public void prepare2_sourceWithoutImports_preparedSource() {
        final SourceExpirationChecker expirationChecker = Mockito.mock(SourceExpirationChecker.class);
        final SourceModificationDateCache datesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.spy(new SourceImportsCache() {

            public void saveSourceImports(final LessSource source, final List<String> importPaths) {
                Assert.assertTrue("List stores import paths must be empty", importPaths.isEmpty());
            }

            public boolean hasSourceImports(final LessSource source) {
                Assert.fail("hasSourceImports should not be called");
                return false;
            }

            public List<String> getSourceImports(final LessSource source) {
                Assert.fail("getSourceImports should not be called");
                return null;
            }
        });
        final LessImportResolver importResolver = Mockito.mock(LessImportResolver.class);
        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);
        final SourceTreePreparationProcessor processor = Mockito
                .spy(new SourceTreePreparationProcessor(expirationChecker, datesCache, importsCache, importResolver, sourceFactory));

        final LessSource source = Mockito.mock(LessSource.class);
        final String sourceCode = "sourceCode";
        Mockito.when(source.getContent()).thenReturn(sourceCode);
        final Date lastModificationDate = Mockito.mock(Date.class);
        Mockito.when(source.getLastModificationDate()).thenReturn(lastModificationDate);

        Mockito.when(importResolver.resolve(sourceCode)).thenReturn(Collections.<LessImportOperation>emptyList());

        @SuppressWarnings("unchecked")
        final Set<String> preparedSourcesPaths = Mockito.mock(Set.class);

        processor.prepareWhenExpired(source, preparedSourcesPaths);

        Mockito.verify(processor).prepareWhenExpired(source, preparedSourcesPaths);
        Mockito.verify(source).getContent();
        Mockito.verify(source).getLastModificationDate();
        Mockito.verify(datesCache).saveSourceModificationDate(source, lastModificationDate);
        Mockito.verify(importResolver).resolve(sourceCode);
        Mockito.verify(importsCache).saveSourceImports(Matchers.any(LessSource.class), Matchers.anyListOf(String.class));
        Mockito.verifyNoMoreInteractions(processor, source, expirationChecker, datesCache, lastModificationDate, importsCache,
                importResolver, sourceFactory);
        Mockito.verifyZeroInteractions(preparedSourcesPaths);
    }

    @Test
    public void prepare2_sourceWithImports_preparedSources() {
        final SourceExpirationChecker expirationChecker = Mockito.mock(SourceExpirationChecker.class);
        final SourceModificationDateCache datesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final LessImportResolver importResolver = Mockito.mock(LessImportResolver.class);
        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);
        final SourceTreePreparationProcessor processor = Mockito
                .spy(new SourceTreePreparationProcessor(expirationChecker, datesCache, importsCache, importResolver, sourceFactory));

        final LessSource source = Mockito.mock(LessSource.class);
        final String sourceCode = "sourceCode";
        Mockito.when(source.getContent()).thenReturn(sourceCode);
        final Date lastModificationDate = Mockito.mock(Date.class);
        Mockito.when(source.getLastModificationDate()).thenReturn(lastModificationDate);

        final String importPath = "import";
        final LessSource importSource = Mockito.mock(LessSource.class);
        final LessImportOperation importOperation = Mockito.mock(LessImportOperation.class);
        Mockito.when(importOperation.getComputedPath()).thenReturn(importPath);
        Mockito.when(sourceFactory.create(source, importPath)).thenReturn(importSource);
        Mockito.when(importResolver.resolve(sourceCode)).thenReturn(Arrays.asList(importOperation));

        @SuppressWarnings("unchecked")
        final Set<String> preparedSourcesPaths = Mockito.mock(Set.class);

        Mockito.doNothing().when(processor).prepare(importSource, preparedSourcesPaths);

        processor.prepareWhenExpired(source, preparedSourcesPaths);

        Mockito.verify(processor).prepareWhenExpired(source, preparedSourcesPaths);
        Mockito.verify(source).getContent();
        Mockito.verify(source).getLastModificationDate();
        Mockito.verify(datesCache).saveSourceModificationDate(source, lastModificationDate);
        Mockito.verify(importResolver).resolve(sourceCode);
        Mockito.verify(importOperation, Mockito.times(2)).getComputedPath();
        Mockito.verify(sourceFactory).create(source, importPath);
        Mockito.verify(processor).prepare(importSource, preparedSourcesPaths);
        Mockito.verify(importsCache).saveSourceImports(Matchers.any(LessSource.class), Matchers.anyListOf(String.class));
        Mockito.verifyNoMoreInteractions(processor, source, datesCache, importsCache, importResolver, sourceFactory);
        Mockito.verifyZeroInteractions(preparedSourcesPaths, lastModificationDate, expirationChecker);
    }
}
