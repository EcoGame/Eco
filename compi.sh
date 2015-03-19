#!/bin/bash
reset
echo Returning from the ashes to live another day…
cd $(dirname "$0")
echo Compiling...
javac -cp .:lib/* src/**/*.java
echo Compiled...
cd src
echo Running…
java -cp .:../lib/* -Djava.library.path=../native eco/Main
cd eco
rm -f *.class
