package com.jakacraft.caffeinedungeons;

import com.jakacraft.caffeinedungeons.config.caffeinedungeonsconfigscreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class modmenuintergration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> caffeinedungeonsconfigscreen.create(parent);
    }
}

