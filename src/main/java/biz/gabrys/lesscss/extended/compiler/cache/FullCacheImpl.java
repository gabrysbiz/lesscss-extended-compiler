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
package biz.gabrys.lesscss.extended.compiler.cache;

import java.io.File;
import java.util.Date;
import java.util.List;

import biz.gabrys.lesscss.extended.compiler.source.LessSource;
import biz.gabrys.lesscss.extended.compiler.storage.DataStorage;

/**
 * Full cache implementation based on the {@link DataStorage}. It stores files in the flat structure.
 * @since 1.0
 */
public class FullCacheImpl implements FullCache, DeletableCache {

    /**
     * The data storage.
     * @since 1.0
     */
    protected final DataStorage storage;

    /**
     * Constructs a new instance.
     * @param storage the data storage.
     * @throws IllegalArgumentException if storage is equal to {@code null}.
     * @since 1.0
     */
    public FullCacheImpl(final DataStorage storage) {
        if (storage == null) {
            throw new IllegalArgumentException("Data storage cannot be null");
        }
        this.storage = storage;
    }

    public void saveSourceModificationDate(final LessSource source, final Date modificationDate) {
        storage.put(createFileName(EntryType.MODIFICATION_DATE, source), String.valueOf(modificationDate.getTime()));
    }

    public boolean hasSourceModificationDate(final LessSource source) {
        return storage.hasData(createFileName(EntryType.MODIFICATION_DATE, source));
    }

    /**
     * {@inheritDoc}
     * @throws CacheException if an error occurs during read modification date.
     * @since 1.0
     */
    public Date getSourceModificationDate(final LessSource source) {
        final String modificationDate = storage.getText(createFileName(EntryType.MODIFICATION_DATE, source));
        try {
            return new Date(Long.parseLong(modificationDate));
        } catch (final NumberFormatException e) {
            throw new CacheException(e);
        }
    }

    public void saveSourceImports(final LessSource source, final List<String> imports) {
        storage.put(createFileName(EntryType.IMPORTS_LIST, source), imports);
    }

    public boolean hasSourceImports(final LessSource source) {
        return storage.hasData(createFileName(EntryType.IMPORTS_LIST, source));
    }

    public List<String> getSourceImports(final LessSource source) {
        return storage.getLines(createFileName(EntryType.IMPORTS_LIST, source));
    }

    public void saveSourceCode(final LessSource source, final String sourceCode) {
        storage.put(createFileName(EntryType.SOURCE_CODE, source), sourceCode);
    }

    public boolean hasSourceCode(final LessSource source) {
        return storage.hasData(createFileName(EntryType.SOURCE_CODE, source));
    }

    public File getSourceFile(final LessSource source) {
        return storage.getFile(createFileName(EntryType.SOURCE_CODE, source));
    }

    public String getSourceRelativePath(final LessSource source) {
        return createFileName(EntryType.SOURCE_CODE, source);
    }

    public void saveCompilationDate(final LessSource source, final Date compilationDate) {
        storage.put(createFileName(EntryType.COMPILATION_DATE, source), String.valueOf(compilationDate.getTime()));
    }

    public boolean hasCompilationDate(final LessSource source) {
        return storage.hasData(createFileName(EntryType.COMPILATION_DATE, source));
    }

    /**
     * {@inheritDoc}
     * @throws CacheException if an error occurs during read compilation date.
     * @since 1.0
     */
    public Date getCompilationDate(final LessSource source) {
        final String compilationDate = storage.getText(createFileName(EntryType.COMPILATION_DATE, source));
        try {
            return new Date(Long.parseLong(compilationDate));
        } catch (final NumberFormatException e) {
            throw new CacheException(e);
        }
    }

    public void saveCompiledCode(final LessSource source, final String compiledCode) {
        storage.put(createFileName(EntryType.COMPILED_CODE, source), compiledCode);
    }

    public boolean hasCompiledCode(final LessSource source) {
        return storage.hasData(createFileName(EntryType.COMPILED_CODE, source));
    }

    public String getCompiledCode(final LessSource source) {
        return storage.getText(createFileName(EntryType.COMPILED_CODE, source));
    }

    public void delete(final LessSource source) {
        for (final EntryType type : EntryType.values()) {
            storage.delete(createFileName(type, source));
        }
    }

    public void deleteAll() {
        storage.deleteAll();
    }

    private String createFileName(final EntryType type, final LessSource source) {
        final String path = source.getPath();
        final int index = path.replace('\\', '/').lastIndexOf('/');
        final StringBuilder fileName = new StringBuilder(75);
        if (index > 0) {
            fileName.append(path.substring(index + 1));
            fileName.append('-');
        }
        final String hashCode = String.valueOf(path.hashCode());
        if (hashCode.charAt(0) == '-') {
            fileName.append(hashCode.replace('-', 'n'));
        } else {
            fileName.append('p');
            fileName.append(hashCode);
        }
        fileName.append(type.getExtension());
        return fileName.toString();
    }

    private enum EntryType {
        MODIFICATION_DATE, IMPORTS_LIST, SOURCE_CODE, COMPILATION_DATE, COMPILED_CODE;

        private final String extension;

        private EntryType() {
            extension = '.' + name().toLowerCase().replace('_', '.');
        }

        private String getExtension() {
            return extension;
        }
    }
}
