import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import dev.icerock.gradle.MRVisibility
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.mokoResourcesPlugin)
    alias(libs.plugins.buildKofnig)
}

buildscript {

    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.buildkonfig.gradle.plugin)
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.koin.core)

            implementation(libs.moko.resources)
            implementation(libs.moko.resources.compose) // for compose multiplatform
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)

//            implementation(libs.ktor.client.android)
            implementation(libs.sqldelight.android.driver)
            implementation(libs.androidx.compose.material3)
            implementation(libs.koin.androidx.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.pytorch.android)
            implementation(libs.accompanist.permissions)

            implementation(libs.androidx.ui.text.google.fonts)
            implementation(libs.material.icons.extended)
        }

        desktopMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(compose.desktop.currentOs)
            implementation(libs.sqldelight.jvm.driver)
        }

        commonTest.dependencies {
            implementation(libs.moko.resources.test)
        }
    }
}

android {

    namespace = "com.nmi.lexiloop"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.nmi.lexiloop"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            manifestPlaceholders["usesCleartextTraffic"] = "true"
        }
        getByName("release") {
            isMinifyEnabled = false
            manifestPlaceholders["usesCleartextTraffic"] = "false"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    dependencies {

//        implementation(libs.kotlinx.coroutines.android)

        debugImplementation(compose.uiTooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.nmi.lexiloop"
            packageVersion = "1.0.0"
        }
    }
}

sqldelight {
    databases {
        create("QuizDatabase") {
            packageName.set("com.nmi.lexiloop.cache")
        }
    }
}

multiplatformResources {
    resourcesPackage.set("com.nmi.lexiloop") // required
    resourcesClassName.set("MR") // optional, default MR
    resourcesVisibility.set(MRVisibility.Internal) // optional, default Public
//    iosBaseLocalizationRegion.set("en") // optional, default "en"
//    iosMinimalDeploymentTarget.set("11.0") // optional, default "9.0"
}

buildkonfig {
//    packageName Set the package name where BuildKonfig is being placed. Required.
//    objectName Set the name of the generated object. Defaults to BuildKonfig.
//    exposeObjectWithName Set the name of the generated object, and make it public.
//    defaultConfigs Set values which you want to have in common. Required.

    packageName = "com.nmi.lexiloop"
    // objectName = "YourAwesomeConfig"
    // exposeObjectWithName = "YourAwesomePublicConfig"

    defaultConfigs {

        val apiUrl: String = gradleLocalProperties(rootDir).getProperty("API_URL")

        require(apiUrl.isNotEmpty()) {
            "Get api url and store in in local.properties as `API_URL`"
        }

        buildConfigField(STRING, "API_URL", apiUrl)
    }
}