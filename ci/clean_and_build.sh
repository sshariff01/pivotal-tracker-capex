#!/bin/sh -x

export TERM=xterm
pushd ..
./gradlew clean build
popd
