package biz.gabrys.lesscss.extended.compiler.control.provider;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.lesscss.extended.compiler.cache.SourceCodeCache;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;

public final class CachedSourceFileProviderTest {

    @Test
    public void getFile_sourcesAreNullAndNotNull_returnDataFileCache() {
        final SourceCodeCache cache = Mockito.mock(SourceCodeCache.class);
        final CachedSourceFileProvider provider = new CachedSourceFileProvider(cache);

        final File file1 = Mockito.mock(File.class);
        Mockito.when(cache.getSourceFile(null)).thenReturn(file1);
        Assert.assertTrue("Should return file1 for null", file1 == provider.getFile(null));

        final LessSource source = Mockito.mock(LessSource.class);
        final File file2 = Mockito.mock(File.class);
        Mockito.when(cache.getSourceFile(source)).thenReturn(file2);

        Assert.assertTrue("Should return file2 form source", file2 == provider.getFile(source));

        Mockito.verify(cache).getSourceFile(null);
        Mockito.verify(cache).getSourceFile(source);
        Mockito.verifyZeroInteractions(source, file1, file2);
        Mockito.verifyNoMoreInteractions(cache);
    }
}
