/*
 * Created by JFormDesigner on Sat Feb 05 12:44:52 CST 2022
 */

package view;

import com.formdev.flatlaf.FlatLightLaf;
import untils.IdUtils;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author 1
 */
public class Unzip extends JFrame {

    JFrame homeFrame = new JFrame();

    public Unzip(JFrame frame) {
        setVisible(true);
        homeFrame = frame;
        initComponents();
    }

    private void unzipFromButtonMouseClicked(MouseEvent e) {
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        // 打开文件对话框
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.cl", "cl");
        chooser.addChoosableFileFilter(filter);
        chooser.showOpenDialog(homeFrame);
        String path = chooser.getSelectedFile().getAbsolutePath();
        System.out.println(path);
        unzipFromText.setText(path);
    }

    private void button4MouseClicked(MouseEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showOpenDialog(homeFrame);

        String path = chooser.getSelectedFile().getAbsolutePath();
        System.out.println(path);
        unzipToText.setText(path);
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
            // 校验合法性
            String fromPath = unzipFromText.getText();
            File file = new File(fromPath);
            if(!file.exists()) {
                paintDialog("警告","文件不存在");
                return;
            }
            if(!fromPath.endsWith(".cl")){
                paintDialog("警告","文件格式不为cl");
                return;
            }
            File directory = new File(unzipToText.getText());
            if(!directory.exists()) {
                paintDialog("警告","解压至目录不存在");
                return;
            }
            // 加载到缓存中
            String location = "C:\\ProgramData\\Lzw\\";
            String writeId = IdUtils.getUniqueIdByUUID();
            String readId = IdUtils.getUniqueIdByUUID();
            FileInputStream in = new FileInputStream(file);
            FileOutputStream out = new FileOutputStream(location+writeId+".temp");
            byte bytes[] = new byte[31457280];
            int number;
            while((number = in.read(bytes)) > 0) {
                out.write(bytes,0,number);
            }
            // 调用接口送至readId
            long time1 = System.currentTimeMillis();
            LzwDll.dll.unzip(location+writeId+".temp",location+readId+".temp");
            long time2 = System.currentTimeMillis();
            // 读出第一行为文件名
            FileInputStream inLast = new FileInputStream(location+readId+".temp");
            int size = inLast.read(bytes);
            int index;
            for (index = 0; index < size; index++) {
                if (bytes[index] == 10)
                    break;
            }
            String text = new String(bytes,0,index,StandardCharsets.UTF_8);
            System.out.println(text);
            FileOutputStream outLast = new FileOutputStream(unzipToText.getText()+"\\"+text);
            outLast.write(bytes,index + 1,size - index - 1);
            while((size = inLast.read(bytes)) > 0) {
                outLast.write(bytes,0,size);
            }
            inLast.close();
            outLast.close();
            // 完成
            paintDialog("提示","解压完成，解压用时"+(time2 - time1)+"ms");
            this.setVisible(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void unzipToButtonMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        unzipFromText = new JTextField();
        label3 = new JLabel();
        unzipToText = new JTextField();
        label4 = new JLabel();
        unzipToButton = new JButton();
        unzipFromButton = new JButton();
        start = new JButton();

        //======== this ========
        setTitle("\u5927\u5c71\u5e2e\u538b\u7f29");
        setIconImage(new ImageIcon(getClass().getResource("/images/logo.jpg")).getImage());
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(label1);
        label1.setBounds(605, 365, 60, 25);

        //---- unzipFromText ----
        unzipFromText.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
        contentPane.add(unzipFromText);
        unzipFromText.setBounds(150, 60, 315, 45);

        //---- label3 ----
        label3.setText("\u89e3\u538b\u6587\u4ef6\u8def\u5f84");
        label3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 19));
        contentPane.add(label3);
        label3.setBounds(30, 65, 120, 35);

        //---- unzipToText ----
        unzipToText.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
        contentPane.add(unzipToText);
        unzipToText.setBounds(150, 165, 315, 45);

        //---- label4 ----
        label4.setText("\u89e3\u538b\u81f3");
        label4.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 19));
        contentPane.add(label4);
        label4.setBounds(35, 170, 70, 35);

        //---- unzipToButton ----
        unzipToButton.setText("\u6d4f\u89c8");
        unzipToButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
        unzipToButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button4MouseClicked(e);
            }
        });
        contentPane.add(unzipToButton);
        unzipToButton.setBounds(505, 165, 100, 45);

        //---- unzipFromButton ----
        unzipFromButton.setText("\u6d4f\u89c8");
        unzipFromButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
        unzipFromButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                unzipFromButtonMouseClicked(e);
            }
        });
        contentPane.add(unzipFromButton);
        unzipFromButton.setBounds(500, 60, 100, 45);

        //---- start ----
        start.setText("\u89e3\u538b");
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
    private JTextField unzipFromText;
    private JLabel label3;
    private JTextField unzipToText;
    private JLabel label4;
    private JButton unzipToButton;
    private JButton unzipFromButton;
    private JButton start;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
