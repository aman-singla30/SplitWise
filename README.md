###Split Wise Application : Steps to build the docker image and start working on it

#### Prerequisite
- Docker
- Maven
- SpringBoot
- Intellij
- Java8

Application is available as Docker and Jar
### Installation sing Jar:
Open command Line Editor
- git clone 
- cd splitwise
- mvn clean install

Installation using Docker :

Open command Line Editor
- git clone
- cd splitwise

##### Build Docker image
docker build -t="splitwise" .

Maven build will be executes during creation of the docker image.

>Note:if you run this command for first time it will take some time in order to download base image from [DockerHub](https://hub.docker.com/)

##### Run Docker Container

docker run -p 8080:8080 -it --rm splitwise

##### Test application using Api's
>Note : Swagger Link : http://localhost:8080/splitwise/swagger-ui/
> Note : H2 database link : http://localhost:8080/splitwise/h2-ui
#### Create User Curl :
curl --location --request POST 'http://localhost:8080/splitwise/user/create' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Aman Kumar",
"phoneNumber": "9874561789",
"email": "aman.singla0@gmail.com"
}'

####Response :
{"id":1,"name":"Aman Kumar","phoneNumber":"9874561789","email":"aman.singla0@gmail.com","creationDate":"2022-01-30","paidBySettlement":null,"paidToSettlement":null,"transactions":null}


####Create Another User :
curl --location --request POST 'http://localhost:8080/splitwise/user/create' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Raman Kumar",
"phoneNumber": "9874561789",
"email": "raman.kumar@gmail.com"
}'

####Response :
{"id":2,"name":"Raman Kumar","phoneNumber":"9874561789","email":"raman.kumar@gmail.com","creationDate":"2022-01-30","paidBySettlement":null,"paidToSettlement":null,"transactions":null}

####Create Group and assign these users in group:
curl --location --request POST 'http://localhost:8080/splitwise/group/create/1' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Family Group",
"type":"Trip"
}'

####Response :
{"id":1,"name":"Family Group","type":"Trip","creationDate":"2022-01-30","users":[{"id":1,"name":"Aman Kumar","phoneNumber":"9874561789","email":"aman.singla0@gmail.com","creationDate":"2022-01-30","paidBySettlement":[],"paidToSettlement":[],"transactions":[]}],"settlements":null,"transactions":null}

####Add Member into Group :
curl --location --request POST 'http://localhost:8080/splitwise/group/addMember/1' \
--header 'Content-Type: application/json' \
--data-raw '{
"userIds":[2]
}'

####Response :
{"id":1,"name":"Family Group","type":"Trip","creationDate":"2022-01-30","users":[{"id":1,"name":"Aman Kumar","phoneNumber":"9874561789","email":"aman.singla0@gmail.com","creationDate":"2022-01-30","paidBySettlement":[],"paidToSettlement":[],"transactions":[]},{"id":2,"name":"Raman Kumar","phoneNumber":"9874561789","email":"raman.kumar@gmail.com","creationDate":"2022-01-30","paidBySettlement":[],"paidToSettlement":[],"transactions":[]}],"settlements":[],"transactions":[]}

####Add Expense :
curl --location --request POST 'http://localhost:8080/splitwise/expense/add' \
--header 'Content-Type: application/json' \
--data-raw '{
"amount":1000,
"paidByUserId":1,
"paidInGroupId":1,
"description":"Milk",
"expenseType":"PERCENT",
"splits": {
"1":30,
"2":30,
"3":40
}

}'

####Show Expense :
curl --location --request POST 'http://localhost:8080/splitwise/expense/getTotalExpense' \
--header 'Content-Type: application/json' \
--data-raw '{
"userId":"1",
"groupId":"1"

}'

####Response :
{"userId":1,"groupId":1,"totalPaidAmount":1000.0,"totalExpense":{"2":700.0}}



>Note -> Github Link : 


