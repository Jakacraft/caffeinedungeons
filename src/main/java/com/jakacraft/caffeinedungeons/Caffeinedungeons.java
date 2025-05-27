package com.jakacraft.caffeinedungeons;

import com.jakacraft.caffeinedungeons.config.caffeinedungeonsConfig;
import net.fabricmc.api.ModInitializer;
import static com.mojang.text2speech.Narrator.LOGGER;

public class Caffeinedungeons implements ModInitializer {
    public static  final String caffeinedungeons = "caffeinedungeons";

    @Override
    public void onInitialize() {
        LOGGER.info("Caffeinating Dungeons");
        caffeinedungeonsConfig.load();
        LOGGER.info("Dungeons Caffeinated");
    }
}
