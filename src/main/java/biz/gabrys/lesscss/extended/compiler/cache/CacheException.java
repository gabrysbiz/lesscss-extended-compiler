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

import biz.gabrys.lesscss.extended.compiler.ExtendedCompilerException;

/**
 * Thrown to indicate that an an error occurred while accessing to the cache.
 * @since 1.0
 */
public class CacheException extends ExtendedCompilerException {

    private static final long serialVersionUID = 1753945979687502285L;

    /**
     * Constructs a new instance with the specified detail message.
     * @param message the detail message.
     * @since 1.0
     */
    public CacheException(final String message) {
        super(message);
    }

    /**
     * Constructs a new instance with the specified detail message and cause.
     * @param message the detail message.
     * @param cause the cause.
     * @since 1.0
     */
    public CacheException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new instance with the specified cause.
     * @param cause the cause.
     * @since 1.0
     */
    public CacheException(final Throwable cause) {
        super(cause);
    }
}
