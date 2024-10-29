package cn.hanye.fcitx5enhancer.forge;

import cn.hanye.fcitx5enhancer.Fcitx5enhancer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

@Mod(Fcitx5enhancer.MOD_ID)
public final class Fcitx5enhancerForge {
    public Fcitx5enhancerForge() {
        Path gameDir = FMLPaths.GAMEDIR.get();
        // Run our common setup.
        Fcitx5enhancer.init(gameDir);
    }
}
