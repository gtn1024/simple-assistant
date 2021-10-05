package com.github.gtn1024.sa.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

object MainConfig : AutoSavePluginConfig("MainConfig") {
    val admin by value<Long>()
}
