#!/bin/bash
javac -d bin -sourcepath src --module-path /Library/Java/JavaVirtualMachines/1.7.0.jdk/Contents/Home/bin --add-modules javafx.base,javafx.media,javafx.graphics,javafx.controls --add-exports javafx.media/com.sun.media.jfxmedia.events=ALL-UNNAMED src/src_main/Controller.java
java -cp bin --module-path /Library/Java/JavaVirtualMachines/1.7.0.jdk/Contents/Home/bin --add-modules javafx.base,javafx.media,javafx.graphics,javafx.controls src_main.Controller
