plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}

android {
    compileSdk 33
    namespace 'com.example.happybirthday'
    defaultConfig {
        applicationId "com.example.happybirthday"
        minSdk 21
        targetSdk 33
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary true
    }

    buildTypes.release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = '17'
    }
    buildFeatures.compose true
    composeOptions.kotlinCompilerExtensionVersion compose_compiler_version // 1.4.3
    packagingOptions.resources.excludes.add('/META-INF/{AL2.0,LGPL2.1}')
}

dependencies {
    implementation platform('androidx.compose:compose-bom:2023.01.00')   // чтобы не указывать последние верии compose
    androidTestImplementation platform('androidx.compose:compose-bom:2023.01.00') // not need

    implementation 'androidx.core:core-ktx:1.9.0'   // 	1.10.0-alpha02
    implementation 'androidx.activity:activity-compose:1.6.1'  //  	1.8.0-alpha01  1.7.0-beta01
    implementation 'androidx.compose.material3:material3:1.0.1' // 1.0.1   	1.1.0-alpha06  not need
    implementation "androidx.compose.material:material:$compose_material_version"  // 1.3.1  	1.4.0-beta01
    implementation "androidx.compose.ui:ui:$compose_version"  // 1.3.3  1.4.0-beta01
    implementation "androidx.compose.ui:ui-tooling-preview:$compose_version"  // 1.3.3  1.4.0-beta01
    implementation "androidx.compose.ui:ui-graphics:$compose_version"  // 1.3.3  1.4.0-beta01 not need

    debugImplementation "androidx.compose.ui:ui-tooling:$compose_version"  // 1.3.3  1.4.0-beta01
    debugImplementation "androidx.compose.ui:ui-test-manifest:$compose_version" // 1.3.3  1.4.0-beta01 not need
    androidTestImplementation "androidx.compose.ui:ui-test-junit4:$compose_version"  // 1.3.3  1.4.0-beta01

    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'






    //androidTestImplementation 'androidx.compose.ui:ui-test-junit4'
    //debugImplementation 'androidx.compose.ui:ui-tooling'

    // implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.5.1'
    // implementation 'com.google.android.material:material:1.8.0'   //  1.9.0-alpha02
}