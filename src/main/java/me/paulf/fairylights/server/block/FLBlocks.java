package me.paulf.fairylights.server.block;

import me.paulf.fairylights.FairyLights;
import me.paulf.fairylights.server.item.LightVariant;
import me.paulf.fairylights.server.item.SimpleLightVariant;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public final class FLBlocks {
    private FLBlocks() {}

    public static final DeferredRegister<Block> REG = DeferredRegister.create(ForgeRegistries.BLOCKS, FairyLights.ID);

    public static final RegistryObject<FastenerBlock> FASTENER = REG.register("fastener", () -> new FastenerBlock(Block.Properties.of(Material.DECORATION)));

    public static final RegistryObject<LightBlock> FAIRY_LIGHT = REG.register("fairy_light", FLBlocks.createLight(SimpleLightVariant.FAIRY_LIGHT));

    public static final RegistryObject<LightBlock> FLOWER_LIGHT = REG.register("flower_light", FLBlocks.createLight(SimpleLightVariant.FLOWER_LIGHT));

    public static final RegistryObject<LightBlock> CANDLE_LANTERN_LIGHT = REG.register("candle_lantern_light", FLBlocks.createLight(SimpleLightVariant.CANDLE_LANTERN_LIGHT));

    public static final RegistryObject<LightBlock> MOON_LIGHT = REG.register("moon_light", FLBlocks.createLight(SimpleLightVariant.MOON_LIGHT));

    public static final RegistryObject<LightBlock> ICICLE_LIGHTS = REG.register("icicle_lights", FLBlocks.createLight(SimpleLightVariant.ICICLE_LIGHTS));

    public static final RegistryObject<LightBlock> CANDLE_LANTERN = REG.register("candle_lantern", FLBlocks.createLight(SimpleLightVariant.CANDLE_LANTERN));

    private static Supplier<LightBlock> createLight(final LightVariant<?> variant) {
        return createLight(variant, LightBlock::new);
    }

    private static Supplier<LightBlock> createLight(final LightVariant<?> variant, final BiFunction<Block.Properties, LightVariant<?>, LightBlock> factory) {
        return () -> factory.apply(Block.Properties.of(Material.DECORATION).lightLevel(state -> state.getValue(LightBlock.LIT) ? 15 : 0).noCollission(), variant);
    }
}
