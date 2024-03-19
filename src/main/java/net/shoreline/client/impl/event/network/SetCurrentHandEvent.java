package net.shoreline.client.impl.event.network;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.shoreline.client.api.event.Event;
import net.shoreline.client.mixin.network.MixinClientPlayerEntity;
import net.shoreline.client.util.Globals;

/**
 * @author linus
 * @see MixinClientPlayerEntity
 * @since 1.0
 */
public class SetCurrentHandEvent extends Event implements Globals {
    //
    private final Hand hand;

    public SetCurrentHandEvent(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }

    public ItemStack getStackInHand() {
        return mc.player.getStackInHand(hand);
    }
}
