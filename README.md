# Employee Technical Test

## Requisitos Previos

Asegúrate de tener instalados los siguientes requisitos en tu máquina:

- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Base de datos MySQL](https://www.mysql.com/downloads/)

## Instalación

Sigue estos pasos para instalar y configurar el proyecto:

1. **Clonar el repositorio:**

   ```bash
   git clone https://github.com/riquelmip/mantisa-inventory.git
   ```

2. **Configurar la base de datos:**
    ### Base de datos local:

   - Crea una base de datos en MySQL con el nombre `mantisa-inventory-db`.
     - Abre el archivo `src/main/resources/application-local.properties` y configura las
       propiedades `spring.datasource.url`, `spring.datasource.username` y `spring.datasource.password` con los datos de
       tu base de datos.
       De la siguiente manera:
       ```spring.datasource.url=jdbc:mysql://localhost:3306/employees-technical-test-db
          spring.datasource.username=root
          spring.datasource.password=password
       ```

3. **Limpiar el proyecto:**

   ```bash
   mvn clean
   ```

4. **Compilar el proyecto:**

   ```bash
    mvn compile
   ```

5. **Ejecutar el proyecto:**

   ```bash
   mvn spring-boot:run
   ```



# Documentación de la API

La documentación de la API se encuentra en: [Documentación de la API](https://documenter.getpostman.com/view/27314058/2sAXxY48VU).


## Probar la Aplicación
- **Backend**: [https://mantisa-inventory-production.up.railway.app/api](https://mantisa-inventory-production.up.railway.app/api)
- **Frontend**: [https://matinsa-inventory.netlify.app](https://matinsa-inventory.netlify.app)
