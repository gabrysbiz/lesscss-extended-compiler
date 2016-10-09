package biz.gabrys.lesscss.extended.compiler.source;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class LocalSourceFactoryTest {

    private static final String ENCODING = "encoding";

    @Test
    public void createAbsoluteSource_absolutePath_success() throws IOException {
        final LocalSourceFactory factory = Mockito.spy(new LocalSourceFactory());

        final LocalSource source = Mockito.mock(LocalSource.class);
        Mockito.when(source.getEncoding()).thenReturn(ENCODING);
        final String path = new File(".", "file/file.less").getCanonicalPath();

        final LocalSource importSource = factory.createAbsoluteSource(source, path);
        Assert.assertEquals("Path is different than expected", path, importSource.getPath());
        Assert.assertEquals("Encoding is different than expected", ENCODING, importSource.getEncoding());

        Mockito.verify(factory).createAbsoluteSource(source, path);
        Mockito.verify(source).getEncoding();
        Mockito.verifyNoMoreInteractions(factory, source);
    }

    @Test(expected = SecurityException.class)
    public void createAbsoluteSource_pathContainsNullBytes_throwSecuriyException() {
        final LocalSourceFactory factory = new LocalSourceFactory();
        factory.createAbsoluteSource(null, "../file/fi\0le");
    }

    @Test
    public void createRelativeSource_filesInOneDirectory_success() throws IOException {
        final LocalSourceFactory factory = Mockito.spy(new LocalSourceFactory());

        final LocalSource source = Mockito.mock(LocalSource.class);
        Mockito.when(source.getEncoding()).thenReturn(ENCODING);
        Mockito.when(source.getPath()).thenReturn(new File(".", "file/file1.less").getCanonicalPath());
        final String path = "file2.less";

        final LocalSource importSource = factory.createRelativeSource(source, path);
        Assert.assertEquals("Path is different than expected", new File(".", "file/file2.less").getCanonicalPath(), importSource.getPath());
        Assert.assertEquals("Encoding is different than expected", ENCODING, importSource.getEncoding());

        Mockito.verify(factory).createRelativeSource(source, path);
        Mockito.verify(source).getPath();
        Mockito.verify(source).getEncoding();
        Mockito.verifyNoMoreInteractions(factory, source);
    }

    @Test
    public void createRelativeSource_importInSubdirectory_success() throws IOException {
        final LocalSourceFactory factory = Mockito.spy(new LocalSourceFactory());

        final LocalSource source = Mockito.mock(LocalSource.class);
        Mockito.when(source.getEncoding()).thenReturn(ENCODING);
        Mockito.when(source.getPath()).thenReturn(new File(".", "file/file1.less").getCanonicalPath());
        final String path = "test/file2.less";

        final LocalSource importSource = factory.createRelativeSource(source, path);
        Assert.assertEquals("Path is different than expected", new File(".", "file/test/file2.less").getCanonicalPath(),
                importSource.getPath());
        Assert.assertEquals("Encoding is different than expected", ENCODING, importSource.getEncoding());

        Mockito.verify(factory).createRelativeSource(source, path);
        Mockito.verify(source).getPath();
        Mockito.verify(source).getEncoding();
        Mockito.verifyNoMoreInteractions(factory, source);
    }

    @Test
    public void createRelativeSource_filesInDifferentDirectoryTrees_success() throws IOException {
        final LocalSourceFactory factory = Mockito.spy(new LocalSourceFactory());

        final LocalSource source = Mockito.mock(LocalSource.class);
        Mockito.when(source.getEncoding()).thenReturn(ENCODING);
        Mockito.when(source.getPath()).thenReturn(new File(".", "dir1/file1.less").getCanonicalPath());
        final String path = "../dir2/file2.less";

        final LocalSource importSource = factory.createRelativeSource(source, path);
        Assert.assertEquals("Path is different than expected", new File(".", "dir2/file2.less").getCanonicalPath(), importSource.getPath());
        Assert.assertEquals("Encoding is different than expected", ENCODING, importSource.getEncoding());

        Mockito.verify(factory).createRelativeSource(source, path);
        Mockito.verify(source).getPath();
        Mockito.verify(source).getEncoding();
        Mockito.verifyNoMoreInteractions(factory, source);
    }

    @Test(expected = SecurityException.class)
    public void createRelativeSource_pathContainsNullBytes_throwSecuriyException() throws IOException {
        final LocalSourceFactory factory = new LocalSourceFactory();

        final LocalSource source = Mockito.mock(LocalSource.class);
        Mockito.when(source.getPath()).thenReturn(new File(".", "dir1/file1.less").getCanonicalPath());

        factory.createRelativeSource(source, "../file/fi\0le");
    }
}
