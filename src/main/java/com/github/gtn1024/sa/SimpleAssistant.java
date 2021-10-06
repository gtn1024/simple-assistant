package com.github.gtn1024.sa;

import com.github.gtn1024.sa.config.GroupConfig;
import com.github.gtn1024.sa.config.MainConfig;
import com.github.gtn1024.sa.event.bot.BotOfflineHandler;
import com.github.gtn1024.sa.event.message.GroupMessageHandler;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.GlobalEventChannel;

import java.util.List;

public final class SimpleAssistant extends JavaPlugin {
    public static final SimpleAssistant INSTANCE = new SimpleAssistant();

    private SimpleAssistant() {
        super(new JvmPluginDescriptionBuilder("com.github.gtn1024.simple-assistant", "1.0-SNAPSHOT")
            .name("simple-assistant")
            .author("gtn1024")
            .build());
    }

    @Override
    public void onEnable() {
        getLogger().verbose("Start to initialize plugin");
        initPlugin();
        getLogger().verbose("Start to register events");
        registerEvents();
        getLogger().info("Plugin loaded!");
        getLogger().info("Welcome to Simple Assistant!");
    }

    /**
     * 初始化插件
     */
    private void initPlugin() {
        getLogger().debug("Config folder: " + getConfigFolder());
        getLogger().debug("Data folder: " + getDataFolder());

        getLogger().debug("Start to load plugin configures");
        getLogger().debug("--> Start to load MainConfig");
        reloadPluginConfig(MainConfig.INSTANCE);
        getLogger().debug("--> MainConfig loaded!");
        getLogger().debug("--> Start to load GroupConfig");
        reloadPluginConfig(GroupConfig.INSTANCE);
        getLogger().debug("--> GroupConfig loaded!");

        getLogger().debug("Start to generate configures");
        generateConfig();
        getLogger().debug("--> Configures generated!");
    }

    /**
     * 将所有群加入配置文件
     */
    private void generateConfig() {
        final List<Bot> bots = Bot.getInstances();
        if (bots.size() != 1) {
            getLogger().error("未登录或登录的账号数量不为 1！");
            return;
        }

        final Bot bot = bots.get(0);
        final List<GroupConfig.Group> groups = GroupConfig.INSTANCE.getGroups();
        final ContactList<Group> remoteGroups = bot.getGroups();
        getLogger().debug("Groups in config: " + groups);
        getLogger().debug("Groups remote: " + remoteGroups);

        remoteGroups.forEach(it -> {
                if (!groupIsInList(it.getId(), groups)) {
                    // 配置文件不存在该群
                    getLogger().debug("----> Start to add " + it.getId() + " to group list");
                    GroupConfig.INSTANCE.getGroups().add(
                        new GroupConfig.Group(
                            it.getId(),
                            new GroupConfig.Group.Config(
                                true, true, null, false
                            )
                        )
                    );
                }
            }
        );
    }

    private boolean groupIsInList(long id, List<GroupConfig.Group> list) {
        getLogger().debug("--> Check whether " + id + " in group list");
        for (GroupConfig.Group group : list) {
            if (group.getGroupId() == id) {
                getLogger().debug("--> "+id + " is in group list");
                return true;
            }
        }
        getLogger().debug("--> "+id + " is not in group list");
        return false;
    }

    /**
     * 注册监听事件
     */
    private void registerEvents() {
        // Bot
        GlobalEventChannel.INSTANCE.registerListenerHost(new BotOfflineHandler());    // Bot 离线事件处理

        // 消息
        GlobalEventChannel.INSTANCE.registerListenerHost(new GroupMessageHandler());  // 群消息事件处理
    }
}
