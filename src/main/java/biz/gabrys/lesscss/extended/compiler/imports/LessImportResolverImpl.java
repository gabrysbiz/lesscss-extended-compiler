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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Default implementation of the {@link LessImportResolver}.
 * @since 1.0
 */
public class LessImportResolverImpl implements LessImportResolver {

    private static final Pattern IMPORT_PATTERN = Pattern.compile("@import\\s+(\\(([a-z]+)\\))?\\s*((\"([^\"]+)\")|('([^']+)'))\\s*;",
            Pattern.MULTILINE);

    private static final int SOURCE_CODE_GROUP_INDEX = 0;
    private static final int IMPORT_OPTION_GROUP_INDEX = 2;
    private static final int PATH_IN_DOUBLE_QUOTES_GROUP_INDEX = 5;
    private static final int PATH_IN_SINGLE_QUOTES_GROUP_INDEX = 7;

    /**
     * Constructs a new instance.
     * @since 1.0
     */
    public LessImportResolverImpl() {
        // do nothing
    }

    public List<LessImportOperation> resolve(final String sourceCode) {
        final List<LessImportOperation> imports = new ArrayList<LessImportOperation>();
        final Matcher matcher = IMPORT_PATTERN.matcher(sourceCode);
        while (matcher.find()) {
            String path = matcher.group(PATH_IN_DOUBLE_QUOTES_GROUP_INDEX);
            if (path == null) {
                path = matcher.group(PATH_IN_SINGLE_QUOTES_GROUP_INDEX);
            }
            if (path.contains("'") || path.contains("\"")) {
                final String characterInfo = path.contains("'") ? "' (apostrophe)" : "\" (quote)";
                throw new ImportException(String.format("Path contains not allowed character: %s", characterInfo));
            }

            final String option = matcher.group(IMPORT_OPTION_GROUP_INDEX);
            String computedOption = option;

            String computedPath = path;
            if (option == null) {
                computedPath = computedPath.matches(".*\\.[^.\\s]+$") ? computedPath : computedPath + ".less";
                computedOption = computedPath.endsWith(".css") ? "css" : "less";
            }

            if (!"css".equals(computedOption)) {
                imports.add(new LessImportOperation(path, computedPath, option, computedOption, matcher.group(SOURCE_CODE_GROUP_INDEX)));
            }
        }
        return imports;
    }
}
