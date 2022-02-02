plugins {
    java
    id("org.openjfx.javafxplugin") version "0.0.10"
}

group = "org.ansere"
version = ""

repositories {
    mavenCentral()
}

javafx {
    version = "17"
    modules("javafx.controls", "javafx.fxml", "javafx.base", "javafx.graphics", "javafx.media", "javafx.web", "javafx.swing")
}