package untils;

import java.io.*;

public class RewriteFile {

    public static void reWrite(String formPath, String toPath){
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(formPath);
            out = new FileOutputStream(toPath);
            byte[] buf = new byte[31457280];
            int bytesRead;
            while ((bytesRead = in.read(buf)) > 0) {
                out.write(buf, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
