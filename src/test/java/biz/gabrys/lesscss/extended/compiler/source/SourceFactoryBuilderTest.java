package biz.gabrys.lesscss.extended.compiler.source;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public final class SourceFactoryBuilderTest {

    private Map<Class<?>, ConcreteSourceFactory<? extends LessSource>> factories;
    private SourceFactoryBuilder builder;

    @Before
    public void init() {
        factories = Mockito.spy(new LinkedHashMap<Class<?>, ConcreteSourceFactory<? extends LessSource>>());
        builder = Mockito.spy(new SourceFactoryBuilder(factories));
    }

    @Test
    public void withLocal_containOnlyLocal() {
        builder.withLocal();
        Assert.assertEquals("Should contain one factory", 1, factories.size());
        Assert.assertTrue("Should contain Local factory", factories.containsKey(LocalSourceFactory.class));
        Assert.assertNotNull("Factory instance", factories.get(LocalSourceFactory.class));
        Assert.assertEquals("Factory class", LocalSourceFactory.class, factories.get(LocalSourceFactory.class).getClass());
        Mockito.verify(builder).withLocal();
        Mockito.verifyNoMoreInteractions(builder);
    }

    @Test
    public void withHttp_containOnlyHttp() {
        builder.withHttp();
        Assert.assertEquals("Should contain one factory", 1, factories.size());
        Assert.assertTrue("Should contain Http factory", factories.containsKey(HttpSourceFactory.class));
        Assert.assertNotNull("Factory instance", factories.get(HttpSourceFactory.class));
        Assert.assertEquals("Factory class", HttpSourceFactory.class, factories.get(HttpSourceFactory.class).getClass());
        Mockito.verify(builder).withHttp();
        Mockito.verifyNoMoreInteractions(builder);
    }

    @Test
    public void withFtp_containOnlyFtp() {
        builder.withFtp();
        Assert.assertEquals("Should contain one factory", 1, factories.size());
        Assert.assertTrue("Should contain Ftp factory", factories.containsKey(FtpSourceFactory.class));
        Assert.assertNotNull("Factory instance", factories.get(FtpSourceFactory.class));
        Assert.assertEquals("Factory class", FtpSourceFactory.class, factories.get(FtpSourceFactory.class).getClass());
        Mockito.verify(builder).withFtp();
        Mockito.verifyNoMoreInteractions(builder);
    }

    @Test
    public void withClasspath_containOnlyClasspath() {
        builder.withClasspath();
        Assert.assertEquals("Should contain one factory", 1, factories.size());
        Assert.assertTrue("Should contain Classpath factory", factories.containsKey(ClasspathSourceFactory.class));
        Assert.assertNotNull("Factory instance", factories.get(ClasspathSourceFactory.class));
        Assert.assertEquals("Factory class", ClasspathSourceFactory.class, factories.get(ClasspathSourceFactory.class).getClass());
        Mockito.verify(builder).withClasspath();
        Mockito.verifyNoMoreInteractions(builder);
    }

    @Test
    public void withStandard_containLocalHttpAndFtp() {
        builder.withStandard();
        Assert.assertEquals("Should contain one factory", 3, factories.size());
        Assert.assertTrue("Should contain Local factory", factories.containsKey(LocalSourceFactory.class));
        Assert.assertTrue("Should contain Http factory", factories.containsKey(HttpSourceFactory.class));
        Assert.assertTrue("Should contain Ftp factory", factories.containsKey(FtpSourceFactory.class));
        Mockito.verify(builder).withStandard();
        Mockito.verify(builder).withHttp();
        Mockito.verify(builder).withFtp();
        Mockito.verify(builder).withLocal();
        Mockito.verifyNoMoreInteractions(builder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withCustom_factoryIsNull_throwException() {
        builder.withCustom(null);
    }

    @Test
    public void withCustom_factoryIsNotNull_containOnlyCustom() {
        final ConcreteSourceFactory1 factory = new ConcreteSourceFactory1();
        builder.withCustom(factory);
        Assert.assertEquals("Should contain one factory", 1, factories.size());
        Assert.assertTrue("Should contain Concrete1 factory", factories.containsKey(ConcreteSourceFactory1.class));
        Assert.assertNotNull("Factory instance", factories.get(ConcreteSourceFactory1.class));
        Assert.assertEquals("Factory class", ConcreteSourceFactory1.class, factories.get(ConcreteSourceFactory1.class).getClass());
        Mockito.verify(builder).withCustom(factory);
        Mockito.verifyNoMoreInteractions(builder);
    }

    @Test
    public void create() {
        final SourceFactoryImpl sourceFactory = builder.create();

        Assert.assertNotNull("Source factory instance", sourceFactory);
        Assert.assertEquals("Source factory class", SourceFactoryImpl.class, sourceFactory.getClass());

        Mockito.verify(factories).values();
    }

    @Test
    public void withManyFactories() {
        final ConcreteSourceFactory1 factory1 = new ConcreteSourceFactory1();
        final ConcreteSourceFactory2 factory2 = new ConcreteSourceFactory2();
        builder.withHttp();
        builder.withCustom(factory2);
        builder.withLocal();
        builder.withFtp();
        builder.withLocal();
        builder.withCustom(factory1);
        builder.withFtp();
        builder.withHttp();
        builder.withCustom(factory1);
        builder.withCustom(factory2);

        Mockito.verify(factories, Mockito.times(2)).containsKey(LocalSourceFactory.class);
        Mockito.verify(factories, Mockito.times(2)).containsKey(HttpSourceFactory.class);
        Mockito.verify(factories, Mockito.times(2)).containsKey(FtpSourceFactory.class);
        Mockito.verify(factories, Mockito.times(2)).containsKey(ConcreteSourceFactory1.class);
        Mockito.verify(factories, Mockito.times(2)).containsKey(ConcreteSourceFactory2.class);
        Mockito.verify(factories).put(ConcreteSourceFactory1.class, factory1);
        Mockito.verify(factories).put(ConcreteSourceFactory2.class, factory2);

        Assert.assertEquals("Should contain 5 factories", 5, factories.size());
        Assert.assertTrue("Should contain Local factory", factories.containsKey(LocalSourceFactory.class));
        Assert.assertTrue("Should contain Http factory", factories.containsKey(HttpSourceFactory.class));
        Assert.assertTrue("Should contain Ftp factory", factories.containsKey(FtpSourceFactory.class));
        Assert.assertTrue("Should contain Concrete1 factory", factories.containsKey(ConcreteSourceFactory1.class));
        Assert.assertTrue("Should contain Concrete2 factory", factories.containsKey(ConcreteSourceFactory2.class));

        final List<ConcreteSourceFactory<? extends LessSource>> values = new ArrayList<ConcreteSourceFactory<? extends LessSource>>(
                factories.values());
        Assert.assertNotNull("First factory instance", values.get(0));
        Assert.assertEquals("First factory class", HttpSourceFactory.class, values.get(0).getClass());
        Assert.assertNotNull("Second factory instance", values.get(1));
        Assert.assertEquals("Second factory class", ConcreteSourceFactory2.class, values.get(1).getClass());
        Assert.assertNotNull("Third factory instance", values.get(2));
        Assert.assertEquals("Third factory class", LocalSourceFactory.class, values.get(2).getClass());
        Assert.assertNotNull("Fourth factory instance", values.get(3));
        Assert.assertEquals("Fourth factory class", FtpSourceFactory.class, values.get(3).getClass());
        Assert.assertNotNull("Fifth factory instance", values.get(4));
        Assert.assertEquals("Fifth factory class", ConcreteSourceFactory1.class, values.get(4).getClass());
    }

    private static class ConcreteSourceFactory1 implements ConcreteSourceFactory<LessSource> {

        public LessSource createAbsoluteSource(final LessSource source, final String importAbsolutePath) {
            return null;
        }

        public LessSource createRelativeSource(final LessSource source, final String importRelativePath) {
            return null;
        }

        public boolean isAbsolutePath(final String path) {
            return false;
        }
    }

    private static class ConcreteSourceFactory2 implements ConcreteSourceFactory<LessSource> {

        public LessSource createAbsoluteSource(final LessSource source, final String importAbsolutePath) {
            return null;
        }

        public LessSource createRelativeSource(final LessSource source, final String importRelativePath) {
            return null;
        }

        public boolean isAbsolutePath(final String path) {
            return false;
        }
    }
}
