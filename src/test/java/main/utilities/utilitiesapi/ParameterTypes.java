package main.utilities.utilitiesapi;

import io.cucumber.java.ParameterType;

public class ParameterTypes {
    @ParameterType("true|false")
    public boolean booleanType(String value) {
        return Boolean.parseBoolean(value);
    }
}
