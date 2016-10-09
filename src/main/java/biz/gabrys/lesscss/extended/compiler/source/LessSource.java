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

import java.util.Date;

/**
 * Represents a <a href="http://lesscss.org/">Less</a> source file.
 * @since 1.0
 */
public interface LessSource {

    /**
     * Returns an absolute path of the source.
     * @return the absolute path.
     * @since 1.0
     */
    String getPath();

    /**
     * Returns an encoding of the source code.
     * @return the encoding.
     * @since 1.0
     */
    String getEncoding();

    /**
     * Returns a source code.
     * @return the source code.
     * @since 1.0
     */
    String getContent();

    /**
     * Returns a last modification date of the source.
     * @return the last modification date.
     * @since 1.0
     */
    Date getLastModificationDate();
}
