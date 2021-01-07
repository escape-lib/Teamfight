package me.mnemosyne.teamfight.constant;

import me.mnemosyne.teamfight.util.ChatColourUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class GameInventory {
    protected ItemStack createInventoryItem(Material itemMaterial, String itemName, String... itemLore){
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

    protected ItemStack getHitPotionCounter(int hits, int potionsThrown, double potionAccuracy){
        String hitsStr = "&fHits: &7" + hits;
        String potionsThrownStr = "&fPotions Thrown: &7" + potionsThrown;
        String potionAccuracyStr = "&fPotion Accuracy: &7" + potionAccuracy * 100 + "%";

        return createInventoryItem(Material.PAPER, "&6&lStatistics", hitsStr, potionsThrownStr, potionAccuracyStr);
    }

    public Inventory getFightInventory(Player player, UUID fightUUID){
        Inventory inventory = Bukkit.createInventory(null, 54);
        inventory.setItem(50, getHitPotionCounter(482, 25, 0.92));

        return inventory;
    }
}
