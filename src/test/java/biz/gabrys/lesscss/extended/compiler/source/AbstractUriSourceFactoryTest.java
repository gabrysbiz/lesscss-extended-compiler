package biz.gabrys.lesscss.extended.compiler.source;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class AbstractUriSourceFactoryTest {

    @Test
    public void createAbsoluteSource_absolutePath_success() throws URISyntaxException {
        final FactoryImpl factory = Mockito.spy(new FactoryImpl());

        final LessSource source = Mockito.mock(LessSource.class);
        final String encoding = "encoding";
        Mockito.when(source.getEncoding()).thenReturn(encoding);
        final String importPath = "protocol://example.org/file.less";

        final LessSource importSource = factory.createAbsoluteSource(source, importPath);
        Assert.assertEquals("Path is different than expected", importPath, importSource.getPath());

        Mockito.verify(factory).createAbsoluteSource(source, importPath);
        Mockito.verify(source).getEncoding();
        Mockito.verify(factory).createSource(new URI(importPath), encoding);
        Mockito.verifyNoMoreInteractions(factory, source);
    }

    @Test
    public void createRelativeSource_filesInOneDirectory_success() throws URISyntaxException {
        final FactoryImpl factory = Mockito.spy(new FactoryImpl());

        final LessSource source = Mockito.mock(LessSource.class);
        final String encoding = "encoding1";
        Mockito.when(source.getEncoding()).thenReturn(encoding);
        final String sourcePath = "protocol://example.org/file/file1.less";
        Mockito.when(source.getPath()).thenReturn(sourcePath);

        final String importPath = "file2.less";

        final LessSource importSource = factory.createRelativeSource(source, importPath);
        final String importFullPath = "protocol://example.org/file/file2.less";
        Assert.assertEquals("Path is different than expected", importFullPath, importSource.getPath());

        Mockito.verify(factory).createRelativeSource(source, importPath);
        Mockito.verify(source).getPath();
        Mockito.verify(source).getEncoding();
        Mockito.verify(factory).createSource(new URI(importFullPath), encoding);
        Mockito.verifyNoMoreInteractions(factory, source);
    }

    @Test
    public void createRelativeSource_importInSubdirectory_success() throws URISyntaxException {
        final FactoryImpl factory = Mockito.spy(new FactoryImpl());

        final LessSource source = Mockito.mock(LessSource.class);
        final String encoding = "encoding2";
        Mockito.when(source.getEncoding()).thenReturn(encoding);
        final String sourcePath = "protocol://example.org/file/file1.less";
        Mockito.when(source.getPath()).thenReturn(sourcePath);

        final String importPath = "test/file2.less";

        final LessSource importSource = factory.createRelativeSource(source, importPath);
        final String importFullPath = "protocol://example.org/file/test/file2.less";
        Assert.assertEquals("Path is different than expected", importFullPath, importSource.getPath());

        Mockito.verify(factory).createRelativeSource(source, importPath);
        Mockito.verify(source).getPath();
        Mockito.verify(source).getEncoding();
        Mockito.verify(factory).createSource(new URI(importFullPath), encoding);
        Mockito.verifyNoMoreInteractions(factory, source);
    }

    @Test
    public void createRelativeSource_filesInDifferentDirectoryTrees_success() throws URISyntaxException {
        final FactoryImpl factory = Mockito.spy(new FactoryImpl());

        final LessSource source = Mockito.mock(LessSource.class);
        final String encoding = "encoding3";
        Mockito.when(source.getEncoding()).thenReturn(encoding);
        final String sourcePath = "protocol://example.org/dir1/file1.less";
        Mockito.when(source.getPath()).thenReturn(sourcePath);

        final String importPath = "../dir2/file2.less";

        final LessSource importSource = factory.createRelativeSource(source, importPath);
        final String importFullPath = "protocol://example.org/dir2/file2.less";
        Assert.assertEquals("Path is different than expected", importFullPath, importSource.getPath());

        Mockito.verify(factory).createRelativeSource(source, importPath);
        Mockito.verify(source).getPath();
        Mockito.verify(source).getEncoding();
        Mockito.verify(factory).createSource(new URI(importFullPath), encoding);
        Mockito.verifyNoMoreInteractions(factory, source);
    }

    @Test
    public void isAbsolutePath_pathIsNull_returnFalse() {
        final FactoryImpl factory = new FactoryImpl();
        Assert.assertFalse("Factory should return false for null path", factory.isAbsolutePath(null));
    }

    @Test
    public void isAbsolutePath_pathIsEmpty_returnFalse() {
        final FactoryImpl factory = new FactoryImpl();
        Assert.assertFalse("Factory should return false for empty path", factory.isAbsolutePath(""));
    }

    @Test
    public void isAbsolutePath_pathIsNotNullAndEmptyAndSupportedIsTrue_returnTrue() {
        final FactoryImpl factory = new FactoryImpl();
        factory.supported = true;
        final String path = "protocol://path-to-file";
        Assert.assertTrue("Factory should return true", factory.isAbsolutePath(path));
    }

    @Test
    public void isAbsolutePath_pathIsNotNullAndEmptyAndSupportedIsFalse_returnFalse() {
        final FactoryImpl factory = new FactoryImpl();
        factory.supported = false;
        final String path = "protocol://path-to-file";
        Assert.assertFalse("Factory should return false", factory.isAbsolutePath(path));
    }

    private static class FactoryImpl extends AbstractUriSourceFactory<LessSource> {

        private boolean supported;

        @Override
        protected LessSource createSource(final URI uri, final String encoding) {
            final LessSource source = Mockito.mock(LessSource.class);
            Mockito.when(source.getPath()).thenReturn(uri.toString());
            Mockito.when(source.getEncoding()).thenReturn(encoding);
            return source;
        }

        @Override
        protected boolean isSupportedPath(final String path) {
            return supported;
        }
    }
}
