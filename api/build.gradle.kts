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
    runtimeOnly("com.mysql:mysql-connector-j")
}