package biz.gabrys.lesscss.extended.compiler.control.provider;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.lesscss.extended.compiler.source.LessSource;

public final class SourcePathFileProviderTest {

    @Test(expected = NullPointerException.class)
    public void getFile_sourceIsNull_throwException() {
        final SourcePathFileProvider provider = new SourcePathFileProvider();
        provider.getFile(null);
    }

    @Test
    public void getFile_sourceIsNotNull_returnFile() {
        final SourcePathFileProvider provider = new SourcePathFileProvider();
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.when(source.getPath()).thenReturn("dir/file.less");
        final File file = provider.getFile(source);
        Assert.assertEquals("File name", "file.less", file.getName());
        Assert.assertEquals("Dir name", "dir", file.getParentFile().getName());
        Mockito.verify(source).getPath();
        Mockito.verifyNoMoreInteractions(source);
    }
}
