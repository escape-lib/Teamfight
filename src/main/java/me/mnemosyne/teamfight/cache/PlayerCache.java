package me.mnemosyne.teamfight.cache;

import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class PlayerCache {
    @Setter private HashMap<UUID, String> uuidStringCacheMap;
    @Setter private HashMap<String, UUID> stringUUIDCacheMap;

    public PlayerCache(){
        this.uuidStringCacheMap = new HashMap<>();
        this.stringUUIDCacheMap = new HashMap<>();
    }

    public void addPlayerToCache(Player player){
        if(uuidStringCacheMap.containsKey(player.getUniqueId())
                || stringUUIDCacheMap.containsValue(player.getUniqueId())){


            /*checking if player has changed ign*/

            String cachePlayerName = getPlayerNameByUUID(player.getUniqueId());
            if(cachePlayerName.equals(player.getName())){ return; }

            uuidStringCacheMap.remove(player.getUniqueId());
            stringUUIDCacheMap.remove(cachePlayerName);
        }

        uuidStringCacheMap.put(player.getUniqueId(), player.getName());
        stringUUIDCacheMap.put(player.getName(), player.getUniqueId());
    }

    public String getPlayerNameByUUID(UUID uuid){
        return uuidStringCacheMap.get(uuid);
    }

    public UUID getPlayerUUIDByName(String playerName){
        return stringUUIDCacheMap.get(playerName);
    }

    public Collection<String> getPlayersNamesByUUIDs(Collection<UUID> uuids){
        Collection<String> playerNames = new ArrayList<>();
        for(UUID uuid : uuids){
            playerNames.add(uuidStringCacheMap.get(uuid));
        }

        if(playerNames.size() > 0){
            return playerNames;
        }

        return null;
    }

    public Collection<UUID> getPlayersUUIDsByNames(Collection<String> names){
        Collection<UUID> playerUUIDs = new ArrayList<>();
        for(String name : names){
            playerUUIDs.add(stringUUIDCacheMap.get(name));
        }

        if(playerUUIDs.size() > 0){
            return playerUUIDs;
        }

        return null;
    }
}
