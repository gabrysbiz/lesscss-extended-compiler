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

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of the {@link SourceFactory}.
 * @since 1.0
 */
public class SourceFactoryImpl implements SourceFactory {

    private final List<ConcreteSourceFactory> factories = new ArrayList<ConcreteSourceFactory>();

    /**
     * Constructs a new instance and sets concrete factories.
     * @param factories concrete factories.
     * @since 1.0
     */
    public SourceFactoryImpl(final Iterable<ConcreteSourceFactory> factories) {
        for (final ConcreteSourceFactory factory : factories) {
            this.factories.add(factory);
        }
    }

    public LessSource create(final LessSource source, final String importPath) {
        for (final ConcreteSourceFactory factory : factories) {
            if (factory.isAbsolutePath(importPath)) {
                return factory.createAbsoluteSource(source, importPath);
            }
        }
        for (final ConcreteSourceFactory factory : factories) {
            if (factory.isAbsolutePath(source.getPath())) {
                return factory.createRelativeSource(source, importPath);
            }
        }
        throw new SourceFactoryException(String.format("Cannot create import \"%s\" for source %s with path: %s", importPath,
                source.getClass().getSimpleName(), source.getPath()));
    }
}
