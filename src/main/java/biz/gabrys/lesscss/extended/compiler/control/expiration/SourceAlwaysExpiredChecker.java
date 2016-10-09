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

import biz.gabrys.lesscss.extended.compiler.source.LessSource;

/**
 * Checker which always mark source files as expired.
 * @since 1.0
 */
public class SourceAlwaysExpiredChecker implements SourceExpirationChecker {

    /**
     * Constructs a new instance.
     * @since 1.0
     */
    public SourceAlwaysExpiredChecker() {
        // do nothing
    }

    public boolean isExpired(final LessSource source) {
        return true;
    }
}
