package biz.gabrys.lesscss.extended.compiler.source;

import org.junit.Assert;
import org.junit.Test;

public final class ClasspathSourceFactoryTest {

    @Test
    public void isSupportedPath_pathStartsWithClasspath_returnTrue() {
        final ClasspathSourceFactory factory = new ClasspathSourceFactory();
        final String path = "classpath://path-to-file";
        Assert.assertTrue("Factory should return true for " + path, factory.isSupportedPath(path));
    }

    @Test
    public void isSupportedPath_pathDoesNotStartWithClasspath_returnFalse() {
        final ClasspathSourceFactory factory = new ClasspathSourceFactory();
        final String path = "protocol://path-to-file";
        Assert.assertFalse("Factory should return false for " + path, factory.isSupportedPath(path));
    }
}
