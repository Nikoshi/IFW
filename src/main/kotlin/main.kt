package net.informatiger.ifw

import net.informatiger.ifw.config.launchAllWatchers
import net.informatiger.ifw.utils.ConfigUtils

fun main() {
    val config = ConfigUtils.loadConfig()

    config?.let {
        launchAllWatchers(it.watchers)
    }

    println("No more watchers running. Exiting...")
}
