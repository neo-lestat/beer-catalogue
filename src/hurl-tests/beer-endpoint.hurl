#Beer
#Requieres to run first the manufacturer endpoint tests

#hurl v6 json template improved so i can add a manufacturer and get the id
#to pass as paramenter in the json body to create a beer

#add manufacturer as it is required to save a beer
POST http://localhost:8080/api/manufacturer
Content-Type: application/json
{
	"name": "estrella",
	"nationality": "spanish"
}
HTTP 200
[Captures]
manufacturer-id: jsonpath "$['id']"
[Asserts]
jsonpath "$['id']" == 1
jsonpath "$.name" == "estrella"

# add
POST http://localhost:8080/api/beer
Content-Type: application/json
{
	"name": "estrella",
	"graduation": 0.8,
	"description": "beer ale type",
	"beerType": "ale",
	"manufacturer": {
		"id": {{manufacturer-id}},
  	    "name": "estrella",
		"nationality": "spanish"
	}
}
HTTP 200
[Captures]
beer-id: jsonpath "$['id']"
[Asserts]
jsonpath "$['id']" != null


#get by id
GET http://localhost:8080/api/beer/{{beer-id}}
HTTP 200
[Asserts]
jsonpath "$['id']" != null

#get all
#In this method the param name is optional
GET http://localhost:8080/api/beer/all
Content-Type: application/json
[QueryStringParams]
name:
page:0
size:5

HTTP 200
[Asserts]
jsonpath "$.totalItems" > 0
jsonpath "$.totalPages" > 0
jsonpath "$.beers" != null

#update
PUT http://localhost:8080/api/beer
Content-Type: application/json
{
    "id": 1,
	"name": "estrella",
	"graduation": 0.8,
	"description": "beer ale type changed",
	"beerType": "ale",
	"manufacturer": {
		"id": 1,
  	    "name": "estrella",
		"nationality": "spanish"
	}
}
HTTP 200
[Asserts]
jsonpath "$.id" != null
jsonpath "$.description" == "beer ale type changed"

#delete
DELETE http://localhost:8080/api/beer/{{beer-id}}
HTTP 200


