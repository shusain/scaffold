package com.shaunhusain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceLoader {
    private static Logger logger = LoggerFactory.getLogger(PackageJSONEditor.class);

    public String readTestResource(String resourceRelPath) {
        return readTestResource(resourceRelPath,false);
    }
    public String readTestResource(String resourceRelPath, Boolean verbose) {
        InputStream is = Scaffold.class.getClassLoader().getResourceAsStream(resourceRelPath);
        // InputStream is = Scaffold.class.getClassLoader().getResourceAsStream("test.html");
        StringBuilder sb = new StringBuilder();
        
        try (BufferedReader r = new BufferedReader(new InputStreamReader(is));) {
            if(verbose)
                logger.info("=== Resource Contents ===");
            
            while (r.ready()) {
                String nextLine = r.readLine();
                sb.append(nextLine + System.lineSeparator());
                if(verbose) {
                    logger.info(nextLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if(verbose) {
            logger.info("=== End ===");
        }
        return sb.toString();
    }
}
