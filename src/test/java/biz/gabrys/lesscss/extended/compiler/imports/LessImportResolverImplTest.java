package biz.gabrys.lesscss.extended.compiler.imports;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public final class LessImportResolverImplTest {

    @Test
    public void resolve_oneImport_withoutOption_inSingleQuotes_withoutExtension() {
        final String code = "@import 'file';";
        final LessImportOperation operation = new LessImportOperation("file", "file.less", null, "less", code);
        makeTest(code, operation);
    }

    @Test
    public void resolve_oneImport_withoutOption_inSingleQuotes_withCssExtension() {
        makeTest("@import 'file.css';");
    }

    @Test
    public void resolve_oneImport_withoutOption_inSingleQuotes_withNotCssExtension() {
        final String code = "@import 'file.ext';";
        final LessImportOperation operation = new LessImportOperation("file.ext", "file.ext", null, "less", code);
        makeTest(code, operation);
    }

    @Test
    public void resolve_oneImport_withoutOption_inSingleQuotes_pathContainsSpaceAtEnd() {
        final String code = "@import 'file.ext ';";
        final LessImportOperation operation = new LessImportOperation("file.ext ", "file.ext .less", null, "less", code);
        makeTest(code, operation);
    }

    @Test
    public void resolve_oneImport_withLessOption_isSingleQuotes_withoutExtension() {
        final String code = "@import (less) 'file';";
        final LessImportOperation operation = new LessImportOperation("file", "file", "less", "less", code);
        makeTest(code, operation);
    }

    @Test
    public void resolve_oneImport_withCssOption_inSingleQuotes_withoutExtension() {
        makeTest("@import (css) 'file';");
    }

    @Test
    public void resolve_oneImport_withCssOption_inDoubleQuotes_withoutExtension() {
        makeTest("@import (css) \"file\";");
    }

    @Test
    public void resolve_oneImport_withLessOption_inSingleQuotes_withExtension() {
        final String code = "@import (less) 'file.ext';";
        final LessImportOperation operation = new LessImportOperation("file.ext", "file.ext", "less", "less", code);
        makeTest(code, operation);
    }

    @Test
    public void resolve_oneImport_withCssOption_inSingleQuotes_withExtension() {
        makeTest("@import (css) 'file.ext';");
    }

    @Test
    public void resolve_oneImport_betweenCode_withoutOption_inSingleQuotes_withoutExtension() {
        final String code = ".testClass1{}\n\n@import 'file';\n.testClass2{}";
        final LessImportOperation operation = new LessImportOperation("file", "file.less", null, "less", "@import 'file';");
        makeTest(code, operation);
    }

    @Test
    public void resolve_oneImport_betweenCode_withoutOption_inSingleQuotes_withExtension() {
        final String code = ".testClass1{}\n\n@import 'file.ext';\n.testClass2{}";
        final LessImportOperation operation = new LessImportOperation("file.ext", "file.ext", null, "less", "@import 'file.ext';");
        makeTest(code, operation);
    }

    @Test
    public void resolve_oneImport_betweenCode_withLessOption_inSingleQuotes_withoutExtension() {
        final String code = ".testClass1{}\n\n@import (less) 'file';\n.testClass2{}";
        final LessImportOperation operation = new LessImportOperation("file", "file", "less", "less", "@import (less) 'file';");
        makeTest(code, operation);
    }

    @Test
    public void resolve_oneImport_betweenCode_withLessOption_inSingleQuotes_withExtension() {
        final String code = ".testClass1{}\n\n@import (less) 'file.ext';\n.testClass2{}";
        final LessImportOperation operation = new LessImportOperation("file.ext", "file.ext", "less", "less", "@import (less) 'file.ext';");
        makeTest(code, operation);
    }

    @Test
    public void resolve_twoImports() {
        final String code = ".testClass1{}\n\n@import 'file1';\n.testClass2{}\n  @import (once) 'file2.less ';\n\n";
        final LessImportOperation operation1 = new LessImportOperation("file1", "file1.less", null, "less", "@import 'file1';");
        final LessImportOperation operation2 = new LessImportOperation("file2.less ", "file2.less ", "once", "once",
                "@import (once) 'file2.less ';");
        makeTest(code, operation1, operation2);
    }

    @Test
    public void resolve_compressedCode_twoImports() {
        final String code = ".testClass1{width:0}@import 'file1-comp';.testClass2{width:0}@import (once)'file-comp2.less ';";
        final LessImportOperation operation1 = new LessImportOperation("file1-comp", "file1-comp.less", null, "less",
                "@import 'file1-comp';");
        final LessImportOperation operation2 = new LessImportOperation("file-comp2.less ", "file-comp2.less ", "once", "once",
                "@import (once)'file-comp2.less ';");
        makeTest(code, operation1, operation2);
    }

    private void makeTest(final String code, final LessImportOperation... operations) {
        final LessImportResolver resolver = new LessImportResolverImpl();
        final List<LessImportOperation> resolvedOperations = resolver.resolve(code);
        Assert.assertEquals("Resolved operations count is different than expected", operations.length, resolvedOperations.size());
        for (int i = 0; i < operations.length; ++i) {
            Assert.assertEquals("Resolved operation (index: " + i + ") is different than expected", operations[i],
                    resolvedOperations.get(i));
        }
    }
}
