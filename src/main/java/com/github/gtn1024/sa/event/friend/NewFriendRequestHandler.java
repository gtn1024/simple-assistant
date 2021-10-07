package com.github.gtn1024.sa.event.friend;

import com.github.gtn1024.sa.SimpleAssistant;
import com.github.gtn1024.sa.config.SuperAdminConfig;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import org.jetbrains.annotations.NotNull;

/**
 * 一个账号请求添加机器人为好友
 */
public class NewFriendRequestHandler extends SimpleListenerHost {
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
        SimpleAssistant.INSTANCE.getLogger().error(exception);
    }

    @EventHandler
    public void onMessage(@NotNull NewFriendRequestEvent event) {
        if (SuperAdminConfig.INSTANCE.getAutoAddFriend()) {
            event.accept();
        }
    }
}