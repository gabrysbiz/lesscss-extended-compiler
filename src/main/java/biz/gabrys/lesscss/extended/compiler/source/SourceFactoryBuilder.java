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

    private final Map<Class<?>, ConcreteSourceFactory<? extends LessSource>> factories;

    /**
     * Constructs a new instance with zero factories.
     * @since 1.0
     */
    public SourceFactoryBuilder() {
        this(new LinkedHashMap<Class<?>, ConcreteSourceFactory<? extends LessSource>>());
    }

    SourceFactoryBuilder(final Map<Class<?>, ConcreteSourceFactory<? extends LessSource>> factories) {
        this.factories = factories;
    }

    /**
     * Appends standard factories at the end or does nothing if you try to add the same factory again.
     * @return {@code this} builder.
     * @since 1.0
     * @see HttpSourceFactory
     * @see FtpSourceFactory
     * @see LocalSourceFactory
     */
    public SourceFactoryBuilder withStandard() {
        return withHttp().withFtp().withLocal();
    }

    /**
     * Appends {@link LocalSourceFactory} at the end or does nothing if you try to add the same factory again.
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
     * Appends {@link HttpSourceFactory} at the end or does nothing if you try to add the same factory again.
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
     * Appends {@link FtpSourceFactory} at the end or does nothing if you try to add the same factory again.
     * @return {@code this} builder.
     * @since 2.0
     */
    public SourceFactoryBuilder withFtp() {
        if (!factories.containsKey(FtpSourceFactory.class)) {
            factories.put(FtpSourceFactory.class, new FtpSourceFactory());
        }
        return this;
    }

    /**
     * Appends {@link ClasspathSourceFactory} at the end or does nothing if you try to add the same factory again.
     * @return {@code this} builder.
     * @since 2.1
     */
    public SourceFactoryBuilder withClasspath() {
        if (!factories.containsKey(ClasspathSourceFactory.class)) {
            factories.put(ClasspathSourceFactory.class, new ClasspathSourceFactory());
        }
        return this;
    }

    /**
     * Appends {@link ConcreteSourceFactory} at the end or does nothing if you try to add the same factory again.
     * @param factory the concrete source factory.
     * @return {@code this} builder.
     * @throws IllegalArgumentException is factory is equal to {@code null}.
     * @since 1.0
     */
    public SourceFactoryBuilder withCustom(final ConcreteSourceFactory<? extends LessSource> factory) {
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
