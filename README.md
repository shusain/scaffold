# Intro
Scaffold tool meant to help simplify the process of getting up and running with using Typescript (or other languages) with other supporting libs for various tasks.

Download a prebuilt release from the [releases page](https://github.com/shusain/scaffold/releases/)

Feel free to rename or move the binary to a convenient location like `/usr/local/bin` or any other location in your PATH or update your PATH so it can be used from any folder/directory in a terminal.

Common use cases for a Typescript app are:

Template Name | Description
------------- | --------------
ts-cli        | CLI/console/terminal text-based apps.
ts-express    | ExpressJS based APIs
ts-pixi       | PixiJS+Parcel graphics/animation/asset library example
ts-matter     | MatterJS+Parcel 2D Physics Engine

More to come.

# Prerequisite

To run the prebuilt executable you will need Node v20 or higher installed already.  Use `nvm` or any other means of getting `node/npm` onto the system environment PATH.

Use: https://nodejs.org/en/download

Or for bare minimum to get `node/npm` setup on linux:

```bash

wget https://nodejs.org/dist/v20.19.0/node-v20.19.0-linux-x64.tar.xz     # Download the compressed package
tar xvfJ node-v20.19.0-linux-x64.tar.xz                                  # Extract the contents
cd node-v20.19.0-linux-x64                                               # Go into extracted folder
pwd                                                                      # Print working/current directory full path

export PATH=$(pwd)/node-v20.19.0-linux-x64/bin:$PATH                     # Update current PATH env var so can use immediately
echo "export PATH=$(pwd)/node-v20.19.0-linux-x64/bin:\$PATH" >> ~/.bashrc # Update bash runtime config so each new bash session sets this up

```


---

To build and run you'll need OpenJDK 21 LTS for the native build GraalVM JDK is required for the `native-image` and supporting tools.

Work in Progress - Building native images for each platform so no JDK is necessary to use/run the tool.

# Compile and Run

`./mvnw clean compile exec:java`

Cleans any prebuilt .class files, rebuilds them with javac and executes the main class.

If want to test out the CLI args for the Java app pass them along like:

`./mvnw clean compile exec:java -Dexec.args=-h`

If already built to simply run the app:

`./mvnw exec:java -Dexec.args -h`

# Package

`./mvnw -P native package`

Creates a JAR in the target folder then uses `native-image` GraalVM command to convert the JAR into a native binary to run standalone.

# Run

After packaging the native build you can run the executable directly:

`./target/scaffold`

# Automated Build

GitHub Actions are used for automating builds for each platform (found on Github Releases for this repo).  The build runs a set of jobs (one for each target platform)

## Git Tagging

Example:
```
git tag # list all current tags, go to end for last made, bump by 1
git tag -a v0.1.1-beta -m "better logging"
git push origin v0.1.1-beta
```

# Troubleshooting

A set of JARs can be loaded into the class-path and the app can be run more directly with `java -cp` like:

`java -cp "target/scaffold-1.0-SNAPSHOT.jar:target/lib/*" com.shaunhusain.Scaffold ./`

Similarly to build a native image (notice don't include the com.shaunhusain.Scaffold args to the app when building native-image).

```bash
# Building the executable from JAR
# Extra flags to bundle resources not found by static code analysis by Graal native-image builder
native-image \
-H:IncludeResources=test.html \
-cp "target/scaffold-1.0-SNAPSHOT.jar:target/lib/*" com.shaunhusain.Scaffold scaffold-test

# Example execution
./scaffold-test -t ts-pixi /tmp/test-pixi
```

To update the metadata for the program before running a native build execute the app like:

```bash
# Clear out metadata
rm -rf src/main/resources/META-INF/native-image/
mkdir src/main/resources/META-INF/native-image/

java -agentlib:native-image-agent=config-output-dir=./src/main/resources/META-INF/native-image/ \
-jar target/scaffold-jar-with-dependencies.jar /tmp/test-default


# Notice use config-merge-dir, to add extra executions
java -agentlib:native-image-agent=config-merge-dir=./src/main/resources/META-INF/native-image/ \
-jar target/scaffold-jar-with-dependencies.jar -t ts-pixi /tmp/test-pixi
```

https://www.graalvm.org/latest/reference-manual/native-image/metadata/AutomaticMetadataCollection/
