package com.shaunhusain.scaffolders.typescript;

import static com.shaunhusain.BashLike.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shaunhusain.PackageJSONEditor;
import com.shaunhusain.ResourceLoader;
import com.shaunhusain.scaffolders.IScaffold;

public class PixiScaffolder implements IScaffold {
    private static Logger logger = LoggerFactory.getLogger(PixiScaffolder.class);

    public void scaffold(String targetDirectory) {
        logger.info("Making project directory: " + targetDirectory + "\n");
        mkdir(targetDirectory);

        logger.info("Initializing npm with defaults accepted");
        exec("npm init -y", targetDirectory);

        logger.info("Installing typescript and intializing tsconfig.json with defaults");
        exec("npm install typescript ts-node parcel pixi.js", targetDirectory);
        
        exec("npx tsc --init", targetDirectory);

        try {
            // loading prebaked index.html
            ResourceLoader rl = new ResourceLoader();
            String indexFile = rl.readTestResource("templates/node-typescript-pixijs/src/index.html");
            writeFile(targetDirectory+File.separator+"index.html", indexFile);

            String appFile = rl.readTestResource("templates/node-typescript-pixijs/src/AppPixi.ts");
            writeFile(targetDirectory+File.separator+"AppPixi.ts", appFile);
            

            // Making updates to the package.json after initialization and install steps
            String packageJSON = new String(Files.readAllBytes(Paths.get(targetDirectory+File.separator+"package.json")));
            PackageJSONEditor packageEditor = new PackageJSONEditor(packageJSON);
            packageEditor.setSource("index.html");
            packageEditor.removeMain();
            packageEditor.replaceScripts(
            """
            {\r
                "start": "parcel",\r
                "build": "parcel build"\r
            }\r
            """);



            writeFile(targetDirectory+File.separator+"package.json", packageEditor.getPackageTree().toPrettyString());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }    
}
