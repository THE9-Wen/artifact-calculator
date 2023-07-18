cd artifact-calculator-java
#打包jar
./gradlew bootJar

cd ../artifact-calculator-vite
mkdir web-service
cp ../artifact-calculator-java/build/libs/artifact-calculator-0.0.1-SNAPSHOT.jar ./web-service/
npm install
npm run build:mac

