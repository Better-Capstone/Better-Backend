tasks.jar {
    enabled = false
}

dependencies {
    implementation(project(":core"))
    implementation("org.springframework.boot:spring-boot-starter-batch:3.0.4")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("com.h2database:h2:2.1.214")
}