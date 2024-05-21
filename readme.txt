# Iniciar docket colima
colima start --cpu 4 --memory 8 --mount-type 9p

# Setear context para colima
docker context use colima

# Crea la imagen docker
docker build -t bankdemo-service .

#Ejecutar container
docker run -p 8080:7000 -it bankdemo-service

# Test endpoints
-- Crear cliente
curl -X POST http://localhost:8080/clients -H 'Content-Type: application/json' -d '{"name": "Jose Luis", "cedula": "79660778"}'

-- Crear cuenta
curl -X POST -H "Content-Type: application/json" -d '{"clientId":1}' http://localhost:8080/accounts

-- Hacer deposito
curl -X POST -H "Content-Type: application/json" -d '{"accountId":1, "amount":550000, "cedula":"79660778"}' http://localhost:8080/deposit

-- Hacer retiro
curl -X POST -H "Content-Type: application/json" -d '{"accountId":1, "amount":70000, "cedula":"79660778"}' http://localhost:8080/withdraw

-- Consultar saldo
curl -X GET http://localhost:8080/balance/1

-- Consular eventos
curl -X GET http://localhost:8080/events/1


--- A continuación pasos para desplegar en minikube
1. iniciar minikube
minikube start

2. Usar el demonio de docker de minikube
eval $(minikube docker-env)

3. Construir la imagen de docker en minikube
docker build -t auth-service .

4.Para desplegar en Kubernetes
kubectl apply -f bankdemo-service-deployment.yaml

5. Obtener el puerto externo del servicio en miniKube
minikube service bankdemo-service --url
