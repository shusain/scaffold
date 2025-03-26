package com.shaunhusain;

import java.io.File;

import static com.shaunhusain.BashLike.*;

public class TypescriptCLIScaffolder {

    void scaffold(String targetDirectory) {
        echo("Making project directory: " + targetDirectory + "\n");
        mkdir(targetDirectory);

        // String path = System.getenv( "PATH" );
        // System.out.println(String.format("The system path is: %s", path));

        echo("Initializing npm with defaults accepted");
        exec("npm init -y", targetDirectory);

        echo("Installing typescript and intializing tsconfig.json with defaults\n");
        exec("npm install typescript ts-node @types/node", targetDirectory);
        
        exec("npx tsc --init", targetDirectory);
        

        PackageJSONEditor packageEditor = new PackageJSONEditor(targetDirectory, true);
        packageEditor.setMain("index.ts");
        packageEditor.replaceScripts(
        """
        {
            "start": "ts-node index.ts"
        }
        """);
        writeFile(targetDirectory+File.separator+"package.json", packageEditor.packageNodeTree.toPrettyString());

        // Loading up resource from baked in resources for given template type and writing out to target directory
        ResourceLoader rl = new ResourceLoader();
        String indexTSContents = rl.readTestResource("templates/node-typescript-cli/index.ts");
        writeFile(targetDirectory+File.separator+"index.ts", indexTSContents);

        echo("Adjust the tsconfig.json as necessary for the target or output and compiler flags");
        echo("Creating empty index.ts to start");
    }    
}
