package util;

import http.HttpMethod;

import java.util.function.Supplier;
import java.util.regex.Pattern;

public final class Regex {

    public static Supplier<Pattern> httpRequestTargetPattern = () -> Pattern.compile("^%s\\s(\\/[\\S]*).*".formatted(HttpMethod.getAllMethods()));

}