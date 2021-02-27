package net.informatiger.ifw.utils

import com.sksamuel.hoplite.ConfigLoader
import net.informatiger.ifw.config.Config

object ConfigUtils {
    private val logger = LogUtils.getLogger("ConfigLoader")

    fun loadConfig(): Config? {
        return try {
            ConfigLoader().loadConfigOrThrow("/app.conf")
        } catch (ex: Exception) {
            logger.error(ex.message)
            null
        }
    }
}
