package net.informatiger.ifw.utils

import net.informatiger.ifw.config.CustomFile

object FileUtils {
    fun checkIfFilesExist(filesToWatch: List<CustomFile>): Boolean {
        filesToWatch.forEach { file ->
            if (!file.fileObject.exists()) {
                println("File ${file.fileObject.name} does not exist! Stopping watcher.")
                return false
            }
        }
        return true
    }
}
