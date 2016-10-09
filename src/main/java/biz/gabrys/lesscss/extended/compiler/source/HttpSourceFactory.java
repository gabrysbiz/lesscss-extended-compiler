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

/**
 * Responsible for creating new instances of the {@link HttpSource}.
 * @since 1.0
 */
public class HttpSourceFactory extends AbstractUriSourceFactory<HttpSource> {

    /**
     * Constructs a new instance.
     * @since 1.0
     */
    public HttpSourceFactory() {
        // do nothing
    }

    @Override
    protected HttpSource createSource(final URI uri, final String encoding) throws MalformedURLException {
        return new HttpSource(uri.toURL());
    }

    @Override
    protected boolean isSupportedPath(final String path) {
        return path.startsWith("http://") || path.startsWith("https://");
    }
}
