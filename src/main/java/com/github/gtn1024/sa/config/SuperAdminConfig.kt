package com.github.gtn1024.sa.config

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value
import net.mamoe.yamlkt.Comment

@ValueDescription("超级管理员配置")
object SuperAdminConfig : AutoSavePluginConfig("SuperAdminConfig") {
    @Serializable
    data class AutoJoinGroup(
        @Comment("允许超级管理员")
        var allowSa: Boolean,
        @Comment("允许陌生人")
        var allowStranger: Boolean,
        @Comment("允许以下人")
        var otherPeople: List<Long>
    )

    @Comment("超级管理员")
    val sa by value<Long>(10000)

    @Comment("管理前缀")
    val saCommandPrefix by value<String>("#")

    @Comment("自动添加好友")
    val autoAddFrient by value<Boolean>(false)

    @Comment("自动加群")
    val autoJoinGroup: AutoJoinGroup by value(
        AutoJoinGroup(
            allowSa = true,
            allowStranger = false,
            otherPeople = listOf(10000)
        )
    )


}
