package com.shaunhusain;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

/**
 * Scaffolding tool for setting up projects with reasonable defaults. Initial
 * goal is to target TS projects since the configuration typically requires
 * at least a few steps to compile TS to JS and build/run projects.
 * 
 * This tool assumes you do already have node/npm installed and available on
 * your PATH environment variable.
 * 
 * The tool is bundled as a standalone native-image executable built from the JAR.
 * 
 * Java Sources/Resources -> Bundled into JAR -> Compiled into Native executable
 * 
 * This build will allow for all the necessary resources for each type of project
 * I want to be able to scaffold quickly to be baked in.
 * 
 * The default execution of the scaffold tool will print the help information
 * displaying available options.
 */
public class Scaffold 
{
    public static void main(String[] args )
    {
        setupArgParser(args);

        System.out.println( "Scaffold tool started" );

        ResourceLoader rl = new ResourceLoader();
        rl.readTestResource();

    }

    public static void setupArgParser(String[] args) {

        ArgumentParser parser = ArgumentParsers.newFor("App").build()
                .defaultHelp(true)
                .description("Generates initial project configurations for various development work.");
        parser.addArgument("-t", "--type")
                .choices("ts-cli", "ts-express", "ts-pixi", "ts-matter").setDefault("typescript-cli")
                .help("Specify template type to use. Options: {ts-cli,ts-express,ts-pixi,ts-matter}").metavar("");

        parser.addArgument("folder")
            .type(Arguments.fileType()
            // The output directory does not exist, but it is possible to create it.
            .verifyNotExists().verifyCanCreate()
            .or()
            // The output directory already exists, and it is possible to write to it.
            .verifyIsDirectory().verifyCanWrite());

        Namespace ns = null;

        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
        System.out.println(ns.getString("type"));
    }
}
