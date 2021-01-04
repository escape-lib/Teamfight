package me.mnemosyne.teamfight.util;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.World;

@Getter
@Setter
public class LocationWrapper {
    World worldName;
    double x;
    double y;
    double z;
}
