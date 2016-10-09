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
 * Responsible for creating new instances of the {@link LessSource} with the particular type.
 * @param <T> created instances type.
 * @since 1.0
 */
public interface ConcreteSourceFactory<T extends LessSource> {

    /**
     * Creates a {@link LessSource} based on the absolute path to imported source.
     * @param source the {@link LessSource} which contains import.
     * @param importAbsolutePath the absolute path of the imported path.
     * @return the new instance of the {@link LessSource}.
     * @since 1.0
     */
    T createAbsoluteSource(LessSource source, String importAbsolutePath);

    /**
     * Creates a {@link LessSource} based on the {@link LessSource} and relative path to imported source.
     * @param source the {@link LessSource} which contains import.
     * @param importRelativePath the relative path of the imported path.
     * @return the new instance of the {@link LessSource}.
     * @since 1.0
     */
    T createRelativeSource(LessSource source, String importRelativePath);

    /**
     * Checks whether the path represents an absolute path.
     * @param path the checked path.
     * @return {@code true} whether the path represents an absolute path, otherwise {@code false}.
     * @since 1.0
     */
    boolean isAbsolutePath(String path);
}
