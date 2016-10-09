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
import java.util.Collection;
import java.util.List;

/**
 * Responsible for storing file data.
 * @since 1.0
 */
public interface DataStorage {

    /**
     * Tests whether store data related with file.
     * @param fileName the file name.
     * @return {@code true} whether store data, otherwise {@code false}.
     */
    boolean hasData(String fileName);

    /**
     * Puts a file data to the storage.
     * @param fileName the file name.
     * @param file the file with data.
     * @since 1.0
     */
    void put(String fileName, File file);

    /**
     * Returns a file with the data.
     * @param fileName the file name.
     * @return the file with the data.
     * @since 1.0
     */
    File getFile(String fileName);

    /**
     * Puts a text to the storage.
     * @param fileName the file name.
     * @param text the text with data.
     * @since 1.0
     */
    void put(String fileName, String text);

    /**
     * Returns a text with data.
     * @param fileName the file name.
     * @return the text with data.
     * @since 1.0
     */
    String getText(String fileName);

    /**
     * Puts lines to the storage.
     * @param fileName the file name.
     * @param lines the text lines with data.
     * @since 2.0
     */
    void put(String fileName, Collection<String> lines);

    /**
     * Returns lines with data.
     * @param fileName the file name.
     * @return the text lines with data.
     * @since 1.0
     */
    List<String> getLines(String fileName);

    /**
     * Deletes data by file name.
     * @param fileName the file name.
     * @since 1.0
     */
    void delete(String fileName);

    /**
     * Deletes all data.
     * @since 1.0
     */
    void deleteAll();
}
