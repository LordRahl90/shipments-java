## Shipments Calculation System

[Postman Docs](https://www.getpostman.com/collections/497742b6deae56e91248) <br />

## Local Setup:
Make sure docker is installed on the target machine
* Clone Repository

## Steps
* RUN `make bi` to build the docker image
* RUN `docker-compose up` to start the service and the accompanying mysql database

## OR
* RUN `make`

## API Docs
## API Documentation:
* POST `/new` -> Creates a new shipment record with the following payload and returns a JWT Token for future authentication:
```json
{
    "name": "Bart Beatty",
    "email": "cordiajacobi@carroll.net",
    "origin": "us",
    "destination": "se",
    "weight": 45
}
```
If all is well, you should get this response
```json
{
    "success": true,
    "reference": "82a9472f-b81c-44b1-a595-c094002b3a15",
    "customer_id": "93891958-5b45-4048-8b58-efe00fe8e31f",
    "price": 1250
}
```

* GET `/history/:email` -> Returns the array of shipments that the user with the given email has created on our system. <br />
  Sample response looks like:
```json
[
    {
        "id": "4706abb1-f4a5-4ca4-bd45-de1ece07bf26",
        "customer_id": "93891958-5b45-4048-8b58-efe00fe8e31f",
        "origin": "us",
        "destination": "se",
        "float": 45,
        "price": 1250
    },
    {
        "id": "e50407d0-e1f5-4bbf-bb7e-f5a6bf8731f6",
        "customer_id": "93891958-5b45-4048-8b58-efe00fe8e31f",
        "origin": "us",
        "destination": "se",
        "float": 45,
        "price": 1250
    }
]
```

* GET `/pricing?origin=:origin&destination=:destination&weight=:weight` -> Returns the price information of the given parameter. <br />
  Sample response:
```json
{
    "weight": 10,
    "origin": "us",
    "destination": "se",
    "weight_category": "small",
    "price": 250
}
```