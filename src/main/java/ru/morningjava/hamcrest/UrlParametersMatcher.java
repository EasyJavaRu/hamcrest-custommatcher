package ru.morningjava.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class UrlParametersMatcher extends TypeSafeMatcher<String> {
    private final String expectedString;
    private final Map<String, String> expected;

    protected final Map<String, String> paramStringToMap(final String string) {
        return Arrays.stream(string.split("&"))
                .collect(Collectors.toMap(
                        (String s) -> s.split("=")[0],
                        (String s) -> s.split("=").length==2 ? s.split("=")[1] : ""));
    }

    /**
     * Constructs matcher.
     * @param e model string to match.
     */
    public UrlParametersMatcher(final String e) {
        expectedString = e;
        expected = paramStringToMap(expectedString);
    }

    @Override
    protected final boolean matchesSafely(final String actualString) {
        return expected.equals(paramStringToMap(actualString));
    }

    @Override
    public final void describeTo(final Description description) {
        description
                .appendText("Parameters should match: ")
                .appendValue(expectedString);
    }

    @Override
    protected final void describeMismatchSafely(
            final String item,
            final Description mismatchDescription) {
        Map<String, String> actual = paramStringToMap(item);
        if (expected.size() != actual.size()) {
            mismatchDescription
                    .appendText("Actual number of parameters is ")
                    .appendValue(actual.size())
                    .appendText(" while expecting ")
                    .appendValue(expected.size())
                    .appendText(" parameters");
            return;
        }
        for (Map.Entry<String, String> entry: expected.entrySet()) {
            if (!actual.containsKey(entry.getKey())) {
                mismatchDescription
                        .appendText("Expected parameter ")
                        .appendValue(entry.getKey())
                        .appendText(" is not found");
                return;
            }
            if (!entry.getValue().equals(actual.get(entry.getKey()))) {
                mismatchDescription
                        .appendText("For parameter ")
                        .appendValue(entry.getKey())
                        .appendText(" actual value is ")
                        .appendValue(actual.get(entry.getKey()))
                        .appendText(" while expected value was ")
                        .appendValue(entry.getValue());
                return;
            }
        }
    }

    /**
     * Constructs ready-to-use matcher.
     * @param parameters model string.
     * @return Fully constructed matcher.
     */
    public static UrlParametersMatcher
    parametersEqual(final String parameters) {
        return new UrlParametersMatcher(parameters);
    }
}
