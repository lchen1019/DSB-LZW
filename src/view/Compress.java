/*
 * Created by JFormDesigner on Sat Feb 05 12:54:10 CST 2022
 */

package view;

import com.formdev.flatlaf.FlatLightLaf;
import untils.IdUtils;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.swing.*;

/**
 * @author 1
 */
public class Compress extends JFrame {
    JFrame homeFrame;

    public Compress(JFrame frame) {
        homeFrame = frame;
        setVisible(true);
        initComponents();
    }

    private void compressFromButtonMouseClicked(MouseEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.showOpenDialog(homeFrame);
        String path = chooser.getSelectedFile().getAbsolutePath();
        System.out.println(path);
        compressFromText.setText(path);
    }

    private void compressToButtonMouseClicked(MouseEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showOpenDialog(homeFrame);

        String path = chooser.getSelectedFile().getAbsolutePath();
        System.out.println(path);
        compressToText.setText(path);
    }

    //绘制对话框的方法
    public void paintDialog(String title,String text){
        JDialog dialog = new JDialog(this,true);
        dialog.setSize(250,130);
        dialog.setLocationRelativeTo(this);
        dialog.setTitle(title);
        JLabel jLabel = new JLabel(text);
        jLabel.setFont(new Font("宋体",Font.BOLD,16));
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalStrut(20));
        box.add(jLabel);
        dialog.add(box);
        dialog.setVisible(true);
    }

    private void startMouseClicked(MouseEvent e) {
        try {
            // 获取路径
            File fromFile = new File(compressFromText.getText());
            if(!fromFile.exists()){
                paintDialog("警告","待压缩文件不存在");
                return;
            }
            File directory = new File(compressToText.getText());
            if(!directory.exists()) {
                paintDialog("警告","压缩至目录不存在");
                return;
            }
            // 写入programData
            File file = new File("C:\\ProgramData\\Lzw");
            if(!file.exists())
                file.mkdirs();
            FileInputStream in = new FileInputStream(fromFile);
            String[] str = compressFromText.getText().split("\\\\");
            String name = str[str.length - 1];
            int index = name.lastIndexOf(".");
            String prefix = name.substring(0,index);
            String readId = IdUtils.getUniqueIdByUUID();
            String readPath = "C:\\ProgramData\\Lzw\\"+readId+".temp";
            FileOutputStream out = new FileOutputStream(readPath);
            byte[] bytes = new byte[31457280];
            byte[] bytes_ = (name+"\n").getBytes(StandardCharsets.UTF_8);
            out.write(bytes_);
            int number;
            while ((number = in.read(bytes)) > 0) {
                out.write(bytes,0,number);
            }
            in.close();
            out.close();

            // 调用接口
            long time1 = System.currentTimeMillis();
            String writeId = IdUtils.getUniqueIdByUUID();
            String writePath = "C:\\ProgramData\\Lzw\\"+writeId+".cl";
            LzwDll.dll.compress(readPath,writePath);
            long time2 = System.currentTimeMillis();
            System.out.println(time2 - time1);

            // 将文件重写写入到正确位置
            FileInputStream inLast = new FileInputStream(writePath);
            FileOutputStream outLast = new FileOutputStream(compressToText.getText()+"\\"+prefix+".cl");
            while ((number = inLast.read(bytes)) > 0) {
                outLast.write(bytes,0,number);
            }
            paintDialog("提示","压缩完成，用时"+(time2 - time1) + "ms");
            inLast.close();
            outLast.close();
            this.setVisible(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void initComponents() {
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        compressFromText = new JTextField();
        label2 = new JLabel();
        compressToText = new JTextField();
        label4 = new JLabel();
        compressToButton = new JButton();
        compressFromButton = new JButton();
        start = new JButton();

        //======== this ========
        setTitle("\u5927\u5c71\u5e2e\u538b\u7f29");
        setIconImage(new ImageIcon(getClass().getResource("/images/logo.jpg")).getImage());
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(label1);
        label1.setBounds(605, 365, 60, 25);

        //---- compressFromText ----
        compressFromText.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
        contentPane.add(compressFromText);
        compressFromText.setBounds(150, 60, 315, 45);

        //---- label2 ----
        label2.setText("\u538b\u7f29\u6587\u4ef6\u8def\u5f84");
        label2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 19));
        contentPane.add(label2);
        label2.setBounds(30, 65, 120, 35);

        //---- compressToText ----
        compressToText.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
        contentPane.add(compressToText);
        compressToText.setBounds(150, 165, 315, 45);

        //---- label4 ----
        label4.setText("\u538b\u7f29\u81f3");
        label4.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 19));
        contentPane.add(label4);
        label4.setBounds(35, 170, 70, 35);

        //---- compressToButton ----
        compressToButton.setText("\u6d4f\u89c8");
        compressToButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
        compressToButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                compressToButtonMouseClicked(e);
            }
        });
        contentPane.add(compressToButton);
        compressToButton.setBounds(505, 165, 100, 45);

        //---- compressFromButton ----
        compressFromButton.setText("\u6d4f\u89c8");
        compressFromButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
        compressFromButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                compressFromButtonMouseClicked(e);
            }
        });
        contentPane.add(compressFromButton);
        compressFromButton.setBounds(500, 60, 100, 45);

        //---- start ----
        start.setText("\u538b\u7f29");
        start.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startMouseClicked(e);
            }
        });
        contentPane.add(start);
        start.setBounds(275, 280, 100, 45);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JTextField compressFromText;
    private JLabel label2;
    private JTextField compressToText;
    private JLabel label4;
    private JButton compressToButton;
    private JButton compressFromButton;
    private JButton start;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
