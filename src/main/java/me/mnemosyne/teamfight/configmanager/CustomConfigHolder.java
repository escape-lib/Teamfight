package me.mnemosyne.teamfight.configmanager;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

@Getter
@Setter
public class CustomConfigHolder {
    private File file;
    private FileConfiguration config;
}
