#!/bin/bash

# Create bin directory
rm -rf ./bin
mkdir -p ./bin

# Compile class files in bin directory
javac *.java -d ./bin

# Create TheSQL.jar in main directory
cd ./bin
jar cmvf ../META-INF/MANIFEST.MF ../TheSQL.jar ./*.class ../postgresql-42.2.8.jar

cd ..
# Run jar file
java -jar TheSQL.jar
