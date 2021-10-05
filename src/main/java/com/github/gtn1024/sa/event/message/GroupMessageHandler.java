package com.github.gtn1024.sa.event.message;

import com.github.gtn1024.sa.SimpleAssistant;
import com.github.gtn1024.sa.config.MainConfig;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import org.jetbrains.annotations.NotNull;

public class GroupMessageHandler extends SimpleListenerHost {
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
        SimpleAssistant.INSTANCE.getLogger().error(exception);
    }

    @EventHandler
    public void onMessage(@NotNull GroupMessageEvent event) { // 可以抛出任何异常, 将在 handleException 处理
        if (event.getSender().getId() != MainConfig.INSTANCE.getAdmin()) {
            event.getGroup().sendMessage(
                new MessageChainBuilder()
                    .append("我只听")
                    .append(new At(MainConfig.INSTANCE.getAdmin()))
                    .append("的话")
                    .build()
            );
            return;
        }

        event.getGroup().sendMessage(
            new MessageChainBuilder()
                .append(new PlainText("你好，"))
                .append(new At(event.getSender().getId())).append("\n")
                .append("你刚才发送了以下内容：\n")
                .append(event.getMessage().contentToString())
                .build()
        );
    }
}
