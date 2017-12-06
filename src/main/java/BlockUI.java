import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BlockUI extends JFrame{
    private JTable blockTable;
    private JPanel mainPanel;
    private JButton removeBlockButton;
    private JTextField textFieldAdd;
    private JButton addBlockButton;

    BlockMakerModel blockMakerModel = new BlockMakerModel();

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

    }

    public int showOptionQuery(String message) {
        return JOptionPane.showConfirmDialog(this, message, null, JOptionPane.YES_NO_OPTION);
    }
}
