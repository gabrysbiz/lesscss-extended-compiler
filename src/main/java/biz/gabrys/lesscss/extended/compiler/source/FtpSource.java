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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * Represents a <a href="http://lesscss.org/">Less</a> source file accessible via
 * <a href="https://tools.ietf.org/html/rfc959">FTP</a>.
 * @since 2.0
 */
public class FtpSource implements LessSource {

    private final URL url;
    private final int port;
    private final String encoding;
    private Date lastModificationDate;

    /**
     * Constructs a new instance and sets URL of the source file with default platform encoding.
     * @param url the source file URL.
     * @since 2.0
     */
    public FtpSource(final URL url) {
        this(url, Charset.defaultCharset().toString());
    }

    /**
     * Constructs a new instance and sets URL of the source file and its encoding.
     * @param url the source file URL.
     * @param encoding the source file encoding.
     * @since 2.0
     */
    public FtpSource(final URL url, final String encoding) {
        this.url = url;
        port = url.getPort() != -1 ? url.getPort() : FTP.DEFAULT_PORT;
        this.encoding = encoding;
    }

    public String getPath() {
        return url.toString();
    }

    public String getEncoding() {
        return encoding;
    }

    public String getContent() {
        final FTPClient connection = connect();
        String content = null;
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            if (!connection.retrieveFile(url.getPath(), outputStream)) {
                throw new SourceException(String.format("Cannot download source \"%s\", reason: %s", url, connection.getReplyString()));
            }
            content = outputStream.toString(encoding);
        } catch (final IOException e) {
            throw new SourceException(e);
        } finally {
            disconnect(connection);
            IOUtils.closeQuietly(outputStream);
        }
        lastModificationDate = getModificationDate(getFile());
        return content;
    }

    /**
     * Returns an encoding of the source code. Before the first {@link #getContent()} call returns current source
     * encoding. After the first {@link #getContent()} call always returns the source encoding read while downloading
     * the source contents.
     * @return the encoding.
     * @since 2.0
     */
    public Date getLastModificationDate() {
        if (lastModificationDate != null) {
            return (Date) lastModificationDate.clone();
        }

        final FTPFile file = getFile();
        return getModificationDate(file);
    }

    private static Date getModificationDate(final FTPFile file) {
        if (file.getTimestamp() == null) {
            return new Date();
        }
        return file.getTimestamp().getTime();
    }

    private FTPFile getFile() {
        final FTPClient connection = connect();
        FTPFile[] files;
        try {
            files = connection.listFiles(url.getPath());
        } catch (final IOException e) {
            throw new SourceException(e);
        }
        if (files.length == 0) {
            throw new SourceException(String.format("Source file \"%s\" does not exist!", url));
        }
        disconnect(connection);
        return files[0];
    }

    private FTPClient connect() {
        final FTPClient connection = new FTPClient();
        try {
            connection.setAutodetectUTF8(true);
            connection.connect(url.getHost(), port);
            connection.enterLocalActiveMode();
            if (!connection.login("anonymous", "gabrys.biz Extended LessCSS Compiler")) {
                throw new SourceException(String.format("Cannot login as anonymous user, reason %s", connection.getReplyString()));
            }
            if (!FTPReply.isPositiveCompletion(connection.getReplyCode())) {
                throw new SourceException(String.format("Cannot download source \"%s\", reason: %s", url, connection.getReplyString()));
            }
            connection.enterLocalPassiveMode();
            connection.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (final IOException e) {
            disconnect(connection);
            throw new SourceException(e);
        }
        return connection;
    }

    private static void disconnect(final FTPClient connection) {
        try {
            connection.disconnect();
        } catch (final IOException e) {
            // do nothing
        }
    }
}
