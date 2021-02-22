package net.informatiger.ifw

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

fun loadConfig(): Config? {
    return try {
        ConfigLoader
        ConfigLoader().loadConfigOrThrow("/app.conf")
    }
    catch(ex: Exception) {
        println(ex.message)
        null
    }
}