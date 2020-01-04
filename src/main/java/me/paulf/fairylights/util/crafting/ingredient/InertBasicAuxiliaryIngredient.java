package me.paulf.fairylights.util.crafting.ingredient;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class InertBasicAuxiliaryIngredient extends BasicAuxiliaryIngredient<Void> {
	public InertBasicAuxiliaryIngredient(Item item, boolean isRequired, int limit) {
		super(item, isRequired, limit);
	}

	public InertBasicAuxiliaryIngredient(Block block, boolean isRequired, int limit) {
		super(block, isRequired, limit);
	}

	public InertBasicAuxiliaryIngredient(ItemStack stack, boolean isRequired, int limit) {
		super(stack, isRequired, limit);
	}

	public InertBasicAuxiliaryIngredient(ItemStack stack) {
		super(stack, true, Integer.MAX_VALUE);
	}

	@Nullable
	@Override
	public final Void accumulator() {
		return null;
	}

	@Override
	public final void consume(Void v, ItemStack ingredient) {}

	@Override
	public final boolean finish(Void v, ItemStack stack) {
		return false;
	}
}