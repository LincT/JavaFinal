import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class BlockMakerModel extends AbstractTableModel {
    FileIO fileIO = new FileIO();


    private final String dir = fileIO.getDirectory("AppData",".minecraft");
    private final String configFolder = "config" + fileIO.separator;
    private final String modConfig = configFolder + "bmm" + fileIO.separator;
    private final String blockFile = "blockNames.txt";
    private final String absoluteBlockFilePath = dir + modConfig + blockFile;

    private ArrayList<String> records = fileIO.getArrayListFromFile(absoluteBlockFilePath);

    @Override
    public int getRowCount() {
        return records.size();
    }

    @Override
    public int getColumnCount() {
        int columnCount = 1;
        if (records.toString().contains(fileIO.delimeter)){

            for (String s:records){
                System.out.println(s.split(fileIO.delimeter).length);
                if (s.split(fileIO.delimeter).length>columnCount){

                    columnCount = s.split(fileIO.delimeter).length;
                }
            }
        }
        return columnCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String rowData = records.get(rowIndex);
        String columnData = "";
        if (rowData.split(fileIO.delimeter).length > columnIndex){
            columnData = (rowData.split(fileIO.delimeter))[columnIndex];
        }
        return columnData;
    }

    public void testConnection(){
        // normally I would wire this up to a database like MySQL, however, this is
        // working to spoof db interactions using only a text file for persistent data

        fileIO.verifyFile(dir + modConfig,blockFile);
    }

    public boolean addRecord(String record){
        if (record.equalsIgnoreCase("")){
            return false;
        }
        else {
            if (records.contains(record)){
                return false;
            }
            else {
                String newData = record;

                records.add(newData);
                this.fireTableDataChanged();
                if (newData.contains(fileIO.delimeter)){
                    this.fireTableStructureChanged();
                }
                return true;
            }
        }
    }

    public boolean removeRecord(int row){
        try {
            //if (records.remove(row))
            System.out.println(records.remove(row));
            this.fireTableStructureChanged();
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
            return false;
        }

    }

    public void saveData(){
        ArrayList<String> header = new ArrayList<String>();
        header.add("# Use this file to add block names, one name per line,");
        header.add("# Lines that start with # are ignored if");
        header.add("# one wishes to add comments.");
        header.add("# if blocks are not rendering, verify a .png file exists w/ the same name as the block");
        header.add("# Generated at: " + fileIO.currentDate());
        records.addAll(0,header);
        fileIO.writeTextFile(dir + modConfig , blockFile,records);
    }
}
