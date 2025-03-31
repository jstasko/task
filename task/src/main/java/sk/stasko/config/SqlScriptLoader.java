package sk.stasko.config;

import sk.stasko.util.AppConstants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class SqlScriptLoader {
    public static String loadInitScript() {
        String fileName = AppConfig.get(AppConstants.SQL_INIT_SCRIPT_PROPERTY);
        try (var reader = new BufferedReader(new InputStreamReader(
                SqlScriptLoader.class.getClassLoader().getResourceAsStream(fileName)))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load SQL script: " + fileName, e);
        }
    }
}
