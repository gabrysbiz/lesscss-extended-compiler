package biz.gabrys.lesscss.extended.compiler.control.processor;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.lesscss.extended.compiler.cache.SourceImportsCache;
import biz.gabrys.lesscss.extended.compiler.control.expiration.SourceExpirationChecker;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactory;

public final class AbstractSourceTreePreparationProcessorTest {

    @Test
    public void prepare_sourceExpired_preparedSource() {
        final SourceExpirationChecker expirationChecker = Mockito.mock(SourceExpirationChecker.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);
        final PreCompilationProcessorImpl processor = Mockito
                .spy(new PreCompilationProcessorImpl(expirationChecker, importsCache, sourceFactory));

        final LessSource source = Mockito.mock(LessSource.class);
        final String sourcePath = "sourcePath";
        Mockito.when(source.getPath()).thenReturn(sourcePath);
        Mockito.when(expirationChecker.isExpired(source)).thenReturn(true);

        final Set<String> preparedSourcesPaths = Mockito.spy(new HashSet<String>());

        processor.prepare(source, preparedSourcesPaths);

        Mockito.verify(processor).prepare(source, preparedSourcesPaths);
        Mockito.verify(source).getPath();
        Mockito.verify(preparedSourcesPaths).contains(sourcePath);
        Mockito.verify(preparedSourcesPaths).add(sourcePath);
        Mockito.verify(expirationChecker).isExpired(source);
        Mockito.verify(processor).prepareWhenExpired(source, preparedSourcesPaths);
        Mockito.verifyNoMoreInteractions(processor, source, preparedSourcesPaths, expirationChecker);
        Mockito.verifyZeroInteractions(importsCache, sourceFactory);
    }

    @Test
    public void prepare_sourceNotExpiredWithoutImports_didNothing() {
        final SourceExpirationChecker expirationChecker = Mockito.mock(SourceExpirationChecker.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);
        final PreCompilationProcessorImpl processor = Mockito
                .spy(new PreCompilationProcessorImpl(expirationChecker, importsCache, sourceFactory));

        final LessSource source = Mockito.mock(LessSource.class);
        final String sourcePath = "sourcePath";
        Mockito.when(source.getPath()).thenReturn(sourcePath);
        Mockito.when(expirationChecker.isExpired(source)).thenReturn(false);

        Mockito.when(importsCache.getSourceImports(source)).thenReturn(Collections.<String>emptyList());

        final Set<String> preparedSourcesPaths = Mockito.spy(new HashSet<String>());

        processor.prepare(source, preparedSourcesPaths);

        Mockito.verify(processor).prepare(source, preparedSourcesPaths);
        Mockito.verify(source).getPath();
        Mockito.verify(preparedSourcesPaths).contains(sourcePath);
        Mockito.verify(preparedSourcesPaths).add(sourcePath);
        Mockito.verify(expirationChecker).isExpired(source);
        Mockito.verify(importsCache).getSourceImports(source);
        Mockito.verifyNoMoreInteractions(processor, source, preparedSourcesPaths, expirationChecker, importsCache);
        Mockito.verifyZeroInteractions(sourceFactory);
    }

    @Test
    public void prepare_sourceNotExpiredWithExpiredImport_preparedImport() {
        final SourceExpirationChecker expirationChecker = Mockito.mock(SourceExpirationChecker.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);
        final PreCompilationProcessorImpl processor = Mockito
                .spy(new PreCompilationProcessorImpl(expirationChecker, importsCache, sourceFactory));

        final LessSource source = Mockito.mock(LessSource.class);
        final String sourcePath = "sourcePath";
        Mockito.when(source.getPath()).thenReturn(sourcePath);
        Mockito.when(expirationChecker.isExpired(source)).thenReturn(false);

        final String importPath = "importPath";
        Mockito.when(importsCache.getSourceImports(source)).thenReturn(Arrays.asList(importPath));

        final LessSource importSource = Mockito.mock(LessSource.class);
        Mockito.when(importSource.getPath()).thenReturn(importPath);
        Mockito.when(sourceFactory.create(source, importPath)).thenReturn(importSource);
        Mockito.when(expirationChecker.isExpired(importSource)).thenReturn(true);

        final Set<String> preparedSourcesPaths = Mockito.spy(new HashSet<String>());

        processor.prepare(source, preparedSourcesPaths);

        Mockito.verify(processor).prepare(source, preparedSourcesPaths);
        Mockito.verify(source).getPath();
        Mockito.verify(preparedSourcesPaths).contains(sourcePath);
        Mockito.verify(preparedSourcesPaths).add(sourcePath);
        Mockito.verify(expirationChecker).isExpired(source);
        Mockito.verify(importsCache).getSourceImports(source);
        Mockito.verify(sourceFactory).create(source, importPath);
        Mockito.verify(processor).prepare(importSource, preparedSourcesPaths);
        Mockito.verify(importSource).getPath();
        Mockito.verify(preparedSourcesPaths).contains(importPath);
        Mockito.verify(preparedSourcesPaths).add(importPath);
        Mockito.verify(expirationChecker).isExpired(importSource);
        Mockito.verify(processor).prepareWhenExpired(importSource, preparedSourcesPaths);
        Mockito.verifyNoMoreInteractions(processor, source, expirationChecker, preparedSourcesPaths, importsCache, sourceFactory);
    }

    @Test
    public void prepare_sourceNotExpiredWithNotExpiredImport_didNothing() {
        final SourceExpirationChecker expirationChecker = Mockito.mock(SourceExpirationChecker.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);
        final PreCompilationProcessorImpl processor = Mockito
                .spy(new PreCompilationProcessorImpl(expirationChecker, importsCache, sourceFactory));

        final LessSource source = Mockito.mock(LessSource.class);
        final String sourcePath = "sourcePath";
        Mockito.when(source.getPath()).thenReturn(sourcePath);
        Mockito.when(expirationChecker.isExpired(source)).thenReturn(false);

        final String importPath = "importPath";
        Mockito.when(importsCache.getSourceImports(source)).thenReturn(Arrays.asList(importPath));

        final LessSource importSource = Mockito.mock(LessSource.class);
        Mockito.when(sourceFactory.create(source, importPath)).thenReturn(importSource);
        Mockito.when(importSource.getPath()).thenReturn(importPath);
        Mockito.when(expirationChecker.isExpired(importSource)).thenReturn(false);

        Mockito.when(importsCache.getSourceImports(importSource)).thenReturn(Collections.<String>emptyList());

        final Set<String> preparedSourcesPaths = Mockito.spy(new HashSet<String>());

        processor.prepare(source, preparedSourcesPaths);

        Mockito.verify(processor).prepare(source, preparedSourcesPaths);
        Mockito.verify(source).getPath();
        Mockito.verify(preparedSourcesPaths).contains(sourcePath);
        Mockito.verify(preparedSourcesPaths).add(sourcePath);
        Mockito.verify(expirationChecker).isExpired(source);
        Mockito.verify(importsCache).getSourceImports(source);
        Mockito.verify(sourceFactory).create(source, importPath);
        Mockito.verify(processor).prepare(importSource, preparedSourcesPaths);
        Mockito.verify(importSource).getPath();
        Mockito.verify(preparedSourcesPaths).contains(importPath);
        Mockito.verify(preparedSourcesPaths).add(importPath);
        Mockito.verify(expirationChecker).isExpired(importSource);
        Mockito.verify(importsCache).getSourceImports(importSource);
        Mockito.verifyNoMoreInteractions(processor, source, preparedSourcesPaths, expirationChecker, importsCache, sourceFactory);
    }

    private static class PreCompilationProcessorImpl extends AbstractSourceTreePreparationProcessor {

        private PreCompilationProcessorImpl(final SourceExpirationChecker expirationChecker, final SourceImportsCache importsCache,
                final SourceFactory sourceFactory) {
            super(expirationChecker, importsCache, sourceFactory);
        }

        @Override
        protected void prepareWhenExpired(final LessSource source, final Set<String> preparedSourcesPaths) {
            // do nothing
        }
    }
}
