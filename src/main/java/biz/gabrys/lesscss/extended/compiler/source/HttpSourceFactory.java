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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang.StringUtils;

/**
 * Responsible for creating new instances of the {@link HttpSource}.
 * @since 1.0
 */
public class HttpSourceFactory implements ConcreteSourceFactory {

    /**
     * Constructs a new instance.
     * @since 1.0
     */
    public HttpSourceFactory() {
        // do nothing
    }

    public LessSource createAbsoluteSource(final LessSource source, final String importAbsolutePath) {
        try {
            final URI importUri = new URI(importAbsolutePath).normalize();
            return new HttpSource(importUri.toURL());
        } catch (final URISyntaxException e) {
            throw new SourceFactoryException("Cannot normalize URL", e);
        } catch (final MalformedURLException e) {
            throw new SourceFactoryException("Cannot create relative URL", e);
        }
    }

    public LessSource createRelativeSource(final LessSource source, final String importRelativePath) {
        try {
            final String sourcePath = source.getPath();
            final String parentPath = sourcePath.substring(0, sourcePath.lastIndexOf('/'));
            final URI importUri = new URI(parentPath + '/' + importRelativePath).normalize();
            return new HttpSource(importUri.toURL());
        } catch (final URISyntaxException e) {
            throw new SourceFactoryException("Cannot normalize URL", e);
        } catch (final MalformedURLException e) {
            throw new SourceFactoryException("Cannot create relative URL", e);
        }
    }

    public boolean isAbsolutePath(final String path) {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        return path.startsWith("http://") || path.startsWith("https://");
    }
}
