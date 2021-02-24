package net.informatiger.ifw.utils

import com.sksamuel.hoplite.ConfigLoader
import net.informatiger.ifw.config.Config

object ConfigUtils {
    fun loadConfig(): Config? {
        return try {
            ConfigLoader().loadConfigOrThrow("/app.conf")
        } catch (ex: Exception) {
            println(ex.message)
            null
        }
    }
}
