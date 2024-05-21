```markdown
# Iniciar Docker Colima
```bash
colima start --cpu 4 --memory 8 --mount-type 9p
```
## Setear context para Colima
```bash
docker context use colima
```
## Crear la imagen Docker
```bash
docker build -t bankdemo-service .
```
## Ejecutar el contenedor
```bash
docker run -p 8080:7000 -it bankdemo-service
```
## Test endpoints
### Crear cliente
```bash
curl -X POST http://localhost:8080/clients -H 'Content-Type: application/json' -d '{"name": "Jose Luis", "cedula": "79660778"}'
```
### Crear cuenta
```bash
curl -X POST -H "Content-Type: application/json" -d '{"clientId":1}' http://localhost:8080/accounts
```
### Hacer depósito
```bash
curl -X POST -H "Content-Type: application/json" -d '{"accountId":1, "amount":550000, "cedula":"79660778"}' http://localhost:8080/deposit
```
### Hacer retiro
```bash
curl -X POST -H "Content-Type: application/json" -d '{"accountId":1, "amount":70000, "cedula":"79660778"}' http://localhost:8080/withdraw
```
### Consultar saldo
```bash
curl -X GET http://localhost:8080/balance/1
```
### Consultar eventos
```bash
curl -X GET http://localhost:8080/events/1
```
## Pasos para desplegar en Minikube
1. Iniciar Minikube
```bash
minikube start
```
2. Usar el demonio de Docker de Minikube
```bash
eval $(minikube docker-env)
```
3. Construir la imagen de Docker en Minikube
```bash
docker build -t auth-service .
```
4. Para desplegar en Kubernetes
```bash
kubectl apply -f bankdemo-service-deployment.yaml
```
5. Obtener el puerto externo del servicio en Minikube
```bash
minikube service bankdemo-service --url
```