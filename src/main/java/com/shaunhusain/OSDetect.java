package com.shaunhusain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OSDetect {
    private static Logger logger = LoggerFactory.getLogger(OSDetect.class);

    public enum OS {
        WINDOWS, LINUX, MAC, SOLARIS
    };// Operating systems.

    private static OS os = null;

    public static OS getOS() {
        if (os == null) {
            String operSys = System.getProperty("os.name").toLowerCase();
            if (operSys.contains("win")) {
                os = OS.WINDOWS;
            } else if (operSys.contains("nix") || operSys.contains("nux")
                    || operSys.contains("aix")) {
                os = OS.LINUX;
            } else if (operSys.contains("mac")) {
                os = OS.MAC;
            } else if (operSys.contains("sunos")) {
                os = OS.SOLARIS;
            }
        }
        return os;
    }

    public static void printPATHenvVar() {
        String path = System.getenv( "PATH" );
        logger.info(String.format("The system path is: %s", path));
    }
}