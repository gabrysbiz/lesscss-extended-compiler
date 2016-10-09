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

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

/**
 * Represents a <a href="http://lesscss.org/">Less</a> source stored in local file system.
 * @since 1.0
 */
public class LocalSource implements LessSource {

    private final File file;
    private final String encoding;

    private Date lastModificationDate;

    /**
     * Constructs a new instance and sets file which store source code with default platform encoding.
     * @param file the file which store source code.
     * @since 1.0
     */
    public LocalSource(final File file) {
        this(file, Charset.defaultCharset().toString());
    }

    /**
     * Constructs a new instance and sets file which store source code and its encoding.
     * @param file the file which store source code.
     * @param encoding the source file encoding.
     * @since 1.0
     */
    public LocalSource(final File file, final String encoding) {
        this.file = file;
        this.encoding = encoding;
    }

    public String getPath() {
        try {
            return file.getCanonicalPath();
        } catch (final IOException e) {
            throw new SourceException(e);
        }
    }

    public String getEncoding() {
        return encoding;
    }

    public String getContent() {
        String content;
        try {
            content = FileUtils.readFileToString(file, encoding);
        } catch (final IOException e) {
            throw new SourceException(e);
        }
        lastModificationDate = new Date(file.lastModified());
        return content;
    }

    /**
     * {@inheritDoc} Before the first {@link #getContent()} call returns current source modification time. After the
     * first {@link #getContent()} call always returns the source modification time read while reading the source
     * contents.
     * @since 1.0
     */
    public Date getLastModificationDate() {
        if (lastModificationDate != null) {
            return (Date) lastModificationDate.clone();
        }
        return new Date(file.lastModified());
    }

    /**
     * Checks whether the path represents an absolute path.
     * @param path the checked path.
     * @return {@code true} whether the path represents an absolute path, otherwise {@code false}.
     * @since 1.0
     */
    public static boolean isAbsolutePath(final String path) {
        return isAbsolutePath(path, SystemUtils.IS_OS_WINDOWS);
    }

    static boolean isAbsolutePath(final String path, final boolean windowsOperatingSystem) {
        final char separator = '/';
        final String normalizedPath = FilenameUtils.normalize(path, true);
        if (StringUtils.isEmpty(normalizedPath)) {
            return false;
        }

        final char firstChar = normalizedPath.charAt(0);
        if (!windowsOperatingSystem) {
            return firstChar == separator;
        }

        if (!Character.isLetter(firstChar)) {
            return false;
        }

        final int colonIndex = normalizedPath.indexOf(':');
        return colonIndex == 1 && normalizedPath.length() > 2 && normalizedPath.charAt(2) == separator;
    }
}