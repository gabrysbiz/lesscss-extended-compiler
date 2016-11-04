package biz.gabrys.lesscss.extended.compiler.source;

import org.junit.Assert;
import org.junit.Test;

public final class FtpSourceFactoryTest {

    @Test
    public void isSupportedPath_pathStartsWithFtp_returnTrue() {
        final FtpSourceFactory factory = new FtpSourceFactory();
        final String path = "ftp://path-to-file";
        Assert.assertTrue("Factory should return true for " + path, factory.isSupportedPath(path));
    }

    @Test
    public void isSupportedPath_pathDoesNotStartWithFtp_returnFalse() {
        final FtpSourceFactory factory = new FtpSourceFactory();
        final String path = "http://path-to-file";
        Assert.assertFalse("Factory should return false for " + path, factory.isSupportedPath(path));
    }
}
