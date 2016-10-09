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
 * Responsible for returning file representation of the {@link LessSource}.
 * @since 1.0
 */
public interface SourceFileProvider {

    /**
     * Returns {@link LessSource} file representation.
     * @param source the source file.
     * @return {@link LessSource} file representation.
     * @since 1.0
     */
    File getFile(LessSource source);
}
