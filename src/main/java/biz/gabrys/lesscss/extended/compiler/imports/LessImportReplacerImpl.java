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
 * Default implementation of the {@link LessImportReplacer}.
 * @since 1.0
 */
public class LessImportReplacerImpl implements LessImportReplacer {

    /**
     * Constructs a new instance.
     * @since 1.0
     */
    public LessImportReplacerImpl() {
        // do nothing
    }

    public String replace(final String sourceCode, final LessImportOperation operation, final String importPath) {
        final String importSourceCode = operation.getSourceCode();
        final int importIndex = sourceCode.indexOf(importSourceCode);

        if (importIndex < 0) {
            throw new ImportException(String.format("Source code does not contain \"%s\"", importSourceCode));
        }

        final String beginCode = sourceCode.substring(0, importIndex);
        final String endCode = sourceCode.substring(importIndex + importSourceCode.length());

        final StringBuilder newImportSourceCode = new StringBuilder(importPath.length() + 25);
        newImportSourceCode.append("@import (");
        newImportSourceCode.append(operation.getComputedOption());
        newImportSourceCode.append(") \"");
        newImportSourceCode.append(importPath);
        newImportSourceCode.append("\";");

        return beginCode + newImportSourceCode + endCode;
    }
}
