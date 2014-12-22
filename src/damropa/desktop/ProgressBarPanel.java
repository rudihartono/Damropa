package damropa.desktop;

import javax.swing.*;
import java.awt.*;

/**
 * Created by rudihartono on 23/12/2014.
 */
public class ProgressBarPanel extends JPanel {
    public JProgressBar pbar;
    public JLabel labelStatus;
    public JLabel progressCount;
    public ImageIcon icon;
    static final int MIN =0;
    static final int MAX = 100;

    public ProgressBarPanel(){
        pbar = new JProgressBar();
        labelStatus = new JLabel();
        labelStatus.setText("Ready");
        labelStatus.setBounds(5, 0, 200, 25);
        pbar.setBounds(650,2,335,20);
        this.setLayout(null);
        setForeground(Color.DARK_GRAY);
        setBackground(Color.GRAY);

        add(labelStatus);
        add(pbar);
    }
    public void updateBar(int param){
        pbar.setValue(param);
    }
}
