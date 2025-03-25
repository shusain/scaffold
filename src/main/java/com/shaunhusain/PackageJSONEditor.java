package com.shaunhusain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PackageJSONEditor {

    ObjectNode packageNodeTree;

    PackageJSONEditor(String packageJSON) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode temp = mapper.readTree(packageJSON);
            if(temp.isObject())
            {
                packageNodeTree = (ObjectNode) temp;
            }
            else {
                System.out.println("Failed to parse package.json as an object contact Scaffold dev for assistance");
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
            System.out.println("Scripts before replacement");
            System.out.println(packageNodeTree.get("scripts"));
        }

        JsonNode temp;
        try {
            temp = (JsonNode)mapper.readTree(updatedScripts);
            packageNodeTree.set("scripts", temp);

            if(verbose) {
                System.out.println("After replacement:");
                System.out.println(packageNodeTree.toPrettyString());
            }
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void setSource(String source) {
        packageNodeTree.put("source", source);
    }
    public void removeMain(){
        packageNodeTree.remove("main");
    }
}
