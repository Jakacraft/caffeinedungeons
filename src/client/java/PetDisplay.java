import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import java.util.List;

public class PetDisplay implements ClientModInitializer {

    private static ItemStack selectedPet = ItemStack.EMPTY;
    private static String petName = "";
    private static int petLevel = 0;

    @Override
    public void onInitializeClient() {
        initialize();
    }

    public static void initialize() {
        HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (!selectedPet.isEmpty()) {
                drawPetHUD(matrices, client);
            }
        });

        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            for (Text line : lines) {
                String s = line.getString();
                if (s.contains("Selected")) {
                    selectedPet = stack;
                    petName = stack.getName().getString();
                    petLevel = parseLevelFromTooltip(lines);
                }
            }
        });
    }

    private static int parseLevelFromTooltip(List<Text> lines) {
        for (Text line : lines) {
            String s = line.getString();
            if (s.startsWith("Level ")) {
                try {
                    return Integer.parseInt(s.replace("Level ", "").trim());
                } catch (NumberFormatException ignored) {}
            }
        }
        return 0;
    }

    private static void drawPetHUD(MatrixStack matrices, MinecraftClient client) {
        int x = 10;
        int y = 10;
        TextRenderer renderer = client.textRenderer;

        renderer.draw(matrices, "Pet: " + petName, x, y, 0xFFFFFF);          // White text
        renderer.draw(matrices, "Level: " + petLevel, x, y + 10, 0xAAAAFF);  // Bluish text
        client.getItemRenderer().renderInGui(selectedPet, x + 60, y);        // Draw item icon
    }
}