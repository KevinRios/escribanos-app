# Escribanos App

Aplicación web para consultar la nómina de escribanos del Colegio de Escribanos de la Ciudad de Buenos Aires.

## Requisitos

- Java 11
- Maven 3.6.x
- Spring Boot 2.4.x
- Tomcat 9.x

## Configuración

1. Clonar el repositorio:
```bash
git clone https://github.com/KevinRios/escribanos-app.git
```



2. Configurar application.properties:

```properties
# JWT Configuration
jwt.issuer=<issuer>
jwt.secret=<secret>
jwt.subject=<subject>
jwt.audience=<audience>
jwt.role=<role>
jwt.expiration=30

```
Agregar las credenciales correspondientes


![image](https://github.com/user-attachments/assets/f60068f6-9281-457d-9d85-783f11970f49)


## Ejecución

1. Compilar el proyecto:
```bash
mvn clean install
```

2. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

# Como ejecutar en IDE STS
```bash
Ir a Run
Run Configurations
Spring Boot App
```
![image](https://github.com/user-attachments/assets/0f1254bd-9ea5-464c-9cfb-a15a29cd03fb)

```bash
En project -> escribanos-app
El Main Type aparece al seleccionar el project
```
Con esto ya se puede ejecutar el proyecto
![image](https://github.com/user-attachments/assets/ac926caa-a587-42fa-b250-d82b66eea9ba)




La aplicación estará disponible en: http://localhost:8080

La pagina principal se ve asi 
![image](https://github.com/user-attachments/assets/2b0aa2ab-08ba-4f49-95b7-88ccf295b83a)



## Endpoints

### Buscar Escribano por CUIT

```
GET /nomina/search?cuit={cuit}
```

Desde postman el endpoint completo seria 

```
GET http://localhost:8080/nomina/search?cuit={cuit}
```

#### Ejemplos de Respuestas

1. Escribano encontrado (200 OK):
```json
{
    "mensaje": "Escribano encontrado exitosamente",
    "data": {
        "nombre": "Santiago Joaquin Enrique",
        "apellido": "PANO",
        "matricula": "4818",
        "telefono": "4322-4322/4201",
        "email": "desarrollo@colegio-escribanos.org.ar",
        "estado": "ACTIVO"
    }
}
```

![image](https://github.com/user-attachments/assets/489c5352-cda0-430d-b294-7ed5523aa0cb)



2. Escribano no encontrado (200 OK):
```json
{
    "mensaje": "No se encontró un escribano para la CUIT informada",
    "data": null
}
```
![image](https://github.com/user-attachments/assets/b796b69e-5886-4eb1-8551-f3d501dc9a07)




3. CUIT inválido (400 Bad Request):
```json
{
    "mensaje": "La CUIT debe ser numérica, de longitud 11",
    "data": null
}
```
![image](https://github.com/user-attachments/assets/3c3b5e4e-2e17-47eb-80e3-b37f0f44ccd5)




