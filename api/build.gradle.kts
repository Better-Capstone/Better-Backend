plugins {
    kotlin("plugin.jpa") version "1.8.22"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.13.0")
    implementation ("org.apache.httpcomponents.client5:httpclient5:5.1.4")
    implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+")
}