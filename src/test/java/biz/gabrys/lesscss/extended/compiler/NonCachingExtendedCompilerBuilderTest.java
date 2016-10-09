package biz.gabrys.lesscss.extended.compiler;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.lesscss.compiler.LessCompiler;
import biz.gabrys.lesscss.compiler.LessCompilerImpl;
import biz.gabrys.lesscss.extended.compiler.control.processor.DoNothingPostCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.DoNothingPreCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.PostCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.processor.PreCompilationProcessor;
import biz.gabrys.lesscss.extended.compiler.control.provider.SourceFileProvider;
import biz.gabrys.lesscss.extended.compiler.control.provider.SourcePathFileProvider;

public final class NonCachingExtendedCompilerBuilderTest {

    @Test
    public void create_defaultValues() {
        final NonCachingExtendedCompilerBuilder builder = new NonCachingExtendedCompilerBuilder();

        final ExtendedCompiler extendedCompiler = builder.create();
        Assert.assertNotNull("Extended compiler instance", extendedCompiler);
        Assert.assertEquals("Extended compiler class", SimpleExtendedCompiler.class, extendedCompiler.getClass());
        final SimpleExtendedCompiler simpleExtendedCompiler = (SimpleExtendedCompiler) extendedCompiler;

        Assert.assertNotNull("Compiler instance", simpleExtendedCompiler.compiler);
        Assert.assertEquals("Compiler class", LessCompilerImpl.class, simpleExtendedCompiler.compiler.getClass());
        Assert.assertNotNull("Pre processor instance", simpleExtendedCompiler.preProcessor);
        Assert.assertEquals("Pre processor class", DoNothingPreCompilationProcessor.class, simpleExtendedCompiler.preProcessor.getClass());
        Assert.assertNotNull("File provider instance", simpleExtendedCompiler.fileProvider);
        Assert.assertEquals("File provider class", SourcePathFileProvider.class, simpleExtendedCompiler.fileProvider.getClass());
        Assert.assertNotNull("Post processor instance", simpleExtendedCompiler.postProcessor);
        Assert.assertEquals("Post processor class", DoNothingPostCompilationProcessor.class,
                simpleExtendedCompiler.postProcessor.getClass());
    }

    @Test
    public void create_customValues() {
        final NonCachingExtendedCompilerBuilder builder = new NonCachingExtendedCompilerBuilder();

        final LessCompiler compiler = Mockito.mock(LessCompiler.class);
        final PreCompilationProcessor preProcessor = Mockito.mock(PreCompilationProcessor.class);
        final SourceFileProvider fileProvider = Mockito.mock(SourcePathFileProvider.class);
        final PostCompilationProcessor postProcessor = Mockito.mock(PostCompilationProcessor.class);
        builder.withCompiler(compiler);
        builder.withPreProcessor(preProcessor);
        builder.withFileProvider(fileProvider);
        builder.withPostProcessor(postProcessor);

        final ExtendedCompiler extendedCompiler = builder.create();
        Assert.assertNotNull("Extended compiler instance", extendedCompiler);
        Assert.assertEquals("Extended compiler class", SimpleExtendedCompiler.class, extendedCompiler.getClass());
        final SimpleExtendedCompiler simpleExtendedCompiler = (SimpleExtendedCompiler) extendedCompiler;

        Assert.assertEquals("Compiler instance", compiler, simpleExtendedCompiler.compiler);
        Assert.assertEquals("Pre processor instance", preProcessor, simpleExtendedCompiler.preProcessor);
        Assert.assertEquals("File provider instance", fileProvider, simpleExtendedCompiler.fileProvider);
        Assert.assertEquals("Post processor instance", postProcessor, simpleExtendedCompiler.postProcessor);
    }
}
