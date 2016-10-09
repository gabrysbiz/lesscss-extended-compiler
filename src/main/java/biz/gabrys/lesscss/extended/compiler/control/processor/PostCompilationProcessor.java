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
 * Compilation processor responsible for preparing compiled code before returning it.
 * @since 1.0
 */
public interface PostCompilationProcessor {

    /**
     * Prepares compiled code before returning it.
     * @param source the source file.
     * @param compiledCode the compiled code.
     * @return the compiled code ready to return to the client.
     * @since 1.0
     */
    String prepare(LessSource source, String compiledCode);
}
