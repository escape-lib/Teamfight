package me.mnemosyne.teamfight.constant;

import lombok.Getter;
import me.mnemosyne.teamfight.util.ChatColourUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class Inventory {
    private ItemStack createInventoryItem(Material itemMaterial, String itemName, String... itemLore){
        ItemStack itemStack = new ItemStack(itemMaterial, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> loreArray = new ArrayList<>(Arrays.asList(itemLore));

        for(String s : loreArray){
            s = ChatColourUtil.convert(s);
        }

        itemMeta.setDisplayName(ChatColourUtil.convert(itemName));
        itemMeta.setLore(loreArray);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack getHitPotionCounter(int hits, double potionAccuracy){
        String hitsStr = "&fHits: &7" + Integer.toString(hits);
        String potionAccuracyStr = "&fPotion Accuracy: &7" + Double.toString(potionAccuracy * 100) + "%";

        ItemStack item = createInventoryItem(Material.PAPER, "&6&lStatistics", "", hitsStr, potionAccuracyStr);

        return item;
    }
}
