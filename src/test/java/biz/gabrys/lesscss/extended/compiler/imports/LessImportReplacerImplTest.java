package biz.gabrys.lesscss.extended.compiler.imports;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class LessImportReplacerImplTest {

    @Test
    public void replace_sourceWithNewLines_replacedCorrectly() {
        final String operationSourceCode = "@import (once) 'file';";
        final LessImportOperation operation = Mockito.mock(LessImportOperation.class);
        Mockito.when(operation.getSourceCode()).thenReturn(operationSourceCode);
        Mockito.when(operation.getComputedOption()).thenReturn("once");

        final String sourceCode = "text\n" + operationSourceCode + "\nother text";
        final String replacedCode = "text\n@import (once) \"cache\";\nother text";
        final String importPath = "cache";

        final LessImportReplacer replacer = Mockito.spy(new LessImportReplacerImpl());
        Assert.assertEquals("Returned code is different than expected", replacedCode, replacer.replace(sourceCode, operation, importPath));

        Mockito.verify(replacer).replace(sourceCode, operation, importPath);
        Mockito.verify(operation).getSourceCode();
        Mockito.verify(operation).getComputedOption();
        Mockito.verifyNoMoreInteractions(replacer, operation);
    }

    @Test
    public void replace_sourceWithoutNewLines_replacedCorrectly() {
        final String operationSourceCode = "@import (once) 'file';";
        final LessImportOperation operation = Mockito.mock(LessImportOperation.class);
        Mockito.when(operation.getSourceCode()).thenReturn(operationSourceCode);
        Mockito.when(operation.getComputedOption()).thenReturn("once");

        final String sourceCode = "text@import (once) 'file';other text";
        final String replacedCode = "text@import (once) \"cache\";other text";
        final String importPath = "cache";

        final LessImportReplacer replacer = Mockito.spy(new LessImportReplacerImpl());
        Assert.assertEquals("Returned code is different than expected", replacedCode, replacer.replace(sourceCode, operation, importPath));

        Mockito.verify(replacer).replace(sourceCode, operation, importPath);
        Mockito.verify(operation).getSourceCode();
        Mockito.verify(operation).getComputedOption();
        Mockito.verifyNoMoreInteractions(replacer, operation);
    }

    @Test
    public void replace_importWithSpecialChars_replacedCorrectly() {
        final String operationSourceCode = "@import (once) '@import';";
        final LessImportOperation operation = Mockito.mock(LessImportOperation.class);
        Mockito.when(operation.getSourceCode()).thenReturn(operationSourceCode);
        Mockito.when(operation.getComputedOption()).thenReturn("once");

        final String sourceCode = "text" + operationSourceCode + "other text";
        final String replacedCode = "text@import (once) \"cache\";other text";
        final String importPath = "cache";

        final LessImportReplacer replacer = Mockito.spy(new LessImportReplacerImpl());
        Assert.assertEquals("Returned code is different than expected", replacedCode, replacer.replace(sourceCode, operation, importPath));

        Mockito.verify(replacer).replace(sourceCode, operation, importPath);
        Mockito.verify(operation).getSourceCode();
        Mockito.verify(operation).getComputedOption();
        Mockito.verifyNoMoreInteractions(replacer, operation);
    }

    @Test(expected = ImportException.class)
    public void replace_importDoesNotExist_throwsException() {
        final String operationSourceCode = "@import (once) 'not-existed';";
        final LessImportOperation operation = Mockito.mock(LessImportOperation.class);
        Mockito.when(operation.getSourceCode()).thenReturn(operationSourceCode);
        Mockito.when(operation.getComputedOption()).thenReturn("once");

        final LessImportReplacer replacer = new LessImportReplacerImpl();
        replacer.replace("@import (once) 'other';", operation, "cache");
    }

    @Test
    public void replace_twoImports_replacedOnlyFirst() {
        final String operationSourceCode = "@import (once) 'file';";
        final LessImportOperation operation = Mockito.mock(LessImportOperation.class);
        Mockito.when(operation.getSourceCode()).thenReturn(operationSourceCode);
        Mockito.when(operation.getComputedOption()).thenReturn("once");

        final String sourceCode = operationSourceCode + "\n" + operationSourceCode + "\n";
        final String replacedCode = "@import (once) \"cache\";\n" + operationSourceCode + "\n";
        final String importPath = "cache";

        final LessImportReplacer replacer = Mockito.spy(new LessImportReplacerImpl());
        Assert.assertEquals("Returned code is different than expected", replacedCode, replacer.replace(sourceCode, operation, importPath));

        Mockito.verify(replacer).replace(sourceCode, operation, importPath);
        Mockito.verify(operation).getSourceCode();
        Mockito.verify(operation).getComputedOption();
        Mockito.verifyNoMoreInteractions(replacer, operation);
    }
}
