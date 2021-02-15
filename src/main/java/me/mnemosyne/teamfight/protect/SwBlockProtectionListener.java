package me.mnemosyne.teamfight.protect;

import me.mnemosyne.teamfight.util.ChatColourUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

/*
        .'.'o00000000000000000000000OO0000000OOOOOOOOOOOOOOOOOOOOOOOkkkkkxxdoll,;O0kdoloxkkxxxxxddddooooooodxkkOOOOOOOOOOOOOOOOOOOOOOOOkkkkkkkkkkkkk
        .'..cO000000000000000000000000000OOOOOOOOOOOOOOOOOOOOOOOOOOkkkkkkxxdolc',k0kdlldxkxxxxxxdddoooooooodxkkkOkOOOOOOOOOOOOOOOOOOOOOOkkkkkkkkkkkk
        ..'.:k0000000000000OO00000000OOOOOOOOOOOOOOOOOOOOOOOOOOOOkkkkkkkkxxdoc;.'dxdoloxkxxxxxddddooooooolodxkkkkkkOOOOOOOOOOOOOOOOOOOkkkkkkkkkkkkkO
        ..'.,x0000000000000000000OOOOOOOOOOOOOOOOOOOOOOOOOOOOOkkkkkkkkkkxxdoo:,.'oxolloxxxxxxxdddooooooollodxkkkkkkkOOOOOOOOOOOOOOOOkkkkkkkkkkkkkkkO
        ..'.'d000000000000000000000OOOOOOOOOOOOOOOOOOOOOOOkkkOOOOOkkkkkkxxdol:'.'oxoccdxxxxxdddddoooolllllloxkkkkOkOOOOOOOOOOOOOOkkkkkkkkkkkkkkkkkkk
        ....'lO0O0000000000000OO00OOOOOOOOOOOOOOOOOOOOOOOkOOOOkkkkkkkkkxxxdol;..,dxoccdxxxxdddddooooolllllloxxkkOOOOOOOOOOOOOOOOkkkkkkkkkkkkkkkkkkkk
        .....:k0000000000000OO0OOOOOOOOOOOOOOOOOOOOOOOOOkOOkkkkkkkkkkkxxxxdll,..,dxo;:dxxddddddoooooolllllccoxkkkkOOOOOOOOOOOOOkkkkkkkkkkkkkkkkkkkkk
        .....;x0O000O0000000OOOOOOOOOOOOOOOOOOOOOOOOOOOkkkkkkkkkkkkkkxxxxddlc,..:xxl,cdddddddoollllcc:;,,'..',:cldxkkkkkkkOkkkkkkkkkkkkkkkkkkkkkkkkk
        ...'.,d0000000000000OOOOOOOOOOOOOOOOOOOOOOOOOOOkkkkkkkkkkkkxkxxxxdolc'..cxxc,:cc::;,,'''..................'',coxkkkkkkkkkkkkkkkkkkkkkkkkkkkk
        ...'''lO00000000OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOkkkkkkkkkkkkkxxxxxxdolc. .co:'............................ .......;lxkkkkkkkkkkkkkkkkkkkkkkkkk
        ....'.:k0OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOkkkkkkkkkkkkkxxxxxxxdol:...'........................   ..............,lxkkkkkkkkkkkkkkkkkkkkkkk
        ......;x0OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOkkkkkkkkkkkkkkkkxxxxxdol;........................ .     ................,dkkkkkxxxxxxxxxkkkkkkkkk
        ....'.,dOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOkkkkkkkkkkkkkkkkxxxxxdddc,.......................    .  ...................;lddddxxxxxxxxxxxxkkkkkk
        ....'.'lOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOkkkkkkkkkkkkkkkkkkxxxxddo:'.......................     ..........................',:odxxxxxxxxxxxkkkk
        ......'ckOOOOOOOOOOOOOOOOOOOOOOOOOOOkkkkkkkkkkkkkkkkkkkxxxxdol;'........................      .............................'cdxxxxxxxxxxxxxx
        .......;xOOOOOOOOklcdkOOOOOOOOkkkkkkkkkkkkkkkkkkkkkkkkxxxdc;'......... .....................................................,lddxxxxxxxxxxxx
        .......,dOOOOOOOOkl...;cdkOOOkkkkkkkkkkkkkkkkkkkkkkkkxdl:'...................................................................;oddddxxxxxxxxx
        .......'lOOOOOOOOOkl'.  .',;coxkkkkkkkkkkkkkkkxkkkxxoc,...........  .........................................................,lodddddddxxxxx
        .......'ckOOOOOOOOOxl'.     ..,:cllooooddxxkkkxxxdl;'..........     ..........................................................';:cloooddddxx
        ........:xOOOOOOOOOkkl.     ............'',;:cooc,.............................................................    ...........  ....,;cooddd
        ........;dOkOOkkkkOOko.     ..................................................................................      ..........        .,codd
        ........'lkkOkkkkkkkx:.       ........      ...................................................................        .......         .;loo
        ........'ckkkkkOkkxo,.                         ..........................                             ........         ....,dkdc;..    .o00k
        .........:xkkkkkkd;....                         ..................  ..                                   ....         .....lKNNNX0o.   .xXXX
        .........,dOkkkkxc...                            ................                                                    .....:kXNNNNXkc,,;lOXXK
        .........'okkkkkx,...                             .  .............                                                  .....ckKNNNNNXK0KKKXXKkd
        ..........ckkkkkx, ...                  ..              ...  ......                                        ...      ...,d0XNNNNNNK0XXXX0ko:;
        ..........:xkkkxxc. .           ..                       ..     ...                                       ...........,lOXNNNNNNNX0KXX0kl:;,,
        ..........;dkxkkko,.....  ........                       ..                                             ...........:d0XNNNNNNNNNK00KOo:;,,,,
        ..........,okxdolcl,..'..  .....                         ...     ...                                   .... ...,cokKXNNNNNNNNNNX00Oxl;,,''''
        ..........':c:;,';c'.:ddc.                                ....  ....                                 .......;ok0XXNNNNNNNWWNNNXKKKx:'..'''''
        ...................'.,d0O;.                               ..........                               ......;oOXNNNNNNNNWWWWWWWNX0KKOc.  .....'
        .............      ..;oxkl...,,..             .          .........                               .....;lkKNNNNNNWWWWWWWWWWNNX0KKOo'        .
        .............        ..',,'',coxd:.        ....          .......                                ..':oOKNNNNNWWWWWWWWWWWWWNNK0KX0d;.
        .............            .....':odl;..........            ... ...                      ...   ..;cd0XNNNWWWWWWWWWWWWWWWWWNX00KX0xc.
        .............            ..  ....;lolccc:;..              ..........                 .....,:ldOKNNWWWWWWWWWWWWWWWWWWWWNNKO0XXOdl,
        .............         ...'.     ...,coc;...                ........                 ..:ox0XNNWWWWWWWWWWWWWWWWWWWWWWWNNKO0KXKkdl;.
        ..........''.     ...',;::,.       ..,'..                                        .';okXNWWWWWWWWWWWWWMMWWWWWWWWWWWNX0O0KXX0xol;.
        ..........''.. ...';;:ccc:'          ...                                      ..:dOKNWWWWWWWWWWWWWMMMMMMMWWWWWWNXK0O0XXXKOdll:...
        .....'........',,;cllc::;,.                                                .':oOXNWWWWWWWWMWWMMWWWWMMMWWWWWWNXK000KNNXKOxoll:.........
        ............';clcccc:;;;;;.                                              .;xKNWWWWWWMMMWMMMMMMMMWWWWWWWWNX0000KXNNXK0Oxolll:'.............
        ......'',:::cllc:;;;;;;;;;.            ..                              .ckXWWWWWWWWWWWMMMWWWWWWWWNNXK000000KNNNXXK0kollllc;.................
        .'',,,;:ccccc::::;;;;;;;;,.           .''.                          ..,ck0KKXXXXNNNNNXXXXXKKK0000000KKXNNNNXXK0Okdollool:,'..........'......
        ,,,;ccccc::::::::::;;::;;,.          ..'''.                        .cOKKK00KK00KKKKKKKKKKKKKKXXNNNWWNNXXXKKOkdoollodolc;'...............'''.
        ::cccc:;;;;;::::::::;;;;;,.          .'','..                     .,d0XXXNNNNNWWWWWWWWWWWWWWNNNNNXXXKK0Okxdooooodddol:;'..................'''
        cc:::::::;;::::::::::::::,.         .''''''..                  ..,cdxkOO00KKKXXXXXXXXXXXXXKKK0OOkxxddooooddxxxxdoc:,,,'.....................
        ::::::cc:::::ccc:::::::::,.        .''''''''..                .';::::cccllooddddddxxxxxdddddooooooddxxxkkxxdolc:;,,,;:,.................''..
        ::ccccccccccccccc:::::;;;,.       .'''''''....               .';cooddooooooooolloooooooddddxxkkkkkkkxxddolc:;;;;,,,:cl;........'.''''.....'.
        clccccccccccccccc::::;;;,,.       .'''''...'...              .,;:cclooddddxxxxxxkkkkkkkkkkkkkxxddoollcc:::;;;,,',,;cll;.....''''''''''......
        lollcc::ccccc:::::;;;;;,,,.       .'''''.......             .,;;:::::cccccllllloooooooollllllccccc:::::::;;,,'..';:cll,....'''''''''''......
        cllllcc::::::::::;;;;,,,,'.       .''''''......            .';;;::::ccccccccccccccccccccccccccccc::::;;;,,''....,:clol,....'''''''''''''....
        c:cc:::cc:;;::;;;;;;,,,,,,..      .'..'''......            .;;;;;:::::::cccccccccccccccccc::::::::;;,,'''''..  .'clooc'..'''''''''''''''....
        :::::;;::::;;;;;;,,,,,,,,,,.     .''..''.......           .,::::::::::::::::cccccccccc:::::::;;,,''''.'''''''..';lodo;'.''',''''''''.''.....
        ::c:;;;;;;;:::;,,,,,,,,'',,;,....'''''....'''..           .;::::::::::::::::::::::::::;;;;;;,,,'....''''''',,;:cloolc,.',,,,',,,'''''''....'
        ;:::;;,,,,;,,;;;;,,,,,''''',;:::;,''''....''...           .:::::::::::::::::::::::::;;;;;,,,,,,''.''''''',,,;:llllc:,'''',,,'''''''''''.''''
        ;;;;;,,;;;;;,,,;;;;,'','''''''''''''''..''''...          .,::::::::::::::::::::::::::;;;;;;;,,,,''''''',,,;:cllcc:,''','''''''''''''..''''''
        ::::;;;;;;;;;,,,,;;;,,''''''''''''''.....'''''.          .;:::;:c:::::::::::ccccc:::::;;;;;;;;,,,,'',,,,;;:cccc;,'.'''','''''''''''.'''''''.
        ::::;;;;;;;;;;;;,,,,,;,,'''''''..........''''..         .,:cccccc::::::c:::ccccccc::::;;;;;;;;;;;,,,,,;::ccc:;,'',,'''''''''......',''''....
        ::;;;;;;;;;;;;;;,,,,,,,;;,'''''.'....'''..'','.        .'::cc:ccccccccccccccccccccc::::;;;;;;;;;;;,,;::cc:;,'',,,,,'',''''''....'''''.......
        ::;:;;;;::;;;;;;,;;,,;;;;;;,,''''''''''.'',,,'.        .;cc:ccccccccccccccccccccccccc:::::::;;;;;;:::ccc;'...',,;,,'''''''.'''',,'''''''.'''
        :;;;;;;;;::;;;;;;;;;;;;;;;;;;;,'''''''',,,''''.        .:ccccccccccccccccccccclllllccc:::::::::::cccll:,'.'''''',,,,,''''''',,,,''''''''''..
        ;;::::;;;;;:::::::::;;;;;;;;;:::;,,',;,'''''','..  ...',;:ccccccccccccccccllllllooolllccccccclllllolc,''',,,,'',,,',,,,''',;,''''''''''.....
        :;;::::::::::::::::::;;:::::::::ccc:;,,,,,,'',ldlcloxko,.,;ccccllllllllllllllooodddooollloooooooooc;',,,,;;;;,,,,,,,,'',;::,'''''''''''.....
        :::::::::c::::::::::::::cc::ccccc:;;:;;,,;,,,,oOO0KKKKd,''',:cllllllllllloooooddddddddoooodddddol;,,,,;;;;;;;,,,,,,,'',;:::;,,,'''''''......
        :::::::::::::::::::::::ccccclccc::c:;;;;;;,,,,:dOOOOOxc,,;;;,;;::clooooooooddddddddddddddddddoc:;;;;;;;;;,,;;;;;,,,,;:::::::::;,,'''''''''..
        :::cc::cc:c:::::::::cccccllccccccc:;,,,;;;;;,,,:llllc:;;;;;;::;;;;::clloddddddddxxxxxxxxddoc:;;::::::;;;;;:;;;;;,;;:cccccc::::::;;,''',''...
        ::ccccccccc::cc:cccccccccc:::c::::;;,,;,,,,;;;,,,;;;;;;:::::::::::;;:ccclloodddxxxxxxxdoc:;:::::::::;;;;;;::;,,;:ccccclccccc:::::::;,,,''...
        cccc:::::::::cccllllllc:::::::::::;;;;;,,,,',;;;;;;::::cccccccccccccllllllccclooooolllcccccccc:cc:::::::::::;;:cccccllllccccccc::;;;,,,,''''
        c::c:::::::c:cccclllc:::::::::::::::;;,,;;;,,;;:cc::cccccccccccclccclllllllllllcc::::loollllcccccccc:;;;:::::ccccc:cclcclcccc:c::::;;,,,,,,'
        c:::::;;:::cccccllc:::::::::::::::::;,,,;;;;;;;:ccclc::cccccllcccc:ccccccllllllclllooooooooollcc::::;;;;;::cccccccc:cclccccc:::::::;;;,,''''
*/

public class SwBlockProtectionListener implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onInventoryClose(InventoryCloseEvent event){
        if(!(event.getPlayer() instanceof Player)){
            return;
        }

        Player player = Bukkit.getPlayer(event.getPlayer().getUniqueId());
        if(player.isBlocking()){
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(ChatColourUtil.convert("&flook! &7" + player.getName() + " &ftried to use the sword block glitch! looks like they cant win a fight without cheating =)"));
            Bukkit.broadcastMessage("");

            player.kickPlayer(ChatColourUtil.convert("&cwhy u glitching? too shit to win fights without it? =)"));
        }
    }
}
