package damropa.desktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by rudihartono on 23/12/2014.
 */
public class RoadAnomalyListPanel extends JPanel{
    private String label[] = {""};
    private DataList data = new DataList();
    private JList list;
    private JScrollPane pane;
    private JButton button;

    public RoadAnomalyListPanel(){
        this.setLayout(new BorderLayout());
        list = new JList(data.listModel);
        pane = new JScrollPane(list);
        button = new JButton("Lihat");
        add(pane, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
    }

    public void setDamropaList(){
        //this.list = new JList(data.listModel);
        //this.list = new JList(label);
    }

    public void showListtoDestop(){

    }
    //inner class
    class PrintListener implements ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            int selected[] = list.getSelectedIndices();
            System.out.println("Selected Elements: ");
            for(int i=0;i<selected.length;i++){
                String element = list.getModel().getElementAt(selected[i]).toString();
                System.out.println(" "+ element);
            }
        }
    }

    public void setLabel(String[] label){
        this.label = label;
    }
    public String[] getLabel(){
        DefaultListModel dataList = new DefaultListModel();
        return label;
    }

    public void update(){
       data.update(label);
    }

    public JButton getButton(){
        return this.button;
    }
    public JList getList(){
        return this.list;
    }

    private class DataList{
        private final DefaultListModel listModel = new DefaultListModel();

        public void update(String[] stringModel){
            listModel.clear();
            for(int i=0;i<stringModel.length;i++){
                listModel.addElement((i+1) + ". "+ stringModel[i]);
            }
        }
    }
}
