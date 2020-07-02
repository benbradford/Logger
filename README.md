
to test:

curl -X POST --data '{"logLevel": 1, "message":"mymessage", "timestamp":1234567}' -H 'Content-Type: application/json' http://localhost:8080/api/v1/log

curl -X POST --data '{"requests":[{"message": "message1", "logLevel" : 2, "timestamp": 123456},{"message":"happy","logLevel":3, "timestamp": 234567}]}' -H 'Content-Type: application/string' http://localhost:8080/api/v1/batch

curl http://localhost:8080/api/v1/stop