package net.informatiger.ifw.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object LogUtils {
    fun getLogger(name: String): Logger = LoggerFactory.getLogger(name)
}
