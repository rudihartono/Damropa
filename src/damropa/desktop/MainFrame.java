package damropa.desktop;

import com.sun.java.swing.plaf.windows.WindowsTabbedPaneUI;
import damropa.code.*;
import google.staticmap.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by rudihartono on 23/12/2014.
 */
public class MainFrame implements MouseListener, MouseMotionListener{
    private JFrame window;
    private final int WIDTH = 1024;
    private final int HEIGHT = 720;
    private MenuBarDamropa md;
    private ProgressBarPanel progressBarPanel;
    private RoadAnomalyListPanel listPanel;
    private JTabbedPane tabPanel;
    private RoadAnomalyPanel countPanel;

    private ReaderCSVDamropa reader;
    private WriterCSVDamropa writer;
    private ArrayList<RawDataDamropa> rawData;

    private String fileSelected;
    private String fileLocation;
    private String session;
    private String status;

    private JLabel sessionLabel;
    private JLabel mapLabel;
    private JLabel damageRoadLabel;
    private JLabel progressLabel;

    private int progress;
    private int fileIndex;

    private ArrayList<RoadAnomalyDamropa> roadDetections;
    private ArrayList<File> file;
    private File fileImage;
    private JButton processButton;

    private BufferedImage imageLabel;
    private BufferedImage damageRoadMap;

    public MainFrame(){
        file = new ArrayList<File>();
        file.add(new File(""));
        initComponent();
    }

    public BufferedImage generateGoogleStaticMap(int width, int height, int zoom, Coordinate center){
        try{
            GoogleStaticMapsUrlGenerator generator = new GoogleStaticMapsUrlGenerator();
            generator.setDimentations(width, height);
            generator.setZoomLevel((short) zoom);
            generator.setCenter(center);
            Marker start = new Marker(center);
            start.setColor(google.staticmap.Color.RED);
            start.setLabel('A');
            start.setSize(MarkerSize.MID);

            generator.addMarker(start);

            generator.setMapType(MapType.ROADMAP);

            BufferedImage image  = ImageIO.read(generator.generateURL());

            return image;

        } catch (InvalidParameterException e) {
            //e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (URLOverLengthException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            this.status = "Gambar tidak dapat ditampilkan karena koneksi terputus";
            return null;
            //e.printStackTrace();
        }catch (IllegalArgumentException e){
            System.out.println(e.toString());
            return null;
        }
    }

    public void onClickProcessButton(){
        int indexTabOpen = tabPanel.getSelectedIndex();
        fileLocation = file.get(indexTabOpen).getPath();
        System.out.println(fileLocation);
        reader = new ReaderCSVDamropa(fileLocation,";");
        reader.read();
        rawData = reader.get_data();
        FilterDamropa filter = new FilterDamropa();
        filter.setParameter(0.17,0.4012,0.0215);
        filter.setLength(150);
        filter.setRawdata(rawData);

        //fase filter
        filter.filter_by_speed(rawData);
        filter.high_pass_filter(filter.getRawDataSecondPhase());
        filter.filter_by_z(filter.getRawDataSecondPhase());
        System.out.println(filter.getFilteredData().size());

        if(md.filterMenu.getItem(1).isSelected()){
            filter.filter_by_tx(filter.getFilteredData());
            System.out.println("after filtered by tx : " + filter.getFilteredData().size());
        }

        if(md.filterMenu.getItem(2).isSelected()){
            filter.filter_by_ts(filter.getFilteredData());
            System.out.println("after filtered by ts : " + filter.getFilteredData().size());
        }

        //fase label
        filter.toRoadAnomali(filter.getFilteredData());
        ArrayList<RoadAnomalyDamropa> roadDetected = filter.getFinalData();
        String[] locationList = new String[roadDetected.size()];

        for(int i=0;i<locationList.length;i++){
            locationList[i] = roadDetected.get(i).getLocation().toString();
        }

        listPanel.setLabel(locationList);
        //listPanel.setDamropaList();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                listPanel.setDamropaList();
                listPanel.updateUI();
            }
        });

        //reader.printLocation(roadDetected);

        //fase cluster
    }
    public void initComponent(){
        //instansiasi
        window = new JFrame("Damropa"); //windows
        md = new MenuBarDamropa(); //menubar
        progressBarPanel = new ProgressBarPanel(); //progress bar
        listPanel = new RoadAnomalyListPanel(); //listview
        tabPanel = new JTabbedPane(); //tab
        JLabel label = new JLabel("Location has Detected.");
        progressLabel = new JLabel();
        sessionLabel = new JLabel("Please click new session!");
        countPanel = new RoadAnomalyPanel();
        processButton = new JButton();
        //processButton.setBorder(new BorderUIResource.LineBorderUIResource(Color.RED));

        listPanel.setSize(245, 320);
        progressBarPanel.setSize(WIDTH, 50);
        listPanel.setBackground(Color.blue);
        listPanel.setName("Location");
        listPanel.setBorder(new BorderUIResource.LineBorderUIResource(Color.BLACK));
        countPanel.setBounds(254, 505, 760, 135);
        countPanel.setBackground(Color.ORANGE);
        countPanel.setForeground(Color.BLACK);

        tabPanel.setLocation(254, md.getY() + md.getHeight());
        tabPanel.setSize(760, 500);
        tabPanel.setUI(new WindowsTabbedPaneUI());
        sessionLabel.setLocation(5, md.getY() + md.getHeight());
        sessionLabel.setSize(252, 20);
        mapLabel = new JLabel(new ImageIcon("resources/defaultImage.png"));
        mapLabel.setBounds(5, sessionLabel.getY() + sessionLabel.getHeight(), 245, 245);
        mapLabel.setBorder(new BorderUIResource.LineBorderUIResource(Color.BLACK));
        processButton.setText("Process");
        processButton.setBounds(mapLabel.getX(), mapLabel.getY() + mapLabel.getHeight() + 5, 100, 25);
        label.setBounds(processButton.getX(), processButton.getY() + processButton.getHeight()+2, 200, 20);
        listPanel.setLocation(label.getX(), label.getY() + label.getHeight() + 3);
        progressBarPanel.setLocation(0, listPanel.getY() + listPanel.getHeight()+5);
        progressBarPanel.setBorder(new BorderUIResource.LineBorderUIResource(Color.BLACK));
        countPanel.setBorder(new BorderUIResource.LineBorderUIResource(Color.BLACK));

        window.setForeground(Color.RED);
        window.setSize(WIDTH, HEIGHT);
        window.setLayout(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        window.setJMenuBar(md);
        window.add(sessionLabel);
        window.add(mapLabel);
        window.add(tabPanel);
        window.add(listPanel);
        window.add(label);
        window.add(progressBarPanel);
        window.add(countPanel);
        window.add(processButton);

        window.setVisible(true);

        for(int i=0;i<md.fileMenu.getItemCount();i++){
            md.fileMenu.getItem(i).addMouseListener(this);
        }

        for(int i=0;i<md.mediaMenu.getItemCount();i++){
            md.mediaMenu.getItem(i).addMouseListener(this);
        }

        for(int i=0;i<md.filterMenu.getItemCount();i++){
            md.filterMenu.getItem(i).addMouseListener(this);
        }
        for(int i=0;i<md.aboutMenu.getItemCount();i++){
            md.aboutMenu.getItem(i).addMouseListener(this);
        }

        listPanel.getButton().addMouseListener(this);
        mapLabel.addMouseMotionListener(this);
        processButton.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("location is here");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() == md.fileMenu.getItem(3)){

            //menampilkan file chooser
            JFileChooser chooser = new JFileChooser( );
            chooser.setMultiSelectionEnabled(true);
            FileNameExtensionFilter fn = new FileNameExtensionFilter("Text File(CSV)","csv");
            chooser.setFileFilter(fn);

            if(fileIndex < 1){
                chooser.setCurrentDirectory(file.get(fileIndex));
            }else{
                chooser.setCurrentDirectory(file.get(fileIndex-1));
                file.add(new File(""));
            }

            int option = chooser.showOpenDialog(new Panel());
            if (option == JFileChooser.APPROVE_OPTION) {

                file.set(fileIndex, chooser.getSelectedFile());
                fileSelected = file.get(fileIndex).getName();
                fileLocation = file.get(fileIndex).getPath();
                reader = new ReaderCSVDamropa(fileLocation, ";");
                reader.read();

                int dataTengah = reader.getSize()/2;

                Coordinate center = new Coordinate(reader.get_data().get(dataTengah).get_lat(), reader.get_data().get(dataTengah).get_lng());
                BufferedImage bi = generateGoogleStaticMap(760,500,18,center);

                if(bi != null){
                    damageRoadLabel = new JLabel(new ImageIcon(generateGoogleStaticMap(760,500,17,center)));
                    damageRoadLabel.setBounds(0,0,760, 500);
                }else{
                    damageRoadLabel = new JLabel(this.status);
                    tabPanel.setLayout(null);
                    damageRoadLabel.setBounds(10,10,720,500);
                }

                tabPanel.addTab(fileSelected, damageRoadLabel);
                fileIndex++;
            }
        }else if(e.getSource() == md.fileMenu.getItem(4)){

            final JDialog jd = new JDialog();
            jd.setTitle("Exit this Application");
            jd.setLocation(200,200);

            JButton ok = new JButton("Ok");
            JButton cancel = new JButton("Cancel");
            jd.setLayout(null);
            jd.setSize(300,200);
            ok.setBounds(10,10,50,20);
            cancel.setBounds(75,10,75,20);
            jd.add(ok);
            jd.add(cancel);
            jd.setVisible(true);
            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jd.setVisible(false);
                }
            });

        }else if(e.getSource() == listPanel.getButton()){
            int selected[] = listPanel.getList().getSelectedIndices();

            System.out.println(listPanel.getList().getSelectedValuesList().size());
            System.out.println(listPanel.getList().getModel().getElementAt(0));

            System.out.println("total terseleksi " + listPanel.getList().getSelectedIndices()[0]);

            System.out.println(listPanel.getList().getModel().getSize());

            System.out.println("Selected Elements: ");

            for(int i=0;i<selected.length;i++){

                String element = listPanel.getList().getModel().getElementAt(selected[i]).toString();
                System.out.println(listPanel.getList().getModel().getElementAt(i));
                System.out.println(" "+ element);
            }
        }
        else if(e.getSource() == md.fileMenu.getItem(0)){
            tabPanel.removeAll();
            session = "New Session";
            sessionLabel.setText(session);
            sessionLabel.setLocation(5, md.getY() + md.getHeight());
            sessionLabel.setSize(252, 10);
            sessionLabel.setBackground(Color.LIGHT_GRAY);
            sessionLabel.setForeground(Color.BLACK);
            listPanel.setLabel(new String[]{""});
            listPanel.setDamropaList();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    listPanel.update();
                }
            });
            sessionLabel.updateUI();
        }else if(e.getSource() == md.mediaMenu.getItem(1)){

            JFileChooser savedialog = new JFileChooser( );

            FileNameExtensionFilter fn = new FileNameExtensionFilter("Picture (PNG)","png,jpg,gif");
            savedialog.setFileFilter(fn);

            int option = savedialog.showSaveDialog(new Panel());

            savedialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (option == JFileChooser.APPROVE_OPTION) {
                StringBuilder sb = new StringBuilder();
                sb.append(savedialog.getCurrentDirectory().toString());
                sb.append("\\");
                sb.append(savedialog.getSelectedFile().getName()+".png");
                try{
                    fileImage = new File(sb.toString());
                    ImageIO.write(damageRoadMap, "png", fileImage);
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        }else if(e.getSource() == processButton){
            if(!fileSelected.equals("")){
                onClickProcessButton();
                System.out.println("was clicked.");
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mapLabel.updateUI();
    }
}
