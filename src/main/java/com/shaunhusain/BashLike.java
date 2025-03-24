package com.shaunhusain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BashLike {
    
    public static void mkdir(String targetDirectory) {
        File theDir = new File(targetDirectory);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
    }


    public static void exec(String command, String workingDirectory) {
        Process p = null;

        ProcessBuilder pb = new ProcessBuilder("/bin/bash","-c", command);
        pb.directory(new File(workingDirectory));

        try {
            p = pb.start();

            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader buff = new BufferedReader (isr);

            String line;
            while((line = buff.readLine()) != null)
                System.out.println(line);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }


    public static void writeFile(String filePath, String contents) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(contents);
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }


    public static void echo(String msg) {
        System.out.println(msg);
    }
}
