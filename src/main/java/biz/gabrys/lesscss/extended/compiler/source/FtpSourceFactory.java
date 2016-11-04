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
 * Responsible for creating new instances of the {@link FtpSource}.
 * @since 1.0
 */
public class FtpSourceFactory extends AbstractUriSourceFactory<FtpSource> {

    /**
     * Constructs a new instance.
     * @since 1.0
     */
    public FtpSourceFactory() {
        // do nothing
    }

    @Override
    protected FtpSource createSource(final URI uri, final String encoding) throws MalformedURLException {
        return new FtpSource(uri.toURL(), encoding);
    }

    @Override
    protected boolean isSupportedPath(final String path) {
        return path.startsWith("ftp://");
    }
}
