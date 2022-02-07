# Log-Transaction
> Receive credit card transaction logs and expose customer's endpoint who most transacted in the last 30min, by credit card company.

<img src="https://img.shields.io/badge/license-MIT-green"/>


## Development setup
### Prerequisites
You will need to have the following tools installed on your machine:
- [Git](https://git-scm.com)
- [JDK 11 ou superior](https://www.java.com/pt-BR/download/)

##### For windows:
- [Docker](https://docs.docker.com/desktop/windows/install/)
- [Maven](https://maven.apache.org/guides/getting-started/windows-prerequisites.html#maven-on-windows)

  - Configure environment variable: `PATH="C:\<path.maven>\bin"`   
  
### Building project
- --
```sh
# Clone this git repository
$ git clone <https://github.com/alecomparini-dev/log-transaction.git>

# Access the project folder 
$ cd log-transaction

# Download dependencies 
$ mvn clean install

# Build the project
$ docker build -t log-transaction .
or
$ docker build --no-cache -t log-transaction .

# Up the containers
$ docker-compose up
or
docker-compose up --force-recreate

# Service available at the port: 8080 
<http://localhost:8080>
```

### Running locally
- --
```sh
# Clone this git repository
$ git clone <https://github.com/alecomparini-dev/log-transaction.git>

# Access the project folder 
$ cd log-transaction

# Up the containers
$ docker-compose up
or
docker-compose up --force-recreate

# Run application
$ mvn spring-boot:run

# Service available at the port: 8081 
<http://localhost:8081>
```

### Other configuration
- --
###### open file: _docker-compose.yml_
- JWT token expiration time: `API_SECURITY_JWT_EXPIRATE-TOKEN=<seconds>`
  - *`Default:`* **`42`** 
  

- REDIS cache expiration time: `API_CACHE_REDIS_EXPIRATE=<seconds>`
  - *`Default:`* **`60`**

> After changing run the following command: 
> - `docker-compose up`
> or
>`docker-compose up --force-recreate`

### Example Postman Colletion
[![Run in Postman](https://run.pstmn.io/button.svg)](https://god.gw.postman.com/run-collection/16009368-3ee32736-e7b3-478c-9f10-ee80ee66ff2a)

> Endpoints: 
>- _**/login:**_ `authentication`
>- *_**/log:**_ `send transaction logs`
>- *_**/client:**_ `customers who most transacted in the last 30 min, by credit card company`

OBS:
`*=Authentication required`





