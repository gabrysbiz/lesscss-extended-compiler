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

/**
 * Responsible for creating new instances of the {@link ClasspathSource}.
 * @since 2.1
 */
public class ClasspathSourceFactory extends AbstractUriSourceFactory<ClasspathSource> {

    /**
     * Constructs a new instance.
     * @since 2.1
     */
    public ClasspathSourceFactory() {
        // do nothing
    }

    @Override
    protected ClasspathSource createSource(final URI uri, final String encoding) {
        return new ClasspathSource(uri, encoding);
    }

    @Override
    protected boolean isSupportedPath(final String path) {
        return path.startsWith(ClasspathSource.PROTOCOL_PREFIX);
    }
}
