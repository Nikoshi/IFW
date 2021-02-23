package net.informatiger.ifw

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import net.informatiger.ifw.config.CustomFile
import net.informatiger.ifw.config.Watcher
import net.informatiger.ifw.config.filesAsFileList
import net.informatiger.ifw.utils.ConfigUtils

fun main() {
    val config = ConfigUtils.loadConfig()

    config?.let {
        launchAllWatchers(it.watchers)
    }

    println("No more watchers running. Exiting...")
}

@Suppress("DeferredResultUnused")
fun launchAllWatchers(watchers: List<Watcher>) {
    runBlocking {
        watchers.forEach { watcher ->
            async {
                println("Watcher ${watcher.name} starting!")
                launchWatcher(watcher)
            }
        }
    }
}

suspend fun launchWatcher(watcher: Watcher) {
    // We want the files as File objects
    val filesToWatch = watcher.filesAsFileList()

    // We want to be sure, that all files are available
    // If at least one file does not exist, stop watcher
    while (checkIfFilesExist(filesToWatch)) {

        filesToWatch.forEach { file ->
            if (file.isUpdated(watcher.useLastModTimestamp)) {
                // Trigger command
                println("File ${file.fileObject.name} is updated... triggering command ${watcher.commandToLaunch}")
            }
        }
        delay(watcher.intervalMs)
    }

    println("Watcher ${watcher.name} stopped!")
}

private fun checkIfFilesExist(filesToWatch: List<CustomFile>): Boolean {
    filesToWatch.forEach { file ->
        if (!file.fileObject.exists()) {
            println("File ${file.fileObject.name} does not exist! Stopping watcher.")
            return false
        }
    }
    return true
}
