package biz.gabrys.lesscss.extended.compiler.util;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public final class ParameterUtilsTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void verifyNotNull_parameterValueIsNotNull_doNothing() {
        ParameterUtils.verifyNotNull(null, "value");
        ParameterUtils.verifyNotNull(null, Boolean.FALSE);
        ParameterUtils.verifyNotNull(null, new ArrayList<String>());
        ParameterUtils.verifyNotNull("name", "value");
        ParameterUtils.verifyNotNull("name", Boolean.FALSE);
        ParameterUtils.verifyNotNull("name", new ArrayList<String>());
    }

    @Test
    public void verifyNotNull_parameterNameAndValueAreNull_throwException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter is null");

        ParameterUtils.verifyNotNull(null, null);
    }

    @Test
    public void verifyNotNull_parameterNameIsNotNullAndValueIsNull_throwException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter \"name\" is null");

        ParameterUtils.verifyNotNull("name", null);
    }
}
