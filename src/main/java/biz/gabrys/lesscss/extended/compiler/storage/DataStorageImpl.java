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
package biz.gabrys.lesscss.extended.compiler.storage;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * Default implementation of the {@link DataStorage}.
 * @since 1.0
 */
public class DataStorageImpl implements DataStorage {

    private final Object mutex = new Object();
    private final File directory;

    /**
     * Constructs a new instance.
     * @param workingDirectory the working directory.
     * @throws IllegalArgumentException if working directory is equal to {@code null}.
     * @since 1.0
     */
    public DataStorageImpl(final File workingDirectory) {
        if (workingDirectory == null) {
            throw new IllegalArgumentException("Working directory cannot be null");
        }
        directory = workingDirectory;
    }

    public boolean hasData(final String fileName) {
        return getFile(fileName) != null;
    }

    /**
     * {@inheritDoc}
     * @throws DataStorageException if an I/O error occurred.
     * @since 1.0
     */
    public void put(final String fileName, final File file) {
        synchronized (mutex) {
            final File cache = getFilePreparedForPut(fileName);
            try {
                FileUtils.copyFile(file, cache);
            } catch (final IOException e) {
                throw new DataStorageException(e);
            }
        }
    }

    public File getFile(final String fileName) {
        synchronized (mutex) {
            final File file = new File(directory, fileName);
            if (file.exists()) {
                return file;
            }
            return null;
        }
    }

    /**
     * {@inheritDoc}
     * @throws DataStorageException if an I/O error occurred.
     * @since 1.0
     */
    public void put(final String fileName, final String text) {
        synchronized (mutex) {
            final File cache = getFilePreparedForPut(fileName);
            try {
                FileUtils.write(cache, text);
            } catch (final IOException e) {
                throw new DataStorageException(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     * @throws DataStorageException if an I/O error occurred.
     * @since 1.0
     */
    public String getText(final String fileName) {
        synchronized (mutex) {
            final File cache = getFile(fileName);
            if (cache == null) {
                return null;
            }
            try {
                return FileUtils.readFileToString(cache);
            } catch (final IOException e) {
                throw new DataStorageException(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     * @throws DataStorageException if an I/O error occurred.
     * @since 2.0
     */
    public void put(final String fileName, final Collection<String> lines) {
        synchronized (mutex) {
            final File cache = getFilePreparedForPut(fileName);
            try {
                FileUtils.writeLines(cache, lines);
            } catch (final IOException e) {
                throw new DataStorageException(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     * @throws DataStorageException if an I/O error occurred.
     * @since 1.0
     */
    public List<String> getLines(final String fileName) {
        synchronized (mutex) {
            final File cache = getFile(fileName);
            if (cache == null) {
                return null;
            }
            try {
                return FileUtils.readLines(cache);
            } catch (final IOException e) {
                throw new DataStorageException(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     * @throws DataStorageException if an I/O error occurred.
     * @since 1.0
     */
    public void delete(final String fileName) {
        synchronized (mutex) {
            getFilePreparedForPut(fileName);
        }
    }

    private File getFilePreparedForPut(final String fileName) {
        final File file = new File(directory, fileName);
        if (file.exists() && !file.delete()) {
            throw new DataStorageException(String.format("Cannot remove old source file \"%s\"", file));
        }
        return file;
    }

    /**
     * {@inheritDoc}
     * @throws DataStorageException if an I/O error occurred.
     * @since 1.0
     */
    public void deleteAll() {
        synchronized (mutex) {
            try {
                FileUtils.cleanDirectory(directory);
            } catch (final IOException e) {
                throw new DataStorageException(e);
            }
        }
    }
}
