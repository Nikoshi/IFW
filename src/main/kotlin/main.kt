package net.informatiger.ifw

import java.io.File
import com.sksamuel.hoplite.ConfigLoader
import net.informatiger.ifw.config.Config

fun main() {
    val config = loadConfig()

    config?.let {
        it.watchers.forEach { watcher ->
            println(watcher.id)
            watcher.filesToWatch.forEach { file ->
                println("\t$file")
            }
            println("\t${watcher.intervalMs}")
            println("\t${watcher.commandToLaunch}")
        }
    }
}