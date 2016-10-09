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
 * Represents the cache that allows deleting its content.
 * @since 1.0
 */
public interface DeletableCache {

    /**
     * Deletes all cache entries.
     * @since 1.0
     */
    void deleteAll();

    /**
     * Delete cache entries related with the {@link LessSource}.
     * @param source the {@link LessSource}.
     * @since 1.0
     */
    void delete(LessSource source);
}
