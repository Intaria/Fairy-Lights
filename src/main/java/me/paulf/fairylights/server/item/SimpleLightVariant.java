package me.paulf.fairylights.server.item;

import me.paulf.fairylights.server.feature.light.BrightnessLightBehavior;
import me.paulf.fairylights.server.feature.light.ColorChangingBehavior;
import me.paulf.fairylights.server.feature.light.ColorLightBehavior;
import me.paulf.fairylights.server.feature.light.CompositeBehavior;
import me.paulf.fairylights.server.feature.light.DefaultBrightnessBehavior;
import me.paulf.fairylights.server.feature.light.FixedColorBehavior;
import me.paulf.fairylights.server.feature.light.LightBehavior;
import me.paulf.fairylights.server.feature.light.MultiLightBehavior;
import me.paulf.fairylights.server.feature.light.StandardLightBehavior;
import me.paulf.fairylights.server.feature.light.TorchLightBehavior;
import me.paulf.fairylights.server.feature.light.TwinkleBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.function.Function;

public class SimpleLightVariant<T extends LightBehavior> implements LightVariant<T> {
    public static final LightVariant<StandardLightBehavior> FAIRY_LIGHT = new SimpleLightVariant<>(true, 1.0F, new AABB(-0.138D, -0.138D, -0.138D, 0.138D, 0.138D, 0.138D), 0.044D, SimpleLightVariant::standardBehavior, true);
    public static final LightVariant<StandardLightBehavior> FLOWER_LIGHT = new SimpleLightVariant<>(true, 1.0F, new AABB(-0.483D, -0.227D, -0.483D, 0.436D, 0.185D, 0.436D), 0.069D, SimpleLightVariant::standardBehavior, true);
    public static final LightVariant<StandardLightBehavior> CANDLE_LANTERN_LIGHT = new SimpleLightVariant<>(false, 1.5F, new AABB(-0.198D, -0.531D, -0.198D, 0.198D, 0.091D, 0.198D), 0.000D, SimpleLightVariant::standardBehavior);
    public static final LightVariant<StandardLightBehavior> MOON_LIGHT = new SimpleLightVariant<>(true, 1.0F, new AABB(-0.200D, -0.669D, -0.144D, 0.300D, 0.063D, 0.144D), 0.044D, SimpleLightVariant::standardBehavior, true);
    public static final LightVariant<MultiLightBehavior> ICICLE_LIGHTS = new SimpleLightVariant<>(false, 0.625F, new AABB(-0.264D, -1.032D, -0.253D, 0.276D, 0.091D, 0.266D), 0.012D, stack -> MultiLightBehavior.create(4, () -> standardBehavior(stack)));
    public static final LightVariant<BrightnessLightBehavior> CANDLE_LANTERN = new SimpleLightVariant<>(false, 1.5F, new AABB(-0.198D, -0.531D, -0.198D, 0.198D, 0.091D, 0.198D), 0.000D, stack -> new TorchLightBehavior(0.2D));
    
    private final boolean parallelsCord;

    private final float spacing;

    private final AABB bounds;

    private final double floorOffset;

    private final Function<ItemStack, T> behaviorFactory;

    private final boolean orientable;

    SimpleLightVariant(final boolean parallelsCord, final float spacing, final AABB bounds, final double floorOffset, final Function<ItemStack, T> behaviorFactory) {
        this(parallelsCord, spacing, bounds, floorOffset, behaviorFactory, false);
    }

    SimpleLightVariant(final boolean parallelsCord, final float spacing, final AABB bounds, final double floorOffset, final Function<ItemStack, T> behaviorFactory, final boolean orientable) {
        this.parallelsCord = parallelsCord;
        this.spacing = spacing;
        this.bounds = bounds;
        this.floorOffset = floorOffset;
        this.behaviorFactory = behaviorFactory;
        this.orientable = orientable;
    }

    @Override
    public boolean parallelsCord() {
        return this.parallelsCord;
    }

    @Override
    public float getSpacing() {
        return this.spacing;
    }

    @Override
    public AABB getBounds() {
        return this.bounds;
    }

    @Override
    public double getFloorOffset() {
        return this.floorOffset;
    }

    @Override
    public T createBehavior(final ItemStack stack) {
        return this.behaviorFactory.apply(stack);
    }

    @Override
    public boolean isOrientable() {
        return this.orientable;
    }

    private static StandardLightBehavior standardBehavior(final ItemStack stack) {
        final BrightnessLightBehavior brightness;
        if (TwinkleBehavior.exists(stack)) {
            brightness = new TwinkleBehavior(0.05F, 40);
        } else {
            brightness = new DefaultBrightnessBehavior();
        }
        final ColorLightBehavior color;
        if (ColorChangingBehavior.exists(stack)) {
            color = ColorChangingBehavior.create(stack);
        } else {
            color = FixedColorBehavior.create(stack);
        }
        return new CompositeBehavior(brightness, color);
    }
}
