package net.informatiger.ifw.utils

import java.io.IOException
import java.util.concurrent.TimeUnit

object CommandUtils {
    fun String.runCommand() {
        try {
            ProcessBuilder(*split(" ").toTypedArray())
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start()
                .waitFor(2, TimeUnit.MINUTES)
        } catch (ex: IOException) {
            println(ex.message)
        }
    }
}
