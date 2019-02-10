# Spring Reactive Example using MongoDB with JWT Token Enabled Security

- Uses Spring Boot 2.0.0-M3 with Spring 5 Framework using Reactor Streams for Reactive REST endpoints
- Uses Spring MVC Style Resource push.

### - Make sure to start mongodb at 27017 port.

```
sh
docker run -p 27017:27017 -d mongo
```

### Important details
- To get Authenticated, first hit /token endpoint.
- Change security key from 'youtube' to something more secure
- /rest/class and /rest/school are 2 different endpoints.
- /rest/class/all will return all students of class.
- /rest/class/{id} will return unique student
- /rest/class/{id}/event will send student details in every 2 seconds.
- Thus makes use of Spring Flux Reactive Stream and Servlet 3.1 which uses nio (Non Blocking I/O)


### How to request for token
```$xslt
send request to {URL}/token
{
	"name": "Anuj",
	"id": "1234",
	"role": "Admin"
}
```
You will get back a JWT Token
 - Example request
 ```jshelllanguage
token=curl -X POST -H "Content-Type: application/json" -d '{"user":"anuj", "id":"12345", "role":"adm"}' "http://localhost:8082/token"
```

### How to use Token?

```$xslt
Send any request to /rest/class/all with Following Header
"Authorisation": "Token <Token>"
```

```sh
curl  -H "Authorisation: Token $token" "http://localhost:8082/rest/class/all"
curl  -H "Authorisation: Token $token" "http://localhost:8082/rest/class/53279d19-5c6f-4988-8b43-9d5d3c68be6d/events
```

