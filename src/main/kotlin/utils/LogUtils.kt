package net.informatiger.ifw.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object LogUtils {
    /**
     * Get a logger with the given [name] from the [LoggerFactory]
     *
     * @param name Name of the logger
     * @return [Logger]
     */
    fun getLogger(name: String): Logger = LoggerFactory.getLogger(name)
}
