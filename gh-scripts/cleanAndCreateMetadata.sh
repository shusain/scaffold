#!/bin/bash


# Clear out metadata
rm -rf src/main/resources/META-INF/native-image/
mkdir src/main/resources/META-INF/native-image/

# default run (uses ts-cli option)
java -agentlib:native-image-agent=config-output-dir=./src/main/resources/META-INF/native-image/ \
-jar target/scaffold-jar-with-dependencies.jar /tmp/test-default-cli


# Notice use config-merge-dir, to add extra executions

# TS-Pixi
java -agentlib:native-image-agent=config-merge-dir=./src/main/resources/META-INF/native-image/ \
-jar target/scaffold-jar-with-dependencies.jar -t ts-pixi /tmp/test-pixi

# TS-Express
java -agentlib:native-image-agent=config-merge-dir=./src/main/resources/META-INF/native-image/ \
-jar target/scaffold-jar-with-dependencies.jar -t ts-express /tmp/test-express

# TS-Matter
java -agentlib:native-image-agent=config-merge-dir=./src/main/resources/META-INF/native-image/ \
-jar target/scaffold-jar-with-dependencies.jar -t ts-matter /tmp/test-matter

# Cleaning up test folders since metadata should be updated now these aren't needed
# rm -rf /tmp/test-default-cli
# rm -rf /tmp/test-pixi
# rm -rf /tmp/test-express
# rm -rf /tmp/test-matter