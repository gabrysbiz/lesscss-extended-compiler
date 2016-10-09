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

import java.util.List;

import biz.gabrys.lesscss.extended.compiler.source.LessSource;

/**
 * Responsible for storing source files imports paths.
 * @since 1.0
 */
public interface SourceImportsCache {

    /**
     * Saves a imports list defined in a {@link LessSource}.
     * @param source the {@link LessSource}.
     * @param imports the imports list.
     * @since 1.0
     */
    void saveSourceImports(LessSource source, List<String> imports);

    /**
     * Tests whether cache contains imports list for a {@link LessSource}.
     * @param source the {@link LessSource}.
     * @return {@code true} whether cache contains imports list, otherwise {@code false}.
     * @since 1.0
     */
    boolean hasSourceImports(LessSource source);

    /**
     * Returns imports list defined in a {@link LessSource}.
     * @param source the {@link LessSource}.
     * @return the imports list.
     * @since 1.0
     */
    List<String> getSourceImports(LessSource source);
}
