package com.github.vaatech.aom.test.rest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to build populated `Map` instances. Page instances are mutable (builder-like).
 *
 * <p>Usage: <code><pre>
 * Map&lt;String, Object&gt; params = Parameters.with("foo", foo)
 *     .and("bar", bar)
 *     .and("gee", gee)
 *     .map();
 * </pre></code>
 *
 * @author Stéphane Épardaud
 */
public class Parameters {
    private final Map<String, String> values = new HashMap<>();

    /**
     * Add a parameter to this {@link Parameters}.
     *
     * @param name  name of the parameter to add
     * @param value value of the parameter to add
     * @return this instance, modified.
     * @see {@link Parameters#map()}
     */
    public Parameters and(String name, String value) {
        values.put(name, value);
        return this;
    }

    /**
     * Constructs an unmodifiable {@link Map} with the current parameters.
     *
     * @return an unmodifiable {@link Map} with the current parameters.
     */
    public Map<String, String> map() {
        return Collections.unmodifiableMap(values);
    }

    /**
     * Build a {@link Parameters} with a single parameter.
     *
     * @param name  name of the first parameter
     * @param value value of the first parameter
     * @return a {@link Parameters} with a single parameter.
     * @see {@link Parameters#and(String, String)}
     * @see {@link Parameters#map()}
     */
    public static Parameters with(String name, String value) {
        return new Parameters().and(name, value);
    }

    /**
     * Build a {@link Parameters} with a map of parameters.
     *
     * @param values map of the parameters
     * @return a {@link Parameters} with multiple parameters.
     * @see {@link Parameters#and(String, String)}
     * @see {@link Parameters#map()}
     */
    public static Parameters with(Map<String, String> values) {
        Parameters parameters = new Parameters();
        values.forEach(parameters::and);
        return parameters;
    }
}
