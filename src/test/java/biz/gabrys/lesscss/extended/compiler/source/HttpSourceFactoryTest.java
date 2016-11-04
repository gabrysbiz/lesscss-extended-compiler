package biz.gabrys.lesscss.extended.compiler.source;

import org.junit.Assert;
import org.junit.Test;

public final class HttpSourceFactoryTest {

    @Test
    public void isSupportedPath_pathStartsWithHttp_returnTrue() {
        final HttpSourceFactory factory = new HttpSourceFactory();
        final String path = "http://path-to-file";
        Assert.assertTrue("Factory should return true for " + path, factory.isSupportedPath(path));
    }

    @Test
    public void isSupportedPath_pathStartsWithHttps_returnTrue() {
        final HttpSourceFactory factory = new HttpSourceFactory();
        final String path = "https://path-to-file";
        Assert.assertTrue("Factory should return true for " + path, factory.isSupportedPath(path));
    }

    @Test
    public void isSupportedPath_pathDoesNotStartWithHttpOrHttps_returnFalse() {
        final HttpSourceFactory factory = new HttpSourceFactory();
        final String path = "ftp://path-to-file";
        Assert.assertFalse("Factory should return false for " + path, factory.isSupportedPath(path));
    }
}
