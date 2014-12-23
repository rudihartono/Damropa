package damropa.desktop;

import javax.swing.*;
import java.awt.*;

/**
 * Created by rudihartono on 23/12/2014.
 */
public class MenuBarDamropa extends JMenuBar{
    private final String[] fileItem = new String[]{"New Session","Save Session","Open Session file","Open file","Exit"};
    private final String[] mediaItem = new String[]{"View image fullscreen","Save image"};
    private final String[] filterItemS = new String[]{"Filter by TZ","Filter by TX","Filter by TS"};
    private final String[] aboutitem = new String[]{"Help topics","About"};

    JMenu fileMenu = new JMenu("File");
    JMenu mediaMenu = new JMenu("Media");
    JMenu filterMenu = new JMenu("Filter");
    JMenu aboutMenu = new JMenu("About");

    JCheckBoxMenuItem filterItem;
    JMenuItem item;

    public MenuBarDamropa(){

        for(int i=0;i<fileItem.length;i++){
            item = new JMenuItem(fileItem[i]);
            fileMenu.add(item);
        }

        for(int i=0;i<mediaItem.length;i++){
            item = new JMenuItem(mediaItem[i]);
            mediaMenu.add(item);
        }

        for(int i=0;i<filterItemS.length;i++){
            filterItem = new JCheckBoxMenuItem(filterItemS[i]);
            filterMenu.add(filterItem);
        }

        for(int i=0;i<aboutitem.length;i++){
            item = new JMenuItem(aboutitem[i]);
            aboutMenu.add(item);
        }


        this.setBackground(Color.BLACK);
        this.setForeground(Color.WHITE);

        fileMenu.setForeground(Color.WHITE);
        aboutMenu.setForeground(Color.WHITE);
        mediaMenu.setForeground(Color.WHITE);
        filterMenu.setForeground(Color.WHITE);

        fileMenu.setBackground(Color.BLACK);
        mediaMenu.setBackground(Color.BLACK);
        filterMenu.setBackground(Color.BLACK);
        aboutMenu.setBackground(Color.BLACK);

        add(fileMenu);
        add(mediaMenu);
        add(filterMenu);
        add(aboutMenu);
    }
}
