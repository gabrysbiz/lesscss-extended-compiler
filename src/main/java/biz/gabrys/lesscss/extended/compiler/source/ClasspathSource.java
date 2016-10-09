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

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.commons.io.IOUtils;

/**
 * Represents a <a href="http://lesscss.org/">Less</a> source file located in classpath.
 * @since 2.1.0
 */
public class ClasspathSource implements LessSource {

    /**
     * Stores protocol prefix with colon and slashes.
     * @since 2.1.0
     */
    protected static final String PROTOCOL_PREFIX = "classpath://";

    private final URI uri;
    private final String path;
    private final String encoding;
    private Date lastModificationDate;

    /**
     * Constructs a new instance and sets {@link URI} of the source file with default platform encoding.
     * @param uri the {@link URI} which pointer to source file.
     * @since 2.1.0
     */
    public ClasspathSource(final URI uri) {
        this(uri, Charset.defaultCharset().toString());
    }

    /**
     * Constructs a new instance and sets {@link URI} of the source file and its encoding.
     * @param uri the {@link URI} which pointer to source file.
     * @param encoding the source file encoding.
     * @since 2.1.0
     */
    public ClasspathSource(final URI uri, final String encoding) {
        this.uri = uri;
        path = uri.toString().substring(PROTOCOL_PREFIX.length());
        this.encoding = encoding;
    }

    public String getPath() {
        return uri.toString();
    }

    public String getEncoding() {
        return encoding;
    }

    public String getContent() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final String content;
        try {
            content = IOUtils.toString(classLoader.getResourceAsStream(path), encoding);
        } catch (final IOException e) {
            throw new SourceException(String.format("Cannot read content of the \"%s\" resource", path), e);
        }
        lastModificationDate = getModificationDate();
        return content;
    }

    /**
     * {@inheritDoc} Before the first {@link #getContent()} call returns current source modification time. After the
     * first {@link #getContent()} call always returns the source modification time read while reading the source
     * contents.
     * @return the encoding.
     * @since 2.1.0
     */
    public Date getLastModificationDate() {
        if (lastModificationDate != null) {
            return (Date) lastModificationDate.clone();
        }
        return getModificationDate();
    }

    private Date getModificationDate() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final URL resource = classLoader.getResource(path);
        final URLConnection connection;
        try {
            connection = resource.openConnection();
        } catch (final IOException e) {
            throw new SourceException(String.format("Cannot read last modification date for \"%s\" resource", path), e);
        }
        return new Date(connection.getLastModified());
    }
}
