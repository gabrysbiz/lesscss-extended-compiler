package biz.gabrys.lesscss.extended.compiler.control.processor;

import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.lesscss.extended.compiler.source.LessSource;

public final class DoNothingPreCompilationProcessorTest {

    @Test
    public void prepare_sourcesAreNullAndNotNull_didNothing() {
        final DoNothingPreCompilationProcessor processor = new DoNothingPreCompilationProcessor();
        processor.prepare(null);

        final LessSource source = Mockito.mock(LessSource.class);
        processor.prepare(source);
        Mockito.verifyZeroInteractions(source);
    }
}
