# packet-client
Client that consumes Packet's APIs for deployment automation

Usecase solved:

In a CI/CD pipeline, after CI is done, there is a need to do user acceptance testing in a staging environment. I have tried to automate this process(excluding the actual testing) using Packet’s API

Workflow:

User inputs a config environment that needs to be set up, this includes the kind of server(device) that is required.
The program takes the input and talks to Packet to create a device.
The device is then polled for the status, once the device is active, testing process could start,
If the user wants to tear down the environment after the testing is done, the device will be (force) deleted. Or, if the user wants to retain the environment, the device will be switched off.
User can also get the project keys in order to access the device through a terminal.
Log file will contain information about the execution.

APIs Integrated:

Device:
 	Create, Read, Actions, Delete
  
Project:
	SSH Keys
	
Environment Setup:

	Language: Java8
	Framework: Spring Boot 2
	Dependency management: gradle 4.6 (or greater)

Steps to run the app:

Ensure the above mentioned tooling is installed

Clone the repo https://github.com/praveen-ub/packet-client.git

On a terminal, navigate to the project home

Edit ‘application.properties’ file located at /src/main/resources to include projectId and API key

Execute ‘gradle clean build’

Execute ‘gradle bootRun’

             The app will start on the default port 8080, log can be found in home folder
