package net.informatiger.ifw.config

import net.informatiger.ifw.utils.HashUtils
import java.io.File

class CustomFile(
    val fileObject: File,
    var hash: String = "",
    var lastMod: Long = 0
) {
    /**
     * update file status
     *
     * @param useLastModTimestamp if enabled, we use the last modified timestamp.
     * Otherwise we use hashing (not implemented yet)
     */
    fun updateState(useLastModTimestamp: Boolean) {
        if (useLastModTimestamp) {
            this.lastMod = this.fileObject.lastModified()
        } else {
            this.hash = HashUtils.md5(this.fileObject)
        }
    }

    /**
     * check if the file was updated
     *
     * @param useLastModTimestamp if enabled, we use the last modified timestamp. Otherwise we use hashing (not implemented yet)
     */
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
