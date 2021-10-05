package com.github.gtn1024.sa;

import com.github.gtn1024.sa.config.MainConfig;
import com.github.gtn1024.sa.event.bot.BotOfflineHandler;
import com.github.gtn1024.sa.event.message.GroupMessageHandler;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;

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

    private void initPlugin() {
        getLogger().debug("Config folder: " + getConfigFolder());
        getLogger().debug("Data folder: " + getDataFolder());

        reloadPluginConfig(MainConfig.INSTANCE);
    }

    private void registerEvents() {
        // Bot
        GlobalEventChannel.INSTANCE.registerListenerHost(new BotOfflineHandler());       // Bot 离线事件处理

        // 消息
        GlobalEventChannel.INSTANCE.registerListenerHost(new GroupMessageHandler());     // 群消息事件处理
    }
}
