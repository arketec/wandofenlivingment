package arketec.wandofenlivingment.items;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;

public class CreativeWandOfEnlivingmentItem extends AbstractWandOfEnlivingmentItem {

    public CreativeWandOfEnlivingmentItem() {
        super(new Properties().durability(0).rarity(Rarity.EPIC));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return true;
    }

    @Override
    protected boolean isAllowedMending() {
        return true;
    }

    @Override
    protected boolean isAllowedBlockEntity(Block block) {
        return true;
    }

    @Override
    public net.minecraft.network.chat.Component getName(ItemStack stack) {
        String base = "Creative Wand of Enlivingment";
        long t = Util.getMillis();
        return rainbowize(base, t, 1800L, 0.08f);
    }

    private static MutableComponent rainbowize(String s, long millis, long periodMs, float hueStep) {
        float baseHue = (millis % periodMs) / (float) periodMs;
        MutableComponent out = net.minecraft.network.chat.Component.empty().withStyle(ChatFormatting.BOLD); // goady
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                out.append(" ");
                continue;
            }
            float hue = (baseHue + i * hueStep) % 1f;
            int rgb = java.awt.Color.HSBtoRGB(hue, 1f, 1f);
            out.append(net.minecraft.network.chat.Component.literal(String.valueOf(c))
                    .withStyle(
                            style -> style.withColor(TextColor.fromRgb(rgb)).withItalic(true) // extra flashy
                            ));
        }
        return out;
    }
}
