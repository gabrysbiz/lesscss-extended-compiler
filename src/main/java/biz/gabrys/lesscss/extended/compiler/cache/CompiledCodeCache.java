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

import biz.gabrys.lesscss.extended.compiler.source.LessSource;

/**
 * Responsible for storing compiled <a href="http://www.w3.org/Style/CSS/">CSS</a> code.
 * @since 1.0
 */
public interface CompiledCodeCache {

    /**
     * Saves compiled <a href="http://www.w3.org/Style/CSS/">CSS</a> code of the {@link LessSource}.
     * @param source the {@link LessSource}.
     * @param compiledCode the compiled <a href="http://www.w3.org/Style/CSS/">CSS</a> code.
     * @since 1.0
     */
    void saveCompiledCode(LessSource source, String compiledCode);

    /**
     * Tests whether cache contains compiled <a href="http://www.w3.org/Style/CSS/">CSS</a> code for the
     * {@link LessSource}.
     * @param source the {@link LessSource}.
     * @return {@code true} whether cache contains compiled <a href="http://www.w3.org/Style/CSS/">CSS</a> code,
     *         otherwise {@code false}.
     * @since 1.0
     */
    boolean hasCompiledCode(LessSource source);

    /**
     * Returns a compiled <a href="http://www.w3.org/Style/CSS/">CSS</a> code of the {@link LessSource}.
     * @param source the {@link LessSource}.
     * @return the compiled <a href="http://www.w3.org/Style/CSS/">CSS</a> code.
     * @since 1.0
     */
    String getCompiledCode(LessSource source);
}
