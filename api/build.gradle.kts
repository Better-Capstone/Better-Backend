dependencies {
    implementation(project(":core"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.13.0")
    implementation ("org.apache.httpcomponents.client5:httpclient5:5.1.4")
    implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+")
    runtimeOnly("com.mysql:mysql-connector-j")
    testRuntimeOnly("com.h2database:h2:1.4.200")
}