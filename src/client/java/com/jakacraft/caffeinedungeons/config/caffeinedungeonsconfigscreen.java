package com.jakacraft.caffeinedungeons.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class caffeinedungeonsconfigscreen {
    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.literal("Caffeine Dungeons Config"));

        builder.setSavingRunnable(caffeinedungeonsConfig::save);

        ConfigCategory general = builder.getOrCreateCategory(Text.literal("Pet Display"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder
                .startBooleanToggle(Text.literal("Enable Pet Hud"), caffeinedungeonsConfig.enablePetHud)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> caffeinedungeonsConfig.enablePetHud = newValue)
                .build());

        general.addEntry(entryBuilder
                .startIntField(Text.literal("Pet Hud X"), caffeinedungeonsConfig.PetHudX)
                .setDefaultValue(30)
                .setSaveConsumer(newValue -> caffeinedungeonsConfig.PetHudX = newValue)
                .build());

        general.addEntry(entryBuilder
                .startIntField(Text.literal("Pet Hud Y"), caffeinedungeonsConfig.PetHudY)
                .setDefaultValue(10)
                .setSaveConsumer(newValue -> caffeinedungeonsConfig.PetHudY = newValue)
                .build());

        general.addEntry(entryBuilder
                .startFloatField(Text.literal("Pet Hud Scale"), caffeinedungeonsConfig.PetHudScale)
                .setDefaultValue(1)
                .setSaveConsumer(newValue -> caffeinedungeonsConfig.PetHudScale = newValue)
                .build());

        return builder.build();
    }
}
