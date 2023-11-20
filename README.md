# Tenpo evaluation project

### The application has 2 porjects

1) Calculator
2) Mock-server

### Running the application from containers
1) Clone tenpo procject from git
2) Run `tenpo/docker-compose up -d`
3) Test the calculator service on http://localhost:8080/swagger-ui/index.html
4) Test the calculator and mock server by the postman collection provided `/postman` of each project

### Running the application from IDE.
1) Clone tenpo procject from git
2) Run `tenpo/calculator/docker-compose up -d`
3) Start calculator / mock-server form the IDE ports (8080 and 8081). For a successful test start fist the mock-server
4) Test the calculator service on http://localhost:8080/swagger-ui/index.html
5) Test the calculator and mock server by the postman collection provided `/postman` of each project

### Considerations
1) The database is in create mode, so with each restart the data will be lost. If it is required it can be changed form application.propreties file
   `spring.jpa.hibernate.ddl-auto=create`
2) The default configuration is designed to make the test easier, in a real environment the configuration the `refresh_interval` should be about  15/30 minutes and the `retry_time` could be about 2 minutes.
3) This is the default configuration for calculator:

```
percentage.service.max_attempts=3
percentage.service.retry_time=10000
percentage.service.url=http://mock-server:8081/api/v1/percentage/getPercentage
percentage.job.refresh_interval=60000
percentage.job.enabled=true
```
4) This is the default configuration for mock-server:
```
server.port=8081
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
mock_server.percentage=10.0
```
5) To run the docker file, replace the database and mock-server url to localhost in calculator/application.properties 

### Docker images
1) https://hub.docker.com/r/gveloso/calculator
2) https://hub.docker.com/r/gveloso/mock-server

----------------------
# Tenpo proyecto de evaluación

### La aplicación tiene dos proyectos

1) Calculator
2) Mock-server

### Ejecutando la aplicación desde los contenedores
1) Clonar el proyecto tempo de git
2) Ejecutar `tenpo/docker-compose up -d`
3) Probar el servicio calculator en http://localhost:8080/swagger-ui/index.html
4) Probar el serrvicio calculator y mock-server con las colecciones postman provistas en `/postman` de cada proyecto

### Ejecutando la aplicación desde el entorno de desarrollo local.
1) Cloar el proyecto tenpo procject de git
2) Ejecutar `tenpo/calculator/docker-compose up -d`
3) Iniciar el proyecto calculator / mock-server desde el IDE puertos (8080 and 8081). Para una prueba exitosa iniciar primero el mock-server
4) Probar el servicio calculator en http://localhost:8080/swagger-ui/index.html
6) Probar el serrvicio calculator y mock-server con las colecciones postman provistas en `/postman` de cada proyecto


### Consideraciones
1) La base de datos esta configurada en modo de auto creación, por lo que en cada reinicio se limpiará todo su contenido. Si se necesita se puede modificar en el archivo application.propreties 
   `spring.jpa.hibernate.ddl-auto=create`
2) La configuración por defecto está diseñada para facilitar las pruebas, en un caso real el `refresh_interval` debería ser de 15/30 minutos  y el `retry_time` podría ser de 2 minutos.
3) Esta es la configuración por defecto del calculador:

```
percentage.service.max_attempts=3
percentage.service.retry_time=10000
percentage.service.url=http://mock-server:8081/api/v1/percentage/getPercentage
percentage.job.refresh_interval=60000
percentage.job.enabled=true
```
4) Esta es la configuración por defecto del mock-server:
```
server.port=8081
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
mock_server.percentage=10.0
```
5) Para ejecutar el docker file, reemplazar la url de la base de datos y del mock-sever a localhost en calculator/application.properties

### Imagenes Docker
1) https://hub.docker.com/r/gveloso/calculator
2) https://hub.docker.com/r/gveloso/mock-server