<h1>Short.me</h1>

<h2>An URL shortener API built with Spring Boot, Thymeleaf and MongoDB.</h1>

<h3>GET /</h3>
<b>Home page</b> <br />
A single page to access the API: <br />
1. Form to input the original URL  <br />
2. Do POST in /api/shorturls <br />
3. Retrieve shortened URL as a button to open it <br />

<h3>POST /api/shorturls</h3>
<b>Generates Shorter URL</b> <br />
Endpoint responsible for: <br/>
1. Generate new Random ID  <br />
2. Save the ID and original URL in MongoDB <br />
3. Return the Shorter URL

<h3>GET /{id}</h3>
<b>Redirect to URL</b> <br />
Endpoint responsible for: <br/>
1. Search the URLid in MongoDB <br />
2. Redirect to the original URL <br />

<h3>GET /api/shorturls</h3>
<b>Lists all URLs</b> <br />
Endpoint responsible for: <br/>
1. Return all stored URLs from MongoDB <br />

<h3>DELETE /api/shorturls/{id}</h3>
<b>Delete on URL</b> <br />
Endpoint responsible for: <br/>
1. Search the URLid in MongoDB <br />
2. Remove it <br />


<br /> <br />
<h2>To run:</h2>
1. Start up MongoDB' Server

```
mongod
```

2. Build the project

```
gradle build
```


3. Run the project

```
gradle run
```

<br />
By default the Server will run on localhost:8080 <br/>
To test, access the home page at http://localhost:8080 OR <br />
send POST Request to http://localhost:8080/api/shorturls with a body of type application/json with body

```
{
  'fullUrl' : '<INSERT URL>'
}
```
