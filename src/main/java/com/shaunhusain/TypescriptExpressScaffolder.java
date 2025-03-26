package com.shaunhusain;

import java.io.File;

import static com.shaunhusain.BashLike.*;

public class TypescriptExpressScaffolder {

    void scaffold(String targetDirectory) {
        echo("Making project directory: " + targetDirectory + "\n");
        mkdir(targetDirectory);

        // String path = System.getenv( "PATH" );
        // System.out.println(String.format("The system path is: %s", path));

        echo("Initializing npm with defaults accepted");
        exec("npm init -y", targetDirectory);

        echo("Installing typescript and intializing tsconfig.json with defaults\n");
        exec("npm install typescript ts-node express cors @types/cors @types/express", targetDirectory);
        
        exec("npx tsc --init", targetDirectory);
        exec("npx json --in-place -f package.json -e 'this.scripts={\n    \"start\": \"parcel\"\n    \"build\": \"parcel build\"}'", targetDirectory);
        

        // Making updates to the package.json after initialization and install steps
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
        String indexTSContents = rl.readTestResource("templates/node-typescript-expressjs/index.ts");
        writeFile(targetDirectory+File.separator+"index.ts", indexTSContents);

        echo("Adjust the tsconfig.json as necessary for the target or output and compiler flags");
        echo("Creating empty index.ts to start");
    }    
}
