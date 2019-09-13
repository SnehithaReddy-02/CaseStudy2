# ChatbotWebService
User is a clinical person seeking help about Philips Patient Monitoring equipment. The webservice's job is to make it easier.The ChatbotWebService will accept input and give its response to the user.
The features of the chatbot wil let the user to:
1. Search for a monitor: In search, user can search monitor by entering monitor's name 
2. Select monitors according to the user's choice:  In Normal, it will display the next set of questions (for filtering of monitors) on the basis of user input in first stage.
3. Input the user details: The user will enter his details, so that customer support can contact them in future.

The details of the monitors along with flow of questions are stored in a MySQL database.
Run the dbcreate.bat file to create all the required tables and insert data into them(stageonequerytable, stagetwoquerytable, monitor and userdetails).
Edit the MySQL db's version in the POM file.
Run the ChatbotwebserviceApplication and use 'Postman' as the client.
The documentation of all the APIs in the ChatbotController class is provided in the link given below:
http://localhost:8080/swagger-ui.html#/chatbot-controller







