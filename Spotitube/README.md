# Spotitube JavaEE Application

**Student**: Patrick Roelofs

​	**Studentnummer**: 584025 

​	**Laatste bewerking**: 03-22-2021



**Docent**: Michel Portier

## Opdrachtomschrijving

Spotify en Youtube hebben de handen ineengeslagen en werken gezamenlijk aan een app  (Spotitube) waarmee een klant een overzicht kan krijgen van afspeellijsten met daarin  audio- en videostreams. Ze willen eerst een deel van de back-end ontwikkelen en deze  testen via een bestaande webapplicatie alvorens over te gaan tot de ontwikkeling van de  app.

De applicatie maakt gebruik van de volgende APIs en frameworks: 

- JAX-RS v2.0 (REST, JSON) 
- CDI (Context & Dependency injection) 
- JDBC API 

De applicatie moet gedeployed kunnen worden op Apache TomEE Plus. De front-end en back-end moeten Restful (JSON over HTTP) kunnen communiceren volgens  de REST API specificatie.

Realiseer de Spotitube-applicatie waarbij je gebruik maakt van de volgende layers: 

- Data access layer: Implementeer het Data Mapper pattern en Separated Interface door middel van JDBC en een SQL database.
- Domain layer of Service Layer pattern.
- Remote Facade pattern doormiddel van REST-services.

## Package diagram

![Package](documents/package.png)

De package diagram laat zien hoe verschillende lagen met elkaar spreken, in mijn applicatie is het zo opgebouwd;

De Service package waarin de RESTful api verwerkt is, daarin zitten ook de DTO's.

De Service package praat met de datasource/DAO laag om database en domain functies uit te voeren.

De Domain package slaat alle data op in de server, waarbij de mapper het converteert naar een DTO en met de controller weer terug stuurt naar de gebruiker.

De exceptions worden door de DAO en service aangeroepen, dit zijn custom exceptions.



## Deployment Diagram

![Deployment](documents/deployment.png)

Het deployment diagram bestaat uit drie devices:
⦁	Een cliënt.
⦁	Een server waarop een Apache TomEE server draait.
⦁	Een server waarop de MySQL database draait.

De client draait op de webbrowser van de gebruiker, met als client application Angular. De client spreekt met de Spotitube TomEE server via de REST server. 

De TomEE Server verbindt met de database via JDBC. De connectie wordt geregeld door de database toe te voegen in de config bestand van TomEE. Hierdoor hoef je alleen nog maar de datasource te injecteren in de client. 

Je zou als alternatief ook een database class kunnen schrijven die de verbinding regelt, dit kost wat meer tijd en kan makkelijker fout gaan. Door op de gebruikte manier verbinding te maken met de server bespaar je veel tests en veel zorgen.



## Ontwerpkeuzes

- **Datamapper**

  Ik heb de domain naar DTO converteer functie een eigen package en class gemaakt om deze apart te maken van de service laag, dit geeft in de service geen onnodige functies die niet te maken hebben met de REST API.

* **DAO**

  De DAO's zijn verbonden met Interfaces, dit maakt het makkelijk om later andere DAO's te maken, voor bijvoorbeeld NoSQL of andere database methodes.

* **Service**

  Ik heb ervoor gekozen om de service laag zo min mogelijk te laten doen, zo doet hij bij elke functie alleen een controle uitvoeren of de user is ingelogd en daarna stuurt hij alles door naar zijn bijbehorende DAO. Dit geeft een makkelijk leesbare overzicht over alle API verbindingen die er zijn.