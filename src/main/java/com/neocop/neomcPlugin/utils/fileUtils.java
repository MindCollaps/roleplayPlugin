/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.neomcPlugin.utils;

import com.neocop.neomcPlugin.port.PortList;
import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Noah
 */
public class fileUtils {

    public static Object loadObject(String path) {
        String filePath = new File(path).getAbsolutePath();
        File file = new File(filePath);
        System.out.println("\n[File loader] Loading Object Flile: " + filePath);
        FileInputStream stream = null;
        ObjectInputStream objStream = null;
        Object obj = null;

        createFileRootAndFile(file);

        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            objStream = new ObjectInputStream(stream);
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            obj = objStream.readObject();
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            objStream.close();
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            stream.close();
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return obj;
    }

    public static void saveOject(String path, Object obj) {
        String filePath = new File(path).getAbsolutePath();
        File file = new File(filePath);
        System.out.println("\n[File loader] Save Object File: " + filePath);
        FileOutputStream stream = null;
        ObjectOutputStream objStream = null;

        createFileRootAndFile(file);

        try {
            stream = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            objStream = new ObjectOutputStream(stream);
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            objStream.writeObject(obj);
            objStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            objStream.close();
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            stream.close();
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static PortList loadPorts(String path) {
        String filePath = new File(path).getAbsolutePath();
        File file = new File(filePath);
        System.out.println("\n[File loader] Loading Object Flile: " + filePath);
        FileInputStream stream = null;
        ObjectInputStream objStream = null;
        PortList obj = null;

        createFileRootAndFile(file);

        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            objStream = new ObjectInputStream(stream);
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            obj = (PortList) objStream.readObject();
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            objStream.close();
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            stream.close();
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return obj;
    }

    public static void savePorts(String path, PortList obj) {
        String filePath = new File(path).getAbsolutePath();
        File file = new File(filePath);
        System.out.println("\n[File loader] Save Object File: " + filePath);
        FileOutputStream stream = null;
        ObjectOutputStream objStream = null;

        createFileRootAndFile(file);

        try {
            stream = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            objStream = new ObjectOutputStream(stream);
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            objStream.writeObject(obj);
            objStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            objStream.close();
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            stream.close();
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static String loadReadebleTxtFile(String path) {
        String filePath = new File(path).getAbsolutePath();
        System.out.println("\n[File loader] Read Object File: " + filePath);
        File file = new File(filePath);
        createFileRootAndFile(file);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FileReader reader = null;
        BufferedReader bufferReader = null;
        String txt = "";
        String add = "";

        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        bufferReader = new BufferedReader(reader);
        try {
            for (int i = 0; bufferReader.read() >= i; i++) {
                add = bufferReader.readLine();
                txt += add;
            }
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (txt.isEmpty()) {
            System.out.println("\n[File Loader] readed empty file");
        } else {
            System.out.println("\n[File Loader] readed: \n" + txt);
        }
        try {
            bufferReader.close();
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return txt;
    }

    public static void saveReadbleFile(String txt, String path) {
        String filePath = new File(path).getAbsolutePath();
        System.out.println("\n[File loader] Save Readable Flile: " + filePath);
        File file = new File(filePath);
        FileWriter fw = null;

        createFileRootAndFile(file);
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        createFileRootAndFile(file);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            fw = new FileWriter(file);
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fw.write(txt);
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void createFileRootAndFile(File file) {
        String pas = file.getAbsolutePath().replace("\\", "/");
        String[] path = pas.split("/");
        String pat = path[0] + "/";
        for (int i = 1; i < path.length - 1; i++) {
            pat = pat + "/" + path[i];
        }
        File dir = new File(pat);
        if (!dir.mkdirs()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                System.err.println("[file Utils create File Dirs] cant create file dirs");
            }
        }
        createFiles(file);
    }
    
    public static void createFiles(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("[file Utils create File Dirs] cant create file dirs");
            }
        }
    }
}
