package com.shaunhusain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PackageJSONEditor {
    private static Logger logger = LoggerFactory.getLogger(PackageJSONEditor.class);

    ObjectNode packageNodeTree;
    ObjectMapper mapper = new ObjectMapper();

    public ObjectNode getPackageTree() {
        return packageNodeTree;
    }

    public PackageJSONEditor(String packageJSON) {
        init(packageJSON);
    }

    public PackageJSONEditor(String targetDirectory, boolean asFilePath) {
        try {
            String packageJSON = new String(Files.readAllBytes(Paths.get(targetDirectory+File.separator+"package.json")));
            init(packageJSON);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void init(String packageJSON) {
        try {
            JsonNode temp = mapper.readTree(packageJSON);
            if(temp.isObject())
            {
                packageNodeTree = (ObjectNode) temp;
            }
            else {
                logger.error("Failed to parse package.json as an object. \nContact Scaffold dev for assistance");
            }
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void replaceScripts(String updatedScripts){
        replaceScripts(updatedScripts, false);
    }

    public void replaceScripts(String updatedScripts, boolean verbose) {
        ObjectMapper mapper = new ObjectMapper();

        if(verbose) {
            logger.info("Scripts before replacement");
            logger.info(packageNodeTree.get("scripts").toPrettyString());
        }

        JsonNode temp;
        try {
            temp = (JsonNode)mapper.readTree(updatedScripts);
            packageNodeTree.set("scripts", temp);

            if(verbose) {
                logger.info("After replacement:");
                logger.info(packageNodeTree.toPrettyString());
            }
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setSource(String value) {
        packageNodeTree.put("source", value);
    }

    public void setMain(String value) {
        packageNodeTree.put("main", value);
    }
    
    public void removeMain(){
        packageNodeTree.remove("main");
    }
}
