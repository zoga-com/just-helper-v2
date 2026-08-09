package com.prikolz.justhelper.mixin;

import com.prikolz.justhelper.CommandBuffer;
import com.prikolz.justhelper.codespace.CodeSpace;
import com.prikolz.justhelper.JustHelperClient;
import com.prikolz.justhelper.util.Scheduler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    public void onTick(CallbackInfo ci) {
        if (CodeSpace.isActive()) CodeSpace.tick();
        CommandBuffer.tick(50);
        Scheduler.tick();
    }

    @Inject(method = "setLevel", at = @At("TAIL"))
    public void onSetLevel(ClientLevel clientLevel, CallbackInfo ci) {
        try {
            CodeSpace.initialize(clientLevel);
        } catch (Throwable t) {
            JustHelperClient.LOGGER.error("Develop world initialization error: {}", t.getMessage());
        }
    }
}
