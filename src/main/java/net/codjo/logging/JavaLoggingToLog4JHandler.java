/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.logging;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
/**
 * Pont du loggin java.util vers log4J.
 */
public class JavaLoggingToLog4JHandler extends Handler {
    public static final Map<java.util.logging.Level, Level> LEVEL_CONVERSION_MAP =
          new HashMap<java.util.logging.Level, Level>();


    static {
        LEVEL_CONVERSION_MAP.put(java.util.logging.Level.OFF, Level.OFF);
        LEVEL_CONVERSION_MAP.put(java.util.logging.Level.SEVERE, Level.FATAL);
        LEVEL_CONVERSION_MAP.put(java.util.logging.Level.WARNING, Level.WARN);
        LEVEL_CONVERSION_MAP.put(java.util.logging.Level.INFO, Level.INFO);
        LEVEL_CONVERSION_MAP.put(java.util.logging.Level.CONFIG, Level.INFO);
        LEVEL_CONVERSION_MAP.put(java.util.logging.Level.FINE, Level.DEBUG);
        LEVEL_CONVERSION_MAP.put(java.util.logging.Level.FINER, Level.DEBUG);
        LEVEL_CONVERSION_MAP.put(java.util.logging.Level.FINEST, Level.DEBUG);
        LEVEL_CONVERSION_MAP.put(java.util.logging.Level.ALL, Level.ALL);
    }


    @Override
    public void close() {}


    @Override
    public void flush() {}


    @Override
    public void publish(LogRecord record) {
        LoggingEvent loggingEvent = getLoggingEvent(record);
        Logger logger = Logger.getLogger(loggingEvent.getLoggerName());
        logger.callAppenders(loggingEvent);
    }


    protected LoggingEvent getLoggingEvent(LogRecord logRecord) {
        String fqn = logRecord.getLoggerName();
        Logger logger = Logger.getLogger(fqn);
        Level level = LEVEL_CONVERSION_MAP.get(logRecord.getLevel());
        long timestamp = logRecord.getMillis();
        Object message = logRecord.getMessage();
        return new LoggingEvent(fqn, logger, timestamp, level, message,
                                logRecord.getThrown());
    }
}
