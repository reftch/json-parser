.PHONY: test clean

test: clean
	./gradlew test

clean:
	./gradlew clean

build:
	./gradlew build