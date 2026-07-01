MS-Pagos

Microservicio encargado de gestionar los pagos y liquidaciones de las subastas en la Caleta Lo Abarca.

## Tecnologías
- Java 21
- Spring Boot 3.5.14
- PostgreSQL (Neon)
- Maven

## Puerto
`8085`

## Endpoints

### Pagos
| Método | URL | Descripción |
|--------|-----|-------------|
| POST | /api/pagos | Crear nuevo pago |
| GET | /api/pagos | Obtener todos los pagos |
| GET | /api/pagos/{id} | Obtener pago por ID |
| GET | /api/pagos/pescador/{pescadorId} | Obtener pagos por pescador |
| GET | /api/pagos/comprador/{compradorId} | Obtener pagos por comprador |
| PUT | /api/pagos/{id}/confirmar | Confirmar pago |
| PUT | /api/pagos/{id}/anular | Anular pago |

## Cómo correr el proyecto

1. Clonar el repositorio
```bash
git clone https://github.com/Malos-pal-catre/ms-pagos.git
```

2. Entrar a la carpeta
```bash
cd ms-pagos
```

3. Correr el proyecto
```bash
./mvnw spring-boot:run
```

## Modelo de datos

**Pago**
- id, subastaId, pescadorId, compradorId, especie, kilos, precioFinal, porcentajeCaleta, montoCaleta, montoNeto, estado, fechaCreacion, fechaPago