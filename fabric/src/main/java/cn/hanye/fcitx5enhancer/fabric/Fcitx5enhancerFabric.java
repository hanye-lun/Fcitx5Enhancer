package cn.hanye.fcitx5enhancer.fabric;

import cn.hanye.fcitx5enhancer.Fcitx5enhancer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public final class Fcitx5enhancerFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        Path gameDir = FabricLoader.getInstance().getGameDir();

        // Run our common setup.
        Fcitx5enhancer.init(gameDir);
    }
}
