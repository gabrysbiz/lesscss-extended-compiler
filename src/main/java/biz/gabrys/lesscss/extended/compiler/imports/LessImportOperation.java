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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Represents a <a href="http://lesscss.org/">Less</a> import operation.
 * @since 1.0
 * @see <a href="http://lesscss.org/features/#import-directives-feature">Import Directives</a>
 */
public class LessImportOperation {

    /**
     * The source path.
     * @since 2.1.0
     */
    protected String path;
    /**
     * The computed source path.
     * @since 2.1.0
     */
    protected String computedPath;
    /**
     * The import option.
     * @since 2.1.0
     */
    protected String option;
    /**
     * The computed import option.
     * @since 2.1.0
     */
    protected String computedOption;
    /**
     * The source code.
     * @since 2.1.0
     */
    protected String sourceCode;

    /**
     * Constructs a new instance and sets file path, computed path, option, computed option and source code.
     * @param path the source path.
     * @param computedPath the computed source path.
     * @param option the import option.
     * @param computedOption the computed import option.
     * @param sourceCode the source code.
     * @since 1.0
     */
    public LessImportOperation(final String path, final String computedPath, final String option, final String computedOption,
            final String sourceCode) {
        this.path = path;
        this.computedPath = computedPath;
        this.option = option;
        this.computedOption = computedOption;
        this.sourceCode = sourceCode;
    }

    /**
     * Returns a path from source code.
     * @return the path.
     * @since 1.0
     */
    public String getPath() {
        return path;
    }

    /**
     * Returns a computed source path.
     * @return the computed source path.
     * @since 1.0
     * @see <a href="http://lesscss.org/features/#import-directives-feature-file-extensions">File extensions</a>
     */
    public String getComputedPath() {
        return computedPath;
    }

    /**
     * Returns an import option.
     * @return the import option or {@code null} if not specified.
     * @since 1.0
     * @see <a href="http://lesscss.org/features/#import-options">Import Options</a>
     */
    public String getOption() {
        return option;
    }

    /**
     * Returns a computed import option.
     * @return the computed import option.
     * @since 1.0
     * @see <a href="http://lesscss.org/features/#import-options">Import Options</a>
     */
    public String getComputedOption() {
        return computedOption;
    }

    /**
     * Returns a source code which represents this import.
     * @return the source code.
     * @since 1.0
     */
    public String getSourceCode() {
        return sourceCode;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(path).append(computedPath).append(option).append(computedOption).append(sourceCode)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final LessImportOperation other = (LessImportOperation) obj;
        return new EqualsBuilder().append(path, other.path).append(computedPath, other.computedPath).append(option, other.option)
                .append(computedOption, other.computedOption).append(sourceCode, other.sourceCode).isEquals();
    }
}
