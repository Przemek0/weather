# Weather

1. [General info](#general-info)
2. [Technologies](#technologies)
    * [Back-end](#back-end)
    * [Tests](#tests)
    * [Database](#database)
    * [Build tools](#build-tools)
3. [Endpoints](#endpoints)

## General info

Rest api with weather data. You can check the weather for a specific city. The application returns data like
temperature, atmospheric pressure, direction of wind, speed of wind and cloud cover.

## Technologies

### Back-end

* Java 11
* Spring Boot 2.5.2
    * Spring-Web
    * Spring-Data-JPA
    * Spring-Validation
* ModelMapper 2.4.4

### Tests

* JUnit 5
* Mockito

### Database

* PostgreSQL
* Flyway migration

### Build tools

* Docker

## Endpoints

### Current weather

Returns the newest weather for given city name.

* **URL:** `/weathers/current?city={city}`
* **Method:** `GET`
* **URL Params**</br>
  Required:</br>
  `city=[string]`
* **Data Params**</br>
  None
* **Success response:**
    * Code: `200 OK`</br>
      Content:

```JSON
{
  "date": "07.07.2021",
  "time": "14:00:00",
  "temperature": 32.5,
  "atmosphericPressure": 1000.0,
  "windDirectionDeg": 180,
  "windSpeed": 5,
  "cloudCover": {
    "id": 1,
    "name": "cloudless"
  },
  "city": {
    "name": "Katowice",
    "zipCode": "40-000",
    "country": {
      "name": "Polska",
      "code": "PL"
    }
  }
}
```

* **Error response:**
    * Code: `404 NOT FOUND`</br>
      Content: `{error : cityName + " not found."}`

### Current weather by time

Returns current weather between time parameters.

* **URL:** `/weathers/current/time?city={city}&start={startTime}&end={endTime}`
* **Method:** `GET`
* **URL Params**</br>
  Required:</br>
  `city=[string]`

  Optional:</br>
  `start=[string pattern(HH:mm)]`</br>
  `end=[string pattern(HH:mm)]`
* **Data Params**</br>
  None
* **Success response:**
    * Code: `200 OK`</br>
      Content:

```JSON
[
  {
    "date": "07.07.2021",
    "time": "14:00",
    "temperature": 32.5,
    "atmosphericPressure": 1000.0,
    "windDirectionDeg": 180,
    "windSpeed": 5,
    "cloudCover": {
      "id": 1,
      "name": "cloudless"
    },
    "city": {
      "name": "Katowice",
      "zipCode": "40-000",
      "country": {
        "name": "Polska",
        "code": "PL"
      }
    }
  }
]
```

* **Error response:**
    * Code: `400 BAD REQUEST`</br>
      Content: `{error : cityName + " not found."}`

  **OR**

    * Code: `400 BAD REQUEST`</br>
      Content: `{error : "The start time can't be after the end time."}`

### History weather by date

Returns history weather between given dates.

* **URL:** `/weathers/history?city={city}&start={startDate}&end={endDate}`
* **Method:** `GET`
* **URL Params**</br>
  Required:</br>
  `city=[string]`

  Optional:</br>
  `start=[string pattern(dd.MM.yyyy)]`</br>
  `end=[string pattern(dd.MM.yyyy)]`
* **Data Params**</br>
  None
* **Success response:**
    * Code: `200 OK`</br>
      Content:

```JSON
[
  {
    "date": "07.07.2021",
    "time": "14:00",
    "temperature": 32.5,
    "atmosphericPressure": 1000.0,
    "windDirectionDeg": 180,
    "windSpeed": 5,
    "cloudCover": {
      "id": 1,
      "name": "cloudless"
    },
    "city": {
      "name": "Katowice",
      "zipCode": "40-000",
      "country": {
        "name": "Polska",
        "code": "PL"
      }
    }
  }
]
```

* **Error response:**
    * Code: `400 BAD REQUEST`</br>
      Content: `{error : cityName + " not found."}`

  **OR**

    * Code: `400 BAD REQUEST`</br>
      Content: `{error : "The start date can't be after the end date."}`