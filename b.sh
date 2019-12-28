#!/bin/bash
javac -d bin -sourcepath src --module-path $PATH_TO_FX --add-modules javafx.base,javafx.media,javafx.graphics,javafx.controls --add-exports javafx.media/com.sun.media.jfxmedia.events=ALL-UNNAMED src/src_main/Controller.java
java -cp bin --module-path $PATH_TO_FX --add-modules javafx.base,javafx.media,javafx.graphics,javafx.controls src_main.Controller
