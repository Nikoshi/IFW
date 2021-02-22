package net.informatiger.ifw.config

data class Config(val watchers: List<Watcher>)
data class Watcher(val id: String, val filesToWatch: List<String>, val intervalMs: Long, val commandToLaunch: String)
