package com.shaunhusain.scaffolders.typescript;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shaunhusain.PackageJSONEditor;
import com.shaunhusain.ResourceLoader;
import com.shaunhusain.scaffolders.IScaffold;

import static com.shaunhusain.BashLike.*;

public class CommandLineAppScaffolder implements IScaffold {
    private static Logger logger = LoggerFactory.getLogger(CommandLineAppScaffolder.class);

    public void scaffold(String targetDirectory) {
        logger.info("Making project directory: " + targetDirectory + "\n");
        mkdir(targetDirectory);

        logger.info("Initializing npm with defaults accepted");
        exec("npm init -y", targetDirectory);

        logger.info("Installing typescript and intializing tsconfig.json with defaults\n");
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
        writeFile(targetDirectory + File.separator + "package.json", packageEditor.getPackageTree().toPrettyString());

        // Loading up resource from baked in resources for given template type and
        // writing out to target directory
        ResourceLoader rl = new ResourceLoader();
        String indexTSContents = rl.readTestResource("templates/node-typescript-cli/index.ts");
        writeFile(targetDirectory + File.separator + "index.ts", indexTSContents);

        logger.info("Adjust the tsconfig.json as necessary for the target or output and compiler flags");
        logger.info("Creating empty index.ts to start");
    }
}
