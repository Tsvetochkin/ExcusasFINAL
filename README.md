# Excusas S.A. — Sistema de Gestión de Excusas

Trabajo práctico parcial 2 — Diseño de Sistemas — Universidad Da Vinci (5° cuatrimestre)

REST API construida con **Spring Boot 4** y **Java 21** que simula el proceso de presentación y evaluación de excusas laborales a través de una cadena de encargados.

---

## Tecnologías

| Tecnología | Versión |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.6 |
| Spring Data JPA / Hibernate | — |
| Base de datos | H2 (in-memory) |
| Build | Maven (wrapper incluido) |

---

## Patrones de diseño implementados

| Patrón | Dónde |
|---|---|
| **Chain of Responsibility** | `Encargado` y sus subclases — cadena de reclasificación de excusas |
| **Template Method** | `Encargado.revisarExcusa()` — define el esqueleto del algoritmo |
| **State** | `DeliveryModo` + `IModo` — el comportamiento del encargado cambia según cuántas excusas procesó |
| **Observer** | `AdministradorProntuarios` notifica a `EquipoDireccion` al registrar un prontuario |
| **Singleton** | `AdministradorProntuarios` — una sola instancia en toda la aplicación |
| **Builder** | `CadenaEncargadosBuilder` — arma la cadena paso a paso |
| **Factory Method** | Creación de instancias de `Excusa` según el tipo |

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

# ejecutar con el wrapper de Maven (no requiere tener Maven instalado)
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

## Diagrama de flujo de una excusa

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

Los diagramas están en la carpeta `/diagrams` en formato `.drawio` (importar en [app.diagrams.net](https://app.diagrams.net)):

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
