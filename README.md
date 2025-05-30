# 🔍 Microservicio de Creación de Productos - Spring Boot + MariaDB + Kubernetes

Este proyecto es un **microservicio de creación de productos** desarrollado con **Spring Boot**. Su única funcionalidad es **crear productos** en una base de datos relacional **MariaDB**.  
Está preparado para desplegarse en un clúster **Kubernetes**, e incluye pruebas unitarias, de integración con **Testcontainers**, y generación de **reportes de cobertura** con **JaCoCo**.

---

## ⚙️ Características

- Creación de productos (`/api/productos/crear`)
- Integración con base de datos MariaDB
- Despliegue en Kubernetes con manifiestos YAML
- Pruebas unitarias (JUnit 5 + Mockito)
- Pruebas de integración con Testcontainers
- Reporte de cobertura de pruebas con JaCoCo

---

## 🧰 Tecnologías Utilizadas

- Java 17
- Spring Boot 3
- MariaDB
- Kubernetes (manifiestos YAML)
- JUnit 5
- Mockito
- Testcontainers
- JaCoCo

---

## 📦 Funcionalidad del Microservicio

| Método | Endpoint                | Descripción                                           |
|--------|-------------------------|-------------------------------------------------------|
| `POST` | `/api/productos/crear`  | Devuelve un mensaje dependiendo el resultado obtenido |

---

## 🚀 Pruebas

- **Pruebas unitarias** del controlador y del servicio:

  ```bash
  ./mvnw test -Dtest="*ControllerTest,*ServiceTest"
  ````

- **Pruebas de integración** con base de datos usando Testcontainers:

  ```bash
  ./mvnw test -Dtest="*IT"
  ```

---

## 📊 Reporte de Cobertura

- **Generar reporte:**

  ```bash
  ./mvnw verify
  ```

- **Ver el reporte HTML:**
  Abre el archivo:

  ```
  target/site/jacoco/index.html
  ```

- **Ver en consola:**

  ```bash
  ./mvnw test -Pcoverage
  ```

---

## ☸️ Despliegue en Kubernetes

Todos los manifiestos YAML se encuentran en la carpeta:

```
k8s/
```

Incluyen:

* Namespace
* Deployment
* Service
* Ingress
* Secret
* Configuración para acceso a imagen de GHCR

> Se espera que la base de datos MariaDB esté ya desplegada en el clúster y accesible desde este microservicio.

---

## Diagrama de infraestructura

![Diagrama de infraestructura](./img/diagrama-infraestructura.png)

---

## 👩‍💻 Autores

**Angie Dayanna Díaz Flórez**
**Daniela Villalba Torres**
*Estudiantes de Ingeniería de Software - Universidad EAM*

---

## 📄 Licencia

Uso académico. Libre para fines educativos y no comerciales.

