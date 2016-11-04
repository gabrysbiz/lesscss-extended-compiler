package biz.gabrys.lesscss.extended.compiler.control.processor;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.lesscss.extended.compiler.source.LessSource;

public final class DoNothingPostCompilationProcessorTest {

    @Test
    public void prepare_sourceIsNull_returnCodeWithoutAnyChanges() {
        final DoNothingPostCompilationProcessor processor = new DoNothingPostCompilationProcessor();
        final String compiledCode = "code";
        Assert.assertTrue("Should return the same instance", compiledCode == processor.prepare(null, compiledCode));
    }

    @Test
    public void prepare_sourceIsNotNull_returnCodeWithoutAnyChanges() {
        final DoNothingPostCompilationProcessor processor = new DoNothingPostCompilationProcessor();
        final LessSource source = Mockito.mock(LessSource.class);
        final String compiledCode = "code";
        Assert.assertTrue("Should return the same instance", compiledCode == processor.prepare(source, compiledCode));
        Mockito.verifyZeroInteractions(source);
    }
}
