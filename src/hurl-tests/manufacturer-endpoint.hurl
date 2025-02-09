# Manufacturer
# Requieres fresh database (as H2 mem is used we just need to restart the application)

#hurl v6 improve json template so no need to add a extra manufacturer here

#add
POST http://localhost:8080/api/manufacturer
Content-Type: application/json
{
	"name": "estrella-manufacturer",
	"nationality": "spanish"
}
HTTP 200
[Captures]
manufacturer-id: jsonpath "$['id']"
[Asserts]
jsonpath "$['id']" != null
jsonpath "$.name" == "estrella 2"

# get by id
GET http://localhost:8080/api/manufacturer/id/{{manufacturer-id}}
Content-Type: application/json
HTTP 200
[Asserts]
jsonpath "$.id" != null
jsonpath "$.name" == "estrella 2"

# get all, In this method the param name is optional
GET http://localhost:8080/api/manufacturer/all
Content-Type: application/json'
[QueryStringParams]
name:
page:0
size:5

HTTP 200
[Asserts]
jsonpath "$.totalItems" > 0
jsonpath "$.totalPages" > 0
jsonpath "$.manufacturers" != null

# update
PUT http://localhost:8080/api/manufacturer
Content-Type: application/json
{
	"id": 2,
	"name": "miguel",
	"nationality": "spanish"
}
HTTP 200
[Asserts]
jsonpath "$.id" != null
jsonpath "$.name" == "miguel"

# delete
DELETE http://localhost:8080/api/manufacturer/{{manufacturer-id}}
HTTP 200


