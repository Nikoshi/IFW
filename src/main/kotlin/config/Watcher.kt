package net.informatiger.ifw.config

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
