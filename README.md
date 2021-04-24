# beer-catalogue

## To build

run 
mvn clean package

docker build -t beer-catalogue .

or

execute script docker-build.sh

## To run jar
java -jar target/beer-catalogue-0.0.1-SNAPSHOT.jar

## To run docker image
docker run -p 8080:8080 -e beer-catalogue
