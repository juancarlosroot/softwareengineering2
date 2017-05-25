# Proyecto de generación de grafos 

Para correr el proyecto se necesita lo siguiente


Instalar graphviz
--------------------

Es necesario instalar graphviz para la generación del grafo de ejecución del programa, la versión para windows se puede encontrar en el siguiente enlace, se siguen los pasos de instalación

http://www.graphviz.org/pub/graphviz/stable/windows/graphviz-2.38.msi

Es necesario checar que el programa se instala en Program Files(x86)


Generación del grafo de ejecución
--------------------

Para la generación del grafo de ejecución se realizó un análisis sintáctico en el código del programa en C, utilizando las herramientas
+jFlex
+JavaCup

En JavaCup al analizar las reglas gramaticales se generan a la vez los nodos que servirán para representar el grafo de ejecución, el archivo que contiene la gramática es el siguiente:

+Parser6.cup

En el caso de que falten ciertas reglas gramaticales, esperamos sus comentarios y tareas a realizar en ISSUES
