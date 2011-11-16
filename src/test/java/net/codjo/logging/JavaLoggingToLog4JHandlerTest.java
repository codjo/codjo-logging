/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.logging;
import net.codjo.test.common.LogString;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import junit.framework.TestCase;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
/**
 * Classe de test de {@link net.codjo.logging.JavaLoggingToLog4JHandler}.
 */
public class JavaLoggingToLog4JHandlerTest extends TestCase {
    private JavaLoggingToLog4JHandler handler = new JavaLoggingToLog4JHandler();
    private LogString log = new LogString();


    public void test_publish() throws Exception {
        org.apache.log4j.Logger logger = Logger.getLogger("bobo");
        logger.addAppender(new AppenderMock(new LogString("bobo", log)));

        LogRecord javaRecord = new LogRecord(Level.INFO, "message");
        javaRecord.setLoggerName("bobo");

        handler.publish(javaRecord);

        log.assertContent("bobo.append(message)");
        logger.removeAllAppenders();
    }


    public void test_getLoggingEvent() throws Exception {
        LogRecord javaLog = new LogRecord(Level.INFO, "test");
        javaLog.setLoggerName("bobo");
        LoggingEvent log4jEvent = handler.getLoggingEvent(javaLog);

        assertNotNull(log4jEvent);
        assertEquals(org.apache.log4j.Level.INFO, log4jEvent.getLevel());
        assertEquals("test", log4jEvent.getMessage());
        assertEquals("bobo", log4jEvent.getLoggerName());
    }


    private static class AppenderMock extends AppenderSkeleton {
        private LogString log;


        AppenderMock(LogString log) {
            this.log = log;
        }


        @Override
        protected void append(LoggingEvent loggingEvent) {
            log.call("append", loggingEvent.getMessage());
        }


        public void close() {}


        public boolean requiresLayout() {
            return false;
        }
    }
}
