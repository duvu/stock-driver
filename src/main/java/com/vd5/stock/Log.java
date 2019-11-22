package com.vd5.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author beou on 11/20/19 22:00
 */
public class Log {
    private static final Logger _log = LoggerFactory.getLogger(RTConstants.PACKAGE_NAME);

    public static void info(String text) {
        _log.info("[>_] {}", text);
    }
    public static void info(String tag, String text) {
        _log.info("[{} >_] {}", tag, text);
    }

    public static void info(String tag, String text, Throwable th) {
        _log.info("[{} >_] {}", tag, text, th);
    }

    public static void warn(String tag, String text) {
        _log.warn("[{} >_] {}", tag, text);
    }

    public static void warn(String tag, String text, Throwable th) {
        _log.warn("[{} >_] {}", tag, text, th);
    }


    public static void error(String tag, String text) {
        _log.error("[{} >_] {}", tag, text);
    }

    public static void error(String tag, String text, Throwable th) {
        _log.error("[{} >_] {}", tag, text, th);
    }
}
