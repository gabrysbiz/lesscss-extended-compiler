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

import java.io.File;
import java.io.IOException;

final class TemporaryDirectoryUtils {

    private static final String DIRECTORY_NAME_PREFIX = "gabrys-biz-extended-lesscss-compiler-";
    private static final int CREATION_MAX_ATTEMPTS_COUNT = 1000;

    private TemporaryDirectoryUtils() {
        // blocks the possibility of create a new instance
    }

    static File create() throws IOException {
        final File baseDirectory = new File(System.getProperty("java.io.tmpdir"));

        for (int i = 0; i < CREATION_MAX_ATTEMPTS_COUNT; ++i) {
            final File directory = new File(baseDirectory, DIRECTORY_NAME_PREFIX + System.nanoTime());
            if (directory.mkdir()) {
                return directory;
            }
        }
        throw new IOException(String.format("Cannot to create temporary directory within %s attempts", CREATION_MAX_ATTEMPTS_COUNT));
    }
}
