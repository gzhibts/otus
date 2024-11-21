plugins {
    id("java")
}

group = "ru.otus"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")

    implementation("ch.qos.logback:logback-classic")
    implementation("org.ehcache:ehcache")
    implementation("org.hibernate.orm:hibernate-core")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql:10.4.1")

    implementation("org.postgresql:postgresql:42.7.4")

    testImplementation("com.h2database:h2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.mockito:mockito-junit-jupiter")

    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql:1.20.2")
}