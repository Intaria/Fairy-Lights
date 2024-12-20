package me.paulf.fairylights.client;

import me.paulf.fairylights.FairyLights;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class FLModelLayers {

    public static final ModelLayerLocation BOW = main("bow");
    public static final ModelLayerLocation GARLAND_RINGS = main("garland_rings");
    public static final ModelLayerLocation FAIRY_LIGHT = main("fairy_light");
    public static final ModelLayerLocation FLOWER_LIGHT = main("flower_light");
    public static final ModelLayerLocation CANDLE_LANTERN_LIGHT = main("color_candle_lantern");
    public static final ModelLayerLocation MOON_LIGHT = main("moon_light");
    public static final ModelLayerLocation ICICLE_LIGHTS_1 = main("icicle_lights_1");
    public static final ModelLayerLocation ICICLE_LIGHTS_2 = main("icicle_lights_2");
    public static final ModelLayerLocation ICICLE_LIGHTS_3 = main("icicle_lights_3");
    public static final ModelLayerLocation ICICLE_LIGHTS_4 = main("icicle_lights_4");
    public static final ModelLayerLocation CANDLE_LANTERN = main("candle_lantern");
    public static final ModelLayerLocation LETTER_WIRE = main("letter_wire");
    public static final ModelLayerLocation PENNANT_WIRE = main("pennant_wire");
    public static final ModelLayerLocation VINE_WIRE = main("vine_wire");
    public static final ModelLayerLocation LIGHTS_WIRE = main("lights_wire");

    private static ModelLayerLocation main(String name) {
        return layer(name, "main");
    }

    private static ModelLayerLocation layer(String name, String layer) {
        return new ModelLayerLocation(new ResourceLocation(FairyLights.ID, name), layer);
    }
}
