#!/bin/bash

if [ -e volacal.jar ]; then
	echo "Using jar file"
	java -jar volacal.jar
elif [ -e classes/Main.class ]; then
	echo "Using class files directly"
	java -cp classes/ Main
else 
	echo "Nothing to run"
fi
