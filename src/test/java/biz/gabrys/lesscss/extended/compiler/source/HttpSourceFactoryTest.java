package biz.gabrys.lesscss.extended.compiler.source;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class HttpSourceFactoryTest {

    @Test
    public void createAbsoluteSource_absolutePath_success() throws IOException {
        final HttpSourceFactory factory = Mockito.spy(new HttpSourceFactory());

        final HttpSource source = Mockito.mock(HttpSource.class);
        final String importPath = "http://example.org/file.less";

        final HttpSource importSource = factory.createAbsoluteSource(source, importPath);
        Assert.assertEquals("Path is different than expected", importPath, importSource.getPath());

        Mockito.verify(factory).createAbsoluteSource(source, importPath);
        Mockito.verifyNoMoreInteractions(factory);
        Mockito.verifyZeroInteractions(source);
    }

    @Test
    public void createAbsoluteSource_absolutePathWithPort_success() throws IOException {
        final HttpSourceFactory factory = Mockito.spy(new HttpSourceFactory());

        final HttpSource source = Mockito.mock(HttpSource.class);
        final String importPath = "http://example.org:123/file.less";

        final HttpSource importSource = factory.createAbsoluteSource(source, importPath);
        Assert.assertEquals("Path is different than expected", "http://example.org:123/file.less", importSource.getPath());

        Mockito.verify(factory).createAbsoluteSource(source, importPath);
        Mockito.verifyNoMoreInteractions(factory);
        Mockito.verifyZeroInteractions(source);
    }

    @Test
    public void createRelativeSource_filesInOneDirectory_success() throws IOException {
        final HttpSourceFactory factory = Mockito.spy(new HttpSourceFactory());

        final HttpSource source = Mockito.mock(HttpSource.class);
        final String sourcePath = "http://example.org/file/file1.less";
        Mockito.when(source.getPath()).thenReturn(sourcePath);

        final String importPath = "file2.less";

        final HttpSource importSource = factory.createRelativeSource(source, importPath);
        Assert.assertEquals("Path is different than expected", "http://example.org/file/file2.less", importSource.getPath());

        Mockito.verify(factory).createRelativeSource(source, importPath);
        Mockito.verify(source).getPath();
        Mockito.verifyNoMoreInteractions(factory, source);
    }

    @Test
    public void createRelativeSource_filesWithPortInOneDirectory_success() throws IOException {
        final HttpSourceFactory factory = Mockito.spy(new HttpSourceFactory());

        final HttpSource source = Mockito.mock(HttpSource.class);
        final String sourcePath = "http://example.org:123/file/file1.less";
        Mockito.when(source.getPath()).thenReturn(sourcePath);

        final String importPath = "file2.less";

        final HttpSource importSource = factory.createRelativeSource(source, importPath);
        Assert.assertEquals("Path is different than expected", "http://example.org:123/file/file2.less", importSource.getPath());

        Mockito.verify(factory).createRelativeSource(source, importPath);
        Mockito.verify(source).getPath();
        Mockito.verifyNoMoreInteractions(factory, source);
    }

    @Test
    public void createRelativeSource_importInSubdirectory_success() throws IOException {
        final HttpSourceFactory factory = Mockito.spy(new HttpSourceFactory());

        final HttpSource source = Mockito.mock(HttpSource.class);
        final String sourcePath = "http://example.org/file/file1.less";
        Mockito.when(source.getPath()).thenReturn(sourcePath);

        final String importPath = "test/file2.less";

        final HttpSource importSource = factory.createRelativeSource(source, importPath);
        Assert.assertEquals("Path is different than expected", "http://example.org/file/test/file2.less", importSource.getPath());

        Mockito.verify(factory).createRelativeSource(source, importPath);
        Mockito.verify(source).getPath();
        Mockito.verifyNoMoreInteractions(factory, source);
    }

    @Test
    public void createRelativeSource_sourceWithPortAndImportInSubdirectory_success() throws IOException {
        final HttpSourceFactory factory = Mockito.spy(new HttpSourceFactory());

        final HttpSource source = Mockito.mock(HttpSource.class);
        final String sourcePath = "http://example.org:123/file/file1.less";
        Mockito.when(source.getPath()).thenReturn(sourcePath);

        final String importPath = "test/file2.less";

        final HttpSource importSource = factory.createRelativeSource(source, importPath);
        Assert.assertEquals("Path is different than expected", "http://example.org:123/file/test/file2.less", importSource.getPath());

        Mockito.verify(factory).createRelativeSource(source, importPath);
        Mockito.verify(source).getPath();
        Mockito.verifyNoMoreInteractions(factory, source);
    }

    @Test
    public void createRelativeSource_filesInDifferentDirectoryTrees_success() throws IOException {
        final HttpSourceFactory factory = Mockito.spy(new HttpSourceFactory());

        final HttpSource source = Mockito.mock(HttpSource.class);
        final String sourcePath = "http://example.org/dir1/file1.less";
        Mockito.when(source.getPath()).thenReturn(sourcePath);

        final String importPath = "../dir2/file2.less";

        final HttpSource importSource = factory.createRelativeSource(source, importPath);
        Assert.assertEquals("Path is different than expected", "http://example.org/dir2/file2.less", importSource.getPath());

        Mockito.verify(factory).createRelativeSource(source, importPath);
        Mockito.verify(source).getPath();
        Mockito.verifyNoMoreInteractions(factory, source);
    }

    @Test
    public void createRelativeSource_filesWithPortInDifferentDirectoryTrees_success() throws IOException {
        final HttpSourceFactory factory = Mockito.spy(new HttpSourceFactory());

        final HttpSource source = Mockito.mock(HttpSource.class);
        final String sourcePath = "http://example.org:123/dir1/file1.less";
        Mockito.when(source.getPath()).thenReturn(sourcePath);

        final String importPath = "../dir2/file2.less";

        final HttpSource importSource = factory.createRelativeSource(source, importPath);
        Assert.assertEquals("Path is different than expected", "http://example.org:123/dir2/file2.less", importSource.getPath());

        Mockito.verify(factory).createRelativeSource(source, importPath);
        Mockito.verify(source).getPath();
        Mockito.verifyNoMoreInteractions(factory, source);
    }
}
