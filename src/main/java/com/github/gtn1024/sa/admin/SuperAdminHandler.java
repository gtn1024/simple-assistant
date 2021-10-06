package com.github.gtn1024.sa.admin;

import com.github.gtn1024.sa.SimpleAssistant;
import com.github.gtn1024.sa.config.SuperAdminConfig;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * 超级管理员事件处理
 */
public class SuperAdminHandler extends SimpleListenerHost {
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
        SimpleAssistant.INSTANCE.getLogger().error(exception);
    }

    @EventHandler
    public void onMessage(@NotNull GroupMessageEvent event) { // 可以抛出任何异常, 将在 handleException 处理
        if (!event.getMessage().toString().startsWith(SuperAdminConfig.INSTANCE.getSaCommandPrefix())) return;

        if (!isSa(event.getSender().getId())) {
            event.getGroup().sendMessage(new MessageChainBuilder()
                .append(new At(event.getSender().getId()))
                .append("，你不是超管，无法使用！")
                .build()
            );
            return;
        }

    }

    private boolean isSa(long id) {
        return SuperAdminConfig.INSTANCE.getSa() == id;
    }
}
