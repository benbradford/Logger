
to test:

curl -X POST --data '{"logLevel": 1, "message":"mymessage"}' -H 'Content-Type: application/json' http://localhost:8080/api/v1/log


curl -X POST --data '{"requests":[{"message":"mymessage", "error": "myError", "nothing" : "123"}]}' -H 'Content-Type: application/string' http://localhost:8080/api/v1/batch