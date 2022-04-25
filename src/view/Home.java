package view;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Fri Feb 04 23:23:27 CST 2022
 */



/**
 * @author 1
 */
public class Home extends JFrame {

    private ImageIcon image = new ImageIcon(getClass().getResource("/images/background.jpg"));
    public Home() {
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image.getImage(),0,0,this.getWidth(),this.getHeight(),null);
            }
        };
        setContentPane(panel);
        initComponents();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void unzipButtonMouseClicked(MouseEvent e) {
        new Unzip(this);
    }

    private void compressButtonMouseClicked(MouseEvent e) {
        new Compress(this);
    }

    private void initComponents() {
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        unzipButton = new JButton();
        compressButton = new JButton();
        label1 = new JLabel();

        //======== this ========
        setBackground(new Color(60, 151, 255));
        setForeground(new Color(51, 51, 255));
        setTitle("\u5927\u5c71\u5e2e\u538b\u7f29");
        setIconImage(new ImageIcon(getClass().getResource("/images/logo.jpg")).getImage());
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- unzipButton ----
        unzipButton.setFont(unzipButton.getFont().deriveFont(24f));
        unzipButton.setIcon(new ImageIcon(getClass().getResource("/images/compress.png")));
        unzipButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                unzipButtonMouseClicked(e);
            }
        });
        contentPane.add(unzipButton);
        unzipButton.setBounds(230, 175, 150, 130);

        //---- compressButton ----
        compressButton.setFont(compressButton.getFont().deriveFont(24f));
        compressButton.setIcon(new ImageIcon(getClass().getResource("/images/unzip.png")));
        compressButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                compressButtonMouseClicked(e);
            }
        });
        contentPane.add(compressButton);
        compressButton.setBounds(400, 175, 150, 130);
        contentPane.add(label1);
        label1.setBounds(755, 440, 55, 30);

        contentPane.setPreferredSize(new Dimension(785, 540));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton unzipButton;
    private JButton compressButton;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
