package net.informatiger.ifw

import net.informatiger.ifw.config.launchAllWatchers
import net.informatiger.ifw.utils.ConfigUtils
import net.informatiger.ifw.utils.LogUtils

fun main() {
    val logger = LogUtils.getLogger("Main")
    val config = ConfigUtils.loadConfig()

    config?.let {
        launchAllWatchers(it.watchers)
    }

    logger.info("No more watchers running. Exiting...")
}
