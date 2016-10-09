package biz.gabrys.lesscss.extended.compiler.control.expiration;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.lesscss.extended.compiler.cache.SourceModificationDateCache;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;

public final class SourceModificationDateBasedExpirationCheckerTest {

    @Test
    public void isExpired_dateNotExistInCache_returnsTrue() {
        final LessSource source = Mockito.mock(LessSource.class);
        final SourceModificationDateCache cache = Mockito.mock(SourceModificationDateCache.class);
        final SourceModificationDateBasedExpirationChecker checker = Mockito.spy(new SourceModificationDateBasedExpirationChecker(cache));

        Mockito.when(cache.hasSourceModificationDate(source)).thenReturn(false);

        Assert.assertTrue("Checker returned false for expired source", checker.isExpired(source));

        Mockito.verify(checker).isExpired(source);
        Mockito.verify(cache).hasSourceModificationDate(source);
        Mockito.verifyNoMoreInteractions(checker, cache);
        Mockito.verifyZeroInteractions(source);
    }

    @Test
    public void isExpired_dateInCacheIsOlder_returnsTrue() {
        final LessSource source = Mockito.mock(LessSource.class);
        final SourceModificationDateCache cache = Mockito.mock(SourceModificationDateCache.class);
        final SourceModificationDateBasedExpirationChecker checker = Mockito.spy(new SourceModificationDateBasedExpirationChecker(cache));

        Mockito.when(cache.hasSourceModificationDate(source)).thenReturn(true);

        final Date lastModificationDate = Mockito.mock(Date.class);
        Mockito.when(source.getLastModificationDate()).thenReturn(lastModificationDate);
        final Date sourceModificationDate = Mockito.mock(Date.class);
        Mockito.when(cache.getSourceModificationDate(source)).thenReturn(sourceModificationDate);
        Mockito.when(sourceModificationDate.before(lastModificationDate)).thenReturn(true);

        Assert.assertTrue("Checker returned false for expired source", checker.isExpired(source));

        Mockito.verify(checker).isExpired(source);
        Mockito.verify(source).getLastModificationDate();
        Mockito.verify(cache).hasSourceModificationDate(source);
        Mockito.verify(cache).getSourceModificationDate(source);
        Mockito.verify(sourceModificationDate).before(lastModificationDate);
        Mockito.verifyNoMoreInteractions(checker, source, cache, sourceModificationDate);
        Mockito.verifyZeroInteractions(lastModificationDate);
    }

    @Test
    public void isExpired_datesAreEqual_returnsFalse() {
        final LessSource source = Mockito.mock(LessSource.class);
        final SourceModificationDateCache cache = Mockito.mock(SourceModificationDateCache.class);
        final SourceModificationDateBasedExpirationChecker checker = Mockito.spy(new SourceModificationDateBasedExpirationChecker(cache));

        Mockito.when(cache.hasSourceModificationDate(source)).thenReturn(true);

        final Date lastModificationDate = Mockito.mock(Date.class);
        Mockito.when(source.getLastModificationDate()).thenReturn(lastModificationDate);
        final Date sourceModificationDate = Mockito.mock(Date.class);
        Mockito.when(cache.getSourceModificationDate(source)).thenReturn(sourceModificationDate);
        Mockito.when(sourceModificationDate.before(lastModificationDate)).thenReturn(false);

        Assert.assertFalse("Checker returned true for not expired source", checker.isExpired(source));

        Mockito.verify(checker).isExpired(source);
        Mockito.verify(source).getLastModificationDate();
        Mockito.verify(cache).hasSourceModificationDate(source);
        Mockito.verify(cache).getSourceModificationDate(source);
        Mockito.verify(sourceModificationDate).before(lastModificationDate);
        Mockito.verifyNoMoreInteractions(checker, source, cache, sourceModificationDate);
        Mockito.verifyZeroInteractions(lastModificationDate);
    }
}
