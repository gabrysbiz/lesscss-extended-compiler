package biz.gabrys.lesscss.extended.compiler;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import biz.gabrys.lesscss.compiler.CompilerException;
import biz.gabrys.lesscss.extended.compiler.cache.CompilationDateCache;
import biz.gabrys.lesscss.extended.compiler.cache.CompiledCodeCache;
import biz.gabrys.lesscss.extended.compiler.control.expiration.CompiledSourceExpirationChecker;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;

public final class CachingCompiledCodeExtendedCompilerTest {

    @Test
    public void compile_sourceNeverCompiled_runCompilerAndSaveCompiledCode() throws CompilerException {
        final ExtendedCompiler compiler = Mockito.mock(ExtendedCompiler.class);
        final CompiledSourceExpirationChecker expirationChecker = Mockito.mock(CompiledSourceExpirationChecker.class);
        final CompilationDateCache datesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache codeCache = Mockito.mock(CompiledCodeCache.class);
        final CachingCompiledCodeExtendedCompiler cachingCompiler = Mockito
                .spy(new CachingCompiledCodeExtendedCompiler(compiler, expirationChecker, datesCache, codeCache));

        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.when(datesCache.hasCompilationDate(source)).thenReturn(false);

        final String compiledCode = "compiledCode";
        Mockito.when(compiler.compile(source)).thenReturn(compiledCode);

        Assert.assertEquals("Compiled code is different than expected", compiledCode, cachingCompiler.compile(source));

        Mockito.verify(cachingCompiler).compile(source);
        Mockito.verify(cachingCompiler).isExpired(source);
        Mockito.verify(datesCache).hasCompilationDate(source);
        Mockito.verify(compiler).compile(source);
        Mockito.verify(datesCache).saveCompilationDate(Matchers.any(LessSource.class), Matchers.any(Date.class));
        Mockito.verify(codeCache).saveCompiledCode(source, compiledCode);
        Mockito.verifyNoMoreInteractions(cachingCompiler, datesCache, compiler, codeCache);
        Mockito.verifyZeroInteractions(source, expirationChecker);
    }

    @Test
    public void compile_sourceNotExpired_returnsCodeFromCache() throws CompilerException {
        final ExtendedCompiler compiler = Mockito.mock(ExtendedCompiler.class);
        final CompiledSourceExpirationChecker expirationChecker = Mockito.mock(CompiledSourceExpirationChecker.class);
        final CompilationDateCache datesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache codeCache = Mockito.mock(CompiledCodeCache.class);
        final CachingCompiledCodeExtendedCompiler cachingCompiler = Mockito
                .spy(new CachingCompiledCodeExtendedCompiler(compiler, expirationChecker, datesCache, codeCache));

        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.when(datesCache.hasCompilationDate(source)).thenReturn(true);
        final Date compilationDate = Mockito.mock(Date.class);
        Mockito.when(datesCache.getCompilationDate(source)).thenReturn(compilationDate);
        Mockito.when(expirationChecker.isExpired(source, compilationDate)).thenReturn(false);
        final String compiledCode = "compiledCode";
        Mockito.when(codeCache.getCompiledCode(source)).thenReturn(compiledCode);

        Assert.assertEquals("Compiled code is different than expected", compiledCode, cachingCompiler.compile(source));

        Mockito.verify(cachingCompiler).compile(source);
        Mockito.verify(cachingCompiler).isExpired(source);
        Mockito.verify(datesCache).hasCompilationDate(source);
        Mockito.verify(datesCache).getCompilationDate(source);
        Mockito.verify(expirationChecker).isExpired(source, compilationDate);
        Mockito.verify(codeCache).getCompiledCode(source);
        Mockito.verifyNoMoreInteractions(cachingCompiler, datesCache, compiler, codeCache);
        Mockito.verifyZeroInteractions(source, compilationDate, expirationChecker);
    }

    @Test
    public void compile_sourceExpired_runCompilerAndSaveCompiledCode() throws CompilerException {
        final LessSource source = Mockito.mock(LessSource.class);
        final ExtendedCompiler compiler = Mockito.mock(ExtendedCompiler.class);
        final CompiledSourceExpirationChecker expirationChecker = Mockito.mock(CompiledSourceExpirationChecker.class);
        final CompilationDateCache datesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache codeCache = Mockito.mock(CompiledCodeCache.class);
        final CachingCompiledCodeExtendedCompiler cachingCompiler = Mockito
                .spy(new CachingCompiledCodeExtendedCompiler(compiler, expirationChecker, datesCache, codeCache));

        Mockito.when(datesCache.hasCompilationDate(source)).thenReturn(true);
        final Date compilationDate = Mockito.mock(Date.class);
        Mockito.when(datesCache.getCompilationDate(source)).thenReturn(compilationDate);
        Mockito.when(expirationChecker.isExpired(source, compilationDate)).thenReturn(true);
        final String compiledCode = "compiledCode";
        Mockito.when(compiler.compile(source)).thenReturn(compiledCode);

        Assert.assertEquals("Compiled code is different than expected", compiledCode, cachingCompiler.compile(source));

        Mockito.verify(cachingCompiler).compile(source);
        Mockito.verify(cachingCompiler).isExpired(source);
        Mockito.verify(datesCache).hasCompilationDate(source);
        Mockito.verify(datesCache).getCompilationDate(source);
        Mockito.verify(expirationChecker).isExpired(source, compilationDate);
        Mockito.verify(compiler).compile(source);
        Mockito.verify(datesCache).saveCompilationDate(Matchers.any(LessSource.class), Matchers.any(Date.class));
        Mockito.verify(codeCache).saveCompiledCode(source, compiledCode);
        Mockito.verifyNoMoreInteractions(cachingCompiler, datesCache, compiler, expirationChecker, codeCache);
        Mockito.verifyZeroInteractions(source, compilationDate);
    }
}
