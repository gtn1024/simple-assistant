package com.github.gtn1024.sa.config

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

@ValueDescription("群配置")
object GroupConfig : AutoSavePluginConfig("GroupConfig") {
    @Serializable
    data class Group(
        var groupId: Long,
        var config: Config
    ) {
        @Serializable
        data class Config(
            var ownerIsAdmin: Boolean,
            var managerIsAdmin: Boolean,
            var otherAdmin: List<Long>?,
            var enabled: Boolean
        )

        override fun toString(): String {
            return "Group(groupId=$groupId)"
        }


    }

    val groups: MutableList<Group> by value()

}
