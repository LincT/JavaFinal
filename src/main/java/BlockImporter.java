import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BlockImporter {
    FileIO fileIO = new FileIO();

    //because no mod should work outside this directory
    private final String dir = fileIO.getDirectory("AppData",".minecraft");
    private final String configFolder = "config" + fileIO.separator;
    private final String modConfig = configFolder + BlockMakerMod.MODID + fileIO.separator;
    private final String blockFile = "blockNames.txt";
    private final String absoluteBlockFilePath = dir + modConfig + blockFile;
    private HashMap<String,Block> blockCollection = new HashMap<String, Block>();
    private ArrayList<String> langData  = new ArrayList<String>();
    
    public BlockImporter(){
        //place for any setup that needs to be done
        fileIO.verifyFolder(dir, configFolder);
        fileIO.verifyFolder(dir, modConfig);

        if (!fileIO.verifyFile(dir + modConfig,blockFile)){
            ArrayList<String> header = new ArrayList<String>();
            header.add("# Use this file to add block names, one name per line,");
            header.add("# Lines that start with # are ignored if");
            header.add("# one wishes to add comments.");
            header.add("# if blocks are not rendering, verify a .png file exists w/ the same name as the block");
            header.add("# Generated at: " + new Date().toString());
            fileIO.createTextFile(dir + modConfig,blockFile,header);
        }
    }

    //register our blocks
    public void registerBlocks(){

        if (blockCollection != null && blockCollection.keySet().size()>0){
            for (String s:blockCollection.keySet()){
                Block block = blockCollection.get(s);
                GameRegistry.registerBlock(
                        block,block.getUnlocalizedName().substring(5));

            }
        }
        else {
            System.out.println("No blocks in block registry");
        }
    }

    //get block data
    public void parseBlockData(){
        // TODO allow for different types of blocks and custom attributes
        ArrayList<String> blocks = fileIO.getArrayListFromFile(absoluteBlockFilePath);

        for (String s:blocks){
            Block newBlock = new MonoTextureBlock(Material.clay);
                    newBlock.setBlockName(s);
                    newBlock.setBlockTextureName(String.format("custom:%s",s.toLowerCase()));
                    newBlock.setCreativeTab(BlockMakerMod.blockMakerTab);
            blockCollection.put(s,newBlock);

            String blockString = String.format("tile.%s.name=%s",newBlock.getUnlocalizedName().substring(5),
                    newBlock.getUnlocalizedName().substring(5));
            addLangData(blockString);
        }
    }

    //lang file handling
    public void updateLangFile(){
        LanguageRegistry languageRegistry = LanguageRegistry.instance();
        //DONE update data for Language Registry from here instead of manual update.

        for (String s:langData){
            if (s.contains("=")) {
                //this should always be true from the program, but if somehow an error,
                // skip so we don't end up with erroneous entries and instead default to standard undefined.
                String key = s.split("=")[0];
                String value = s.split("=")[1];
                languageRegistry.addStringLocalization(String.format(key), "en_US", value);
            }

        }

    }

    // TODO texture handling for blocks outside of compiled directories
    private void textureMapBlock(Block block){

    }

    private void addResourceLocation(){
        //there has to be a way to import files...
    }

    public void addLangData(String string){
        langData.add(string);
    }

    public void addBlock(Block block){
        blockCollection.put(block.getUnlocalizedName().substring(5),block);
    }

    public String getUsedDirectory() {
        //debugging method
        return "directory:" + absoluteBlockFilePath;
    }
}
