package com.caspian.client.mixin.accessor;

import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see LightmapTextureManager
 */
@Mixin(LightmapTextureManager.class)
public interface AccessorLightmapTextureManager
{
    /**
     *
     * @param dirty
     */
    @Accessor("dirty")
    void setUpdateLightmap(boolean dirty);
}
