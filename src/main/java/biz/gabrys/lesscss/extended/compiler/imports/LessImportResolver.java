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
package biz.gabrys.lesscss.extended.compiler.imports;

import java.util.List;

/**
 * Responsible for returning a list of import operations from <a href="http://lesscss.org/">Less</a> source code.
 * @since 1.0
 * @see <a href="http://lesscss.org/features/#import-directives-feature">Import Directives</a>
 * @see <a href="http://lesscss.org/features/#import-options">Import Options</a>
 */
public interface LessImportResolver {

    /**
     * Returns all import operations from source code.
     * @param sourceCode the <a href="http://lesscss.org/">Less</a> source code.
     * @return all import operations.
     * @since 1.0
     */
    List<LessImportOperation> resolve(String sourceCode);
}
