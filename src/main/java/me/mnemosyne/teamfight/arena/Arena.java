package me.mnemosyne.teamfight.arena;

import lombok.Getter;
import lombok.Setter;
import me.mnemosyne.teamfight.util.LocationWrapper;

@Getter
@Setter
public class Arena {
    private String arenaName;
    private LocationWrapper location;
}
