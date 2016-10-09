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
package biz.gabrys.lesscss.extended.compiler.cache;

import java.io.File;

import biz.gabrys.lesscss.extended.compiler.source.LessSource;

/**
 * Responsible for storing source files code.
 * @since 1.0
 */
public interface SourceCodeCache {

    /**
     * Saves source code of the {@link LessSource}.
     * @param source the {@link LessSource}.
     * @param sourceCode the source code.
     * @since 1.0
     */
    void saveSourceCode(LessSource source, String sourceCode);

    /**
     * Tests whether cache contains source code for the {@link LessSource}.
     * @param source the {@link LessSource}.
     * @return {@code true} whether cache contains source code, otherwise {@code false}.
     * @since 1.0
     */
    boolean hasSourceCode(LessSource source);

    /**
     * Returns a file with the source code of the {@link LessSource}.
     * @param source the {@link LessSource}.
     * @return the file with the source code.
     * @since 1.0
     */
    File getSourceFile(LessSource source);

    /**
     * Returns a file relative path to the cache.
     * @param source the {@link LessSource}.
     * @return the file relative path.
     * @since 1.0
     */
    String getSourceRelativePath(LessSource source);
}
