package com.github.gtn1024.sa.event.group.member;

import com.github.gtn1024.sa.SimpleAssistant;
import com.github.gtn1024.sa.config.SuperAdminConfig;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 机器人被邀请加入群
 */
public class BotInvitedJoinGroupRequestHandler extends SimpleListenerHost {
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
        SimpleAssistant.INSTANCE.getLogger().error(exception);
    }

    @EventHandler
    public void onMessage(@NotNull BotInvitedJoinGroupRequestEvent event) {
        final long id = event.getInvitorId();
        if (isAllowed(id)) {
            event.accept();
        }
    }

    private boolean isAllowed(long id) {
        if (SuperAdminConfig.INSTANCE.getAutoJoinGroup().getAllowStranger()) {
            return true;
        }
        if ((SuperAdminConfig.INSTANCE.getAutoJoinGroup().getAllowSa()) && SuperAdminConfig.INSTANCE.getSa() == id) {
            return true;
        }
        final List<Long> list = SuperAdminConfig.INSTANCE.getAutoJoinGroup().getOtherPeople();
        if (list != null) {
            for (long o : list) {
                if (id == o) return true;
            }
        }
        return false;
    }
}
