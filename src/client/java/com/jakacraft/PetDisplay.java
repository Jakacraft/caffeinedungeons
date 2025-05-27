package com.jakacraft;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jakacraft.caffeinedungeons.config.caffeinedungeonsConfig.*;

public class PetDisplay implements ClientModInitializer {

    private static ItemStack selectedPet = ItemStack.EMPTY;
    private static String petName = "";
    private static int petLevel = 0;
    private static boolean inPetsScreen = false;

    @Override
    public void onInitializeClient() {
        initialize();
    }

    private static void initialize() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen.getTitle().getString().equalsIgnoreCase("Pets")) {
                inPetsScreen = true;
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.currentScreen == null) {
                inPetsScreen = false;
            }
        });

        HudRenderCallback.EVENT.register((context, tickDeltaIgnored) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (!selectedPet.isEmpty()) {
                if (enablePetHud == true) {
                    drawPetHUD(context, client);
                }
            }
        });

        ItemTooltipCallback.EVENT.register((ItemStack stack, Item.TooltipContext tooltipContext, TooltipType type, List<Text> lines) -> {
            if (inPetsScreen) {
                for (Text line : lines) {
                    String s = line.getString();
                    if (s.contains("Selected!")) {
                        selectedPet = stack;
                        petName = stack.getName().getString();
                        petLevel = parseLevelFromTooltip(lines);
                    }
                }
            }
        });
    }

    private static final Pattern LEVEL_PATTERN = Pattern.compile("Level[: ]+(?<level>\\d{1,3})");

    private static int parseLevelFromTooltip(List<Text> lines) {
        for (Text line : lines) {
            String s = line.getString();
            Matcher matcher = LEVEL_PATTERN.matcher(s);
            if (matcher.find()) {
                try {
                    return Integer.parseInt(matcher.group("level"));
                } catch (NumberFormatException ignored) {}
            }
        }
        return 0;
    }

    private static void drawPetHUD(DrawContext context, MinecraftClient client) {
        int x = PetHudX;
        int y = PetHudY;
        float scale = PetHudScale;
        TextRenderer renderer = client.textRenderer;
        Text petNameText = selectedPet.getName();

        int nameColor = 0xFFFFFF;
        if (petNameText.getStyle().getColor() != null) {
            nameColor = petNameText.getStyle().getColor().getRgb();
        }

        int levelColor = 0xAAAAFF;

        context.getMatrices().push();
        context.getMatrices().translate(x / scale, y / scale, 0);
        context.getMatrices().scale(scale, scale, 1.0f);

        context.drawText(renderer, petNameText, 0, 0, nameColor, false);
        context.drawText(renderer, "Level: " + petLevel, 0, 10, levelColor, false);
        context.drawItem(selectedPet, -20, 0);

        context.getMatrices().pop();
    }
}