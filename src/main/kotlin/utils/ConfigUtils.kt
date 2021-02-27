package net.informatiger.ifw.utils

import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.PropertySource
import net.informatiger.ifw.config.Config
import java.io.File

object ConfigUtils {
    private val logger = LogUtils.getLogger("ConfigLoader")

    /**
     * Load config (at the moment) from the resources
     *
     * @return [Config] or **null**
     */
    fun loadConfig(configFile: File?): Config? {
        return try {
            if (configFile != null) {
                logger.info("Loading config from $configFile")
                ConfigLoader.Builder()
                    .addSource(PropertySource.file(configFile, false))
                    // .addFileExtensionMapping("data", HoconParser)
                    .build()
                    .loadConfigOrThrow()
            } else {
                logger.info("Using default config")
                ConfigLoader().loadConfigOrThrow("/app.conf")
            }
        } catch (ex: Exception) {
            logger.error(ex.message)
            null
        }
    }
}
