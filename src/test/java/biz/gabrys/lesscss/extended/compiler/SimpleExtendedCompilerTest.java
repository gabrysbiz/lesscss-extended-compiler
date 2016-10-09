package biz.gabrys.lesscss.extended.compiler;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.lesscss.compiler.CompilerException;
import biz.gabrys.lesscss.compiler.LessCompiler;
import biz.gabrys.lesscss.extended.compiler.control.processor.PostCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.PreCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.provider.SourceFileProvider;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;

public final class SimpleExtendedCompilerTest {

    @Test
    public void compile() throws CompilerException {
        final LessCompiler compiler = Mockito.mock(LessCompiler.class);
        final PreCompilationProcessor preProcessor = Mockito.mock(PreCompilationProcessor.class);
        final SourceFileProvider fileProvider = Mockito.mock(SourceFileProvider.class);
        final PostCompilationProcessor postProcessor = Mockito.mock(PostCompilationProcessor.class);
        final ExtendedCompiler extendedCompiler = Mockito
                .spy(new SimpleExtendedCompiler(compiler, preProcessor, fileProvider, postProcessor));

        final LessSource source = Mockito.mock(LessSource.class);

        final File sourceFile = Mockito.mock(File.class);
        Mockito.when(fileProvider.getFile(source)).thenReturn(sourceFile);

        final String compiledCode = "compiledCode";
        Mockito.when(compiler.compile(sourceFile)).thenReturn(compiledCode);

        final String preparedCompiledCode = "preparedCompiledCode";
        Mockito.when(postProcessor.prepare(source, compiledCode)).thenReturn(preparedCompiledCode);

        Assert.assertEquals("Compiled code is different than expected", preparedCompiledCode, extendedCompiler.compile(source));

        Mockito.verify(extendedCompiler).compile(source);
        Mockito.verify(preProcessor).prepare(source);
        Mockito.verify(fileProvider).getFile(source);
        Mockito.verify(compiler).compile(sourceFile);
        Mockito.verify(postProcessor).prepare(source, compiledCode);
        Mockito.verifyNoMoreInteractions(extendedCompiler, preProcessor, fileProvider, compiler, postProcessor);
        Mockito.verifyZeroInteractions(source, sourceFile);
    }
}
