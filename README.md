# FormularioGob

## 📝 Descripción
FormularioGob es una aplicación web desarrollada con Spring Boot que permite generar documentos Word (.docx) a partir de formularios web. La aplicación está diseñada para automatizar la creación de solicitudes de inscripción gubernamentales, permitiendo a los usuarios completar un formulario en línea y obtener un documento oficial en formato Word con todos los datos ingresados.

## 🚀 Características

- **Interfaz web intuitiva**: Formulario web con diseño Bootstrap para una experiencia de usuario moderna
- **Generación automática de documentos**: Conversión de datos del formulario a documentos Word oficiales
- **Plantillas personalizables**: Uso de plantillas Word (.docx) con marcadores de posición
- **Procesamiento de fechas**: Descomposición automática de fechas en día, mes y año
- **Descarga directa**: Los documentos generados se descargan automáticamente al navegador

## 🛠️ Tecnologías Utilizadas

### Backend
- **Spring Boot 3.5.3**: Framework principal para el desarrollo de la aplicación
- **Spring Web**: Para el desarrollo de controladores REST y manejo de peticiones HTTP
- **Thymeleaf**: Motor de plantillas para la generación de vistas HTML

### Procesamiento de Documentos
- **Apache POI (5.2.5)**: Biblioteca para manipulación de documentos Office
- **Apache PDFBox (2.0.29)**: Para procesamiento de documentos PDF
- **OpenPDF (1.3.30)**: Biblioteca adicional para generación de PDFs
- **docx4j (8.3.14)**: Para manipulación avanzada de documentos Word
- **docx-stamper (2.0.0)**: Para el reemplazo de marcadores en plantillas Word

### Frontend
- **HTML5**: Estructura de las páginas web
- **Bootstrap 5.3.2**: Framework CSS para diseño responsivo
- **Thymeleaf**: Integración del backend con el frontend

### Herramientas de Desarrollo
- **Maven**: Gestión de dependencias y construcción del proyecto
- **Spring Boot DevTools**: Herramientas de desarrollo para recarga automática
- **Java 24**: Versión del lenguaje de programación

## 📋 Prerrequisitos

Antes de ejecutar la aplicación, asegúrate de tener instalado:

- **Java 23 o superior**
- **Maven 3.6 o superior**
- Un navegador web moderno

## 🔧 Instalación y Configuración

### 1. Clonar el repositorio
```bash
git clone https://github.com/LMarquezx/FormularioGob.git
cd FormularioGob
```

### 2. Compilar el proyecto
```bash
mvn clean compile
```

### 3. Ejecutar la aplicación
```bash
mvn spring-boot:run
```

O alternativamente, puedes generar el JAR y ejecutarlo:
```bash
mvn clean package
java -jar target/FormularioGob-0.0.1-SNAPSHOT.jar
```

### 4. Acceder a la aplicación
Una vez iniciada, la aplicación estará disponible en:
```
http://localhost:9090
```

## 🖥️ Uso de la Aplicación

### Formulario de Inscripción

La aplicación presenta un formulario dividido en dos secciones principales:

#### **Datos Generales**
- Nombre del Plantel
- Clave y Turno
- Localidad y Municipio de la institución
- Fecha de la solicitud
- Año escolar
- Folios de arancel por semestre (1° a 6°)

#### **Datos del Solicitante**
- Nombre completo
- Apellido Paterno
- Apellido Materno
- Lugar de Nacimiento
- Fecha de Nacimiento

### Proceso de Generación

1. **Completar el formulario**: Llena todos los campos requeridos
2. **Enviar datos**: Haz clic en "Generar PDF"
3. **Descarga automática**: El documento Word se generará y descargará automáticamente

## 📁 Estructura del Proyecto

```
FormularioGob/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/FormularioGob/FormularioGob/
│   │   │       ├── FormularioGobApplication.java
│   │   │       └── Controller/
│   │   │           └── PdfController.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── fonts/
│   │       │   └── OpenSans-Regular.ttf
│   │       ├── pdf/
│   │       │   ├── solicitud_template.pdf
│   │       │   └── solicitud_template2.pdf
│   │       ├── templates/
│   │       │   └── form.html
│   │       └── word/
│   │           └── solicitud_template.docx
│   └── test/
├── target/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## ⚙️ Configuración

### application.properties
```properties
spring.application.name=FormularioGob
spring.thymeleaf.cache=false
server.port=9090
```

### Personalización de Plantillas

Las plantillas Word están ubicadas en `src/main/resources/word/`. Para personalizar:

1. Edita el archivo `solicitud_template.docx`
2. Usa marcadores de posición con el formato `{nombreCampo}`
3. Los marcadores serán reemplazados automáticamente con los datos del formulario

#### Marcadores Disponibles:
- `{nombrePlantel}` - Nombre del plantel educativo
- `{claveTurno}` - Clave y turno
- `{localidad}` - Localidad y municipio
- `{fecha}` - Fecha de solicitud (completa)
- `{dSol}`, `{mSol}`, `{aSol}` - Día, mes y año de solicitud (separados)
- `{anioEscolar}` - Año escolar
- `{FOLIO1}` a `{FOLIO6}` - Folios de arancel por semestre
- `{nombreAlumno}` - Nombre del estudiante
- `{apellidoPaterno}` - Apellido paterno
- `{apellidoMaterno}` - Apellido materno
- `{lugarNacimiento}` - Lugar de nacimiento
- `{fechaNacimiento}` - Fecha de nacimiento (completa)
- `{diaNa}`, `{mesNa}`, `{anioNa}` - Día, mes y año de nacimiento (separados)

## 🔍 Funcionalidades Técnicas

### Procesamiento de Fechas
La aplicación incluye una función especial para descomponer fechas:
- Convierte fechas en formato `YYYY-MM-DD` a componentes separados
- Útil para formularios que requieren día, mes y año en campos separados

### Reemplazo de Marcadores
- Busca marcadores en formato `{clave}` dentro de las tablas del documento Word
- Reemplaza automáticamente con los valores del formulario
- Mantiene el formato original del documento

### Generación de Documentos
- Carga plantillas desde el classpath
- Procesa todas las tablas del documento Word
- Genera un nuevo documento con los datos reemplazados
- Retorna el archivo para descarga directa

## 🧪 Testing

Para ejecutar las pruebas:
```bash
mvn test
```

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👥 Autor

- **LMarquezx** - *Desarrollo inicial* - [LMarquezx](https://github.com/LMarquezx)

## 🆘 Soporte

Si encuentras algún problema o tienes preguntas:

1. Revisa los [Issues](https://github.com/LMarquezx/FormularioGob/issues) existentes
2. Crea un nuevo Issue si no encuentras una solución
3. Proporciona toda la información relevante para reproducir el problema

## 📈 Próximas Mejoras

- [ ] Validación avanzada de formularios
- [ ] Soporte para múltiples plantillas
- [ ] Interfaz de administración para gestión de plantillas
- [ ] Exportación a PDF además de Word
- [ ] Integración con bases de datos
- [ ] API REST para integración con otros sistemas
- [ ] Autenticación y autorización de usuarios

---
