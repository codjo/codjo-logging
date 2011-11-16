/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.logging;
import java.net.URL;
/**
 *
 */
public class LoggingUtil {
    private LoggingUtil() {}


    public static URL getDefaultLoggerConfiguration() {
        if (!isConfigurationValid()) {
            return null;
        }
        return LoggingUtil.class.getResource("logger.properties");
    }


    public static boolean isConfigurationValid() {
        return exists(LoggingProperties.LOG_DIR) && exists(LoggingProperties.LOG_FILE_NAME);
    }


    private static boolean exists(String propertyName) {
        return System.getProperty(propertyName) != null;
    }
}
