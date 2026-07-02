# Excusas S.A. — Sistema de Gestión de Excusas Laborales

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![H2](https://img.shields.io/badge/H2-In--Memory%20DB-0000CC?style=for-the-badge&logo=h2&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![REST API](https://img.shields.io/badge/REST-API-009688?style=for-the-badge&logo=fastapi&logoColor=white)

---

## Sobre el proyecto

**Excusas S.A.** es una REST API backend desarrollada como trabajo práctico de la materia **Diseño de Sistemas** (Universidad Da Vinci, 5° cuatrimestre). El sistema modela el flujo de presentación y evaluación de excusas laborales dentro de una empresa ficticia.

El objetivo del proyecto fue aplicar **7 patrones de diseño** en un contexto real, integrándolos en una arquitectura backend por capas con persistencia de datos y API REST consumible.

La lógica central funciona así: un empleado presenta una excusa (trivial, familiar, por corte de luz, compleja o inverosímil). Esa excusa recorre una **cadena de encargados** — cada uno decide si puede manejarla o la pasa al siguiente. El comportamiento de cada encargado varía según su **estado de trabajo** (productivo, normal o vago). Si la excusa es aceptada por el CEO, se genera un **prontuario** que notifica automáticamente al equipo de dirección.

---

## Tech Stack

| Categoría | Tecnología | Detalle |
|---|---|---|
| Lenguaje | Java 21 | Records, sealed classes, pattern matching |
| Framework | Spring Boot 4.0.6 | Auto-configuration, IoC container |
| Persistencia | Spring Data JPA + Hibernate | ORM, repositorios autogenerados |
| Base de datos | H2 In-Memory | Consola web en `/h2-console` |
| API | Spring MVC REST | JSON, manejo de errores con `ResponseEntity` |
| Build | Maven (wrapper) | Sin instalación requerida |

---

## Patrones de diseño implementados

| Patrón | Dónde | Por qué |
|---|---|---|
| **Chain of Responsibility** | `Encargado` y sus subclases | Desacopla quién recibe la excusa de quién la procesa |
| **Template Method** | `Encargado.revisarExcusa()` | Fija el algoritmo de revisión; las subclases solo implementan los pasos variables |
| **State** | `DeliveryModo` + `IModo` | El comportamiento del encargado cambia en runtime según su carga de trabajo |
| **Observer** | `AdministradorProntuarios` → `EquipoDireccion` | Notificación automática al registrar un prontuario sin acoplar los módulos |
| **Singleton** | `AdministradorProntuarios` | Una única fuente de verdad para todos los prontuarios de la aplicación |
| **Builder** | `CadenaEncargadosBuilder` | Construcción paso a paso de la cadena con encadenamiento fluido |
| **Factory Method** | Creación de `Excusa` según `TipoExcusa` | Centraliza la lógica de instanciación de subtipos |

---

## Arquitectura por capas

```
HTTP Client (Postman / Browser)
         ↕
┌─────────────────────────────────┐
│  CAPA REST  (Controllers)       │  recibe y valida la petición HTTP
└─────────────────────────────────┘
         ↓
┌─────────────────────────────────┐
│  CAPA DE SERVICIOS              │  orquesta la lógica de negocio
└─────────────────────────────────┘
         ↓
┌─────────────────────────────────┐
│  CAPA DE DOMINIO                │  Chain · State · Observer · Builder
└─────────────────────────────────┘
         ↓
┌─────────────────────────────────┐
│  REPOSITORIOS  (Spring Data)    │  acceso a datos, SQL autogenerado
└─────────────────────────────────┘
         ↓
┌─────────────────────────────────┐
│  H2 IN-MEMORY DATABASE          │  tablas: excusas · empleados · prontuarios
└─────────────────────────────────┘
```

---

## Cómo ejecutar

```bash
# clonar el repositorio
git clone https://github.com/Tsvetochkin/ExcusasFINAL.git
cd ExcusasFINAL

# ejecutar con el wrapper de Maven (no requiere Maven instalado)
./mvnw spring-boot:run
```

La aplicación arranca en `http://localhost:8080`.

Consola H2 disponible en `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:excusasdb`
- User: `sa` / Password: *(vacío)*

> Al arrancar, `ExcusasApplication.java` precarga empleados, encargados y excusas de ejemplo automáticamente.

---

## API — Endpoints

### Excusas `/excusas`

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/excusas` | Registra una excusa y la procesa por la cadena |
| `GET` | `/excusas` | Lista todas las excusas (filtros opcionales: `?motivo=TRIVIAL` o `?encargado=Ana`) |
| `GET` | `/excusas/{legajo}` | Excusas de un empleado por número de legajo |
| `GET` | `/excusas/rechazadas` | Solo las excusas rechazadas |
| `GET` | `/excusas/busqueda?legajo=&fechaDesde=&fechaHasta=` | Búsqueda por legajo y rango de fechas |
| `DELETE` | `/excusas/eliminar?fechaLimite=YYYY-MM-DD` | Elimina excusas anteriores a la fecha indicada |

**Body — POST `/excusas`:**
```json
{
  "legajo": 501,
  "motivo": "TRIVIAL"
}
```

**Valores válidos para `motivo`:** `TRIVIAL` · `FAMILIAR` · `LUZ` · `COMPLEJA` · `INVEROSIMIL`

**Respuesta:**
```json
{
  "id": 1,
  "motivo": "TRIVIAL",
  "aceptada": true,
  "aceptadaPor": "Recepcionista",
  "fecha": "2026-07-02"
}
```

---

### Empleados `/empleados`

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/empleados` | Registra un nuevo empleado |
| `GET` | `/empleados` | Lista todos los empleados |

**Body — POST `/empleados`:**
```json
{
  "nombre": "Juan Pérez",
  "email": "juan@empresa.com",
  "nroLegajo": 505
}
```

---

### Encargados `/encargados`

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/encargados` | Estado actual de la cadena de encargados |
| `POST` | `/encargados` | Agrega un encargado dinámico a la cadena |
| `PUT` | `/encargados/modo` | Cambia el modo de trabajo de un encargado |

**Body — POST `/encargados`:**
```json
{
  "nombre": "Carlos",
  "email": "carlos@empresa.com",
  "nroLegajo": 200,
  "motivos": ["TRIVIAL", "FAMILIAR"]
}
```

**Body — PUT `/encargados/modo`:**
```json
{
  "nroLegajo": 101,
  "modo": "VAGO"
}
```

**Valores válidos para `modo`:** `NORMAL` · `VAGO` · `PRODUCTIVO`

---

### Prontuarios `/prontuarios`

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/prontuarios` | Lista todos los prontuarios registrados |

> Solo el **CEO** genera prontuarios al aceptar una excusa `INVEROSIMIL`.

---

## Flujo de una excusa

```
POST /excusas { legajo: 501, motivo: "TRIVIAL" }
        ↓
ExcusasController → ExcusaService
        ↓
Busca Empleado en BD → crea ExcusaTrivial
        ↓
Cadena de encargados:
  Recepcionista.revisarExcusa()
    → puedeManejar? esTrivial() = true
    → procesar() → aceptada = true, envía email
        ↓
Guarda en BD → devuelve ExcusaResponseDTO
```

## Jerarquía de excusas

```
Excusa (abstract)
├── ExcusaTrivial          ← la maneja Recepcionista
├── ExcusaCompleja         ← la maneja GerenteRRHH
├── ExcusaInverosimil      ← la maneja CEO (genera prontuario)
└── ExcusaModerada (abstract)
    ├── ExcusaFamiliar     ← la maneja Supervisora
    └── ExcusaLuz          ← la maneja Supervisora
```

---

## Diagramas UML

Los diagramas están en `/diagrams` en formato `.drawio` — importar en [app.diagrams.net](https://app.diagrams.net):

| Archivo | Contenido |
|---|---|
| `casos-de-uso.drawio` | Actores y casos de uso del sistema |
| `diagrama-clases.drawio` | Clases, relaciones, cardinalidades y patrones |
| `diagrama-arquitectura.drawio` | Arquitectura por capas con componentes |

---

## Estructura del proyecto

```
src/main/java/ar/edu/davinci/excusas/
├── ExcusasApplication.java          ← arranque + datos de ejemplo
├── model/
│   ├── builder/
│   │   └── CadenaEncargadosBuilder  ← Builder pattern
│   ├── controller/                  ← REST endpoints
│   ├── domain/
│   │   ├── chain/                   ← Chain of Responsibility + Template Method
│   │   ├── excusas/                 ← jerarquía de Excusa
│   │   ├── observer/                ← Observer pattern + Singleton
│   │   └── state/                   ← State pattern
│   ├── dto/                         ← objetos de transferencia (request/response)
│   ├── repository/                  ← Spring Data JPA
│   └── service/                     ← lógica de negocio
diagrams/
├── casos-de-uso.drawio
├── diagrama-clases.drawio
└── diagrama-arquitectura.drawio
```
