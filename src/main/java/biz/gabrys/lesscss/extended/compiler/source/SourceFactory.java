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
package biz.gabrys.lesscss.extended.compiler.source;

/**
 * Responsible for creating new instances of the {@link LessSource}.
 * @since 1.0
 */
public interface SourceFactory {

    /**
     * Creates a {@link LessSource} based on the {@link LessSource} and path to imported source.
     * @param source the {@link LessSource} which contains import.
     * @param importPath the path of the imported path.
     * @return the new instance of the {@link LessSource}.
     * @since 1.0
     */
    LessSource create(LessSource source, String importPath);
}
