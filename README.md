./gradlew bootRun

Accepts http requests to log messages to file. Log files are stored in
  
  /tmp/logs/YYYY/MM/DD/HH/<filename>.log

where filename is either specified in the request or is "logfile"

Endpoints are POST:

/v1/log
/v1/log/{filename}
/v1/batch
/v1/batch/{filename}

log requests take json of the form:
```
{
    message: string;
    timestamp: long;
    logLevel: int;
}
```
Where timestamp is in milliseconds and logLevel translates to 1->Debug, 2->Info, 3->Warn, 4->Error, 5->Fatal; Bespoke loglevels can be used outside of those ranges

batch requests take a json string of the form "{ [ <requests> ] }" where <requests> are of the form of the request json above
  
when specifying a {filename}, this will be used as the filenamt to log to. Log files are always suffixed with '.log'


to test:

```
curl -X POST --data '{"logLevel": 3, "message":"some warning message", "timestamp":1593760669000}' -H 'Content-Type: application/json' http://localhost:8080/api/v1/log
curl -X POST --data '{"logLevel": 2, "message":"some info message", "timestamp":1593760669000}' -H 'Content-Type: application/json' http://localhost:8080/api/v1/log/someFile

curl -X POST --data '{"requests":[{"message": "message1", "logLevel" : 2, "timestamp": 1593760673000},{"message":"happy","logLevel":3, "timestamp": 1593760679000}]}' -H 'Content-Type: application/string' http://localhost:8080/api/v1/batch
curl -X POST --data '{"requests":[{"message": "message1", "logLevel" : 2, "timestamp": 1593760773000},{"message":"yikes","logLevel":4, "timestamp": 1593760779000}, {"message":"out of this world","logLevel":7, "timestamp": 1593760788000}]}' -H 'Content-Type: application/string' http://localhost:8080/api/v1/batch/someFile
```


