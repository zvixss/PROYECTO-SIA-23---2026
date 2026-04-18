# Sistema de Gestión Hospitalaria (SIA Hospital)

## 📋 Descripción del Proyecto
Este sistema es una solución integral desarrollada en **Java** para la administración eficiente del personal de enfermería y la organización de sus turnos laborales. El programa permite estructurar la información por áreas críticas (como Urgencias o Pediatría), facilitando el registro, búsqueda y edición de profesionales mediante una arquitectura modular y el uso de colecciones anidadas.

---

## 🚀 Características Principales (Requerimientos SIA)

* **Dualidad de Interfaz**: Al iniciar, el sistema permite elegir entre operar mediante **consola** (CLI) o a través de una **interfaz gráfica** desarrollada en Swing (GUI).
* **Persistencia Batch**: Implementa un sistema de carga y guardado automático de datos utilizando archivos **CSV**, asegurando que la información se mantenga entre sesiones.
* **Gestión de Turnos**: Funcionalidad de negocio que permite asignar jornadas laborales específicas y verificar la disponibilidad horaria de cada enfermera para evitar conflictos.
* **Validación de Datos**: Control estricto de formatos mediante la clase `Validador` (validación de RUT chileno) y el manejo de excepciones personalizadas.

---

## 🛠️ Estructura del Código

* **Capa de Dominio**: Clases `Enfermera` y `Area` que representan las entidades del hospital.
* **Lógica de Negocio**: Clase `GestionHospital` que centraliza las operaciones y el manejo de colecciones (`HashMap` y `ArrayList`).
* **Persistencia**: Clase `GestorArchivos` encargada de la comunicación con el archivo `datos_hospital.csv`.
* **Manejo de Errores**: Excepciones personalizadas `RutInvalidoException` y `EnfermeraNoEncontradaException`.

---

## 💻 Instrucciones de Ejecución

### 1. Preparación
* Asegúrese de tener instalado el **JDK 17** o superior.
* Verifique que el archivo `datos_hospital.csv` se encuentre en la carpeta raíz del proyecto.

### 2. Ejecución en NetBeans
1. Vaya a `File` > `New Project` > `Java with Ant` > `Java Project with Existing Sources`.
2. En `Project Folder`, seleccione la carpeta raíz del proyecto.
3. En **Source Package Folders**, añada la carpeta `src` dentro de `Proyecto SIA 23 - 2026`.
4. Haga clic derecho sobre el proyecto y seleccione **Run**.

### 3. Ejecución en Eclipse
1. Vaya a `File` > `New` > `Java Project`.
2. Desmarque **"Use default location"** y busque la carpeta raíz del proyecto.
3. Asegúrese de que la carpeta `src` esté incluida en el `Build Path`.
4. Ejecute la clase `Main.java` como **Java Application**.

### 4. Ejecución en IntelliJ IDEA
1. Seleccione `File` > `Open` y elija la carpeta raíz.
2. El IDE reconocerá automáticamente la configuración de la carpeta `.idea`.
3. Presione el botón de **Run** en la clase `Main`.

---

## 👥 Integrantes
* **Vicente Aburto Falcón**
* **Benjamín Muñoz Ortiz**

**Asignatura**: ICI2241-1 | Programación Avanzada  
**Profesor**: Leonardo Gonzalez Catalan  
**Institución**: Pontificia Universidad Católica de Valparaíso (PUCV)
