import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;



@Mod(modid = BlockMakerMod.MODID,name = BlockMakerMod.NAME,version = BlockMakerMod.VERSION)



public class BlockMakerMod {


    //put annotation variables in a class so we can also call them in code.
    public final static String MODID = "bmm";
    public final static String NAME = "BlockMakerMod Mod";
    public final static String VERSION = "1.0";
    public static Block myBlock; //only here so tab can reference

    FileIO fileIO = new FileIO();
    public String resourceLocation = fileIO.getDirectory("AppData",".minecraft")+
            "config" + fileIO.separator + BlockMakerMod.MODID + fileIO.separator;
    public ResourceLocation custom = new ResourceLocation("custom",resourceLocation+"cottoncandy.png");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){

        //item/block init and reg, cfg handling

        //System.out.println(blockImporter.getUsedDirectory());

        //register item


        //Blocks
        BlockImporter blockImporter = new BlockImporter();
        LanguageRegistry languageRegistry = LanguageRegistry.instance();
        FileIO fileIO = new FileIO();
        String modTabName = "itemGroup.BlockMakerMod=BlockMaker";
        languageRegistry.addStringLocalization(String.format(modTabName.split("=")[0]), "en_US",
                modTabName.split("=")[1]);


        myBlock = new MonoTextureBlock(Material.clay)
                .setBlockName("myBlock")
                .setCreativeTab(BlockMakerMod.blockMakerTab)
                .setBlockTextureName("cottoncandy");
        String blockString = String.format("tile.%s.name=%s",myBlock.getUnlocalizedName().substring(5),
                myBlock.getUnlocalizedName().substring(5).toUpperCase());





        //programatically add blocks from user accessible directory
        //blockImporter.addLangData(blockString);

        //using deprecated method for 1.7.10 since still valid, 1.8 or later will need different handling.
        languageRegistry.addStringLocalization(String.format("tile.myBlock.name"), "en_US", "MyBlockText");

        blockImporter.addBlock(myBlock);

        //have blockimporter read at this time in the program
        blockImporter.parseBlockData();

        //write our names to the lang file hopefully
        blockImporter.updateLangFile();

        //register block
        blockImporter.registerBlocks();

        //GameRegistry.registerBlock(name,name.getUnlocalizedName().substring(5));
    }
    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        // register server commands
        event.registerServerCommand(new CommandDebug());
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event){
        //proxy, tileEntity, entity, gui, pkt init/reg, recipes

        //recipes

        //smelting recipes

        //Shapeless recipes
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){

    }

    public static CreativeTabs blockMakerTab = new CreativeTabs("BlockMakerMod"){

        @Override
        public Item getTabIconItem() {
            try { return new ItemStack(myBlock).getItem(); }

            catch (NullPointerException e){
                //skull is our warning something went wrong
                return new ItemStack(new ItemSkull()).getItem();
            }
            catch (Exception e){
                //if we get a stack trace then something really went wrong.
                System.out.println(e.getMessage());
                return new ItemStack(new ItemSkull()).getItem();
            }
        }
    };
}
