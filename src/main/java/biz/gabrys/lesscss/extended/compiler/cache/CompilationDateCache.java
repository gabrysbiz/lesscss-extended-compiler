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

import java.util.Date;

import biz.gabrys.lesscss.extended.compiler.source.LessSource;

/**
 * Responsible for storing sources compilation dates.
 * @since 1.0
 */
public interface CompilationDateCache {

    /**
     * Saves a compilation date of a {@link LessSource}.
     * @param source the {@link LessSource}.
     * @param compilationDate the compilation date.
     * @since 1.0
     */
    void saveCompilationDate(LessSource source, Date compilationDate);

    /**
     * Tests whether cache contains compilation date for the {@link LessSource}.
     * @param source the {@link LessSource}.
     * @return {@code true} whether cache contains compilation date, otherwise {@code false}.
     * @since 1.0
     */
    boolean hasCompilationDate(LessSource source);

    /**
     * Returns a compilation date of the {@link LessSource}.
     * @param source the {@link LessSource}.
     * @return the compilation date.
     * @since 1.0
     */
    Date getCompilationDate(LessSource source);
}
