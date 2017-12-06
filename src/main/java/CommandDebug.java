import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import java.util.ArrayList;
import java.util.List;

public class CommandDebug implements ICommand {
    //instantiate any needed classes here:
    FileIO fileIO = new FileIO();
    BlockMakerMod main = new BlockMakerMod();

    //first attempt at a debug command that can be imported into mods effortlessly
    public CommandDebug() {
    }

    @Override
    public String getCommandName() {
        return BlockMakerMod.MODID+"-debug";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/Hello: Just type hello. Simple test command";
    }

    @Override
    public List getCommandAliases() {
        ArrayList alias = new ArrayList();
        return alias;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        //newlines don't work in chat output, so need to call a new line for each line we add.
        // Currently doing this by adding strings to an ArrayList, then iterating over the collection.
        ArrayList<String> message = new ArrayList<String>();
        message.add(String.format("Hello %s!", sender.getCommandSenderName()));
        message.add(String.format("ModID: %s", BlockMakerMod.MODID));
        message.add(sender.getEntityWorld().provider.getDimensionName() +
                        ": " + sender.getPlayerCoordinates());
        message.add(String.format("Project Directory: %s", fileIO.getProjectDirectory()));
        message.add(String.format("Canonical test: %s",fileIO.dataTest()));
        message.add(main.custom.getResourceDomain() +" : "+ main.custom.getResourcePath());
        message.add(fileIO.getTextureFile("cottoncandy.png").getAbsolutePath());
        if (sender.getEntityWorld().getPlayerEntityByName(
                sender.getCommandSenderName()).getCurrentEquippedItem()!=null){
            ItemStack equipped = sender.getEntityWorld().getPlayerEntityByName(sender.getCommandSenderName())
                    .getCurrentEquippedItem();
            message.add(equipped.getDisplayName());
            if (Block.getBlockFromItem(equipped.getItem())!=null &&
                    Block.getIdFromBlock(Block.getBlockFromItem(equipped.getItem())) > 0)
            {
                message.add("BLOCK ID:" + Block.getIdFromBlock(Block.getBlockFromItem(equipped.getItem())) + "");
            }
        }

        for (String s:message)
        sender.addChatMessage(new ChatComponentText(s));
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
