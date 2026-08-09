package com.prikolz.justhelper.mixin;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Display;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Display.class)
public interface DisplayMixin {
    @Accessor("DATA_SCALE_ID")
    static EntityDataAccessor<Vector3f> DATA_SCALE_ID() { throw new AssertionError(); }

    @Accessor("DATA_TRANSLATION_ID")
    static EntityDataAccessor<Vector3f> DATA_TRANSLATION_ID() { throw new AssertionError(); }

    @Accessor("DATA_BRIGHTNESS_OVERRIDE_ID")
    static EntityDataAccessor<Integer> DATA_BRIGHTNESS_ID() { throw new AssertionError(); }

    @Accessor("DATA_BILLBOARD_RENDER_CONSTRAINTS_ID")
    static EntityDataAccessor<Byte> DATA_BILLBOARD_ID() { throw new AssertionError(); }

    @Accessor("DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID")
    static EntityDataAccessor<Integer> DATA_INTERPOLATION_DURATION_ID() { throw new AssertionError(); }
}
