package me.mnemosyne.teamfight.constant;

import me.mnemosyne.teamfight.util.ChatColourUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Inventory {
    private ItemStack createInventoryItem(Material itemMaterial, String itemName, String... itemLore){
        ItemStack itemStack = new ItemStack(itemMaterial, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> loreArray = new ArrayList<>();

        for(String s : itemLore){
            loreArray.add(ChatColourUtil.convert(s));
        }

        itemMeta.setDisplayName(ChatColourUtil.convert(itemName));
        itemMeta.setLore(loreArray);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack getHitPotionCounter(int hits, double potionAccuracy){
        String hitsStr = "&fHits: &7" + hits;
        String potionAccuracyStr = "&fPotion Accuracy: &7" + potionAccuracy * 100 + "%";

        return createInventoryItem(Material.PAPER, "&6&lStatistics", "", hitsStr, potionAccuracyStr);
    }
}
