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

import biz.gabrys.lesscss.extended.compiler.cache.SourceModificationDateCache;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;

/**
 * Checker which marks source file as expired when the file modification date is newer than stored by the cache.
 * @since 1.0
 */
public class SourceModificationDateBasedExpirationChecker implements SourceExpirationChecker {

    private final SourceModificationDateCache cache;

    /**
     * Constructs a new instance.
     * @param cache the cache responsible for storing source files modification dates.
     * @since 1.0
     */
    public SourceModificationDateBasedExpirationChecker(final SourceModificationDateCache cache) {
        this.cache = cache;
    }

    public boolean isExpired(final LessSource source) {
        if (!cache.hasSourceModificationDate(source)) {
            return true;
        }
        final Date modificationDate = cache.getSourceModificationDate(source);
        return modificationDate.before(source.getLastModificationDate());
    }
}
