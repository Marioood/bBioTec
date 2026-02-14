package net.marioood.bbiotec.entity;

import net.marioood.bbiotec.BBioTec;
import net.marioood.bbiotec.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BBioTec.MODID);

    public static final RegistryObject<BlockEntityType<BlockEvisceratorEntity>> EVISCERATOR =
            BLOCK_ENTITIES.register("eviscerator_be", () -> BlockEntityType.Builder.of(BlockEvisceratorEntity::new, ModBlocks.EVISCERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<BlockIrradiatorEntity>> IRRADIATOR =
            BLOCK_ENTITIES.register("irradiator_be", () -> BlockEntityType.Builder.of(BlockIrradiatorEntity::new, ModBlocks.IRRADIATOR.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
