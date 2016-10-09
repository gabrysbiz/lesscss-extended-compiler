package biz.gabrys.lesscss.extended.compiler.control.expiration;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.lesscss.extended.compiler.cache.SourceImportsCache;
import biz.gabrys.lesscss.extended.compiler.cache.SourceModificationDateCache;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;
import biz.gabrys.lesscss.extended.compiler.source.SourceFactory;

public final class CompiledSourceExpirationCheckerImplTest {

    @Test
    public void isExpired_sourceExpired_returnsTrue() {
        final SourceExpirationChecker checker = Mockito.mock(SourceExpirationChecker.class);
        final SourceModificationDateCache datesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);
        final CompiledSourceExpirationCheckerImpl expirationChecker = Mockito
                .spy(new CompiledSourceExpirationCheckerImpl(checker, datesCache, importsCache, sourceFactory));

        final LessSource source = Mockito.mock(LessSource.class);
        final Date compilationDate = Mockito.mock(Date.class);
        final Set<String> checkedSourcesPaths = Mockito.spy(new HashSet<String>());

        Mockito.when(checker.isExpired(source)).thenReturn(true);

        Assert.assertTrue("Checker returned false for expired source",
                expirationChecker.isExpired(source, compilationDate, checkedSourcesPaths));

        Mockito.verify(expirationChecker).isExpired(source, compilationDate, checkedSourcesPaths);
        Mockito.verify(checker).isExpired(source);
        Mockito.verifyNoMoreInteractions(expirationChecker, checker);
        Mockito.verifyZeroInteractions(source, compilationDate, checkedSourcesPaths, datesCache, importsCache, sourceFactory);
    }

    @Test
    public void isExpired_sourceNotExpiredAndWithoutImports_returnsFalse() {
        final SourceExpirationChecker checker = Mockito.mock(SourceExpirationChecker.class);
        final SourceModificationDateCache datesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);
        final CompiledSourceExpirationCheckerImpl expirationChecker = Mockito
                .spy(new CompiledSourceExpirationCheckerImpl(checker, datesCache, importsCache, sourceFactory));

        final LessSource source = Mockito.mock(LessSource.class);
        final Date compilationDate = Mockito.mock(Date.class);
        final Set<String> checkedSourcesPaths = Mockito.spy(new HashSet<String>());

        Mockito.when(checker.isExpired(source)).thenReturn(false);
        final Date sourceCompilationDate = Mockito.mock(Date.class);
        Mockito.when(datesCache.getSourceModificationDate(source)).thenReturn(sourceCompilationDate);
        Mockito.when(sourceCompilationDate.after(compilationDate)).thenReturn(false);
        final String sourcePath = "sourcePath";
        Mockito.when(source.getPath()).thenReturn(sourcePath);
        Mockito.when(importsCache.getSourceImports(source)).thenReturn(Collections.<String>emptyList());

        Assert.assertFalse("Checker returned true for not expired source",
                expirationChecker.isExpired(source, compilationDate, checkedSourcesPaths));

        Mockito.verify(expirationChecker).isExpired(source, compilationDate, checkedSourcesPaths);
        Mockito.verify(checker).isExpired(source);
        Mockito.verify(expirationChecker).isModifiedAfterLastCompilation(source, compilationDate);
        Mockito.verify(datesCache).getSourceModificationDate(source);
        Mockito.verify(sourceCompilationDate).after(compilationDate);
        Mockito.verify(source).getPath();
        Mockito.verify(checkedSourcesPaths).add(sourcePath);
        Mockito.verify(importsCache).getSourceImports(source);
        Mockito.verifyNoMoreInteractions(expirationChecker, checker, datesCache, sourceCompilationDate, importsCache, sourceFactory);
        Mockito.verifyZeroInteractions(source, compilationDate, checkedSourcesPaths);
    }

    @Test
    public void isExpired_sourceNotExpiredAndWithOneExpiredImport_returnsTrue() {
        final SourceExpirationChecker checker = Mockito.mock(SourceExpirationChecker.class);
        final SourceModificationDateCache datesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);
        final CompiledSourceExpirationCheckerImpl expirationChecker = Mockito
                .spy(new CompiledSourceExpirationCheckerImpl(checker, datesCache, importsCache, sourceFactory));

        final LessSource source = Mockito.mock(LessSource.class);
        final Date compilationDate = Mockito.mock(Date.class);
        final Set<String> checkedSourcesPaths = Mockito.spy(new HashSet<String>());

        Mockito.when(checker.isExpired(source)).thenReturn(false);
        final Date sourceCompilationDate = Mockito.mock(Date.class);
        Mockito.when(datesCache.getSourceModificationDate(source)).thenReturn(sourceCompilationDate);
        Mockito.when(sourceCompilationDate.after(compilationDate)).thenReturn(false);
        final String sourcePath = "sourcePath";
        Mockito.when(source.getPath()).thenReturn(sourcePath);
        final String importPath = "importPath";
        Mockito.when(importsCache.getSourceImports(source)).thenReturn(Arrays.asList(importPath));

        final LessSource importSource = Mockito.mock(LessSource.class);
        Mockito.when(sourceFactory.create(source, importPath)).thenReturn(importSource);
        Mockito.when(importSource.getPath()).thenReturn(importPath);
        Mockito.when(checker.isExpired(importSource)).thenReturn(true);

        Assert.assertTrue("Checker returned false for expired source",
                expirationChecker.isExpired(source, compilationDate, checkedSourcesPaths));

        Mockito.verify(expirationChecker).isExpired(source, compilationDate, checkedSourcesPaths);
        Mockito.verify(checker).isExpired(source);
        Mockito.verify(expirationChecker).isModifiedAfterLastCompilation(source, compilationDate);
        Mockito.verify(datesCache).getSourceModificationDate(source);
        Mockito.verify(sourceCompilationDate).after(compilationDate);
        Mockito.verify(source).getPath();
        Mockito.verify(checkedSourcesPaths).add(sourcePath);
        Mockito.verify(importsCache).getSourceImports(source);
        Mockito.verify(sourceFactory).create(source, importPath);
        Mockito.verify(importSource).getPath();
        Mockito.verify(checkedSourcesPaths).contains(importPath);
        Mockito.verify(expirationChecker).isExpired(importSource, compilationDate, checkedSourcesPaths);
        Mockito.verify(checker).isExpired(importSource);
        Mockito.verifyNoMoreInteractions(expirationChecker, source, checker, checkedSourcesPaths, datesCache, sourceCompilationDate,
                importsCache, sourceFactory, importSource);
        Mockito.verifyZeroInteractions(compilationDate);
    }

    @Test
    public void isExpired_sourceNotExpiredAndWithOneDuplicatedNotExpiredImport_returnsFalse() {
        final SourceExpirationChecker checker = Mockito.mock(SourceExpirationChecker.class);
        final SourceModificationDateCache datesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceFactory sourceFactory = Mockito.mock(SourceFactory.class);
        final CompiledSourceExpirationCheckerImpl expirationChecker = Mockito
                .spy(new CompiledSourceExpirationCheckerImpl(checker, datesCache, importsCache, sourceFactory));

        final LessSource source = Mockito.mock(LessSource.class);
        final Date compilationDate = Mockito.mock(Date.class);
        final Set<String> checkedSourcesPaths = Mockito.spy(new HashSet<String>());

        Mockito.when(checker.isExpired(source)).thenReturn(false);
        final Date sourceCompilationDate = Mockito.mock(Date.class);
        Mockito.when(datesCache.getSourceModificationDate(source)).thenReturn(sourceCompilationDate);
        Mockito.when(sourceCompilationDate.after(compilationDate)).thenReturn(false);
        final String sourcePath = "sourcePath";
        Mockito.when(source.getPath()).thenReturn(sourcePath);
        final String importPath = "importPath";
        Mockito.when(importsCache.getSourceImports(source)).thenReturn(Arrays.asList(importPath, importPath));

        final LessSource importSource = Mockito.mock(LessSource.class);
        Mockito.when(sourceFactory.create(source, importPath)).thenReturn(importSource);
        Mockito.when(importSource.getPath()).thenReturn(importPath);
        Mockito.when(checker.isExpired(importSource)).thenReturn(false);
        final Date importCompilationDate = Mockito.mock(Date.class);
        Mockito.when(datesCache.getSourceModificationDate(importSource)).thenReturn(importCompilationDate);
        Mockito.when(importCompilationDate.after(compilationDate)).thenReturn(false);
        Mockito.when(importsCache.getSourceImports(importSource)).thenReturn(Collections.<String>emptyList());

        Assert.assertFalse("Checker returned true for not expired source",
                expirationChecker.isExpired(source, compilationDate, checkedSourcesPaths));

        Mockito.verify(expirationChecker).isExpired(source, compilationDate, checkedSourcesPaths);
        Mockito.verify(checker).isExpired(source);
        Mockito.verify(expirationChecker).isModifiedAfterLastCompilation(source, compilationDate);
        Mockito.verify(datesCache).getSourceModificationDate(source);
        Mockito.verify(sourceCompilationDate).after(compilationDate);
        Mockito.verify(source).getPath();
        Mockito.verify(checkedSourcesPaths).add(sourcePath);
        Mockito.verify(importsCache).getSourceImports(source);
        Mockito.verify(sourceFactory, Mockito.times(2)).create(source, importPath);
        Mockito.verify(importSource, Mockito.times(3)).getPath();
        Mockito.verify(checkedSourcesPaths, Mockito.times(2)).contains(importPath);
        Mockito.verify(expirationChecker).isExpired(importSource, compilationDate, checkedSourcesPaths);
        Mockito.verify(checker).isExpired(importSource);
        Mockito.verify(expirationChecker).isModifiedAfterLastCompilation(importSource, compilationDate);
        Mockito.verify(datesCache).getSourceModificationDate(importSource);
        Mockito.verify(importCompilationDate).after(compilationDate);
        Mockito.verify(checkedSourcesPaths).add(importPath);
        Mockito.verify(importsCache).getSourceImports(importSource);
        Mockito.verifyNoMoreInteractions(expirationChecker, source, checker, checkedSourcesPaths, datesCache, sourceCompilationDate,
                importsCache, sourceFactory, importSource, importCompilationDate);
        Mockito.verifyZeroInteractions(compilationDate);
    }
}
