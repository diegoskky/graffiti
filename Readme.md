Proyecto de Programación 
========================
Parte I 
Autómata Finito No Determinista Tarea

Objetivo
-------------------
Diseñar e implementar una aplicación que permita dibujar, de manera interactiva, un autómata finito no determinista; y que además permita determinar si una palabra ingresada pertenece al lenguaje representado por dicho autómata.
El autómata dibujado debe cumplir con las características teóricas revisadas durante clase, de tal manera que sea consistente con la definición formal de una máquina de estados (y por ende, de un AFND formal).

Requisitos Indispensables
-------------------------
Los requisitos indispensables son intransables, lo que significa que cumplir con ellos es necesario para aprobar la unidad. No cumplir con al menos uno de ellos es causal de reprobación de la unidad correspondiente, y por ende, (potencialmente) causal de reprobación del curso (ver sección de Observaciones).

Los requisitos indispensables cumplidos aportan 0.6 puntos cada uno. La nota final de la unidad se calculará contabilizando el puntaje total alcanzado con los requisitos indispensables, y en caso de cumplirse todos, podrá añadirse el puntaje de los requisitos deseables usando el esquema descrito en la sección correspondiente.

1. La aplicación debe permitir dibujar nodos, los cuales deben tener un tipo (inicio, estado, final), una representación visual de acuerdo a su tipo (flecha para nodo de inicio, doble círculo para nodo final), y una etiqueta asociada. Los nodos deben diseñarse de manera que no puedan ser dibujados sobre otros nodos, ni sobre transiciones existentes.
2. La aplicación debe permitir dibujar transiciones entre nodos, las cuales deben una etiqueta (símbolo de transición). Las transiciones deben diseñarse de manera que no puedan intersecarse con nodos, y que eviten intersecarse con otras transiciones.
3. La aplicación debe mostrar en algún lugar de la pantalla la relación de transición del autómata, usando un panel especialmente diseñado para dicho propósito. La relación de transición puede ser mostrada usando la lista de funciones de transición, o una matriz de transiciones.
4. La aplicación debe permitir la verificación de restricciones de integridad del autómata dibujado. Por ejemplo, que sólo exista un nodo de inicio, que exista al menos un estado final, que todas las transiciones tengan etiquetas, etc.).
5. La aplicación debe permitir ingresar una palabra, y determinar si dicha palabra es válida o no; es decir, si dicha palabra corresponde al lenguaje representado por el autómata.

Requisitos Deseables
--------------------
Los requisitos deseables son opcionales, y por lo tanto su cumplimiento no está exigido. Sin embargo, aportan nota adicional sobre la obtenida por cumplir los requisitos indispensables, siempre y cuando éstos hayan sido cumplidos en su totalidad.
Cada requisito opcional añade 1 punto a la nota final del proyecto (máximo 3 puntos, para un total de 7). Si se logra cumplir con más requisitos deseables de los requeridos para obtener una nota final de 7, los puntos en exceso se guardarán para la nota de la Parte II del proyecto. Nótese que los requisitos deseables cumplidos durante el desarrollo de la Parte I no serán contabilizados para la nota adicional de la Parte II.
1. Permitir realizar las siguientes funciones de manera interactiva (y actualizar pantalla):
a. Borrar nodos y sus transiciones asociadas.
b. Borrar transiciones.
c. Modificar nodos (ej., cambiar tipo, nombre).
d. Modificar transiciones (cambiar etiqueta)
e. Actualizar la relación de transición automáticamente después de cada cambio.
2. Permitir la reacomodación de nodos en tiempo de ejecución (ej., drag & drop), tal que los nodos no puedan colocarse sobre otros, y que las transiciones eviten intersecarse entre ellas y con otros nodos.
3. Verificar que exista un camino viable entre el nodo inicial y el final de un autómata dado, y determinar la palabra más corta que puede obtenerse entre dichos nodos.
4. Permitir imprimir, exportar (guardar), e importar (cargar) de manera independiente el panel de visualización y el panel de la relación de transición (por separado).
5. Permitir hacer zoom al panel de dibujo (acercamiento y alejamiento)

Observaciones
-------------
- Reprobar el proyecto de la unidad II o de la unidad III implica reprobar la unidad correspondiente. Reprobar ambos proyectos implica reprobar el curso.
- Dado que los proyectos de las unidades II y III son exigibles, se dará un tiempo adicional para completar los proyectos de dichas unidades (hasta una semana adicional cada uno, o dos semanas sólo a uno), limitando la nota máxima obtenible a 4.0 (independiente de los requisitos deseables que se hayan cumplido).
