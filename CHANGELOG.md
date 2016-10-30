# Extended LessCSS Compiler Changelog

## 2.1.0-SNAPSHOT
New Features:
* Classpath "protocol" support

API:
* [AbstractSourceTreePreparationProcessor#expirationChecker](http://lesscss-extended-compiler.projects.gabrys.biz/LATEST/apidocs/index.html?biz/gabrys/lesscss/extended/compiler/control/processor/AbstractSourceTreePreparationProcessor.html) is no longer `final`
* [AbstractSourceTreePreparationProcessor#importsCache](http://lesscss-extended-compiler.projects.gabrys.biz/LATEST/apidocs/index.html?biz/gabrys/lesscss/extended/compiler/control/processor/AbstractSourceTreePreparationProcessor.html) is no longer `final`
* [AbstractSourceTreePreparationProcessor#sourceFactory](http://lesscss-extended-compiler.projects.gabrys.biz/LATEST/apidocs/index.html?biz/gabrys/lesscss/extended/compiler/control/processor/AbstractSourceTreePreparationProcessor.html) is no longer `final`
* [FullCacheImpl#storage](http://lesscss-extended-compiler.projects.gabrys.biz/LATEST/apidocs/index.html?biz/gabrys/lesscss/extended/compiler/cache/FullCacheImpl.html) is no longer `final`

[See documentation](http://lesscss-extended-compiler.projects.gabrys.biz/LATEST/)

## 2.0
New Features:
* [FTP](https://tools.ietf.org/html/rfc959) protocol support

API:
* [DataStorage#put(fileName, lines)](http://lesscss-extended-compiler.projects.gabrys.biz/2.0/apidocs/index.html?biz/gabrys/lesscss/extended/compiler/storage/DataStorage.html): changed the type of the parameter `lines` from `java.lang.Iterable` to `java.util.Collection`
* [ConcreteSourceFactory](http://lesscss-extended-compiler.projects.gabrys.biz/2.0/apidocs/index.html?biz/gabrys/lesscss/extended/compiler/source/ConcreteSourceFactory.html) is now generic: `ConcreteSourceFactory<T extends LessSource>`

Dependencies:
* Use <code>commons-lang3</code> instead of <code>commons-lang</code>

Bugs:
* Security fixes:
  * changed the way of creating temporary directories (see [OWAPS Top Ten 2013 Category A9](https://www.owasp.org/index.php/Top_10_2013-A9-Using_Components_with_Known_Vulnerabilities))
  * protection against NULL bytes (see [CWE-158](http://cwe.mitre.org/data/definitions/158.html), [WASC-28](http://projects.webappsec.org/w/page/13246949/Null%20Byte%20Injection))
* Optimization of create new collections

[See documentation](http://lesscss-extended-compiler.projects.gabrys.biz/2.0/)

## 1.0
Initial release.

[See documentation](http://lesscss-extended-compiler.projects.gabrys.biz/1.0/)