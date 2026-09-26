plugins { id("com.android.application") }

android {
    namespace = "com.madamgames.dominomada"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.madamgames.dominomada"
        minSdk = 24
        targetSdk = 35
        versionCode = 2
        versionName = "1.0.1"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    val signingStoreFile = System.getenv("LUDOMADA_SIGNING_STORE_FILE")
    val signingStorePassword = System.getenv("LUDOMADA_SIGNING_STORE_PASSWORD")
    val signingKeyAlias = System.getenv("LUDOMADA_SIGNING_KEY_ALIAS")
    val signingKeyPassword = System.getenv("LUDOMADA_SIGNING_KEY_PASSWORD")
    val signingReady = listOf(signingStoreFile, signingStorePassword, signingKeyAlias, signingKeyPassword).all { !it.isNullOrBlank() }
    signingConfigs {
        if (signingReady) {
            create("release") {
                storeFile = file(signingStoreFile!!)
                storePassword = signingStorePassword
                keyAlias = signingKeyAlias
                keyPassword = signingKeyPassword
            }
        }
    }
    buildTypes {
        getByName("release") {
            if (signingReady) signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
        }
    }

}
