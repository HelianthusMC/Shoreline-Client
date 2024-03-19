package net.shoreline.client.impl.module.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.ListConfig;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.network.InteractBlockEvent;
import net.shoreline.client.impl.event.network.PacketEvent;

import java.util.List;

/**
 * @author linus
 * @since 1.0
 */
public class AntiInteractModule extends ToggleModule {
    //
    Config<List<Block>> blocksConfig = new ListConfig<>("Blocks",
            "The blocks to prevent player interact", Blocks.ENDER_CHEST,
            Blocks.ANVIL);

    /**
     *
     */
    public AntiInteractModule() {
        super("AntiInteract", "Prevents player from " +
                "interacting with certain objects", ModuleCategory.WORLD);
    }

    /**
     * @param event
     */
    @EventListener
    public void onInteractBlock(InteractBlockEvent event) {
        BlockPos pos = event.getHitResult().getBlockPos();
        BlockState state = mc.world.getBlockState(pos);
        if (cancelInteract(state.getBlock())) {
            event.cancel();
            // Managers.NETWORK.sendSequencedPacket(sequence -> new PlayerInteractBlockC2SPacket(
            //        event.getHand(), event.getHitResult(), sequence));
        }
    }

    /**
     * @param event
     */
    @EventListener
    public void onPacketOutbound(PacketEvent.Outbound event) {
        if (mc.player == null || mc.world == null) {
            return;
        }
        if (event.getPacket() instanceof PlayerInteractBlockC2SPacket packet) {
            BlockPos pos = packet.getBlockHitResult().getBlockPos();
            BlockState state = mc.world.getBlockState(pos);
            if (cancelInteract(state.getBlock())) {
                event.cancel();
            }
        }
    }

    private boolean cancelInteract(Block block) {
        return ((ListConfig<?>) blocksConfig).contains(block);
    }
}
