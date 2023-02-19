plugins {
    id("xyz.deftu.gradle.multiversion-root")
}

preprocess {
    val fabric11902 = createNode("1.19.2-fabric", 11902, "yarn")
}
