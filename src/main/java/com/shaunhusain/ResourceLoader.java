package com.shaunhusain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceLoader {
    
    public String readTestResource(String resourceRelPath) {
        return readTestResource(resourceRelPath,false);
    }
    public String readTestResource(String resourceRelPath, Boolean verbose) {
        InputStream is = Scaffold.class.getClassLoader().getResourceAsStream(resourceRelPath);
        // InputStream is = Scaffold.class.getClassLoader().getResourceAsStream("test.html");
        StringBuilder sb = new StringBuilder();
        
        try (BufferedReader r = new BufferedReader(new InputStreamReader(is));) {
            if(verbose)
                System.out.println("=== Resource Contents ===");
            
            while (r.ready()) {
                String nextLine = r.readLine();
                sb.append(nextLine + System.lineSeparator());
                if(verbose) {
                    System.out.println(nextLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if(verbose) {
            System.out.println("=== End ===");
        }
        return sb.toString();
    }
}
