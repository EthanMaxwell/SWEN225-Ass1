package GUI;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    ControlPanel(){
        this.setBackground(new Color(200, 200, 255));
        this.setBorder(BorderFactory.createEmptyBorder(50,25,25,25));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }
}
