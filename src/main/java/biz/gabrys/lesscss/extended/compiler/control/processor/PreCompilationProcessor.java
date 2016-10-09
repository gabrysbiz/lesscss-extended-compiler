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
package biz.gabrys.lesscss.extended.compiler.control.processor;

import biz.gabrys.lesscss.extended.compiler.source.LessSource;

/**
 * Compilation processor responsible for preparing source files to the compilation process.
 * @since 1.0
 */
public interface PreCompilationProcessor {

    /**
     * Prepares a source file to the compilation process.
     * @param source the source file.
     * @since 1.0
     */
    void prepare(LessSource source);
}
