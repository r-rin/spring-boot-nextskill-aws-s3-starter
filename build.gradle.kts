plugins {
    id("java")
    id("maven-publish")
}

group = "com.github.rrin.nextskill.s3starter"
version = "1.0.0"

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.r-rin.nextskill"
            artifactId = "spring-boot-nextskill-aws-s3-starter"
            version = "1.0.0"

            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/r-rin/spring-boot-nextskill-aws-s3-starter")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.3.4")
    implementation("software.amazon.awssdk:s3:2.28.21")
    implementation("software.amazon.awssdk:auth:2.28.21")
    implementation("software.amazon.awssdk:bom:2.28.21")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    runtimeOnly("software.amazon.awssdk:bom:2.28.21")
}

tasks.test {
    useJUnitPlatform()
}