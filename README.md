								# Exchange_Stock_Market
Created this project to demostrate the OAuth2 code base with Key Cloak OAuth server.
	BackEnd part:
User Interface:
	A React User Interface that can display 
			User details
			Trade details
			Portfolio details
			Stock details	
Services :
	
	Exchange API GateWay.
			Main API Gateway for all back end application.
			A Normal Spring Boot Application that uses Key Cloack as an authentication server.
			When the user hots amy URL the the Key Cloack auth server is used to validate the user.
	
	Exchange Config Server.
	Exchange Stock Service.
		This service is used to read the Stock details from exchange and send those details to the Exchange Stock repo.
		This service is also used when we need to send trade details like Purchase or sell to the exchange
	User_Service.
	Portfolio_Service.
	Exchange_Stock_Repository
		This is a service that is used to store all the stock details into Elastic indexes.
		The user can fetch the latest stock price, historical stock price for a stock.
		This service uses Kafka Listener that receives stock updates from Exchange Stock Market service and populates all data into Elastic.
	Trade_Service
	Notification_Service:




Main Swagger URl:
http://localhost:8080/swagger-ui.html
