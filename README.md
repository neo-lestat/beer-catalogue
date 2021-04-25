# beer-catalogue

Hello welcome to this little spring-boot project

## Requirements

1. java 11
2. maven
3. docker (optional)

## Build

To build the project there are to options, you can execute the following commands
```
mvn clean package

docker build -t beer-catalogue .
```
or execute the build script
```
docker-build.sh
```
## Run
### Jar file
```
java -jar target/beer-catalogue-0.0.1-SNAPSHOT.jar
```

### Docker image
```
docker run -p 8080:8080 beer-catalogue
```

## How to Call the api

### Manufacturer

#### save
```
curl --request POST \
  --url http://localhost:8080/api/manufacturer \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "estrella",
	"nationality": "spanish"
}'
```

#### get by id
```
curl --request GET \
  --url http://localhost:8080/api/manufacturer/id/1 \
  --header 'Content-Type: application/json' 
```

#### get all
In this method the param name is optional
```
curl --request GET \
  --url 'http://localhost:8080/api/manufacturer/all?name=&page=0&size=5' \
  --header 'Content-Type: application/json' 
```

#### update
```
curl --request PUT \
  --url http://localhost:8080/api/manufacturer \
  --header 'Content-Type: application/json' \
  --data '{
	"id": 2,
	"name": "miguel",
	"nationality": "spanish"
}'
```

#### delete
```
curl --request DELETE \
  --url http://localhost:8080/api/manufacturer/3 \
```

### Beer

#### save
```
curl --request POST \
  --url http://localhost:8080/api/beer \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "estrella",
	"graduation": 0.8,
	"description": "beer ale type",
	"beerType": "ale",
	"manufacturer": {
		"id": 1,
  	"name": "estrella",
		"nationality": "spanish"
	}
}'
```

#### get by id
```
curl --request GET \
  --url http://localhost:8080/api/beer/8
```

#### get all
In this method the param name is optional
```
curl --request GET \
  --url 'http://localhost:8080/api/manufacturer/all?name=&page=0&size=5' \
  --header 'Content-Type: application/json' \
```

#### update
```
curl --request PUT \
  --url http://localhost:8080/api/beer \
  --header 'Content-Type: application/json' \
  --data '{
    "id": 1
	"name": "estrella",
	"graduation": 0.8,
	"description": "beer ale type",
	"beerType": "ale",
	"manufacturer": {
		"id": 1,
  	"name": "estrella",
		"nationality": "spanish"
	}
}'
```

#### delete
```
curl --request DELETE \
  --url http://localhost:8080/api/beer/3 
```
