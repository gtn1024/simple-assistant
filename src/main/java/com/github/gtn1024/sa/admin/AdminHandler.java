package com.github.gtn1024.sa.admin;

import com.github.gtn1024.sa.SimpleAssistant;
import com.github.gtn1024.sa.config.GroupConfig;
import com.github.gtn1024.sa.config.SuperAdminConfig;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdminHandler extends SimpleListenerHost {
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 处理事件处理时抛出的异常
        SimpleAssistant.INSTANCE.getLogger().error(exception);
    }

    @EventHandler
    public void onMessage(@NotNull GroupMessageEvent event) {
        final GroupConfig.Group config = getConfig(event.getGroup().getId());
        if (config == null) {
            SimpleAssistant.INSTANCE.getLogger().error("群 " + event.getGroup().getId() + " 配置文件不存在！");
            return;
        }
        final String msg = event.getMessage().toString();
        if (!msg.trim().startsWith(config.getCommandPrefix().trim())) {
            return;
        }
        if (!isAdmin(event, event.getSender().getId(), config)) {
            event.getGroup().sendMessage(new MessageChainBuilder()
                .append(new At(event.getSender().getId()))
                .append("，你不是管理，无法使用！")
                .build()
            );
            return;
        }
    }

    private boolean isAdmin(GroupMessageEvent event, long id, GroupConfig.Group config) {
        if (id == SuperAdminConfig.INSTANCE.getSa()) {
            return true;
        }
        if ((config.getConfig().getOwnerIsAdmin()) && (id == event.getGroup().getOwner().getId())) {
            return true;
        }
        if ((config.getConfig().getManagerIsAdmin())
            && (event.getPermission().getLevel() == MemberPermission.ADMINISTRATOR.getLevel())) {
            return true;
        }
        final List<Long> otherAdmin = config.getConfig().getOtherAdmin();
        if (otherAdmin != null) {
            for (long admin : otherAdmin) {
                if (id == admin) {
                    return true;
                }
            }
        }
        return false;
    }

    private GroupConfig.Group getConfig(long id) {
        int i = getIndex(id, GroupConfig.INSTANCE.getGroups());
        if (i == -1) {
            return null;
        }
        return GroupConfig.INSTANCE.getGroups().get(i);
    }

    private int getIndex(long id, List<GroupConfig.Group> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getGroupId() == id) {
                return i;
            }
        }
        return -1;
    }
}
