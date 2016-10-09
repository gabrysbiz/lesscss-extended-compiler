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
package biz.gabrys.lesscss.extended.compiler.control.provider;

import java.io.File;

import biz.gabrys.lesscss.extended.compiler.source.LessSource;

/**
 * Provider which creates new instances of the {@link File} with {@link LessSource#getPath()} as pathname.
 * @since 1.0
 */
public class SourcePathFileProvider implements SourceFileProvider {

    /**
     * Constructs a new instance.
     * @since 1.0
     */
    public SourcePathFileProvider() {
        // do nothing
    }

    public File getFile(final LessSource source) {
        return new File(source.getPath());
    }
}
