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

/**
 * Responsible for replacing import operations in <a href="http://lesscss.org/">Less</a> source code with new values.
 * @since 1.0
 */
public interface LessImportReplacer {

    /**
     * Replaces a path of the import operation in source code with a new value.
     * @param sourceCode the source code.
     * @param operation the import operation.
     * @param importPath the new value of the import operation path.
     * @return a source code with replaced path in the import operation.
     * @since 1.0
     */
    String replace(String sourceCode, LessImportOperation operation, String importPath);
}
