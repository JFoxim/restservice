### Swagger ui:
```
http://localhost:8081/swagger-ui/index.html
```
### Creditails:
user: user
password: password

#### 1. БД собирается из файла:
https://github.com/JFoxim/restservice/blob/master/src/main/resources/db-compose/docker-compose.yaml

#### 2. Сборка docker образа из Dockerfile и jar файла сервиса restservice версии 1.0.0
> docker build --build-arg JAR_FILE=build/libs/\*.jar -t restservice:1.0.0 .

#### 3. Создание собственной сети my-network типа bridge
> docker network create my-network

#### 4. Подключение контейнера БД Postgres с именем posrgresql_pdb_1 к сети my-network
> docker network connect my-network posrgresql_pdb_1

#### 5. Запуск сервиса restservice с созданием контейнера с подключением к сети my-network
> docker run --name restservice -e SPRING_DATASOURCE_URL=jdbc:postgresql://posrgresql_pdb_1:5432/users -e SPRING_DATASOURCE_URERNAME=app_user -e SPRING_DATASOURCE_PASSWORD=Postgres123 -p 8081:8081 --network my-network restservice:1.0.0

#### 6. Подключение cервиса restservice к сети my-network
> docker network connect my-network restservice

### Второй способ сервисы БД и приложения собираются из одного docker-compose файла:
https://github.com/JFoxim/restservice/blob/master/docker-compose.yaml

### Скрипты создания БД:
https://github.com/JFoxim/restservice/blob/master/src/main/resources/script/db_create_script.sql

### Push Docker Hub
``` shell
docker login -u renanew
docker tag restservice:1.0.0 renanew/restservice
docker push renanew/restservice
```