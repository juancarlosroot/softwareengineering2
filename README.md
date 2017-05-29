# Proyecto de generación de grafos y cobertura

# Descripción

Este software tiene el objetivo de analizar el grafo de ejecución de un programa y hacer la cobertura de caminos, aristas y lógica del programa, con el proposito de que sea de utilidad en la verificación y pruebas de software

Tiene las siguientes funciones principales

### Generación del grafo de ejecución

El software analiza un programa en C, y genera el grafo de ejecución del programa analizado

### Generar cobertura de caminos primos

El software genera los caminos primos del grafo de ejecución

### Cobertura de aristas

Se calcula la cobertura de pares de aristas del grafo de ejecución

### Cobertura lógica

Se calcula además la cobertura lógica a través de las expresiones booleanas que se obtienen en el programa analizado

--------------------------

Para correr el proyecto se necesita lo siguiente

## JDK 8

El proyecto fué desarrollado con la versión 8 de Java Development Kit

## NetBeans IDE

El proyecto fué desarrollado en esta plataforma

## Instalar graphviz

Es necesario instalar graphviz para la generación del grafo de ejecución del programa, la versión para windows se puede encontrar en el siguiente enlace, se siguen los pasos de instalación

http://www.graphviz.org/pub/graphviz/stable/windows/graphviz-2.38.msi

Es necesario checar que el programa se instala en Program Files(x86)

### Revisar la ruta de graphviz en las clases del proyecto

La clase de configuración que contiene la ruta de graphviz es:
+ ConfigProject.java

La cual contiene la siguiente variable:

    public static final String cmdGraphviz="C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe";
    
La cual es la ruta donde se encuentra el archivo dot.exe el cual se encarga de generar el grafo a partir del texto

En el caso que se use otro sistema operativo se deberá de cambiar la ruta

# Directorios del proyecto

Dentro del proyecto se encuentran varias carpetas, las cuales son:
 + programasC
 
 En esta carpeta se encuentran los ejemplos de los programas en C
 
 + GrafoTexto
 
 Son los archivos que se usarán de entrada para graphviz
 
 + GrafoImg
 
 Aquí se guardan las imagenes generadas por Graphviz
 
 + Salidas
 
 Son los archivos generados que se recibirán de entrada por el módulo que calcula los PrimePaths, DuPaths.

### En el caso de correr el proyecto desde NetBeans


Para importar el proyecto en NetBeans y surge algún problema a la hora de ejecutar el archivo es necesario revisar lo siquiente:
+ propiedades del proyecto

En este caso nos vamos a la opción de RUN, revisamos que en WORKING DIRECTORY no tenga texto


Generación del grafo de ejecución
--------------------

Para la generación del grafo de ejecución se realizó un análisis sintáctico en el código del programa en C, utilizando las herramientas
+ jFlex
+ JavaCup

En JavaCup al analizar las reglas gramaticales se generan a la vez los nodos que servirán para representar el grafo de ejecución, el archivo que contiene la gramática es el siguiente:

+ Parser6.cup

En el caso de que falten ciertas reglas gramaticales, esperamos sus comentarios y tareas a realizar en ISSUES
