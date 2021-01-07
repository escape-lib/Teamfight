package me.mnemosyne.teamfight.user;

import lombok.Getter;
import me.mnemosyne.teamfight.log.Log;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class UserManager {
    @Getter private Collection<User> userList = new ArrayList<>();

    public User getUser(UUID playerUUID){
        for(User u : userList){
            if(u.getPlayerUUID().equals(playerUUID)) { return u; }
        }

        Log.log("&4[FATAL] Attempted to grab user object that doesn't exist!");
        return null;
    }

    public User getUser(Player player){
        return getUser(player.getUniqueId());
    }

    public void updateUser(User user){
        Collection<User> removed = new ArrayList<>();

        for(User u : userList) {
            if (u.getPlayerUUID().equals(user.getPlayerUUID())) {
                removed.add(u);
            }
        }

        userList.removeAll(removed);
        userList.add(user);
    }

    public void updateUser(User[] users){
        Collection<User> removed = new ArrayList<>();

        for(User inUser : users){
            for(User u : userList){
                if(u.getPlayerUUID().equals(inUser.getPlayerUUID())){
                    removed.add(u);
                }
            }
        }

        userList.removeAll(removed);
        userList.addAll(Arrays.asList(users));
    }

    public void removeUser(User user){
        if(userList.contains(user)){
            userList.remove(user);
        }
    }

    public void addUser(User user){
        if(!userList.contains(user)){
            userList.add(user);
        }
    }

    public boolean userExists(UUID uuid){
        for(User u : userList){
            if(u.getPlayerUUID().equals(uuid)) {
                return true;
            }
        }

        return false;
    }
}
