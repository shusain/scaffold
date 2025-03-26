package com.shaunhusain;

import static com.shaunhusain.BashLike.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TypescriptPixiScaffolder {

    void scaffold(String targetDirectory) {
        echo("Making project directory: " + targetDirectory + "\n");
        mkdir(targetDirectory);

        echo("Initializing npm with defaults accepted");
        exec("npm init -y", targetDirectory);

        echo("Installing typescript and intializing tsconfig.json with defaults");
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



            writeFile(targetDirectory+File.separator+"package.json", packageEditor.packageNodeTree.toPrettyString());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            


        // // String path = System.getenv( "PATH" );
        // // System.out.println(String.format("The system path is: %s", path));


        // exec("npx json --in-place -f package.json -e 'this.scripts={\"start\": \"ts-node index.ts\"}'", targetDirectory);
        
        // // Loading up resource from baked in resources for given template type and writing out to target directory
        // ResourceLoader rl = new ResourceLoader();
        // String indexTSContents = rl.readTestResource("templates/node-typescript-cli/index.ts");
        // writeFile(targetDirectory+File.separator+"index.ts", indexTSContents);

        // echo("Adjust the tsconfig.json as necessary for the target or output and compiler flags");
        // echo("Creating empty index.ts to start");
    }    
}
