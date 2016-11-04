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
import java.io.IOException;

import biz.gabrys.lesscss.extended.compiler.storage.DataStorage;
import biz.gabrys.lesscss.extended.compiler.storage.DataStorageImpl;
import biz.gabrys.lesscss.extended.compiler.util.ParameterUtils;

/**
 * Responsible for creating new instances of the {@link FullCacheImpl}.
 * @since 1.0
 */
public class FullCacheBuilder {

    private DataStorage dataStorage;

    /**
     * Constructs a new instance.
     * @since 1.0
     */
    public FullCacheBuilder() {
        // do nothing
    }

    /**
     * Sets specified working directory.
     * @param workingDirectory the working directory.
     * @return {@code this} builder.
     * @throws IllegalArgumentException if working directory is equal to {@code null}.
     * @since 1.0
     */
    public FullCacheBuilder withDirectory(final File workingDirectory) {
        ParameterUtils.verifyNotNull("directory", workingDirectory);
        dataStorage = new DataStorageImpl(workingDirectory);
        return this;
    }

    /**
     * Sets specified data storage.
     * @param dataStorage the data storage.
     * @return {@code this} builder.
     * @throws IllegalArgumentException if data storage is equal to {@code null}.
     * @since 1.0
     */
    public FullCacheBuilder withDataStorage(final DataStorage dataStorage) {
        ParameterUtils.verifyNotNull("data storage", dataStorage);
        this.dataStorage = dataStorage;
        return this;
    }

    /**
     * Crates a new instance of the {@link FullCacheImpl}. If {@link #withDirectory(File)} or
     * {@link #withDataStorage(DataStorage)} has not been called before, it uses temporary directory.
     * @return the new instance of the {@link FullCacheImpl}.
     * @throws UnsupportedOperationException if cannot create temporary directory.
     * @since 1.0
     */
    public FullCacheImpl create() {
        if (dataStorage == null) {
            dataStorage = createFallbackDataStorage();
        }
        return new FullCacheImpl(dataStorage);
    }

    /**
     * Creates a new instance of the {@link DataStorage} which will be use as fallback for {@link #create()} method.
     * @return the new instance of the {@link DataStorage}.
     * @throws UnsupportedOperationException if cannot create temporary directory.
     * @since 2.1.0
     */
    protected DataStorage createFallbackDataStorage() {
        try {
            return new DataStorageImpl(TemporaryDirectoryUtils.create());
        } catch (final IOException e) {
            throw new UnsupportedOperationException("Cannot create temporary directory", e);
        }
    }
}
