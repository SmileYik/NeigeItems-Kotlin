package pers.neige.neigeitems.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.neige.neigeitems.action.catcher.ChatCatcher;
import pers.neige.neigeitems.action.catcher.SignCatcher;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class User {
    @NotNull
    private final UUID uuid;
    @NotNull
    private final ArrayDeque<ChatCatcher> chatCatchers = new ArrayDeque<>();
    @NotNull
    private final ArrayDeque<SignCatcher> signCatchers = new ArrayDeque<>();
    @NotNull
    private final Map<String, Object> metadata = new ConcurrentHashMap<>();
    @NotNull
    private final Map<String, Long> cooldown = new ConcurrentHashMap<>();

    public User(
            @NotNull UUID uuid
    ) {
        this.uuid = uuid;
    }

    @NotNull
    public UUID getUUID() {
        return uuid;
    }

    public void addChatCatcher(ChatCatcher catcher) {
        synchronized (chatCatchers) {
            chatCatchers.add(catcher);
        }
    }

    @Nullable
    public ChatCatcher pollChatCatcher() {
        synchronized (chatCatchers) {
            return chatCatchers.poll();
        }
    }

    public void addSignCatcher(SignCatcher catcher) {
        synchronized (signCatchers) {
            signCatchers.add(catcher);
        }
    }

    @Nullable
    public SignCatcher pollSignCatcher() {
        synchronized (signCatchers) {
            return signCatchers.poll();
        }
    }

    @NotNull
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * 检测冷却状态.<br>
     * 冷却完成则重新设置冷却并返回0.<br>
     * 冷却未完成则返回剩余时间.<br>
     *
     * @param key      冷却组ID
     * @param cooldown 冷却刷新时间
     * @return 剩余冷却时间
     */
    public long checkCooldown(String key, Long cooldown) {
        if (cooldown <= 0) return 0;
        long time = System.currentTimeMillis();
        long lastTime = this.cooldown.getOrDefault(key, 0L);
        if (lastTime > time) {
            return lastTime - time;
        } else {
            this.cooldown.put(key, time + cooldown);
            return 0;
        }
    }

    /**
     * 设置进入冷却状态.<br>
     *
     * @param key      冷却组ID
     * @param cooldown 冷却刷新时间
     */
    public void setCooldown(String key, Long cooldown) {
        long time = System.currentTimeMillis();
        this.cooldown.put(key, time + cooldown);
    }
}
