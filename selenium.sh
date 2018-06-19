#!/bin/bash

BROWSER="phantomjs"

case "$1" in
  firefox)
    BROWSER="firefox"
    ;;
  chrome)
    BROWSER="chrome"
    ;;
  safari)
    BROWSER="safari"
    ;;
esac

mvn clean test -D=browser="$BROWSER" -Dtest=at.qe.sepm.skeleton.tests.GUITest
