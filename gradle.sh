#!/bin/sh -x

export TERM=xterm
./gradlew
gradle clean build
