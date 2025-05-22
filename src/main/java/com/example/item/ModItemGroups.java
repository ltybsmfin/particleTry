package com.example.item;

import com.example.ParticleTry;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

//    public static final RegistryKey<ItemGroup> PARTICLE_TRY = register("particle_try");
//
//    private static RegistryKey<ItemGroup> register(String id) {
//        return RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(ParticleTry.MOD_ID, id));
//
//    }
//
//    public static void registerModItemGroup(){
//        Registry.register(Registries.ITEM_GROUP, PARTICLE_TRY,
//                ItemGroup.create(ItemGroup.Row.TOP, 7).
//                                displayName(Text.translatable("itemGroup.particletry.particle_try")).
//                                icon(() -> new ItemStack(ModItems.ICE_ETHER)).
//                                entries((displayContext, entries) -> {
//                                    entries.add(ModItems.ICE_ETHER);
//                                }).build());
//        ParticleTry.LOGGER.info("Registering Item Groups");
//    }

    public static final ItemGroup PARTICLE_TRY = Registry.register(Registries.ITEM_GROUP, Identifier.of(ParticleTry.MOD_ID, "particle_try"),
            ItemGroup.create( null, -1).displayName(Text.translatable("itemGroup.particle_try"))
                    .icon(() -> new ItemStack(ModItems.ICE_ETHER))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.ICE_ETHER);
                        entries.add(Blocks.BRICKS);
                        entries.add(Items.DIAMOND);
                    }).build());

    public static void registerModItemGroups() {
        ParticleTry.LOGGER.info("Registering Item Groups");
    }
}
