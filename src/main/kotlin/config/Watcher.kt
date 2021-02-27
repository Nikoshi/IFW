package net.informatiger.ifw.config

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.informatiger.ifw.utils.CommandUtils.runCommand
import net.informatiger.ifw.utils.FileUtils.checkIfFilesExist
import net.informatiger.ifw.utils.LogUtils
import java.io.File

private val logger = LogUtils.getLogger("Watchers")

data class Watcher(
    val name: String,
    val filesToWatch: List<String>,
    val commandToLaunch: String,
    val intervalMs: Long = 1000L,
    val useLastModTimestamp: Boolean = true
)

/**
 * Return all files as [CustomFile] objects
 *
 * @return [List] of [CustomFile]
 */
fun Watcher.filesAsFileList(): List<CustomFile> {
    val fileList: MutableList<CustomFile> = mutableListOf()
    this.filesToWatch.forEach { file ->
        fileList.add(
            CustomFile(File(file)).also { updateFile ->
                // We want to set the current state
                updateFile.updateState(this.useLastModTimestamp)
            }
        )
    }

    return fileList
}

/**
 * Launch all [Watcher] objects in the given list
 *
 * @param watchers [List] of [Watcher]
 */
fun launchAllWatchers(watchers: List<Watcher>) {
    runBlocking {
        watchers.forEach { watcher ->
            launch {
                watch(watcher)
            }
        }
    }
}

/**
 * Launch [Watcher]
 *
 * @param watcher [Watcher]
 */
suspend fun watch(watcher: Watcher) {
    logger.info("${watcher.name} is started and watches for changes!")

    // We want the files as File objects
    val filesToWatch = watcher.filesAsFileList()

    // We want to be sure, that all files are available
    // If at least one file does not exist, stop watcher
    while (checkIfFilesExist(filesToWatch)) {
        filesToWatch.forEach { file ->
            if (file.isUpdated(watcher.useLastModTimestamp)) {
                // Trigger command
                logger.info("${watcher.name}: File ${file.fileObject.name} is updated")
		logger.info("${watcher.name}: Triggering command \"${watcher.commandToLaunch}\"")
                watcher.commandToLaunch.replace(
                    oldValue = "\$files",
                    newValue = watcher.filesToWatch.joinToString(separator = " ")
                ).runCommand()
            }
        }
        delay(watcher.intervalMs)
    }

    logger.info("${watcher.name} stopped!")
}
