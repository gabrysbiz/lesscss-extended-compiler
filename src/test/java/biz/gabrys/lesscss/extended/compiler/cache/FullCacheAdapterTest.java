package biz.gabrys.lesscss.extended.compiler.cache;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.lesscss.extended.compiler.source.LessSource;

public class FullCacheAdapterTest {

    @Test
    public void saveSourceModificationDate() {
        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        final FullCacheAdapter adapter = new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache,
                compiledCache);

        final LessSource source = Mockito.mock(LessSource.class);
        final Date modificationDate = new Date();
        adapter.saveSourceModificationDate(source, modificationDate);

        Mockito.verify(modificationDatesCache).saveSourceModificationDate(source, modificationDate);
        Mockito.verifyNoMoreInteractions(modificationDatesCache);
        Mockito.verifyZeroInteractions(importsCache, sourceCache, compilationDatesCache, compiledCache);
    }

    @Test
    public void hasSourceModificationDate() {
        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        final FullCacheAdapter adapter = new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache,
                compiledCache);

        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.when(modificationDatesCache.hasSourceModificationDate(source)).thenReturn(true);
        Assert.assertTrue("Should return true", adapter.hasSourceModificationDate(source));

        Mockito.verify(modificationDatesCache).hasSourceModificationDate(source);
        Mockito.verifyNoMoreInteractions(modificationDatesCache);
        Mockito.verifyZeroInteractions(importsCache, sourceCache, compilationDatesCache, compiledCache);
    }

    @Test
    public void getSourceModificationDate() {
        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        final FullCacheAdapter adapter = new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache,
                compiledCache);

        final LessSource source = Mockito.mock(LessSource.class);
        final Date modificationDate = new Date();
        Mockito.when(modificationDatesCache.getSourceModificationDate(source)).thenReturn(modificationDate);
        Assert.assertEquals("Should return " + modificationDate, modificationDate, adapter.getSourceModificationDate(source));

        Mockito.verify(modificationDatesCache).getSourceModificationDate(source);
        Mockito.verifyNoMoreInteractions(modificationDatesCache);
        Mockito.verifyZeroInteractions(importsCache, sourceCache, compilationDatesCache, compiledCache);
    }

    @Test
    public void saveSourceImports() {
        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        final FullCacheAdapter adapter = new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache,
                compiledCache);

        final LessSource source = Mockito.mock(LessSource.class);
        final List<String> imports = Collections.emptyList();
        adapter.saveSourceImports(source, imports);

        Mockito.verify(importsCache).saveSourceImports(source, imports);
        Mockito.verifyNoMoreInteractions(importsCache);
        Mockito.verifyZeroInteractions(modificationDatesCache, sourceCache, compilationDatesCache, compiledCache);
    }

    @Test
    public void hasSourceImports() {
        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        final FullCacheAdapter adapter = new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache,
                compiledCache);

        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.when(importsCache.hasSourceImports(source)).thenReturn(true);
        Assert.assertTrue("Should return true", adapter.hasSourceImports(source));

        Mockito.verify(importsCache).hasSourceImports(source);
        Mockito.verifyNoMoreInteractions(importsCache);
        Mockito.verifyZeroInteractions(modificationDatesCache, sourceCache, compilationDatesCache, compiledCache);
    }

    @Test
    public void getSourceImports() {
        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        final FullCacheAdapter adapter = new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache,
                compiledCache);

        final LessSource source = Mockito.mock(LessSource.class);
        final List<String> imports = Collections.emptyList();
        Mockito.when(importsCache.getSourceImports(source)).thenReturn(imports);
        Assert.assertEquals("Should return empty list", imports, adapter.getSourceImports(source));

        Mockito.verify(importsCache).getSourceImports(source);
        Mockito.verifyNoMoreInteractions(importsCache);
        Mockito.verifyZeroInteractions(modificationDatesCache, sourceCache, compilationDatesCache, compiledCache);
    }

    @Test
    public void saveSourceCode() {
        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        final FullCacheAdapter adapter = new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache,
                compiledCache);

        final LessSource source = Mockito.mock(LessSource.class);
        final String sourceCode = "code";
        adapter.saveSourceCode(source, sourceCode);

        Mockito.verify(sourceCache).saveSourceCode(source, sourceCode);
        Mockito.verifyNoMoreInteractions(sourceCache);
        Mockito.verifyZeroInteractions(modificationDatesCache, importsCache, compilationDatesCache, compiledCache);
    }

    @Test
    public void hasSourceCode() {
        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        final FullCacheAdapter adapter = new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache,
                compiledCache);

        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.when(sourceCache.hasSourceCode(source)).thenReturn(true);
        Assert.assertTrue("Should return true", adapter.hasSourceCode(source));

        Mockito.verify(sourceCache).hasSourceCode(source);
        Mockito.verifyNoMoreInteractions(sourceCache);
        Mockito.verifyZeroInteractions(modificationDatesCache, importsCache, compilationDatesCache, compiledCache);
    }

    @Test
    public void getSourceFile() {
        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        final FullCacheAdapter adapter = new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache,
                compiledCache);

        final LessSource source = Mockito.mock(LessSource.class);
        final File file = Mockito.mock(File.class);
        Mockito.when(sourceCache.getSourceFile(source)).thenReturn(file);
        Assert.assertEquals("Source file", file, adapter.getSourceFile(source));

        Mockito.verify(sourceCache).getSourceFile(source);
        Mockito.verifyNoMoreInteractions(sourceCache);
        Mockito.verifyZeroInteractions(modificationDatesCache, importsCache, compilationDatesCache, compiledCache);
    }

    @Test
    public void getSourceRelativePath() {
        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        final FullCacheAdapter adapter = new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache,
                compiledCache);

        final LessSource source = Mockito.mock(LessSource.class);
        final String path = "path";
        Mockito.when(sourceCache.getSourceRelativePath(source)).thenReturn(path);
        Assert.assertEquals("Source relative path", path, adapter.getSourceRelativePath(source));

        Mockito.verify(sourceCache).getSourceRelativePath(source);
        Mockito.verifyNoMoreInteractions(sourceCache);
        Mockito.verifyZeroInteractions(modificationDatesCache, importsCache, compilationDatesCache, compiledCache);
    }

    @Test
    public void saveCompilationDate() {
        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        final FullCacheAdapter adapter = new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache,
                compiledCache);

        final LessSource source = Mockito.mock(LessSource.class);
        final Date compilationDate = new Date();
        adapter.saveCompilationDate(source, compilationDate);

        Mockito.verify(compilationDatesCache).saveCompilationDate(source, compilationDate);
        Mockito.verifyNoMoreInteractions(compilationDatesCache);
        Mockito.verifyZeroInteractions(modificationDatesCache, importsCache, sourceCache, compiledCache);
    }

    @Test
    public void hasCompilationDate() {
        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        final FullCacheAdapter adapter = new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache,
                compiledCache);

        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.when(compilationDatesCache.hasCompilationDate(source)).thenReturn(true);
        Assert.assertTrue("Should return true", adapter.hasCompilationDate(source));

        Mockito.verify(compilationDatesCache).hasCompilationDate(source);
        Mockito.verifyNoMoreInteractions(compilationDatesCache);
        Mockito.verifyZeroInteractions(modificationDatesCache, importsCache, sourceCache, compiledCache);
    }

    @Test
    public void getCompilationDate() {
        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        final FullCacheAdapter adapter = new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache,
                compiledCache);

        final LessSource source = Mockito.mock(LessSource.class);
        final Date compilationDate = new Date();
        Mockito.when(compilationDatesCache.getCompilationDate(source)).thenReturn(compilationDate);
        Assert.assertEquals("Compilation date", compilationDate, adapter.getCompilationDate(source));

        Mockito.verify(compilationDatesCache).getCompilationDate(source);
        Mockito.verifyNoMoreInteractions(compilationDatesCache);
        Mockito.verifyZeroInteractions(modificationDatesCache, importsCache, sourceCache, compiledCache);
    }

    @Test
    public void saveCompiledCode() {
        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        final FullCacheAdapter adapter = new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache,
                compiledCache);

        final LessSource source = Mockito.mock(LessSource.class);
        final String compiledCode = "code";
        adapter.saveCompiledCode(source, compiledCode);

        Mockito.verify(compiledCache).saveCompiledCode(source, compiledCode);
        Mockito.verifyNoMoreInteractions(compiledCache);
        Mockito.verifyZeroInteractions(modificationDatesCache, importsCache, sourceCache, compilationDatesCache);
    }

    @Test
    public void hasCompiledCode() {
        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        final FullCacheAdapter adapter = new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache,
                compiledCache);

        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.when(compiledCache.hasCompiledCode(source)).thenReturn(true);
        Assert.assertTrue("Should return true", adapter.hasCompiledCode(source));

        Mockito.verify(compiledCache).hasCompiledCode(source);
        Mockito.verifyNoMoreInteractions(compiledCache);
        Mockito.verifyZeroInteractions(modificationDatesCache, importsCache, sourceCache, compilationDatesCache);
    }

    @Test
    public void getCompiledCode() {
        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        final FullCacheAdapter adapter = new FullCacheAdapter(modificationDatesCache, importsCache, sourceCache, compilationDatesCache,
                compiledCache);

        final LessSource source = Mockito.mock(LessSource.class);
        final String compiledCode = "code";
        Mockito.when(compiledCache.getCompiledCode(source)).thenReturn(compiledCode);
        Assert.assertEquals("Compilation date", compiledCode, adapter.getCompiledCode(source));

        Mockito.verify(compiledCache).getCompiledCode(source);
        Mockito.verifyNoMoreInteractions(compiledCache);
        Mockito.verifyZeroInteractions(modificationDatesCache, importsCache, sourceCache, compilationDatesCache);
    }
}
