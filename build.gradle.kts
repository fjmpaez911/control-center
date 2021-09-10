import org.gradle.api.tasks.testing.logging.TestLogEvent


plugins {
	java
	`maven-publish`
	id("org.springframework.boot") version "2.5.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("com.gorylenko.gradle-git-properties") version "2.2.2"
	id("net.researchgate.release") version "2.8.1"
}


extra["dockerImage"] = "localhost:32000/cas/${rootProject.name}"


configure<JavaPluginConvention> {
	sourceCompatibility = JavaVersion.VERSION_11
}


configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}


extra["springCloudVersion"] = "2020.0.3"
extra["logstashLogbackEncoderVersion"] = "6.3"
extra["springfoxSwaggerVersion"] = "3.0.0"



dependencies {

	implementation("org.springframework.boot", "spring-boot-starter-actuator")
	implementation("org.springframework.boot", "spring-boot-starter-webflux")
	implementation("org.springframework.cloud", "spring-cloud-starter")
	implementation("org.springframework.cloud", "spring-cloud-starter-sleuth")

	implementation("io.springfox", "springfox-boot-starter", "${project.extra["springfoxSwaggerVersion"]}")
	implementation("net.logstash.logback","logstash-logback-encoder", "${project.extra["logstashLogbackEncoderVersion"]}")

	compileOnly("org.projectlombok", "lombok")
	developmentOnly("org.springframework.boot", "spring-boot-devtools")

	annotationProcessor("org.projectlombok", "lombok")
	annotationProcessor("org.springframework.boot", "spring-boot-configuration-processor")

	testImplementation("io.projectreactor", "reactor-test")
	testImplementation("org.springframework.boot", "spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}


tasks {

	test {
		useJUnitPlatform()
		testLogging.events = setOf(TestLogEvent.FAILED, TestLogEvent.SKIPPED)
	}

	bootJar {
		archiveFileName.set("app.jar")
	}

	processResources {

		dependsOn(mutableListOf("copyVueStatic"))

		filesMatching("bootstrap.*") {
			expand(project.properties)
		}
	}

	register("dockerBuild") {

		group = "docker"
		dependsOn(mutableListOf("build"))

		doLast {
			dockerBuild()
		}
	}

	register("dockerPush") {

		group = "docker"
		dependsOn(mutableListOf("dockerBuild"))

		doLast {
			dockerPush()
		}
	}

	register("npmBuild") {

		group = "vue"

		doLast {
			npmBuild()
		}
	}

	register<Copy>("copyVueStatic") {

		group = "vue"
		dependsOn(mutableListOf("npmBuild"))

		from(layout.projectDirectory.dir("src/vue-ui/dist/"))
		into(layout.buildDirectory.dir("resources/main/static"))
	}

}

fun dockerBuild() {

	exec {
		executable("docker")
		args("build", "-t", "${property("dockerImage")}:${project.version}", ".")
	}
}

fun dockerPush() {

	exec {
		executable("docker")
		args("push", "${property("dockerImage")}:${project.version}")
	}
}

fun npmBuild() {

	exec {
		workingDir(layout.projectDirectory.dir("src/vue-ui/"))
		executable("npm")
		args("run", "build")
	}
}


repositories {
	mavenCentral()
	mavenLocal()
}


publishing {
	publications {
		create<MavenPublication>("main") {
			artifact(tasks.getByName("bootJar"))
		}
	}
	repositories {
		maven {
			val releases = "https://library.local/repository/releases/"
			val snapshots = "https://library.local/repository/snapshots/"
			url = uri(if (version.toString().endsWith("-SNAPSHOT")) snapshots else releases)
			credentials {
				username = findProperty("nexusUser").toString()
				password = findProperty("nexusPassword").toString()
			}
		}
	}
}


release {
	preTagCommitMessage = "release "
	tagCommitMessage = ""
	newVersionCommitMessage = ""
}

