package com.github.gtn1024.sa.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

@ValueDescription("超级管理员配置")
object SuperAdminConfig : AutoSavePluginConfig("SuperAdminConfig") {
    val sa by value<Long>(10000)
    val saCommandPrefix by value<String>("#")
}
