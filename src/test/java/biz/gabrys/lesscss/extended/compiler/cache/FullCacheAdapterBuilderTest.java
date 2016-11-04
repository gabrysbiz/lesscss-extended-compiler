package biz.gabrys.lesscss.extended.compiler.cache;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class FullCacheAdapterBuilderTest {

    @Test
    public void create_defaultValues() {
        final FullCache fullCache = Mockito.mock(FullCache.class);
        final FullCacheAdapterBuilder builder = new FullCacheAdapterBuilder(fullCache);

        final FullCacheAdapter adapter = builder.create();
        Assert.assertNotNull("Adapter instance", adapter);

        Assert.assertEquals("Modification dates cache", fullCache, adapter.modificationDatesCache);
        Assert.assertEquals("Imports cache", fullCache, adapter.importsCache);
        Assert.assertEquals("Source code cache", fullCache, adapter.sourceCache);
        Assert.assertEquals("Compilation dates cache", fullCache, adapter.compilationDatesCache);
        Assert.assertEquals("Compiled code cache", fullCache, adapter.compiledCache);
    }

    @Test
    public void create_customModificationDatesCache() {
        final FullCache fullCache = Mockito.mock(FullCache.class);
        final FullCacheAdapterBuilder builder = new FullCacheAdapterBuilder(fullCache);

        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        Assert.assertEquals("Builder instance", builder, builder.withSourceModificationDateCache(modificationDatesCache));

        final FullCacheAdapter adapter = builder.create();
        Assert.assertNotNull("Adapter instance", adapter);

        Assert.assertEquals("Modification dates cache", modificationDatesCache, adapter.modificationDatesCache);
        Assert.assertEquals("Imports cache", fullCache, adapter.importsCache);
        Assert.assertEquals("Source code cache", fullCache, adapter.sourceCache);
        Assert.assertEquals("Compilation dates cache", fullCache, adapter.compilationDatesCache);
        Assert.assertEquals("Compiled code cache", fullCache, adapter.compiledCache);
    }

    @Test
    public void create_customSourceImportsPathsCache() {
        final FullCache fullCache = Mockito.mock(FullCache.class);
        final FullCacheAdapterBuilder builder = new FullCacheAdapterBuilder(fullCache);

        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        Assert.assertEquals("Builder instance", builder, builder.withSourceImportsPathsCache(importsCache));

        final FullCacheAdapter adapter = builder.create();
        Assert.assertNotNull("Adapter instance", adapter);

        Assert.assertEquals("Modification dates cache", fullCache, adapter.modificationDatesCache);
        Assert.assertEquals("Imports cache", importsCache, adapter.importsCache);
        Assert.assertEquals("Source code cache", fullCache, adapter.sourceCache);
        Assert.assertEquals("Compilation dates cache", fullCache, adapter.compilationDatesCache);
        Assert.assertEquals("Compiled code cache", fullCache, adapter.compiledCache);
    }

    @Test
    public void create_customSourceCodeCache() {
        final FullCache fullCache = Mockito.mock(FullCache.class);
        final FullCacheAdapterBuilder builder = new FullCacheAdapterBuilder(fullCache);

        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        Assert.assertEquals("Builder instance", builder, builder.withSourceCodeCache(sourceCache));

        final FullCacheAdapter adapter = builder.create();
        Assert.assertNotNull("Adapter instance", adapter);

        Assert.assertEquals("Modification dates cache", fullCache, adapter.modificationDatesCache);
        Assert.assertEquals("Imports cache", fullCache, adapter.importsCache);
        Assert.assertEquals("Source code cache", sourceCache, adapter.sourceCache);
        Assert.assertEquals("Compilation dates cache", fullCache, adapter.compilationDatesCache);
        Assert.assertEquals("Compiled code cache", fullCache, adapter.compiledCache);
    }

    @Test
    public void create_customCompilationDateCache() {
        final FullCache fullCache = Mockito.mock(FullCache.class);
        final FullCacheAdapterBuilder builder = new FullCacheAdapterBuilder(fullCache);

        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        Assert.assertEquals("Builder instance", builder, builder.withCompilationDateCache(compilationDatesCache));

        final FullCacheAdapter adapter = builder.create();
        Assert.assertNotNull("Adapter instance", adapter);

        Assert.assertEquals("Modification dates cache", fullCache, adapter.modificationDatesCache);
        Assert.assertEquals("Imports cache", fullCache, adapter.importsCache);
        Assert.assertEquals("Source code cache", fullCache, adapter.sourceCache);
        Assert.assertEquals("Compilation dates cache", compilationDatesCache, adapter.compilationDatesCache);
        Assert.assertEquals("Compiled code cache", fullCache, adapter.compiledCache);
    }

    @Test
    public void create_customCompiledCodeCache() {
        final FullCache fullCache = Mockito.mock(FullCache.class);
        final FullCacheAdapterBuilder builder = new FullCacheAdapterBuilder(fullCache);

        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        Assert.assertEquals("Builder instance", builder, builder.withCompiledCodeCache(compiledCache));

        final FullCacheAdapter adapter = builder.create();
        Assert.assertNotNull("Adapter instance", adapter);

        Assert.assertEquals("Modification dates cache", fullCache, adapter.modificationDatesCache);
        Assert.assertEquals("Imports cache", fullCache, adapter.importsCache);
        Assert.assertEquals("Source code cache", fullCache, adapter.sourceCache);
        Assert.assertEquals("Compilation dates cache", fullCache, adapter.compilationDatesCache);
        Assert.assertEquals("Compiled code cache", compiledCache, adapter.compiledCache);
    }

    @Test
    public void create_customValues() {
        final FullCache fullCache = Mockito.mock(FullCache.class);
        final FullCacheAdapterBuilder builder = new FullCacheAdapterBuilder(fullCache);

        final SourceModificationDateCache modificationDatesCache = Mockito.mock(SourceModificationDateCache.class);
        final SourceImportsCache importsCache = Mockito.mock(SourceImportsCache.class);
        final SourceCodeCache sourceCache = Mockito.mock(SourceCodeCache.class);
        final CompilationDateCache compilationDatesCache = Mockito.mock(CompilationDateCache.class);
        final CompiledCodeCache compiledCache = Mockito.mock(CompiledCodeCache.class);
        Assert.assertEquals("Builder instance", builder,
                builder.withSourceModificationDateCache(modificationDatesCache).withSourceImportsPathsCache(importsCache)
                        .withSourceCodeCache(sourceCache).withCompilationDateCache(compilationDatesCache)
                        .withCompiledCodeCache(compiledCache));

        final FullCacheAdapter adapter = builder.create();
        Assert.assertNotNull("Adapter instance", adapter);

        Assert.assertEquals("Modification dates cache", modificationDatesCache, adapter.modificationDatesCache);
        Assert.assertEquals("Imports cache", importsCache, adapter.importsCache);
        Assert.assertEquals("Source code cache", sourceCache, adapter.sourceCache);
        Assert.assertEquals("Compilation dates cache", compilationDatesCache, adapter.compilationDatesCache);
        Assert.assertEquals("Compiled code cache", compiledCache, adapter.compiledCache);
    }
}
