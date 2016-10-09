package biz.gabrys.lesscss.extended.compiler.source;

import org.junit.Assert;
import org.junit.Test;

public final class LocalSourceTest {

    private static final String WINDOWS = "windows: ";
    private static final String OTHER_SYSTEMS = "other systems: ";

    @Test
    public void isAbsolutePath_pathIsNull_returnsFalse() {
        final String path = null;
        final String message = "null should not be treated as absolute path";
        Assert.assertFalse(WINDOWS + message, LocalSource.isAbsolutePath(path, true));
        Assert.assertFalse(OTHER_SYSTEMS + message, LocalSource.isAbsolutePath(path, false));
    }

    @Test
    public void isAbsolutePath_pathIsEmpty_returnsFalse() {
        final String path = "";
        final String message = "\"\" should not be treated as absolute path";
        Assert.assertFalse(WINDOWS + message, LocalSource.isAbsolutePath(path, true));
        Assert.assertFalse(OTHER_SYSTEMS + message, LocalSource.isAbsolutePath(path, false));
    }

    @Test
    public void isAbsolutePath_pathIsRelative_returnsFalse() {
        final String path = "file";
        final String message = "\"file\" should not be treated as absolute path";
        Assert.assertFalse(WINDOWS + message, LocalSource.isAbsolutePath(path, true));
        Assert.assertFalse(OTHER_SYSTEMS + message, LocalSource.isAbsolutePath(path, false));
    }

    @Test
    public void isAbsolutePath_pathIsRelativeAndStartsWithDot_returnsFalse() {
        final String path = "./file";
        final String message = "\"./file\" should not be treated as absolute path";
        Assert.assertFalse(WINDOWS + message, LocalSource.isAbsolutePath(path.replace('/', '\\'), true));
        Assert.assertFalse(OTHER_SYSTEMS + message, LocalSource.isAbsolutePath(path, false));
    }

    @Test
    public void isAbsolutePath_pathIsRelativeAndComplex_returnsFalse() {
        final String path = "dir/.././dir2/../file";
        Assert.assertFalse(WINDOWS + "\"dir\\..\\.\\dir2\\..\\file\" should not be treated as absolute path",
                LocalSource.isAbsolutePath(path.replace('/', '\\'), true));
        Assert.assertFalse(OTHER_SYSTEMS + "\"dir/.././dir2/../file\" should not be treated as absolute path",
                LocalSource.isAbsolutePath(path, false));
    }

    @Test
    public void isAbsolutePath_pathIsAbsolute_returnsTrue() {
        Assert.assertTrue(WINDOWS + "\"C:\\file\" should be treated as absolute path", LocalSource.isAbsolutePath("C:\\file", true));
        Assert.assertTrue(OTHER_SYSTEMS + "\"/file\" should be treated as absolute path", LocalSource.isAbsolutePath("/file", false));
    }

    @Test
    public void isAbsolutePath_pathContainsNullBytes_returnsFalse() {
        Assert.assertFalse(WINDOWS + "\"C:\\file\\fi\0le\" should be treated as absolute path",
                LocalSource.isAbsolutePath("C:\\file\\fi\0le", true));
        Assert.assertFalse(OTHER_SYSTEMS + "\"/file/fi\0le\" should be treated as absolute path",
                LocalSource.isAbsolutePath("/file/fi\0le", false));
    }
}
