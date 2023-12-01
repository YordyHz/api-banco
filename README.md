Documentación API: https://documenter.getpostman.com/view/8722953/2s9YeK2pR3

# Docker execution commands (../api-banco): 
cd cliente-service

docker build -t devsu-cliente_service:1.0.0 .

cd ../cuenta-service

docker build -t devsu-cuenta_service:1.0.0 .

cd ..

docker-compose up -d
