# Feedback

**About the exercise:**

I personally found the task interesting as it involved all phases like coming up with a requirement, reviewing the api doc to understand what's available and feasible, trying out the apis and understanding the details. In all aspects, I would say that this task is very close to an actual task that one might do at work on a day to day basis. I learned a few new things along the way. The challenge description did not give out much details, but was sufficient  enough. I spent roughly 12 hours on the task with ~9 hours on coding.

**API:**

- I liked the RESTful ness of the API. For instance, retrieving a resource includes links to additional resources instead of putting out the entire(unwanted) details, thus saving bandwidth.

- On the website, there is a minor UI flickering issue when a user tries to scroll around the Authentication section. I guess this might be because of window height manipulation and this could be device specific.

- The API doc is neatly organised into sections. While going through create device, I felt Plans, facilities, OS sections could be before the Device section for better understanding as device API deals with those entities. Or even a hyperlink to those section could help in terms of a logical flow. Again, this could be just me.

- Wish list: Plans API - Ability to retrieve plans based on the increasing order of pricing

- Wish list: Plans API - Given a device configuration and budget constraints, suggest a plan that is a close match.

- I liked the way device provisioning could be done in a wild card manner (array of facilities).

- I felt in the API description a little bit talking about scenarios where that API would suit would be helpful.

- Wish list: Facility API: Ability to filter facilities by features

- Get devices API returns full representation of the device’s OS, facility unlike other places where only a link is included.

- POST request returns 406 with empty response body when Accept header is not specified. While the behaviour is standard, I felt that the api could be a bit forgiving (like, sending response in a default format)

**Issues:**

 Using the project level API key, I ran into a couple of issues where the API threw forbidden   
 error
  - Unable to access the check capacity API (POST /capacity), this is not available under the project namespace either.
  
  - Unable to perform actions on devices (like reboot, poweron)
	  POST /devices/{id}/actions
