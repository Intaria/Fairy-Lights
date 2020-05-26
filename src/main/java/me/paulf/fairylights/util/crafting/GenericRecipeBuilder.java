package me.paulf.fairylights.util.crafting;

import com.google.common.base.*;
import me.paulf.fairylights.util.crafting.ingredient.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;

import javax.annotation.*;
import java.util.Objects;
import java.util.*;

public final class GenericRecipeBuilder {
    private static final char EMPTY_SPACE = ' ';

    private final ResourceLocation name;

    private final IRecipeSerializer<GenericRecipe> serializer;

    @Nullable
    private ItemStack output;

    private char[] shape = new char[0];

    private int width;

    private int height;

    private final Map<Character, RegularIngredient> ingredients = new HashMap<>();

    private final List<AuxiliaryIngredient> auxiliaryIngredients = new ArrayList<>();

    public GenericRecipeBuilder(final ResourceLocation name, final IRecipeSerializer<GenericRecipe> serializer, final Item item) {
        this(name, serializer, new ItemStack(item));
    }

    public GenericRecipeBuilder(final ResourceLocation name, final IRecipeSerializer<GenericRecipe> serializer, final Block block) {
        this(name, serializer, new ItemStack(block));
    }

    public GenericRecipeBuilder(final ResourceLocation name, final IRecipeSerializer<GenericRecipe> serializer, final ItemStack output) {
        this(name, serializer);
        this.output = Objects.requireNonNull(output, "output");
    }

    public GenericRecipeBuilder(final ResourceLocation name, final IRecipeSerializer<GenericRecipe> serializer) {
        this.name = name;
        this.serializer = serializer;
    }

    public GenericRecipeBuilder withShape(final String... shape) {
        Objects.requireNonNull(shape, "shape");
        this.width = 0;
        this.height = 0;
        for (final String row : shape) {
            if (row != null && row.length() > this.width) {
                this.width = row.length();
            }
            this.height++;
        }
        final StringBuilder bob = new StringBuilder();
        for (final String row : shape) {
            bob.append(Strings.nullToEmpty(row));
            int trail = this.width - (row == null ? 0 : row.length());
            while (trail-- > 0) {
                bob.append(' ');
            }
        }
        this.shape = bob.toString().toCharArray();
        return this;
    }

    public GenericRecipeBuilder withOutput(final Item item) {
        return this.withOutput(Objects.requireNonNull(item, "item"), 1);
    }

    public GenericRecipeBuilder withOutput(final Item item, final int size) {
        return this.withOutput(new ItemStack(Objects.requireNonNull(item, "item"), size));
    }

    public GenericRecipeBuilder withOutput(final Block block) {
        return this.withOutput(Objects.requireNonNull(block, "block"), 1);
    }

    public GenericRecipeBuilder withOutput(final Block block, final int size) {
        return this.withOutput(new ItemStack(Objects.requireNonNull(block, "block"), size));
    }

    public GenericRecipeBuilder withOutput(final ItemStack output) {
        this.output = Objects.requireNonNull(output, "output");
        return this;
    }

    public GenericRecipeBuilder withIngredient(final char key, final Item item) {
        return this.withIngredient(key, new ItemStack(Objects.requireNonNull(item, "item"), 1));
    }

    public GenericRecipeBuilder withIngredient(final char key, final Block block) {
        return this.withIngredient(key, new ItemStack(Objects.requireNonNull(block, "block"), 1));
    }

    public GenericRecipeBuilder withIngredient(final char key, final ItemStack stack) {
        return this.withIngredient(key, new BasicRegularIngredient(Objects.requireNonNull(stack, "stack")));
    }

    public GenericRecipeBuilder withIngredient(final char key, final Tag<Item> tag) {
        return this.withIngredient(key, new OreRegularIngredient(tag));
    }

    public GenericRecipeBuilder withIngredient(final char key, final RegularIngredient ingredient) {
        this.ingredients.put(key, Objects.requireNonNull(ingredient, "ingredient"));
        return this;
    }

    public GenericRecipeBuilder withAnyIngredient(final char key, final Object... objects) {
        Objects.requireNonNull(objects, "objects");
        final List<RegularIngredient> ingredients = new ArrayList<>(objects.length);
        for (int i = 0; i < objects.length; i++) {
            ingredients.add(asIngredient(objects[i]));
        }
        this.ingredients.put(key, new ListRegularIngredient(ingredients));
        return this;
    }

    public GenericRecipeBuilder withAuxiliaryIngredient(final Item item) {
        return this.withAuxiliaryIngredient(new ItemStack(Objects.requireNonNull(item, "item")));
    }

    public GenericRecipeBuilder withAuxiliaryIngredient(final Item item, final boolean isRequired, final int limit) {
        return this.withAuxiliaryIngredient(new ItemStack(Objects.requireNonNull(item, "item")), isRequired, limit);
    }

    public GenericRecipeBuilder withAuxiliaryIngredient(final Block block) {
        return this.withAuxiliaryIngredient(new ItemStack(Objects.requireNonNull(block, "block")));
    }

    public GenericRecipeBuilder withAuxiliaryIngredient(final Block block, final boolean isRequired, final int limit) {
        return this.withAuxiliaryIngredient(new ItemStack(Objects.requireNonNull(block, "block")), isRequired, limit);
    }

    public GenericRecipeBuilder withAuxiliaryIngredient(final ItemStack stack) {
        return this.withAuxiliaryIngredient(Objects.requireNonNull(stack, "stack"), false, 1);
    }

    public GenericRecipeBuilder withAuxiliaryIngredient(final ItemStack stack, final boolean isRequired, final int limit) {
        return this.withAuxiliaryIngredient(new InertBasicAuxiliaryIngredient(Objects.requireNonNull(stack, "stack"), isRequired, limit));
    }

    public GenericRecipeBuilder withAuxiliaryIngredient(final Tag<Item> tag) {
        return this.withAuxiliaryIngredient(tag, false, 1);
    }

    public GenericRecipeBuilder withAuxiliaryIngredient(final Tag<Item> tag, final boolean isRequired, final int limit) {
        return this.withAuxiliaryIngredient(new InertOreAuxiliaryIngredient(tag, isRequired, limit));
    }

    public GenericRecipeBuilder withAuxiliaryIngredient(final AuxiliaryIngredient<?> ingredient) {
        this.auxiliaryIngredients.add(Objects.requireNonNull(ingredient, "ingredient"));
        return this;
    }

    public GenericRecipe build() {
        final RegularIngredient[] ingredients = new RegularIngredient[this.width * this.height];
        for (int i = 0; i < this.shape.length; i++) {
            final char key = this.shape[i];
            RegularIngredient ingredient = this.ingredients.get(key);
            if (ingredient == null) {
                if (key != EMPTY_SPACE) {
                    throw new IllegalArgumentException("An ingredient is missing for the shape, \"" + key + "\"");
                }
                ingredient = GenericRecipe.EMPTY;
            }
            ingredients[i] = ingredient;
        }
        final AuxiliaryIngredient<?>[] auxiliaryIngredients = this.auxiliaryIngredients.toArray(
            new AuxiliaryIngredient<?>[this.auxiliaryIngredients.size()]
        );
        return new GenericRecipe(this.name, this.serializer, this.output, ingredients, auxiliaryIngredients, this.width, this.height);
    }

    private static RegularIngredient asIngredient(final Object object) {
        if (object instanceof Item) {
            return new BasicRegularIngredient(new ItemStack((Item) object));
        }
        if (object instanceof Block) {
            return new BasicRegularIngredient(new ItemStack((Block) object));
        }
        if (object instanceof ItemStack) {
            return new BasicRegularIngredient((ItemStack) object);
        }
        if (object instanceof Tag) {
            return new OreRegularIngredient((Tag<Item>) object);
        }
        if (object instanceof RegularIngredient) {
            return (RegularIngredient) object;
        }
        throw new IllegalArgumentException("Unknown ingredient object: " + object);
    }
}
