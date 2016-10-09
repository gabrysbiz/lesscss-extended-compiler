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
 * Post compilation processor which returns compiled code without any modifications.
 * @since 1.0
 */
public class DoNothingPostCompilationProcessor implements PostCompilationProcessor {

    /**
     * Constructs a new instance.
     * @since 1.0
     */
    public DoNothingPostCompilationProcessor() {
        // do nothing
    }

    /**
     * Returns compiled code without any modifications.
     * @param source the source file (ignored).
     * @param compiledCode the compiled code.
     * @return the code from parameter without any modifications.
     * @since 1.0
     */
    public String prepare(final LessSource source, final String compiledCode) {
        return compiledCode;
    }
}
