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
package biz.gabrys.lesscss.extended.compiler;

import biz.gabrys.lesscss.compiler.CompilerException;
import biz.gabrys.lesscss.compiler.CompilerOptions;
import biz.gabrys.lesscss.extended.compiler.source.LessSource;

/**
 * Wraps the native <a href="http://lesscss-compiler.projects.gabrys.biz/">LessCSS Compiler</a> expanding it with extra
 * features.
 * @since 1.0
 */
public interface ExtendedCompiler {

    /**
     * Compiles a <a href="http://lesscss.org/">Less</a> source to the <a href="http://www.w3.org/Style/CSS/">CSS</a>
     * code with default options.
     * @param source the source file.
     * @return the <a href="http://www.w3.org/Style/CSS/">CSS</a> code.
     * @throws CompilerException if an error occurred during compilation process.
     * @throws ExtendedCompilerException if an error occurred during the execution of one of the elements of the
     *             extended mechanism.
     * @since 1.0
     */
    String compile(LessSource source) throws CompilerException;

    /**
     * Compiles a <a href="http://lesscss.org/">Less</a> source to the <a href="http://www.w3.org/Style/CSS/">CSS</a>
     * code.
     * @param source the source file.
     * @param options the compiler options.
     * @return the <a href="http://www.w3.org/Style/CSS/">CSS</a> code.
     * @throws CompilerException if an error occurred during compilation process.
     * @throws ExtendedCompilerException if an error occurred during the execution of one of the elements of the
     *             extended mechanism.
     * @since 1.0
     */
    String compile(LessSource source, CompilerOptions options) throws CompilerException;
}
