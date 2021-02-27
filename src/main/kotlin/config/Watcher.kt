package net.informatiger.ifw.config

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.informatiger.ifw.utils.CommandUtils.runCommand
import net.informatiger.ifw.utils.FileUtils.checkIfFilesExist
import java.io.File

data class Watcher(
    val name: String,
    val filesToWatch: List<String>,
    val commandToLaunch: String,
    val intervalMs: Long = 1000L,
    val useLastModTimestamp: Boolean = true
)

fun Watcher.filesAsFileList(): List<CustomFile> {
    val fileList: MutableList<CustomFile> = mutableListOf()
    this.filesToWatch.forEach { file ->
        fileList.add(
            CustomFile(File(file)).also { updateFile ->
                updateFile.updateState(this.useLastModTimestamp)
            }
        )
    }

    return fileList
}

fun launchAllWatchers(watchers: List<Watcher>) {
    runBlocking {
        watchers.forEach { watcher ->
            launch {
                println("Watcher \"${watcher.name}\" starting!")
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
                println("File ${file.fileObject.name} is updated. Triggering command \"${watcher.commandToLaunch}\"")
                watcher.commandToLaunch.replace(
                    oldValue = "\$files",
                    newValue = watcher.filesToWatch.joinToString(separator = " ")
                ).runCommand()
            }
        }
        delay(watcher.intervalMs)
    }

    println("Watcher \"${watcher.name}\" stopped!")
}
