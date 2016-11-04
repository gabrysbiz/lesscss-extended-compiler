package biz.gabrys.lesscss.extended.compiler.source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class SourceFactoryImplTest {

    @Test(expected = SourceFactoryException.class)
    public void create_factoriesAreEmpty_throwException() {
        final LessSource source = Mockito.mock(LessSource.class);
        Mockito.when(source.getPath()).thenReturn("source");
        final String importPath = "import";

        final SourceFactory factory = new SourceFactoryImpl(Collections.<ConcreteSourceFactory<? extends LessSource>>emptyList());
        factory.create(source, importPath);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void create_threeFactoriesAndPathIsAbsolute_returnNewSource() {
        final LessSource source = Mockito.mock(LessSource.class);
        final String importPath = "import";

        final LessSource target = Mockito.mock(LessSource.class);

        final Collection<ConcreteSourceFactory<? extends LessSource>> factories = new ArrayList<ConcreteSourceFactory<? extends LessSource>>();

        final ConcreteSourceFactory<LessSource> factory1 = Mockito.mock(ConcreteSourceFactory.class);
        Mockito.when(factory1.isAbsolutePath(importPath)).thenReturn(false);
        factories.add(factory1);

        final ConcreteSourceFactory<LessSource> factory2 = Mockito.mock(ConcreteSourceFactory.class);
        Mockito.when(factory2.isAbsolutePath(importPath)).thenReturn(true);
        Mockito.when(factory2.createAbsoluteSource(source, importPath)).thenReturn(target);
        factories.add(factory2);

        final ConcreteSourceFactory<LessSource> factory3 = Mockito.mock(ConcreteSourceFactory.class);
        factories.add(factory3);

        final SourceFactory factory = new SourceFactoryImpl(factories);
        Assert.assertEquals("Should return target mock", target, factory.create(source, importPath));

        Mockito.verify(factory1).isAbsolutePath(importPath);
        Mockito.verify(factory2).isAbsolutePath(importPath);
        Mockito.verify(factory2).createAbsoluteSource(source, importPath);
        Mockito.verifyNoMoreInteractions(factory1, factory2);
        Mockito.verifyZeroInteractions(factory3);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void create_threeFactoriesAndPathIsRelative_returnNewSource() {
        final LessSource source = Mockito.mock(LessSource.class);
        final String sourcePath = "source";
        Mockito.when(source.getPath()).thenReturn(sourcePath);
        final String importPath = "import";

        final LessSource target = Mockito.mock(LessSource.class);

        final Collection<ConcreteSourceFactory<? extends LessSource>> factories = new ArrayList<ConcreteSourceFactory<? extends LessSource>>();

        final ConcreteSourceFactory<LessSource> factory1 = Mockito.mock(ConcreteSourceFactory.class);
        Mockito.when(factory1.isAbsolutePath(importPath)).thenReturn(false);
        Mockito.when(factory1.isAbsolutePath(sourcePath)).thenReturn(false);
        factories.add(factory1);

        final ConcreteSourceFactory<LessSource> factory2 = Mockito.mock(ConcreteSourceFactory.class);
        Mockito.when(factory2.isAbsolutePath(importPath)).thenReturn(false);
        Mockito.when(factory2.isAbsolutePath(sourcePath)).thenReturn(true);
        Mockito.when(factory2.createRelativeSource(source, importPath)).thenReturn(target);
        factories.add(factory2);

        final ConcreteSourceFactory<LessSource> factory3 = Mockito.mock(ConcreteSourceFactory.class);
        Mockito.when(factory3.isAbsolutePath(importPath)).thenReturn(false);
        factories.add(factory3);

        final SourceFactory factory = new SourceFactoryImpl(factories);
        Assert.assertEquals("Should return target mock", target, factory.create(source, importPath));

        Mockito.verify(factory1).isAbsolutePath(importPath);
        Mockito.verify(factory2).isAbsolutePath(importPath);
        Mockito.verify(factory3).isAbsolutePath(importPath);
        Mockito.verify(factory1).isAbsolutePath(sourcePath);
        Mockito.verify(factory2).isAbsolutePath(sourcePath);
        Mockito.verify(factory2).createRelativeSource(source, importPath);
        Mockito.verifyNoMoreInteractions(factory1, factory2);
        Mockito.verifyZeroInteractions(factory3);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = SourceFactoryException.class)
    public void create_threeFactoriesAndPathIsNotAbsoluteAndRelative_throwException() {
        final LessSource source = Mockito.mock(LessSource.class);
        final String sourcePath = "source";
        Mockito.when(source.getPath()).thenReturn(sourcePath);
        final String importPath = "import";

        final Collection<ConcreteSourceFactory<? extends LessSource>> factories = new ArrayList<ConcreteSourceFactory<? extends LessSource>>();

        final ConcreteSourceFactory<LessSource> factory1 = Mockito.mock(ConcreteSourceFactory.class);
        Mockito.when(factory1.isAbsolutePath(importPath)).thenReturn(false);
        Mockito.when(factory1.isAbsolutePath(sourcePath)).thenReturn(false);
        factories.add(factory1);

        final ConcreteSourceFactory<LessSource> factory2 = Mockito.mock(ConcreteSourceFactory.class);
        Mockito.when(factory2.isAbsolutePath(importPath)).thenReturn(false);
        Mockito.when(factory2.isAbsolutePath(sourcePath)).thenReturn(false);
        factories.add(factory2);

        final ConcreteSourceFactory<LessSource> factory3 = Mockito.mock(ConcreteSourceFactory.class);
        Mockito.when(factory3.isAbsolutePath(importPath)).thenReturn(false);
        Mockito.when(factory3.isAbsolutePath(sourcePath)).thenReturn(false);
        factories.add(factory3);

        final SourceFactory factory = new SourceFactoryImpl(factories);
        factory.create(source, importPath);
    }
}
