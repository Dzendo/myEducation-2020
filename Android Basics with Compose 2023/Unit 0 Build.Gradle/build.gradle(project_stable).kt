// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    ext {
        compose_compiler_version = '1.4.3'  // for Kotlin 1.8.10
        compose_version = '1.3.3'  // 1.4.0-beta01
        compose_material_version = '1.3.1'  // 	1.4.0-beta01
    }
}
plugins {
    id 'com.android.application' version '8.1.0-alpha06' apply false
    id 'com.android.library' version '8.1.0-alpha06' apply false
    id 'org.jetbrains.kotlin.android' version '1.8.10' apply false
}

tasks.register('clean') {
    delete rootProject.buildDir
}