package com.devsu.clienteservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Yordy Hernandez <yordy.hernandezg@gmail.com>
 */
@Component
public class GeneralLogger {

    private static final Logger logger = LoggerFactory.getLogger(GeneralLogger.class);

    public void logInfo(String message) {
        logger.info(message);
    }

    public void logError(String message) {
        logger.error(message);
    }

    public void logError(String message, Object object) {
        logger.error(message, object);
    }

    public void logError(String message, Throwable exception) {
        logger.error(message, exception);
    }
}
