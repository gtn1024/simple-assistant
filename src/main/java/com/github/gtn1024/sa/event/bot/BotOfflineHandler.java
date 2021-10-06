package com.github.gtn1024.sa.event.bot;

import com.github.gtn1024.sa.SimpleAssistant;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BotOfflineEvent;
import org.jetbrains.annotations.NotNull;

public class BotOfflineHandler extends SimpleListenerHost {
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
        SimpleAssistant.INSTANCE.getLogger().error(exception);
    }

    @EventHandler
    public void onMessage(@NotNull BotOfflineEvent.Active event) {
        // TODO: 主动离线
    }

    @EventHandler
    public void onMessage(@NotNull BotOfflineEvent.Force event) {
        // TODO: 被挤下线
    }

    @EventHandler
    public void onMessage(@NotNull BotOfflineEvent.Dropped event) {
        // TODO: 被服务器断开或因网络问题而掉线
    }

    @EventHandler
    public void onMessage(@NotNull BotOfflineEvent.RequireReconnect event) {
        // TODO: 服务器主动要求更换另一个服务器
    }
}
