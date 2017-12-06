import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CommandGUI implements ICommand {
    //instantiate any needed classes here:
    FileIO fileIO = new FileIO();
    BlockMakerMod main = new BlockMakerMod();

    public CommandGUI() {
    }

    @Override
    public String getCommandName() {
        return BlockMakerMod.MODID+"-gui";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return BlockMakerMod.MODID+"-gui";
    }

    @Override
    public List getCommandAliases() {
        ArrayList alias = new ArrayList();
        return alias;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        final BlockUI gui = new BlockUI();
                gui.setVisible(true);
     }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
        ArrayList tabcompletes = new ArrayList();
        return tabcompletes;
    }

    @Override
    public boolean isUsernameIndex(String[] strings, int i) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
