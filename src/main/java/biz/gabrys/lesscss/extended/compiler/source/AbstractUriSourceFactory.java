/*
 * Extended LessCSS Compiler
 * http://lesscss-extended-compiler.projects.gabrys.biz/
 *
 * Copyright (c) 2015 Adam Gabryś
 *
 * This file is licensed under the BSD 3-Clause (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain:
 *  - a copy of the License at project page
 *  - a template of the License at https://opensource.org/licenses/BSD-3-Clause
 */
package biz.gabrys.lesscss.extended.compiler.source;

import java.net.URI;

import org.apache.commons.lang3.StringUtils;

/**
 * Helps to develop {@link ConcreteSourceFactory} implementations which create {@link LessSource} from {@link URI}.
 * @param <T> created instances type.
 * @since 2.1.0
 */
public abstract class AbstractUriSourceFactory<T extends LessSource> implements ConcreteSourceFactory<T> {

    /**
     * Constructs a new instance.
     * @since 2.1.0
     */
    protected AbstractUriSourceFactory() {
        // do nothing
    }

    public T createAbsoluteSource(final LessSource source, final String importAbsolutePath) {
        try {
            final URI importUri = new URI(importAbsolutePath).normalize();
            return createSource(importUri, source.getEncoding());
        } catch (final Exception e) {
            throw new SourceFactoryException("Cannot create absoulte source", e);
        }
    }

    public T createRelativeSource(final LessSource source, final String importRelativePath) {
        try {
            final String sourcePath = source.getPath();
            final String parentPath = sourcePath.substring(0, sourcePath.lastIndexOf('/'));
            final URI importUri = new URI(parentPath + '/' + importRelativePath).normalize();
            return createSource(importUri, source.getEncoding());
        } catch (final Exception e) {
            throw new SourceFactoryException("Cannot create relative source", e);
        }
    }

    /**
     * Creates a new instance of {@link LessSource}.
     * @param uri the {@link URI} which pointer to the imported source file.
     * @param encoding the encoding of the {@link LessSource} which contains import.
     * @return the new instance of {@link LessSource}.
     * @throws Exception if an error occurred during creation of the source.
     * @since 2.1.0
     */
    protected abstract T createSource(URI uri, String encoding) throws Exception;

    public boolean isAbsolutePath(final String path) {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        return isSupportedPath(path);
    }

    /**
     * Checks whether the path is represents type supported by this factory.
     * @param path the checked path (never {@code null} or empty).
     * @return {@code true} whether the path represents type supported by this factory, otherwise {@code false}.
     * @since 2.1.0
     */
    protected abstract boolean isSupportedPath(final String path);
}
