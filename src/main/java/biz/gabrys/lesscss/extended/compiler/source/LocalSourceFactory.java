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
public class LocalSourceFactory implements ConcreteSourceFactory {

    /**
     * Constructs a new instance.
     * @since 1.0
     */
    public LocalSourceFactory() {
        // do nothing
    }

    public LessSource createAbsoluteSource(final LessSource source, final String importAbsolutePath) {
        return new LocalSource(new File(importAbsolutePath), source.getEncoding());
    }

    public LessSource createRelativeSource(final LessSource source, final String importRelativePath) {
        final File sourceFile = new File(source.getPath());
        final File importFile = new File(sourceFile.getParent(), importRelativePath);
        return new LocalSource(importFile, source.getEncoding());
    }

    public boolean isAbsolutePath(final String path) {
        return LocalSource.isAbsolutePath(path) && new File(path).exists();
    }
}
