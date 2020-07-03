
to test:

curl -X POST --data '{"logLevel": 3, "message":"some warning message", "timestamp":1593760669000}' -H 'Content-Type: application/json' http://localhost:8080/api/v1/log
curl -X POST --data '{"logLevel": 2, "message":"some info message", "timestamp":1593760669000}' -H 'Content-Type: application/json' http://localhost:8080/api/v1/log/someFile

curl -X POST --data '{"requests":[{"message": "message1", "logLevel" : 2, "timestamp": 1593760673000},{"message":"happy","logLevel":3, "timestamp": 1593760679000}]}' -H 'Content-Type: application/string' http://localhost:8080/api/v1/batch
curl -X POST --data '{"requests":[{"message": "message1", "logLevel" : 2, "timestamp": 1593760773000},{"message":"yikes","logLevel":4, "timestamp": 1593760779000}, {"message":"out of this world","logLevel":7, "timestamp": 1593760788000}]}' -H 'Content-Type: application/string' http://localhost:8080/api/v1/batch/someFile


curl http://localhost:8080/api/v1/stop