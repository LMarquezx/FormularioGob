# Configuración de MongoDB Atlas para FormularioGob

## Pasos para configurar MongoDB Atlas

### 1. Crear cuenta en MongoDB Atlas
1. Ve a [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)
2. Regístrate o inicia sesión
3. Crea un nuevo proyecto llamado "FormularioGob"

### 2. Crear un Cluster
1. Haz clic en "Build a Database"
2. Selecciona "FREE" (M0 Sandbox)
3. Selecciona tu región preferida
4. Nombra tu cluster (ej: "cluster-formulario")
5. Haz clic en "Create Cluster"

### 3. Configurar acceso a la base de datos
1. En la sección "Security" → "Database Access":
   - Haz clic en "Add New Database User"
   - Selecciona "Password" como método de autenticación
   - Crea un usuario (ej: `formulario_user`)
   - Genera una contraseña segura (guárdala)
   - En "Database User Privileges", selecciona "Read and write to any database"
   - Haz clic en "Add User"

### 4. Configurar acceso de red
1. En la sección "Security" → "Network Access":
   - Haz clic en "Add IP Address"
   - Selecciona "Allow access from anywhere" (0.0.0.0/0) para desarrollo
   - O agrega tu IP específica para mayor seguridad
   - Haz clic en "Confirm"

### 5. Obtener la cadena de conexión
1. En "Database" → "Clusters", haz clic en "Connect"
2. Selecciona "Connect your application"
3. Selecciona "Java" como driver y la versión más reciente
4. Copia la cadena de conexión que se ve así:
   ```
   mongodb+srv://<username>:<password>@cluster-formulario.xxxxx.mongodb.net/?retryWrites=true&w=majority
   ```

### 6. Configurar application.properties
1. Abre el archivo `src/main/resources/application.properties`
2. Reemplaza la línea de configuración de MongoDB:
   ```properties
   spring.data.mongodb.uri=mongodb+srv://formulario_user:TU_PASSWORD@cluster-formulario.xxxxx.mongodb.net/formulario_gobierno?retryWrites=true&w=majority
   ```
3. Reemplaza:
   - `formulario_user` por tu nombre de usuario
   - `TU_PASSWORD` por tu contraseña
   - `cluster-formulario.xxxxx.mongodb.net` por tu URL de cluster real
   - `formulario_gobierno` es el nombre de la base de datos que se creará automáticamente

### Ejemplo de configuración completa:
```properties
spring.application.name=FormularioGob
spring.thymeleaf.cache=false
server.port=9090

# Configuración de MongoDB Atlas
spring.data.mongodb.uri=mongodb+srv://formulario_user:miPassword123@cluster-formulario.abc123.mongodb.net/formulario_gobierno?retryWrites=true&w=majority
spring.data.mongodb.database=formulario_gobierno

# Configuración de logging
logging.level.org.springframework.data.mongodb=INFO
logging.level.com.FormularioGob=INFO
```

## Estructura de la Base de Datos

### Colección: `solicitudes`
La aplicación creará automáticamente una colección llamada `solicitudes` que contendrá documentos con la siguiente estructura:

```json
{
  "_id": "ObjectId",
  "nombrePlantel": "String",
  "claveTurno": "String",
  "localidad": "String",
  "fecha": "String",
  "anioEscolar": "String",
  "folio1": "String",
  "folio2": "String",
  "nombreAlumno": "String",
  "apellidoPaterno": "String",
  "apellidoMaterno": "String",
  "curp": "String",
  "genero": "String",
  "nacionalidad": "String",
  "documentos": ["Array de Strings"],
  "becas": ["Array de Strings"],
  "discapacidades": ["Array de Strings"],
  "fechaCreacion": "ISODate",
  "nombreDocumentoGenerado": "String"
}
```

## Nuevas Funcionalidades

### URLs disponibles:
- `/` - Formulario principal
- `/solicitudes/consultar` - Página de consultas
- `/solicitudes/listar` - Lista todas las solicitudes
- `/solicitudes/buscar?criterio=X&valor=Y` - Buscar por criterios
- `/solicitudes/detalle/{id}` - Ver detalle de una solicitud
- `/solicitudes/estadisticas` - Ver estadísticas
- `/solicitudes/buscar-por-fecha` - Buscar por rango de fechas

### Criterios de búsqueda disponibles:
- `curp` - Buscar por CURP del estudiante
- `nombre` - Buscar por nombre del estudiante
- `plantel` - Buscar por nombre del plantel
- `semestre` - Buscar por semestre (1-6)
- `area` - Buscar por área propedéutica (QB, EA, HC, FM)

## Seguridad y Mejores Prácticas

### Para Producción:
1. **No hardcodear credenciales**: Usa variables de entorno
   ```properties
   spring.data.mongodb.uri=${MONGODB_URI}
   ```

2. **Restringir acceso de red**: En MongoDB Atlas, configura IPs específicas

3. **Usar SSL**: La configuración por defecto ya incluye SSL

4. **Backup regular**: Configura backups automáticos en MongoDB Atlas

5. **Monitoreo**: Usa las herramientas de monitoreo de Atlas

### Variables de entorno (recomendado para producción):
```bash
export MONGODB_URI="mongodb+srv://user:pass@cluster.net/db?retryWrites=true&w=majority"
```

## Troubleshooting

### Error de conexión:
1. Verifica que la IP esté en la whitelist
2. Confirma usuario y contraseña
3. Verifica que el cluster esté activo
4. Revisa los logs de la aplicación

### Error de autenticación:
1. Verifica las credenciales en MongoDB Atlas
2. Asegúrate de que el usuario tenga permisos correctos
3. Verifica que la contraseña no contenga caracteres especiales sin escapar

### Base de datos no se crea:
- La base de datos se crea automáticamente al insertar el primer documento
- Verifica que la aplicación esté enviando datos al hacer submit del formulario
