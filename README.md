## Adventure Travel CRM
Adventure Travel CRM is a simple management app for travel agenacy. It is written using Spring, Spring Boot, Lombok for backend and Vaadin for frontend.
This solo project has been submitted as the final project for Kodilla bootcamp.

### Prerequisites
Java 17
MySQL

### Installation and running
To run the app follow this guide:
1. Create an account on Mailtrap https://mailtrap.io/ and set the following environment variables:
   - MAIL_USERNAME
   - MAIL_PASSWORD
2. Download the repository for Backend: https://github.com/NataliaWszelaki/AdventureTravelAPI-backend.git
3. Download the repository for Frontend: https://github.com/NataliaWszelaki/AdventureTravelAPI-frontend.git
4. Create a MySql database in your IDE and connect to it.

### Running the app
1. Run the AdventureTravelApiBackendApplication.java - backend
2. Run the AdventureTravelApiFrontendApplication.java - frontend
3. The app will open on http://localhost:8000/
Upon accessing the login page, tou will will find instructions on how to log in as either a user or an admin. After logging in, you will be directed to the main dashboard.
On the left-hand side, you will find a navigation panel: Clients, Reservations, Tours, and Attractions. 
Upon navigating to the selected tab, you can add items to the list, edit existing entries, and, in the admin view, delete or deactivate them as necessary.
In the "Attractions" tab, clicking the "Add" button you can enter a city. Then, from the list displayed, you can select the desired item by clicking on it.
This action will populate a list of attractions. Upon selecting an item from the second list and clicking "Save," the item will be added to the main list of attractions.

### External API
The application fetches data from two external APIs:
1. Tourist Attraction API
2. Currencyapi API
   
### Contact
For questions or comment please contact me: nm.wszelaki@gmail.com
