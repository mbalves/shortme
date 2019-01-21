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
2. Use pagination to retrieve 20 url per page <br/>

<h3>GET /api/shorturls/{id}</h3>
<b>Lists URLs detail</b> <br />
Endpoint responsible for: <br/>
1. Return URL data <br />


<h3>DELETE /api/shorturls/{id}</h3>
<b>Delete on URL</b> <br />
Endpoint responsible for: <br/>
1. Search the URLid in MongoDB <br />
2. Remove it <br />

<h3>GET /api/data/</h3>
<b>API Usage</b> <br />
Endpoint responsible for: <br/>
1. Extract some API usage like: <br />
   - "startDate" <br />
   - "lastChange" <br />
   - "quantity" <br />
   - "quantityLastDay" <br />

<h3>GET /api/data/{id}</h3>
<b>URL Usage</b> <br />
Endpoint responsible for: <br/>
1. Show for each URL link usage: <br />
   - "User IP" <br />
   - "User City" <br />
   - "User Country" <br />
   - "Timestamp" <br />

<h3>GET /actuator/</h3>
<b>Health Check Spring</b> <br />
Endpoint responsible for: <br/>
1. Show application status <br />
2. Show application info <br />

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
By default the Server will run on localhost:80 <br/>
To test, access the home page at <a href='http://localhost'>http://localhost</a> OR <br />
send POST Request to http://localhost/api/shorturls with a body of type application/json with body

```
{
  'fullUrl' : '<INSERT URL>'
}
```


Please, evaluate a preview version running on Heroku: <a href='https://shrtme.herokuapp.com/'>https://shrtme.herokuapp.com/</a>
