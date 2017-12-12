import net.minecraft.client.Minecraft;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BlockUI extends JFrame{
    private JTable blockTable;
    private JPanel mainPanel;
    private JButton removeBlockButton;
    private JTextField textFieldAdd;
    private JButton addBlockButton;
    private JLabel imageLabel;

    BlockMakerModel blockMakerModel = new BlockMakerModel();
    FileIO fileIO = new FileIO();

    private final String dir = fileIO.getDirectory("AppData",".minecraft");
    private final String configFolder = "config" + fileIO.separator;
    private final String modConfig = configFolder + BlockMakerMod.MODID + fileIO.separator;



    public BlockUI(){
        blockTable.setModel(blockMakerModel);
        blockMakerModel.testConnection();
        blockTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //methods to configure components here
        //resetInputs();
        addListeners();


        setContentPane(mainPanel);
        pack();
        setVisible(true);
        blockTable.setRowSelectionInterval(0,0);
        imageLabel.setBounds(10, 10, 400, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void addListeners() {
        addBlockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String blockName = textFieldAdd.getText();
                if (blockMakerModel.addRecord(blockName)) {
                    textFieldAdd.setText("");
                }
            }
        });
        removeBlockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String record = blockTable.getValueAt(blockTable.getSelectedRow(),0).toString();
                blockMakerModel.removeRecord(blockTable.getSelectedRow());
                textFieldAdd.setText(record);
            }
        });
        this.addWindowListener(new WindowAdapter() {
            //in theory this should call the save function for the model, which actually passes data to our
            //file handler for saving.
            @Override
            public void windowClosing(WindowEvent e) {
                switch (showOptionQuery("Save before closing?")){
                    case 0:
                        blockMakerModel.saveData();
                        break;
                    default:
                        break;
                }
                super.windowClosing(e);
            }
        });
        //https://stackoverflow.com/questions/1242581/display-a-jpg-image-on-a-jpanel
        blockTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                try {
                    imageLabel.setText("");
                    String imageName = blockTable
                            .getValueAt(blockTable.getSelectedRow(),0).toString().toLowerCase() + ".png";
                    if (fileIO.verifyFile(dir + modConfig,imageName)){
                        imageLabel.setText("Found:\n"+imageName);
                        //imageLabel = new JLabel(new ImageIcon(dir+modConfig + imageName));
                        //imageLabel.setIcon(new ImageIcon(dir+modConfig + imageName));
                        //imageLabel.setPreferredSize(new Dimension(64,64));
                        //imageLabel.setBounds(10, 10, 400, 400);
                        //imageLabel.setVisible(true);
                    }
                    else {
                        //imageLabel = new JLabel();
                        imageLabel.setText(imageName +"\nNot Found");
                        //imageLabel.setIcon(null);
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    public int showOptionQuery(String message) {
        return JOptionPane.showConfirmDialog(this, message, null, JOptionPane.YES_NO_OPTION);
    }
}
