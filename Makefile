.EXPORT_ALL_VARIABLES:

JAVA_HOME = $(shell /usr/libexec/java_home)

.PHONY: clean build quick test checkstyle findbugs publishLocal


clean:
	@echo "Cleaning Ibotta Codec package"
	@(./gradlew clean)

build:
	@echo "Building Ibotta Codec package"
	@(./gradlew build)

checkstyle:
	@echo "Performing Checkstyle Analysis"
	@(./gradlew checkstyleMain)

findbugs:
	@echo "Performing FindBugs Analysis"
	@(./gradlew findBugs)

quick:
	@echo "Performing Quick Build"
	@(./gradlew clean build -x test)

test:
	@echo "Running tests"
	@(./gradlew test)

publishLocal: clean build
	@echo "Publish locally"
	@(./gradlew publishToMavenLocal)

