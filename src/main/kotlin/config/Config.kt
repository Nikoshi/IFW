package net.informatiger.ifw.config

import net.informatiger.ifw.utils.HashUtils
import java.io.File

data class Config(val watchers: List<Watcher>)

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

class CustomFile(
    val fileObject: File,
    var hash: String = "",
    var lastMod: Long = 0
) {
    fun updateState(useLastModTimestamp: Boolean) {
        if (useLastModTimestamp) {
            this.lastMod = this.fileObject.lastModified()
        } else {
            this.hash = HashUtils.md5(this.fileObject)
        }
    }

    fun isUpdated(useLastModTimestamp: Boolean): Boolean {
        return if (useLastModTimestamp) {
            val newLastMod = this.fileObject.lastModified()

            // if the new lastMod is not equal to the last one, file is propably updated
            (this.lastMod != newLastMod).also {
                this.lastMod = newLastMod
            }
        } else {
            this.hash = HashUtils.md5(this.fileObject)

            // file is not updated (hashing currently not supported)
            false
        }
    }
}
