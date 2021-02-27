package net.informatiger.ifw

import net.informatiger.ifw.config.Config
import net.informatiger.ifw.config.launchAllWatchers
import net.informatiger.ifw.utils.ConfigUtils
import net.informatiger.ifw.utils.LogUtils
import java.io.File

fun main(args: Array<String>) {

    val config: Config? =
        if (args.isNotEmpty() && args[0].isNotEmpty()) {
            val configFile = File(args[0])
            if (configFile.exists())
                ConfigUtils.loadConfig(configFile)
            else
                ConfigUtils.loadConfig(null)
        } else {
            ConfigUtils.loadConfig(null)
        }

    val logger = LogUtils.getLogger("Main")

    config?.let {
        launchAllWatchers(it.watchers)
    }

    logger.info("No more watchers running. Exiting...")
}
