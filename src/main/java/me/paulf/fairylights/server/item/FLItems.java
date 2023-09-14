package me.paulf.fairylights.server.item;

import me.paulf.fairylights.FairyLights;
import me.paulf.fairylights.server.block.FLBlocks;
import me.paulf.fairylights.server.block.LightBlock;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class FLItems {
    private FLItems() {}

    public static final DeferredRegister<Item> REG = DeferredRegister.create(ForgeRegistries.ITEMS, FairyLights.ID);

    public static final RegistryObject<ConnectionItem> HANGING_LIGHTS = REG.register("hanging_lights", () -> new HangingLightsConnectionItem(defaultProperties()));

    public static final RegistryObject<ConnectionItem> PENNANT_BUNTING = REG.register("pennant_bunting", () -> new PennantBuntingConnectionItem(defaultProperties()));

    public static final RegistryObject<ConnectionItem> TINSEL = REG.register("tinsel", () -> new TinselConnectionItem(defaultProperties()));

    public static final RegistryObject<ConnectionItem> LETTER_BUNTING = REG.register("letter_bunting", () -> new LetterBuntingConnectionItem(defaultProperties()));

    public static final RegistryObject<ConnectionItem> GARLAND = REG.register("garland", () -> new GarlandConnectionItem(defaultProperties()));

    public static final RegistryObject<LightItem> FAIRY_LIGHT = REG.register("fairy_light", FLItems.createColorLight(FLBlocks.FAIRY_LIGHT));

    public static final RegistryObject<LightItem> FLOWER_LIGHT = REG.register("flower_light", FLItems.createColorLight(FLBlocks.FLOWER_LIGHT));

    public static final RegistryObject<LightItem> CANDLE_LANTERN_LIGHT = REG.register("candle_lantern_light", FLItems.createColorLight(FLBlocks.CANDLE_LANTERN_LIGHT));

    public static final RegistryObject<LightItem> MOON_LIGHT = REG.register("moon_light", FLItems.createColorLight(FLBlocks.MOON_LIGHT));

    public static final RegistryObject<LightItem> ICICLE_LIGHTS = REG.register("icicle_lights", FLItems.createColorLight(FLBlocks.ICICLE_LIGHTS));

    public static final RegistryObject<LightItem> CANDLE_LANTERN = REG.register("candle_lantern", FLItems.createLight(FLBlocks.CANDLE_LANTERN, LightItem::new));

    public static final RegistryObject<Item> TRIANGLE_PENNANT = REG.register("triangle_pennant", () -> new PennantItem(defaultProperties()));

    public static final RegistryObject<Item> SPEARHEAD_PENNANT = REG.register("spearhead_pennant", () -> new PennantItem(defaultProperties()));

    public static final RegistryObject<Item> SWALLOWTAIL_PENNANT = REG.register("swallowtail_pennant", () -> new PennantItem(defaultProperties()));

    public static final RegistryObject<Item> SQUARE_PENNANT = REG.register("square_pennant", () -> new PennantItem(defaultProperties()));

    private static Item.Properties defaultProperties() {
        return new Item.Properties().tab(FairyLights.ITEM_GROUP);
    }

    private static Supplier<LightItem> createLight(final RegistryObject<LightBlock> block, final BiFunction<LightBlock, Item.Properties, LightItem> factory) {
        return () -> factory.apply(block.get(), defaultProperties().stacksTo(16));
    }

    private static Supplier<LightItem> createColorLight(final RegistryObject<LightBlock> block) {
        return createLight(block, ColorLightItem::new);
    }

    public static Stream<LightItem> lights() {
        return REG.getEntries().stream()
            .flatMap(RegistryObject::stream)
            .filter(LightItem.class::isInstance)
            .map(LightItem.class::cast);
    }
}
