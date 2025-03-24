package com.shaunhusain;

import java.io.File;

import static com.shaunhusain.BashLike.*;

public class TypescriptCLIScaffolder {

    void scaffold(String targetDirectory) {
        echo("Making project directory: $1");
        mkdir(targetDirectory);

        // String path = System.getenv( "PATH" );
        // System.out.println(String.format("The system path is: %s", path));

        echo("Initializing npm with defaults accepted\n");
        exec("npm init -y", targetDirectory);

        echo("Installing typescript and intializing tsconfig.json with defaults\n\n");
        exec("npm install typescript ts-node json", targetDirectory);
        
        exec("npx tsc --init", targetDirectory);
        exec("npx json --in-place -f package.json -e 'this.scripts={\"start\": \"ts-node index.ts\"}'", targetDirectory);
        
        ResourceLoader rl = new ResourceLoader();
        String indexTSContents = rl.readTestResource();
        writeFile(targetDirectory+File.separator+"index.ts", indexTSContents);

        echo("Adjust the tsconfig.json as necessary for the target or output and compiler flags");
        echo("Creating empty index.ts to start");
    }    
}
