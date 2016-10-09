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
package biz.gabrys.lesscss.extended.compiler;

/**
 * Thrown to indicate that an error occurred during the execution of one of the elements of the extended mechanism.
 * @since 1.0
 */
public class ExtendedCompilerException extends RuntimeException {

    private static final long serialVersionUID = 7108141903986570289L;

    /**
     * Constructs a new instance with the specified detail message.
     * @param message the detail message.
     * @since 1.0
     */
    public ExtendedCompilerException(final String message) {
        super(message);
    }

    /**
     * Constructs a new instance with the specified detail message and cause.
     * @param message the detail message.
     * @param cause the cause.
     * @since 1.0
     */
    public ExtendedCompilerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new instance with the specified cause.
     * @param cause the cause.
     * @since 1.0
     */
    public ExtendedCompilerException(final Throwable cause) {
        super(cause);
    }
}
