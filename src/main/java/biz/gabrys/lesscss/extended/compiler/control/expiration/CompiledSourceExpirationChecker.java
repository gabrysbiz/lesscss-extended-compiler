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
package biz.gabrys.lesscss.extended.compiler.control.expiration;

import java.util.Date;

import biz.gabrys.lesscss.extended.compiler.source.LessSource;

/**
 * Responsible for determining whether sources compiled code expired (files requires compilation).
 * @since 1.0
 */
public interface CompiledSourceExpirationChecker {

    /**
     * Tests whether a compiled code for a source file expired.
     * @param source the source file.
     * @param lastCompilationDate the date of last compilation.
     * @return {@code true} whether the compiled code expired, otherwise {@code false}.
     * @since 1.0
     */
    boolean isExpired(LessSource source, Date lastCompilationDate);
}
