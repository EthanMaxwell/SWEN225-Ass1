package GUI;

import javax.swing.*;
import java.awt.*;

public class MenuBar extends JMenuBar {
    MenuBar(){

        //Have the JMenu on the left if the MenuBar
        setLayout(new FlowLayout(FlowLayout.LEFT));

        //Setting the font
        setFont(new Font("Serif", Font.BOLD, 18));

        //Create the file menu
        JMenu file = new JMenu("File");

        //Create the options menu
        JMenu options = new JMenu("Options");

        //Create the help menu
        JMenu help = new JMenu("Help");

        //Create items in Menu. Non-functional
        JMenuItem file1 = new JMenuItem("1");
        JMenuItem file2 = new JMenuItem("2");
        JMenuItem file3 = new JMenuItem("3");
        JMenuItem file4 = new JMenuItem("4");
        file.add(file1);
        file.add(file2);
        file.add(file3);
        file.add(file4);

        //Create items in Menu. Non-functional
        JMenuItem options1 = new JMenuItem("1");
        JMenuItem options2 = new JMenuItem("2");
        JMenuItem options3 = new JMenuItem("3");
        JMenuItem options4 = new JMenuItem("4");
        options.add(options1);
        options.add(options2);
        options.add(options3);
        options.add(options4);

        //Create items in Menu. Non-functional
        JMenuItem help1 = new JMenuItem("1");
        JMenuItem help2 = new JMenuItem("2");
        JMenuItem help3 = new JMenuItem("3");
        JMenuItem help4 = new JMenuItem("4");
        help.add(help1);
        help.add(help2);
        help.add(help3);
        help.add(help4);

        add(file);
        add(options);
        add(help);
    }
}
