// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // Temporarily commented out until google-services.json is added
    id("com.google.gms.google-services") version "4.4.3" apply false
}
