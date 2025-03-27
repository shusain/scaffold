package com.shaunhusain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BashLike {
    private static Logger logger = LoggerFactory.getLogger(BashLike.class);

    public static void mkdir(String targetDirectory) {
        File theDir = new File(targetDirectory);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
    }

    
    public static void exec(String command, String workingDirectory) {
        exec(command, workingDirectory, false);
    }
    
    public static void exec(String command, String workingDirectory, Boolean verbose) {
        Process p = null;
        ProcessBuilder pb;

        switch (OSDetect.getOS()) {
            case WINDOWS:
                pb  = new ProcessBuilder("cmd.exe", "/c", command);
                break;
            default:
                pb  = new ProcessBuilder("/bin/bash","-c", command);
                break;
        }
        pb.directory(new File(workingDirectory));

        // Redirects error to std-out so process output/errors can
        // easily be monitored on a single stream.
        pb.redirectErrorStream(true);

        try {
            p = pb.start();

            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader buff = new BufferedReader (isr);

            String line;

            if(verbose) {
                while((line = buff.readLine()) != null) {
                    logger.info(line);
                }
            }

            // Waiting for the process to complete to make sure exec calls back
            // to back run sequentially (one completes before the next begins)
            p.waitFor();
            
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }


    /**
     * Writes out given contents to the filePath.
     * 
     * @param filePath
     * @param contents
     */
    public static void writeFile(String filePath, String contents) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(contents);
            logger.info("Successfully wrote to the file to: " + filePath);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
