dkhlto hhhhhhhhhhh
docker build -t worker .
docker build -t server .
docker build -t worker2 .
docker run --name worker --ip 172.17.0.2 -p 8082:8082 -t worker
docker run --name worker2 --ip 172.17.0.3 -p 8083:8082 -t worker2
docker run -p 8081:8081 -t server