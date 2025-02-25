package cn.hanye.fcitx5enhancer.mixin;

import cn.xiaym.fcitx5.Fcitx5;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Unique
    private static final IntList PREVENT_KEYS = IntList.of(
            GLFW.GLFW_KEY_BACKSPACE,
            GLFW.GLFW_KEY_ESCAPE, GLFW.GLFW_KEY_ENTER,
            GLFW.GLFW_KEY_TAB,
            GLFW.GLFW_KEY_UP, GLFW.GLFW_KEY_DOWN,
            GLFW.GLFW_KEY_LEFT, GLFW.GLFW_KEY_RIGHT
    );

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (window != MinecraftClient.getInstance().getWindow().getHandle()) {
            return;
        }

        if (PREVENT_KEYS.contains(key) && Fcitx5.userTyping()) {
            ci.cancel();
        }
    }
}
