package com.github.fernthedev.gnick.spigot;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;

@NoArgsConstructor(access = AccessLevel.NONE)
public class HookManager {

    public static boolean isEssentials() {
        return Bukkit.getPluginManager().isPluginEnabled("Essentials");
    }
}
