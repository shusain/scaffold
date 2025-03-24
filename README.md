# WIP Work in Progress
Project is just setup as a starting point for building native executables from JARs for the CLI tool and some basic structure. Still need to migrate some bash scripted functionality into here for scaffolding out projects and load default resources into directories to build new projects.

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

# Troubleshooting

The maven dependencies plugin is setup to copy all the project dependency JAR into `target/lib`.

A set of JARs can be loaded into the class-path and the app can be run more directly with `java -cp` like:

`java -cp "target/scaffold-1.0-SNAPSHOT.jar:target/lib/*" com.shaunhusain.Scaffold ./`

Similarly to build a native image (notice don't include the com.shaunhusain.Scaffold args to the app when building native-image).

```bash
# Building the executable from JAR
# Extra flags to bundle resources not found by static code analysis by Graal native-image builder
native-image \
-H:IncludeResources=test.html \
-cp "target/scaffold-1.0-SNAPSHOT.jar:target/lib/*" com.shaunhusain.Scaffold testing

# Example execution
./testing -t ts-pixi SOME_PATH_HERE
```

To update the metadata for the program before running a native build execute the app like:

```bash
# Clear out metadata
rm -rf src/main/resources/META-INF/native-image/
mkdir src/main/resources/META-INF/native-image/

java -agentlib:native-image-agent=config-output-dir=./src/main/resources/META-INF/native-image/ \
-jar target/scaffold-jar-with-dependencies.jar ./
```

https://www.graalvm.org/latest/reference-manual/native-image/metadata/AutomaticMetadataCollection/