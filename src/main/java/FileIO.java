import com.google.common.io.Files;
import net.minecraft.client.Minecraft;
import java.io.*;
import java.util.ArrayList;


public class FileIO {
    //This class reads lines from file to Array list, writes to files,
    //and has a debug method for detecting current working directory

    public String separator; //in case we need the separator string in other places.

    public FileIO() {
        separator = File.separator;
    }

    public ArrayList<String> getArrayListFromFile(String filename)  {
        //read the data file and return the file as an ArrayList of strings
        ArrayList<String> blocks = new ArrayList<String>();
        BufferedReader bufferedReader = null;
        try {
            File file = new File(filename);
            bufferedReader = new BufferedReader(new FileReader(file));

            String line = (bufferedReader.readLine());
            while (line != null) {
                //strip white space from line, names should be one word.
                line = line.trim().replace("\n", "")
                        .replace("\r", "").replace(" ","");

                //if line doesn't start w/ # and line not empty, add it.
                if (!line.startsWith("#") && !(line.equalsIgnoreCase(""))) {
                    //skip any lines with comments
                    blocks.add(line);
                }
                //try the next line
                line = bufferedReader.readLine();
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        finally {
            try {
                if (bufferedReader != null) {
                    //make sure this closes
                    bufferedReader.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return blocks;
    }

    public void createTextFile(String dir, String filename, ArrayList<String> header){
        try {

            File file = new File(dir + filename);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,false));
            for (String s:header){
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }
        catch (IOException io){
            System.out.println(io.getMessage());
        }
    }

    public void verifyFolder(String dir, String name) {
        //creates folder if missing
        File folder = new File(dir + name);
        try {
            if (folder.exists() && folder.isDirectory()) { } //true as we found the directory, so no need to create
            else {
                System.out.println("\n" + name + " not found, attempting to create");
                boolean created = folder.mkdir();
                if (created) {
                    System.out.println(BlockMakerMod.MODID + "\n\tDirectory Created: " + folder.getPath());
                }
            }
        }
        catch (NullPointerException e){
            System.out.println(e.getMessage());
            //return false; // false because it go boom!
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public boolean verifyFile(String path, String name){
        try {
            File testFile = new File(path + name);
            return testFile.exists();
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return false;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public File getTextureFile(String textureName){
        try {
            String resourceLocation =
                    getDirectory("AppData",".minecraft")+
                    "config" + separator + BlockMakerMod.MODID + separator;
            return new File(resourceLocation,textureName);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

//    public void cloneDirectory(String source){
//        File filePath = new File(source);
//        //String destinationString = ;
//        File destination = new File();
//        System.out.println("TESTPATH" + System.getProperty("user.dir"));
//        if (filePath.isDirectory()&&destination.isDirectory()){
//            for (File file:filePath.listFiles()){
//                if (file.getName().contains(".png")){
//                    try {
//                        Files.copy(file,destination);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//
//        }
//    }

    public String dataTest() {
        try {
            return (Minecraft.getMinecraft().mcDataDir.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
            //let the user know something went wrong, but don't torment them
            // with an error message they may not understand.
            return "Error, please consult your developer";

        }
    }

    public String getProjectDirectory(){
        return new File(System.getProperty("user.dir"))
                .getParentFile().toString();
    }

    public String getDirectory(String environmentVariable,String appString){
        return System.getenv(environmentVariable) + separator + appString + separator;
    }
}
