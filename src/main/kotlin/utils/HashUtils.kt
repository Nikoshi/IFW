package net.informatiger.ifw.utils

import java.io.File

object HashUtils {
    /**
     * Get md5 hash from [File]. This method is not implemented yet
     *
     * @throws NotImplementedError
     * @return Hash as a [String]
     */
    fun md5(file: File): String = throw NotImplementedError("${file.name}: Hashing is not implemented yet")
}
