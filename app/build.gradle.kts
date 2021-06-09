import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
//import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("jvm")
    //id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
    application
}

repositories {
	jcenter()
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:_")
	testImplementation(gradleTestKit())
	testImplementation("io.kotest:kotest-runner-junit5:_")
	testImplementation("io.kotest:kotest-assertions-core:_")
	testImplementation("io.kotest:kotest-assertions-core-jvm:_")
	detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:_")

	// Use the Kotlin test library.
	testImplementation("org.jetbrains.kotlin:kotlin-test")
	// Use the Kotlin JUnit integration.
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

detekt {
    failFast = true
    buildUponDefaultConfig = true
    config = files(project.rootDir.resolve("detektConfig.yml"))
}

/*
ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.JSON)
        reporter(ReporterType.HTML)
    }
}
*/

tasks.withType<Test> {
	useJUnitPlatform() // Use JUnit 5 engine
	testLogging.showStandardStreams = true
	testLogging {
		showCauses = true
		showStackTraces = true
		showStandardStreams = true
		events(*org.gradle.api.tasks.testing.logging.TestLogEvent.values())
		exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		allWarningsAsErrors = true
		jvmTarget = JavaVersion.VERSION_1_8.toString()
	}
}

application {
    // Define the main class for the application.
    mainClass.set("it.slowButSteady.AppKt")
}
