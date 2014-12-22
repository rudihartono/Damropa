package damropa.desktop;

import javax.swing.*;
import java.awt.*;

/**
 * Created by rudihartono on 23/12/2014.
 */
public class RoadAnomalyPanel extends JPanel {
    private JLabel type1, type2, type3, type4, type5;
    private JLabel[] countLabel;
    private JLabel titleType, titleCount;

    public RoadAnomalyPanel() {
        type1 = new JLabel("Heavy Cracks");
        type2 = new JLabel("Corrugation");
        type3 = new JLabel("Revealing");
        type4 = new JLabel("Potholes");
        type5 = new JLabel("Unknown Anomalies");

        countLabel = new JLabel[5];

        for (int i = 0; i < countLabel.length; i++) {
            countLabel[i] = new JLabel("0");
        }

        titleType = new JLabel("Types of road damages");
        titleCount = new JLabel("Count", SwingConstants.RIGHT);
        setLayout(null);

        //label judul
        titleType.setBounds(10, 5, 200, 20);
        titleType.setFont(new Font("Arial", 1, 14));
        titleCount.setBounds(700, 5, 50, 20);
        titleCount.setFont(new Font("Arial", 1, 14));
        //label untuk jenis kerusakan
        type1.setBounds(10, 30, 200, 20);
        type2.setBounds(10, 50, 200, 20);
        type3.setBounds(10, 70, 200, 20);
        type4.setBounds(10, 90, 200, 20);
        type5.setBounds(10, 110, 200, 20);
        int y = 30;

        for (int i = 0; i < countLabel.length; i++) {
            countLabel[i].setBounds(700, y, 50, 20);
            countLabel[i].setHorizontalAlignment(JLabel.CENTER);
            y += 20;
            add(countLabel[i]);
        }

        add(titleType);
        add(titleCount);
        add(type1);
        add(type2);
        add(type3);
        add(type4);
        add(type5);
    }

    public void setcountLabel(int index, int count) {
        String countStr = Integer.toString(count);
        countLabel[index].setText(countStr);
    }

    public void updatePanel() {
        this.updateUI();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
