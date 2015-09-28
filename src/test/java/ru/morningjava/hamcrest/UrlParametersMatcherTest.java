package ru.morningjava.hamcrest;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.morningjava.hamcrest.UrlParametersMatcher.parametersEqual;

public class UrlParametersMatcherTest {

    @Test
    public void assertMatches() {
        assertThat("arg1=val1&arg2=val2&arg3=val3", parametersEqual("arg1=val1&arg2=val2&arg3=val3"));
    }

    /**
     * This test fails intentionally.
     */
    @Test
    public void assertLengthMismatch() {
        assertThat("arg1=val1&arg2=val2&arg3=val3&arg4=val4", parametersEqual("arg1=val1&arg2=val2&arg3=val3"));
    }

    /**
     * This test fails intentionally.
     */
    @Test
    public void assertNamesMismatch() {
        assertThat("arg1=val1&arg22=val2&arg3=val3", parametersEqual("arg1=val1&arg2=val2&arg3=val3"));
    }

    /**
     * This test fails intentionally.
     */
    @Test
    public void assertValuesMismatch() {
        assertThat("arg1=val1&arg2=val22&arg3=val3", parametersEqual("arg1=val1&arg2=val2&arg3=val3"));
    }
}