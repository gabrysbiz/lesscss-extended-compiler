package biz.gabrys.lesscss.extended.compiler.cache;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.lesscss.extended.compiler.storage.DataStorage;
import biz.gabrys.lesscss.extended.compiler.storage.DataStorageImpl;

public final class FullCacheBuilderTest {

    @Test
    public void create_userDoesNotExecuteAnyWithMethod_useFallbackDataStorage() {
        final FullCacheBuilder builder = Mockito.spy(FullCacheBuilder.class);

        final DataStorage storage = Mockito.mock(DataStorage.class);
        Mockito.doReturn(storage).when(builder).createFallbackDataStorage();

        final FullCacheImpl cache = builder.create();

        Assert.assertEquals("Storage instance", storage, cache.storage);
        Mockito.verify(builder).create();
        Mockito.verify(builder).createFallbackDataStorage();
        Mockito.verifyNoMoreInteractions(builder);
    }

    @Test
    public void create_userSetDataStorage_useSetDataStorage() {
        final FullCacheBuilder builder = Mockito.spy(FullCacheBuilder.class);
        final DataStorage storage = Mockito.mock(DataStorage.class);
        builder.withDataStorage(storage);

        final FullCacheImpl cache = builder.create();

        Assert.assertEquals("Storage instance", storage, cache.storage);
        Mockito.verify(builder).create();
        Mockito.verify(builder).withDataStorage(storage);
        Mockito.verifyNoMoreInteractions(builder);
    }

    @Test
    public void create_userSetWorkingDirectory_useDataStorageWithSetDirectory() {
        final FullCacheBuilder builder = Mockito.spy(FullCacheBuilder.class);
        final File workingDirectory = Mockito.mock(File.class);
        builder.withDirectory(workingDirectory);

        final FullCacheImpl cache = builder.create();

        final DataStorage storage = cache.getStorage();
        Assert.assertNotNull("Data storage instance", storage);
        Assert.assertEquals("Data storage class", DataStorageImpl.class, storage.getClass());
        Assert.assertEquals("Working directory", workingDirectory, ((DataStorageImpl) storage).getWorkingDirectory());

        Mockito.verify(builder).create();
        Mockito.verify(builder).withDirectory(workingDirectory);
        Mockito.verifyNoMoreInteractions(builder);
    }
}
