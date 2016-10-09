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

import java.io.File;

/**
 * Responsible for creating new instances of the {@link LocalSource}.
 * @since 1.0
 */
public class LocalSourceFactory implements ConcreteSourceFactory<LocalSource> {

    /**
     * Constructs a new instance.
     * @since 1.0
     */
    public LocalSourceFactory() {
        // do nothing
    }

    /**
     * {@inheritDoc}
     * @throws SecurityException if imported file path contains NULL bytes (see
     *             <a href="http://cwe.mitre.org/data/definitions/158.html">CWE-158</a>,
     *             <a href="http://projects.webappsec.org/w/page/13246949/Null%20Byte%20Injection">WASC-28</a>)
     * @since 1.0
     */
    public LocalSource createAbsoluteSource(final LessSource source, final String importAbsolutePath) {
        validatePath(importAbsolutePath);
        return new LocalSource(new File(importAbsolutePath), source.getEncoding());
    }

    /**
     * {@inheritDoc}
     * @throws SecurityException if imported file path contains NULL bytes (see
     *             <a href="http://cwe.mitre.org/data/definitions/158.html">CWE-158</a>,
     *             <a href="http://projects.webappsec.org/w/page/13246949/Null%20Byte%20Injection">WASC-28</a>)
     * @since 1.0
     */
    public LocalSource createRelativeSource(final LessSource source, final String importRelativePath) {
        final File sourceFile = new File(source.getPath());
        final File importFile = new File(sourceFile.getParent(), importRelativePath);
        validatePath(importFile.getPath());
        return new LocalSource(importFile, source.getEncoding());
    }

    private static void validatePath(final String path) {
        if (path != null && path.indexOf('\0') > -1) {
            throw new SecurityException("Path contains NULL bytes");
        }
    }

    public boolean isAbsolutePath(final String path) {
        return LocalSource.isAbsolutePath(path) && new File(path).exists();
    }
}
