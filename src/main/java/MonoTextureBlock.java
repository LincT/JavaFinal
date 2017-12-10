import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/*
* normally image handling wouldn't be hard coded here, but this is still in an experimental phase where I am attempting
* to parse a png file from a location outside of the assets folder.
* Once we successfully map our test image to our block, this will get cleaned up to allow iteration over a directory
*/

public class MonoTextureBlock extends Block {
    FileIO fileIO = new FileIO();
    public MonoTextureBlock(Material material) {
        super(material);
    }
    public String resourceLocation = fileIO.getDirectory("AppData",".minecraft")+
            "config" + fileIO.separator + BlockMakerMod.MODID + fileIO.separator;
    public ResourceLocation custom = new ResourceLocation("custom",resourceLocation+"cottoncandy.png");

    @Override
    public boolean isOpaqueCube() {
        //if one has transparent or hollow textures, stop mod from x-ray render
        return false;
    }


    // possibly the answer to pulling textures from outside the compiled jar?
    @SideOnly(Side.CLIENT)
    @Override
    public String getLocalizedName()
    {
        return StatCollector.translateToLocalFormatted(this.getItemIconName());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        //image, imageIO, and icon info from here:
        //https://stackoverflow.com/questions/12020597/java-convert-image-to-icon-imageicon
        Image image = null;
        try {
            image = ImageIO.read(fileIO.getTextureFile("cottoncandy.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Icon icon = new ImageIcon(image);
        IIcon iIcon = new IIcon() {
            @Override
            public int getIconWidth() {
                return icon.getIconWidth();
            }

            @Override
            public int getIconHeight() {
                return icon.getIconHeight();
            }

            @Override
            public float getMinU() {
                return 0;
            }

            @Override
            public float getMaxU() {
                return 0;
            }

            @Override
            public float getInterpolatedU(double p_94214_1_) {
                return 0;
            }

            @Override
            public float getMinV() {
                return 0;
            }

            @Override
            public float getMaxV() {
                return 0;
            }

            @Override
            public float getInterpolatedV(double p_94207_1_) {
                return 0;
            }

            @Override
            public String getIconName() {
                return "cottoncandy";
            }
        };
        //return this.blockIcon;
        ResourceLocation resourceLocation = new ResourceLocation("test",
                String.format("%sconfig%s%s%s",
                        fileIO.getDirectory("AppData", ".minecraft"),
                        fileIO.separator, BlockMakerMod.MODID, fileIO.separator));
        IResourcePack pack = new IResourcePack() {
            @Override
            public InputStream getInputStream(ResourceLocation resourceLocation) throws IOException {
                InputStream inputStream = new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                };
                return inputStream;
            }

            @Override
            public boolean resourceExists(ResourceLocation p_110589_1_) {
                return false;
            }

            @Override
            public Set getResourceDomains() {
                return null;
            }

            @Override
            public IMetadataSection getPackMetadata(IMetadataSerializer p_135058_1_, String p_135058_2_) throws IOException {
                return null;
            }

            @Override
            public BufferedImage getPackImage() throws IOException {
                //return null;
                //https://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
                //
                Image img = ImageIO.read(fileIO.getTextureFile("cottoncandy.png"));
                if (img instanceof BufferedImage)
                {
                    return (BufferedImage) img;
                }
                BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

                // Draw the image on to the buffered image
                Graphics2D bGr = bimage.createGraphics();
                bGr.drawImage(img, 0, 0, null);
                bGr.dispose();

                // Return the buffered image
                return bimage;
            }

            @Override
            public String getPackName() {
                return null;
            }
        };
//        ResourcePackRepository repo = new ResourcePackRepository(
//                fileIO.getTextureFile("cottoncandy.png"),
//                fileIO.getTextureFile("cottoncandy.png"));
        return iIcon;
    }


}
