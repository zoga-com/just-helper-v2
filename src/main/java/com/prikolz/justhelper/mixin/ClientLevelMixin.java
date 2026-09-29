package com.prikolz.justhelper.mixin;

import com.prikolz.justhelper.codespace.CodeSpace;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.entity.TransientEntitySectionManager;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin {
    @Final
    @Shadow
    private TransientEntitySectionManager<Entity> entityStorage;

    @Shadow
    public abstract @Nullable Entity getEntity(int id);

    @Inject(method = "onBlockEntityAdded", at = @At("HEAD"))
    private void onBlockEntityAdded(BlockEntity blockEntity, CallbackInfo ci) {
        CodeSpace.addSign(blockEntity);
    }
//
//    /**
//     * @author Zoga_com
//     * @reason РАЗРАБОТЧИКИ МАЙНКРАФТ НАСРАЛИ
//     */
//    @Overwrite
//    public void addEntity(Entity entity) {
//        this.entityStorage.addEntity(entity);
//    }
}
