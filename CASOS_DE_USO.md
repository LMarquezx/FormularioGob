# Casos de Uso - Sistema de Formulario de Inscripción

## Información del Sistema
**Nombre del Sistema:** FormularioGob  
**Tecnología:** Spring Boot + Thymeleaf + Apache POI (Word)  
**Objetivo:** Sistema web para generar solicitudes de inscripción en formato DOCX

---

## Actores

### Actor Principal
- **Usuario/Solicitante**: Persona que llena el formulario de inscripción (estudiante, padre/madre/tutor)

### Actores Secundarios
- **Sistema**: Procesa los datos y genera el documento

---

## Casos de Uso

### CU-001: Acceder al Formulario de Inscripción
**Actor:** Usuario  
**Descripción:** El usuario accede a la página principal del sistema para llenar el formulario de inscripción.

**Flujo Principal:**
1. El usuario navega a la URL del sistema
2. El sistema muestra la página principal con el formulario de inscripción
3. El formulario se presenta organizado en secciones temáticas

**Postcondiciones:** El formulario está disponible para ser llenado

---

### CU-002: Llenar Datos Generales del Plantel
**Actor:** Usuario  
**Descripción:** El usuario ingresa los datos básicos de la institución educativa.

**Flujo Principal:**
1. El usuario completa la sección "Datos Generales" que incluye:
   - Nombre del plantel
   - Clave y turno
   - Localidad y municipio de la institución
   - Fecha de la solicitud
   - Año escolar
   - Folios de arancel por semestre (1° a 6°)

**Precondiciones:** El formulario debe estar cargado
**Postcondiciones:** Los datos generales quedan registrados en el formulario

---

### CU-003: Registrar Datos del Solicitante
**Actor:** Usuario  
**Descripción:** El usuario ingresa la información personal del estudiante que solicita la inscripción.

**Flujo Principal:**
1. El usuario completa la sección "Datos del Solicitante":
   - Nombre completo (nombre, apellido paterno, apellido materno)
   - Lugar y fecha de nacimiento
   - Tipo de sangre
   - Lengua materna
   - CURP
   - Género (MASC/FEM)
   - Nacionalidad (MEX/OTRA)

**Reglas de Negocio:**
- El CURP debe tener exactamente 18 caracteres
- El género y nacionalidad son campos de selección única
- Todos los campos son obligatorios

**Postcondiciones:** Los datos del solicitante quedan registrados

---

### CU-004: Registrar Datos de Contacto
**Actor:** Usuario  
**Descripción:** El usuario proporciona información de contacto del solicitante.

**Flujo Principal:**
1. El usuario completa:
   - Domicilio completo
   - Localidad del solicitante
   - Código postal (5 dígitos)
   - Teléfono (10 dígitos)
   - Correo electrónico

**Reglas de Negocio:**
- El código postal debe ser numérico de 5 dígitos
- El teléfono debe ser numérico de 10 dígitos
- El correo debe tener formato válido

---

### CU-005: Registrar Información de Secundaria
**Actor:** Usuario  
**Descripción:** El usuario ingresa datos sobre la educación secundaria del solicitante.

**Flujo Principal:**
1. El usuario completa:
   - Nombre del plantel de procedencia
   - Localidad de procedencia
   - Fecha de conclusión de secundaria
   - Tipo de alumno (CON CERTIFICADO/FALTA CERTIFICADO)
   - Modalidad (ESCOLARIZADA/VEA)
   - Turno (MAT/VESP/NOCT/ABIERTO)
   - Dependencia (SEV/SEP/OTRO)

**Postcondiciones:** Los datos de secundaria quedan registrados

---

### CU-006: Configurar Datos de Telebachillerato
**Actor:** Usuario  
**Descripción:** El usuario especifica información sobre el telebachillerato donde desea inscribirse.

**Flujo Principal:**
1. El usuario selecciona/completa:
   - Nombre del plantel de telebachillerato
   - Clave del telebachillerato
   - Localidad del telebachillerato
   - Semestre a cursar (1° a 6°)
   - Área propedéutica (QB/EA/HC/FM)
   - Grupo (A-I)
   - Tipo de alumno (REGULAR/IRREGULAR/REPETIDOR)

**Reglas de Negocio:**
- Solo se puede seleccionar un semestre
- Solo se puede seleccionar una área propedéutica
- Solo se puede seleccionar un grupo

---

### CU-007: Registrar Datos de la Madre
**Actor:** Usuario  
**Descripción:** El usuario ingresa información completa de la madre del solicitante.

**Flujo Principal:**
1. El usuario completa información personal:
   - Nombre completo, CURP, fecha de nacimiento
   - Estado civil, domicilio completo
   - Localidad, entidad, código postal
   - Teléfono, ocupación
   - Nivel de estudios, grado específico
2. El usuario selecciona opciones específicas:
   - Sexo (MASCULINO/FEMENINO)
   - ¿Es tutor? (SÍ/NO)
   - ¿Sabe leer? (SÍ/NO)

**Reglas de Negocio:**
- Todos los campos son obligatorios
- Solo puede haber un tutor entre padre y madre

---

### CU-008: Registrar Datos del Padre
**Actor:** Usuario  
**Descripción:** El usuario ingresa información completa del padre del solicitante.

**Flujo Principal:**
1. Similar al CU-007 pero para el padre
2. Incluye validación de tutor único

**Reglas de Negocio:**
- Si la madre ya es tutor, el padre no puede serlo
- Mismas validaciones que para la madre

---

### CU-009: Registrar Datos del Tutor (Opcional)
**Actor:** Usuario  
**Descripción:** Si existe un tutor diferente a los padres, se registran sus datos.

**Flujo Principal:**
1. El usuario completa información del tutor (campos opcionales)
2. Incluye parentesco con el solicitante
3. Opciones de sexo y alfabetización

**Precondiciones:** Debe existir un tutor diferente a los padres

---

### CU-010: Seleccionar Documentos Presentados
**Actor:** Usuario  
**Descripción:** El usuario marca los documentos que está presentando con la solicitud.

**Flujo Principal:**
1. El usuario selecciona de una lista de checkboxes:
   - ACTA DE NACIMIENTO Y 2 FOTO COPIAS
   - CARTA DE BUENA CONDUCTA
   - CURP
   - CONSTANCIA LEGALIZADA
   - CERTIFICADO DE SECUNDARIA COMPLETO Y DOS FOTO COPIAS
   - CUATRO FOTOCOPIAS TAMAÑO INFANTIL EN BLANCO Y NEGRO
   - CARTA RESPONSIVA
2. Opcionalmente especifica otros documentos

**Reglas de Negocio:**
- Se pueden seleccionar múltiples documentos
- Campo "otros documentos" es opcional

---

### CU-011: Registrar Becas del Alumno
**Actor:** Usuario  
**Descripción:** El usuario indica qué becas tiene o solicita el estudiante.

**Flujo Principal:**
1. El usuario selecciona becas aplicables:
   - PROSPERA
   - CONTRA EL ABANDONO ESCOLAR
   - INGRESO
   - PERMANENCIA
   - EXCELENCIA
2. Opcionalmente especifica otra beca

**Reglas de Negocio:**
- Se pueden seleccionar múltiples becas
- Campo "otra beca" es opcional

---

### CU-012: Registrar Discapacidades del Alumno
**Actor:** Usuario  
**Descripción:** El usuario indica si el estudiante tiene alguna discapacidad.

**Flujo Principal:**
1. El usuario selecciona discapacidades aplicables:
   - CEGUERA
   - SORDERA
   - MOTRIZ
   - VISUAL
   - AUDITIVA
2. Opcionalmente especifica otra discapacidad

**Reglas de Negocio:**
- Se pueden seleccionar múltiples discapacidades
- Todos los campos son opcionales

---

### CU-013: Generar Documento de Solicitud
**Actor:** Usuario, Sistema  
**Descripción:** El sistema procesa todos los datos del formulario y genera un documento Word (.docx).

**Flujo Principal:**
1. El usuario hace clic en "Generar Documento"
2. El sistema valida que todos los campos requeridos estén completos
3. El sistema procesa los datos:
   - Convierte fechas al formato requerido
   - Mapea selecciones a símbolos (⚫ para campos marcados)
   - Procesa arrays de checkboxes
4. El sistema carga la plantilla Word desde recursos
5. El sistema reemplaza marcadores en la plantilla con los datos procesados
6. El sistema genera el documento final
7. El sistema envía el documento al navegador para descarga

**Flujo Alternativo:**
- Si faltan campos requeridos, el sistema muestra errores de validación
- Si hay error en la generación, el sistema muestra mensaje de error

**Reglas de Negocio:**
- Solo puede haber un tutor entre padre, madre o tutor externo
- Las fechas se descomponen en día, mes y año
- Los campos de selección múltiple se procesan como arrays
- El documento se genera con fuente Arial, tamaño 7

**Precondiciones:** 
- Todos los campos requeridos deben estar completos
- El sistema debe tener acceso a la plantilla Word

**Postcondiciones:** 
- Se genera un archivo DOCX con nombre "solicitud_generada.docx"
- El archivo se descarga automáticamente al navegador del usuario

---

## Validaciones del Sistema

### Validaciones de Frontend (JavaScript)
- **Validación de Tutor Único**: Impide que tanto padre como madre sean marcados como tutores
- **Validaciones HTML5**: Formato de email, longitud de campos, patrones numéricos

### Validaciones de Backend (Java)
- **Procesamiento de Arrays**: Manejo seguro de campos de selección múltiple
- **Validación de Fechas**: Descomposición y validación de formatos de fecha
- **Mapeo de Campos**: Conversión de valores del formulario a marcadores de plantilla

---

## Tecnologías Utilizadas

- **Frontend**: HTML5, Bootstrap 5.3.2, JavaScript vanilla
- **Backend**: Spring Boot 3.5.3, Spring MVC, Thymeleaf
- **Generación de Documentos**: Apache POI (para manipulación de Word)
- **Build**: Maven
- **Java**: Version 24 (compilación con release 23)

---

## Archivos y Recursos

- **Controlador Principal**: `PdfController.java`
- **Vista del Formulario**: `form.html`
- **Plantilla Word**: `solicitud_template1.docx`
- **Fuentes**: `OpenSans-Regular.ttf`
- **Configuración**: `application.properties`
