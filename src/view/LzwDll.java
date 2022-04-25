package view;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface LzwDll extends Library {

    LzwDll dll = (LzwDll) Native.load("view/LzwDll.dll", LzwDll.class);

    void unzip(String fromPath, String toPath);
    void compress(String fromPath, String toPath);

}
