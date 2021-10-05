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
        SimpleAssistant.INSTANCE.getLogger().error("Caught exception in BotOfflineHandler: ");
        SimpleAssistant.INSTANCE.getLogger().error(exception);
    }

    @EventHandler
    public void onMessage(@NotNull BotOfflineEvent event) throws Exception { // 可以抛出任何异常, 将在 handleException 处理

    }
}
