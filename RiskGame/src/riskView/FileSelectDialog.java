package riskView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileSelectDialog extends JFileChooser {

    public FileSelectDialog(){
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Map FILES", "map");
        setFileFilter(filter);
        setDialogTitle("Select a map file");
        setCurrentDirectory(new File(System.getProperty("user.home")));
    }
}
