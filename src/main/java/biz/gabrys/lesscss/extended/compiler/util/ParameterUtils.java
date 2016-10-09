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
package biz.gabrys.lesscss.extended.compiler.util;

/**
 * Provides tools to work with method parameters.
 * @since 2.1.0
 */
public final class ParameterUtils {

    /**
     * Verifies that a parameter value is not {@code null}.
     * @param name the parameter name.
     * @param value the parameter value.
     * @throws IllegalArgumentException if the parameter value is equal to {@code null}.
     * @since 2.1.0
     */
    public static void verifyNotNull(final String name, final Object value) {
        if (value == null) {
            final String message;
            if (name == null) {
                message = "Parameter is null";
            } else {
                message = String.format("Parameter \"%s\" is null", name);
            }
            throw new IllegalArgumentException(message);
        }
    }
}
