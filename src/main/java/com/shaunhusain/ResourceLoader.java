package com.shaunhusain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceLoader {
    
    public String readTestResource(){
        InputStream is = Scaffold.class.getClassLoader().getResourceAsStream("templates/node-typescript-cli/index.ts");
        // InputStream is = Scaffold.class.getClassLoader().getResourceAsStream("test.html");
        StringBuilder sb = new StringBuilder();
        
        try (BufferedReader r = new BufferedReader(new InputStreamReader(is));) {
            System.out.println("=== Resource Contents ===");
            while (r.ready()) {
                String nextLine = r.readLine();
                sb.append(nextLine);
                System.out.println(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("=== End ===");
        return sb.toString();
    }
}
