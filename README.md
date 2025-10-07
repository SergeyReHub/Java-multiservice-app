### I am using GRPC, Kafka, Redis and Postgres in this project to develop my skills
** file-watcher-service -> watch for file creating into logs folder from root of project -> when detected, sends it by kafka producer for logs topic
** file-processor-data -> processing the consumed data from kafka -> sends by grpc stream logs
** api-gateway -> get logs -> maps it -> fill db and redis
** api-gateway -> REST-controller to get data
