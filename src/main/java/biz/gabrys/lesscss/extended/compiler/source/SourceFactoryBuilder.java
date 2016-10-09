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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Responsible for creating new instances of the {@link SourceFactoryImpl}.
 * @since 1.0
 */
public class SourceFactoryBuilder {

    private final Map<Class<?>, ConcreteSourceFactory> factories = new LinkedHashMap<Class<?>, ConcreteSourceFactory>();

    /**
     * Constructs a new instance with zero factories.
     * @since 1.0
     */
    public SourceFactoryBuilder() {
        // do nothing
    }

    /**
     * Creates a new builder with added standard factories at the end or does nothing if you try to add the same factory
     * again.
     * @return {@code this} builder.
     * @since 1.0
     * @see HttpSourceFactory
     * @see LocalSourceFactory
     */
    public SourceFactoryBuilder withStandard() {
        return withHttp().withLocal();
    }

    /**
     * Creates a new builder with added {@link LocalSourceFactory} at the end or does nothing if you try to add the same
     * factory again.
     * @return {@code this} builder.
     * @since 1.0
     */
    public SourceFactoryBuilder withLocal() {
        if (!factories.containsKey(LocalSourceFactory.class)) {
            factories.put(LocalSourceFactory.class, new LocalSourceFactory());
        }
        return this;
    }

    /**
     * Creates a new builder with added {@link HttpSourceFactory} at the end or does nothing if you try to add the same
     * factory again.
     * @return {@code this} builder.
     * @since 1.0
     */
    public SourceFactoryBuilder withHttp() {
        if (!factories.containsKey(HttpSourceFactory.class)) {
            factories.put(HttpSourceFactory.class, new HttpSourceFactory());
        }
        return this;
    }

    /**
     * Creates a new builder with added {@link ConcreteSourceFactory} at the end or does nothing if you try to add the
     * same factory again.
     * @param factory the concrete source factory.
     * @return {@code this} builder.
     * @throws IllegalArgumentException is factory is equal to {@code null}.
     * @since 1.0
     */
    public SourceFactoryBuilder withCustom(final ConcreteSourceFactory factory) {
        if (factory == null) {
            throw new IllegalArgumentException("Factory cannot be null");
        }
        if (!factories.containsKey(factory.getClass())) {
            factories.put(factory.getClass(), factory);
        }
        return this;
    }

    /**
     * Creates a new instance of the {@link SourceFactoryImpl}.
     * @return the new instance of the {@link SourceFactoryImpl}.
     * @since 1.0
     */
    public SourceFactoryImpl create() {
        return new SourceFactoryImpl(factories.values());
    }
}
