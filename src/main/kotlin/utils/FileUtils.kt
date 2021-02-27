package net.informatiger.ifw.utils

import net.informatiger.ifw.config.CustomFile

object FileUtils {
    private val logger = LogUtils.getLogger("FileUtils")

    fun checkIfFilesExist(filesToWatch: List<CustomFile>): Boolean {
        filesToWatch.forEach { file ->
            if (!file.fileObject.exists()) {
                logger.error("File ${file.fileObject.name} does not exist! Stopping watcher.")
                return false
            }
        }
        return true
    }
}
