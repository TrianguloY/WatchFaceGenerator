# Watchface generator

This is the actual code of the custom Kotlin dsl generator for watchfaces.

It is divided into 3 packages:

- [xml](./src/main/java/com/trianguloy/generator/xml) contains a very minor dsl to build xml files.

- [wff](./src/main/java/com/trianguloy/generator/wff) contains the dsl to build a full watchface xml file (using the xml from before). Using this package should be equivalent as manually creating the watchface.xml file, but using Kotlin instead.

- [custom](./src/main/java/com/trianguloy/generator/custom) contains utility methods to use the wff dsl using way less code. This is the recommended main api.