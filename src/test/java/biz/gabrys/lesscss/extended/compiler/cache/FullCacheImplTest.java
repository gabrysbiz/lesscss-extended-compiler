package biz.gabrys.lesscss.extended.compiler.cache;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import biz.gabrys.lesscss.extended.compiler.cache.FullCacheImpl.EntryType;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;
import biz.gabrys.lesscss.extended.compiler.storage.DataStorage;

public final class FullCacheImplTest {

    private static final String FILE_NAME = "fileName";

    private DataStorage storage;
    private FullCacheImpl cache;

    @Before
    public void setup() {
        storage = Mockito.mock(DataStorage.class);
        cache = Mockito.spy(new FullCacheImpl(storage));

    }

    @Test
    public void saveSourceModificationDate() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.MODIFICATION_DATE, source);

        final Date modificationDate = Mockito.mock(Date.class);
        Mockito.when(modificationDate.getTime()).thenReturn(100L);

        cache.saveSourceModificationDate(source, modificationDate);
        Mockito.verify(storage).put(FILE_NAME, "100");
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test
    public void hasSourceModificationDate() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.MODIFICATION_DATE, source);

        cache.hasSourceModificationDate(source);
        Mockito.verify(storage).hasData(FILE_NAME);
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test
    public void getSourceModificationDate_dateIsValid_returnValue() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.MODIFICATION_DATE, source);
        Mockito.when(storage.getText(FILE_NAME)).thenReturn("100");

        final Date date = cache.getSourceModificationDate(source);
        Assert.assertEquals("Date value", new Date(100L), date);
        Mockito.verify(storage).getText(FILE_NAME);
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test(expected = CacheException.class)
    public void getSourceModificationDate_dateIsInvalid_returnValue() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.MODIFICATION_DATE, source);
        Mockito.when(storage.getText(FILE_NAME)).thenReturn("not-number");

        cache.getSourceModificationDate(source);
    }

    @Test
    public void saveSourceImports() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.IMPORTS_LIST, source);

        final List<String> imports = Collections.emptyList();

        cache.saveSourceImports(source, imports);
        Mockito.verify(storage).put(FILE_NAME, imports);
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test
    public void hasSourceImports() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.IMPORTS_LIST, source);

        cache.hasSourceImports(source);
        Mockito.verify(storage).hasData(FILE_NAME);
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test
    public void getSourceImports() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.IMPORTS_LIST, source);
        final List<String> imports = Collections.emptyList();
        Mockito.when(storage.getLines(FILE_NAME)).thenReturn(imports);

        final List<String> imports2 = cache.getSourceImports(source);
        Assert.assertEquals("Imports", imports, imports2);
        Mockito.verify(storage).getLines(FILE_NAME);
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test
    public void saveSourceCode() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.SOURCE_CODE, source);

        final String sourceCode = "code";

        cache.saveSourceCode(source, sourceCode);
        Mockito.verify(storage).put(FILE_NAME, sourceCode);
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test
    public void hasSourceCode() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.SOURCE_CODE, source);

        cache.hasSourceCode(source);
        Mockito.verify(storage).hasData(FILE_NAME);
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test
    public void getSourceFile() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.SOURCE_CODE, source);
        final File file = Mockito.mock(File.class);
        Mockito.when(storage.getFile(FILE_NAME)).thenReturn(file);

        final File file2 = cache.getSourceFile(source);
        Assert.assertEquals("Source file", file, file2);
        Mockito.verify(storage).getFile(FILE_NAME);
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test
    public void getSourceRelativePath() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.SOURCE_CODE, source);

        final String path = cache.getSourceRelativePath(source);
        Assert.assertEquals("Source relative path", FILE_NAME, path);
        Mockito.verifyZeroInteractions(storage);
    }

    @Test
    public void saveCompilationDate() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.COMPILATION_DATE, source);

        final Date date = new Date(100L);

        cache.saveCompilationDate(source, date);
        Mockito.verify(storage).put(FILE_NAME, "100");
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test
    public void hasCompilationDate() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.COMPILATION_DATE, source);

        cache.hasCompilationDate(source);
        Mockito.verify(storage).hasData(FILE_NAME);
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test
    public void getCompilationDate_dateIsValid_returnValue() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.COMPILATION_DATE, source);
        Mockito.when(storage.getText(FILE_NAME)).thenReturn("100");

        final Date date = cache.getCompilationDate(source);
        Assert.assertEquals("Date value", new Date(100L), date);
        Mockito.verify(storage).getText(FILE_NAME);
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test(expected = CacheException.class)
    public void getCompilationDate_dateIsInvalid_returnValue() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.COMPILATION_DATE, source);
        Mockito.when(storage.getText(FILE_NAME)).thenReturn("not-number");

        cache.getCompilationDate(source);
    }

    @Test
    public void saveCompiledCode() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.COMPILED_CODE, source);

        final String compiledCode = "code";

        cache.saveCompiledCode(source, compiledCode);
        Mockito.verify(storage).put(FILE_NAME, compiledCode);
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test
    public void hasCompiledCode() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.COMPILED_CODE, source);

        cache.hasCompiledCode(source);
        Mockito.verify(storage).hasData(FILE_NAME);
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test
    public void getCompiledCode() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.doReturn(FILE_NAME).when(cache).createFileName(EntryType.COMPILED_CODE, source);
        final String compiledCode = "code";
        Mockito.when(storage.getText(FILE_NAME)).thenReturn(compiledCode);

        final String compiledCode2 = cache.getCompiledCode(source);
        Assert.assertEquals("Compiled code", compiledCode, compiledCode2);
        Mockito.verify(storage).getText(FILE_NAME);
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test
    public void delete() {
        final LessSource source = Mockito.mock(LessSource.class);
        for (final EntryType type : EntryType.values()) {
            Mockito.doReturn(FILE_NAME + type.getExtension()).when(cache).createFileName(type, source);
        }

        cache.delete(source);
        for (final EntryType type : EntryType.values()) {
            Mockito.verify(storage).delete(FILE_NAME + type.getExtension());
        }
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test
    public void deleteAll() {
        cache.deleteAll();

        Mockito.verify(storage).deleteAll();
        Mockito.verifyNoMoreInteractions(storage);
    }

    @Test
    public void createFileName() {
        makeFileNameTest("/root/less", "less");
        makeFileNameTest("C:\\root\\less", "less");
        makeFileNameTest("/root/../file/abc.less", "abc.less");
        makeFileNameTest("C:\\root\\..\\file\\abc.less", "abc.less");
        makeFileNameTest("/file/abc-", "abc-");
    }

    private void makeFileNameTest(final String path, final String name) {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.when(source.getPath()).thenReturn(path);

        final int hashCode = path.hashCode();
        final String suffix;
        if (hashCode < 0) {
            suffix = "n" + Math.abs(hashCode);
        } else {
            suffix = "p" + hashCode;
        }

        for (final EntryType type : EntryType.values()) {
            final String fileName = cache.createFileName(type, source);
            final String expected = name + '-' + suffix + type.getExtension();
            Assert.assertEquals(String.format("File name for path \"%s\" and type \"%s\"", path, type.name()), expected, fileName);
        }
    }
}
