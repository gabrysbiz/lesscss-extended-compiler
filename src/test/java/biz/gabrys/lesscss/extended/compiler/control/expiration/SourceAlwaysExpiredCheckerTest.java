package biz.gabrys.lesscss.extended.compiler.control.expiration;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.lesscss.extended.compiler.source.LessSource;

public final class SourceAlwaysExpiredCheckerTest {

    @Test
    public void isExpired_sourceIsNull_returnTrue() {
        final SourceAlwaysExpiredChecker checker = new SourceAlwaysExpiredChecker();
        Assert.assertTrue("Should return true for null", checker.isExpired(null));
    }

    @Test
    public void isExpired_sourceIsNotNull_returnTrue() {
        final SourceAlwaysExpiredChecker checker = new SourceAlwaysExpiredChecker();
        final LessSource source = Mockito.mock(LessSource.class);
        Assert.assertTrue("Should return true for any source", checker.isExpired(source));
        Mockito.verifyZeroInteractions(source);
    }
}
