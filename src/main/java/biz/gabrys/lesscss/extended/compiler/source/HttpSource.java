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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.apache.commons.io.IOUtils;

/**
 * Represents a <a href="http://lesscss.org/">Less</a> source file accessible via
 * <a href="http://www.w3.org/Protocols/">HTTP</a> or HTTPS.
 * @since 1.0
 */
public class HttpSource implements LessSource {

    private static final Collection<Integer> REDIRECT_CODES = new HashSet<Integer>(
            Arrays.asList(HttpURLConnection.HTTP_MOVED_PERM, HttpURLConnection.HTTP_MOVED_TEMP, HttpURLConnection.HTTP_SEE_OTHER));

    private final URL url;
    private String encoding;
    private Date lastModificationDate;

    /**
     * Constructs a new instance of the {@link HttpSource} and sets URL of the source file.
     * @param url the source file URL.
     * @since 1.0
     */
    public HttpSource(final URL url) {
        this.url = url;
    }

    public String getPath() {
        return url.toString();
    }

    /**
     * {@inheritDoc} Before the first {@link #getContent()} call returns current source encoding. After the first
     * {@link #getContent()} call always returns the source encoding read while downloading the source contents.
     * @since 1.0
     */
    public String getEncoding() {
        if (encoding != null) {
            return encoding;
        }

        final HttpURLConnection connection = makeConnection(false);
        return getEncodingFromContentType(connection.getContentType());
    }

    private static String getEncodingFromContentType(final String contentType) {
        if (contentType == null || !contentType.contains("charset=")) {
            return Charset.defaultCharset().toString();
        }
        return contentType.substring(contentType.lastIndexOf("=") + 1);
    }

    public String getContent() {
        final HttpURLConnection connection = makeConnection(true);
        String content;
        try {
            content = IOUtils.toString(connection.getInputStream(), encoding);
        } catch (final IOException e) {
            throw new SourceException(e);
        }
        encoding = getEncodingFromContentType(connection.getContentType());
        lastModificationDate = getModificationDate(connection.getLastModified());
        return content;
    }

    /**
     * {@inheritDoc} Before the first {@link #getContent()} call returns current source modification time. After the
     * first {@link #getContent()} call always returns the source modification time read while downloading the source
     * contents.
     * @since 1.0
     */
    public Date getLastModificationDate() {
        if (lastModificationDate != null) {
            return (Date) lastModificationDate.clone();
        }

        final HttpURLConnection connection = makeConnection(false);
        return getModificationDate(connection.getLastModified());
    }

    private static Date getModificationDate(final long modificationDate) {
        if (modificationDate == 0) {
            return new Date();
        }
        return new Date(modificationDate);
    }

    private HttpURLConnection makeConnection(final boolean fetchResponseBody) {
        HttpURLConnection connection;
        int responseCode;
        URL resourceUrl = url;
        while (true) {
            try {
                connection = (HttpURLConnection) resourceUrl.openConnection();
                connection.setRequestMethod(fetchResponseBody ? "GET" : "HEAD");
                responseCode = connection.getResponseCode();
            } catch (final IOException e) {
                throw new SourceException(e);
            }

            if (!REDIRECT_CODES.contains(responseCode)) {
                break;
            }
            final String location = connection.getHeaderField("Location");
            try {
                resourceUrl = new URL(location);
            } catch (final MalformedURLException e) {
                throw new SourceException("Invalid Location header: " + location, e);
            }
        }
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new SourceException(String.format("Cannot fetch content form network (error code: %s)", responseCode));
        }
        return connection;
    }
}
