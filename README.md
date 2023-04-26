# NBAStats
Description:

This application takes a search string from the user, and uses it to fetch and display the information of the NBA player.

1.  Implement a native Android application
The name of my native Android application project in Android Studio is: NBASTAT
a. My application uses TextView, EditText, Button, and ImageView.  See content_main.xml for details of how they are incorporated into the constraintLayout.

Here is a screenshot of the layout before the picture has been fetched.

<img src="https://user-images.githubusercontent.com/90142233/234692316-30561eeb-9e12-42c6-b68f-8f516ebdff5b.png" width=200px>

b. Requires input from the user
Here is a screenshot of the user searching for the information of James Harden

<img src="https://user-images.githubusercontent.com/90142233/234692642-56bd2ba9-4e4f-4bc6-9900-24bf28d75319.png" width=200px>

c. Makes an HTTP request (using an appropriate HTTP method) to the web service
My application does an HTTP GET request in GetPlayer.java. The HTTP request is:   ""https://www.balldontlie.io/api/v1/players?search=" + input[0] + "_" + input[1] where input[0] + "_" + input[1] is the user's search term.
The search method makes this request of my web application, parses the returned JSON to find the player URL, fetches the information, and returns the data.

d. Receives and parses an XML or JSON formatted reply from the web service
An example of the JSON reply is:
[{"name":"James Harden","position":"G","height":"6\u00275\"","weight":"220 lbs","team":"Philadelphia 76ers"}]

e. Displays new information to the user
Here is the screen shot after the information has been returned.

<img src="https://user-images.githubusercontent.com/90142233/234692798-a13d1780-4cff-456a-a177-720b6675f306.png" width=200px>

f. Is repeatable (I.e. the user can repeatedly reuse the application without restarting it.)
The user can type in another search term and hit Search.  Here is an example of having typed in "Lebron James".

<img src="https://user-images.githubusercontent.com/90142233/234692845-8e7bc9f9-443b-46bf-866e-0edc90835dff.png" width=200px>

2.  Implement a web application, deployed to Github Codespaces

a. Using an HttpServlet to implement a simple (can be a single path) API
In my web app project:
MVC 
Model: Model.java
View: index.jsp
Controller: Servlet.java

b. Receives an HTTP request from the native Android application
Servlet.java receives the HTTP GET request with the argument "request".  It passes this search string on to the model.

c. Executes business logic appropriate to your application
Model.java makes an HTTP request to:
"https://www.balldontlie.io/api/v1/players?search=" + request
It then parses the JSON response and extracts the parts it needs to respond to the Android application.

d. Replies to the Android application with an XML or JSON formatted response.
index.jsp formats the response to the mobile application in a simple JSON format of my own design:
[{"name":"James Harden","position":"G","height":"6\u00275\"","weight":"220 lbs","team":"Philadelphia 76ers"}]

3. Handle error conditions
The application tests for and handles:
Invalid mobile app input
Invalid server-side input (regardless of mobile app input validation)
Mobile app network failure, unable to reach server
Third-party API unavailable
Third-party API invalid data

4. Log useful information
We log information like id, web service start time, web service end time, web service response time, android device response time, search input and result. 
The reason is that I would like to gain the information of the speed of api fetch and android device fetch.

5.Store the log information in a database
Here are the screenshots of logs storing in MongoDB database:

<img src="https://user-images.githubusercontent.com/90142233/234693709-31fca432-bb02-4862-9524-9133ffe70110.png" width=500px>

6. Display operations analytics and full logs on a web-based dashboard

<img src="https://user-images.githubusercontent.com/90142233/234693765-6c8c08aa-c5a7-42e3-afb4-3d146831b6d0.png" width=500px>

7.Deployed to Github Codespaces
The URL of my web service deployed to Github Codespaces is:
https://bobbyyang1314-automatic-space-broccoli-jw99gq4r55j3pqpq.github.dev

https://bobbyyang1314-automatic-space-broccoli-jw99gq4r55j3pqpq.github.dev/player?{playerName}
Get the player information you want

https://bobbyyang1314-automatic-space-broccoli-jw99gq4r55j3pqpq.github.dev/dashboard
See the log dashboard



