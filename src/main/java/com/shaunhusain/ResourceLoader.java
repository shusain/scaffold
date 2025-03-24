package com.shaunhusain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceLoader {
    
    public void readTestResource(){
        InputStream is = Scaffold.class.getClassLoader().getResourceAsStream("test.html");
        
        try (BufferedReader r = new BufferedReader(new InputStreamReader(is));) {
            System.out.println("=== Resource Contents ===");
            while (r.ready()) {
                String nextLine = r.readLine();
                System.out.println(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("=== End ===");
    }
}
