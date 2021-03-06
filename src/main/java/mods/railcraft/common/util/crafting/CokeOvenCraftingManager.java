/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/
package mods.railcraft.common.util.crafting;

import mods.railcraft.api.crafting.ICokeOvenCraftingManager;
import mods.railcraft.api.crafting.ICokeOvenRecipe;
import mods.railcraft.common.fluids.FluidTools;
import mods.railcraft.common.util.misc.Game;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CokeOvenCraftingManager implements ICokeOvenCraftingManager {

    private final List<ICokeOvenRecipe> recipes = new ArrayList<>();

    private static final CokeOvenCraftingManager INSTANCE = new CokeOvenCraftingManager();

    public static CokeOvenCraftingManager getInstance() {
        return INSTANCE;
    }

    private CokeOvenCraftingManager() {
    }

    @Override
    public Collection<ICokeOvenRecipe> getRecipes() {
        return recipes;
    }

    @Override
    public ICokeOvenRecipe create(Ingredient input, ItemStack output, @Nullable FluidStack liquidOutput, int cookTime) {
        return new ICokeOvenRecipe() {
            @Override
            public Ingredient getInput() {
                return input;
            }

            @Override
            public int getCookTime() {
                return cookTime;
            }

            @Nullable
            @Override
            public FluidStack getFluidOutput() {
                return FluidTools.copy(liquidOutput);
            }

            @Override
            public ItemStack getOutput() {
                return output.copy();
            }
        };
    }

    @Override
    public void addRecipe(ICokeOvenRecipe recipe) {
        if (recipe.getInput().test(ItemStack.EMPTY)) {
            Game.logTrace(Level.ERROR, 10, "Tried to register an invalid coke oven recipe");
            return;
        }
        recipes.add(recipe);
    }

    @Override
    @Nullable
    public ICokeOvenRecipe getRecipe(ItemStack input) {
        for (ICokeOvenRecipe r : recipes) {
            if (r.getInput().test(input)) {
                return r;
            }
        }
        return null;
    }

}
