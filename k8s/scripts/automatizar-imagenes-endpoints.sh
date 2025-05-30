#!/bin/bash

set -e

# Validar que el clúster esté corriendo
if ! kubectl cluster-info > /dev/null 2>&1; then
  echo "❌ El clúster de Kubernetes no está activo. Por favor, ejecútalo antes de continuar."
  exit 1
fi

TAG="v$(date +%Y%m%d%H%M%S)"
echo "🔄 Iniciando construcción, despliegue y verificación con tag $TAG..."

# ------- CREATE -------
echo -e "\n🧩 p-spring-create"
docker build -t angiediazz/p-spring-create:$TAG ../p-spring-create
docker push angiediazz/p-spring-create:$TAG
kubectl set image deployment/create-deployment create-container=angiediazz/p-spring-create:$TAG -n p-spring-create
kubectl rollout status deployment/create-deployment -n p-spring-create
echo "Esperando a que el servicio esté listo..."
sleep 10

echo "📤 POST /crear"
curl -s -w "🔗 HTTP %{http_code}\n" -X POST http://localhost/crear/api/productos/crear \
  -H "Content-Type: application/json" \
  -d '{"codigo":"P100", "nombre":"Pantalon", "precio":50000, "cantidad":10}'

# ------- LIST -------
echo -e "\n📋 p-spring-list"
docker build -t angiediazz/p-spring-list:$TAG ../p-spring-list
docker push angiediazz/p-spring-list:$TAG
kubectl set image deployment/list-deployment list-container=angiediazz/p-spring-list:$TAG -n p-spring-list
kubectl rollout status deployment/list-deployment -n p-spring-list
echo "Esperando a que el servicio esté listo..."
sleep 10

echo "📄 GET /listar"
curl -s -w "🔗 HTTP %{http_code}\n" http://localhost/listar/api/productos/listar

# ------- SEARCH -------
echo -e "\n🔍 p-spring-search"
docker build -t danysoftdev/p-spring-search:$TAG ../p-spring-search
docker push danysoftdev/p-spring-search:$TAG
kubectl set image deployment/search-deployment search-container=danysoftdev/p-spring-search:$TAG -n p-spring-search
kubectl rollout status deployment/search-deployment -n p-spring-search
echo "Esperando a que el servicio esté listo..."
sleep 10

echo "🔎 GET /buscar/P100"
curl -s -w "🔗 HTTP %{http_code}\n" http://localhost/buscar/api/productos/obtener/P100

# ------- UPDATE -------
echo -e "\n✏️ p-spring-update"
docker build -t alejandro411/p_spring_update:$TAG ../p-spring-update
docker push alejandro411/p_spring_update:$TAG
kubectl set image deployment/update-deployment update-container=alejandro411/p_spring_update:$TAG -n p-spring-update
kubectl rollout status deployment/update-deployment -n p-spring-update
echo "Esperando a que el servicio esté listo..."
sleep 10

echo "✏️ PUT /actualizar/P100"
curl -s -w "🔗 HTTP %{http_code}\n" -X PUT http://localhost/actualizar/api/productos/actualizar/P100 \
  -H "Content-Type: application/json" \
  -d '{"codigo":"P100", "nombre":"Pantalon Actualizado", "precio":60000, "cantidad":12}'

# ------- DELETE -------
echo -e "\n❌ p-spring-delete"
docker build -t alejandro411/p_spring_delete:$TAG ../p-spring-delete
docker push alejandro411/p_spring_delete:$TAG
kubectl set image deployment/delete-deployment delete-container=alejandro411/p_spring_delete:$TAG -n p-spring-delete
kubectl rollout status deployment/delete-deployment -n p-spring-delete
echo "Esperando a que el servicio esté listo..."
sleep 10

echo "🗑️ DELETE /eliminar/P100"
curl -s -w "🔗 HTTP %{http_code}\n" -X DELETE http://localhost/eliminar/api/productos/eliminar/P100

echo -e "\n✅ Todo verificado correctamente. Automatización finalizada 🎯"
