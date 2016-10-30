# About
[![License BSD 3-Clause](https://img.shields.io/badge/license-BSD%203--Clause-blue.svg)](http://lesscss-extended-compiler.projects.gabrys.biz/license.txt)
[![Build Status](https://travis-ci.org/gabrysbiz/lesscss-extended-compiler.svg?branch=develop)](https://travis-ci.org/gabrysbiz/lesscss-extended-compiler)

Extended version of the [LessCSS Compiler](http://lesscss-compiler.projects.gabrys.biz/)
(Java library which compiles [Less](http://lesscss.org/) source files to the [CSS](http://www.w3.org/Style/CSS/) code).
Adds features such as:
* caching ([see caching data](http://lesscss-extended-compiler.projects.gabrys.biz/LATEST/caching-data.html)):
 * compiled code
 * source code
* supports sources located at:
 * local drives
 * network protocols: HTTP, HTTPS, FTP
 * classpath (prefix `classpath://`)
 * [custom - defined by programmers](http://lesscss-extended-compiler.projects.gabrys.biz/LATEST/locations.html#custom)
 
# Compatibility
The compatibility with the [Less](http://lesscss.org/) language is determined by:
* native compiler implementation ([biz.gabrys.lesscss.compiler.LessCompiler](http://lesscss-compiler.projects.gabrys.biz/1.2.1/apidocs/index.html?biz/gabrys/lesscss/compiler/LessCompiler.html))
* supported locations of sources ([see supported locations matrix](http://lesscss-extended-compiler.projects.gabrys.biz/LATEST/locations.html#matrix))

# Requirements
The compiler to run requires:
* Java 5.0 or higher
* Third-Party Dependencies ([see list](http://lesscss-extended-compiler.projects.gabrys.biz/LATEST/dependencies.html))

# Download
You can download the library from [this page](http://lesscss-extended-compiler.projects.gabrys.biz/LATEST/download.html)
or using various [dependency management tools](http://lesscss-extended-compiler.projects.gabrys.biz/LATEST/dependency-info.html).
