package com.shaunhusain.scaffolders.typescript;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shaunhusain.PackageJSONEditor;
import com.shaunhusain.ResourceLoader;
import com.shaunhusain.scaffolders.IScaffold;

import static com.shaunhusain.BashLike.*;

public class ExpressAPIScaffolder implements IScaffold {
    private static Logger logger = LoggerFactory.getLogger(ExpressAPIScaffolder.class);

    public void scaffold(String targetDirectory) {
        
        logger.info("Making project directory: " + targetDirectory + "\n");
        mkdir(targetDirectory);

        logger.info("Initializing npm with defaults accepted");
        exec("npm init -y", targetDirectory);

        logger.info("Installing typescript and intializing tsconfig.json with defaults\n");
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
        writeFile(targetDirectory+File.separator+"package.json", packageEditor.getPackageTree().toPrettyString());

        // Loading up resource from baked in resources for given template type and writing out to target directory
        ResourceLoader rl = new ResourceLoader();
        String indexTSContents = rl.readTestResource("templates/node-typescript-expressjs/index.ts");
        writeFile(targetDirectory+File.separator+"index.ts", indexTSContents);

        logger.info("Adjust the tsconfig.json as necessary for the target or output and compiler flags");
        logger.info("Creating empty index.ts to start");
    }    
}
