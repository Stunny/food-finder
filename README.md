# food-finder
Final project for the Android Subject on LaSalle URL

## Problemas Observados

•	GPS:
Necesita de los permisos adecuados y hace la comprobación de que se piden, pero no hace comprobación de que el dispositivo tiene el sistema GPS activo. De igual forma, una vez activados los permisos, tras el dialog inicial, al desactivar de nuevo el gps y reiniciar la app, no obteníamos el pop up avisando de que el dispositivo de geolocalización no se hallaba activo. Implementada dicha comprobación.
Por último, mencionar que el dialog de los permisos es uno prediseñado por Google, el cual por algún motivo inicialmente no muestra la opción de “No volver a mostrar” y también el hecho que si seleccionabas susodicha acción, la acción de “Aceptar” ya no se hallaba disponible, tan solo la de cancelar.

•	Gradle.build:
Al no incluir los ficheros de gradle.build, algunos miembros del grupo tenían problemas de compatibilidad para el correcto funcionamiento del proyecto. Esto se solucionó adjuntando los ficheros de gradle al repositorio de github.

•	Action bar:
Inicialmente queríamos implementar nuestra propia action bar para cada actividad, resultando luego en una problemática mayor de la prevista. Esto nos llevó a volver a crear el archivo style para poder solucionarlo.

•	Radio buttons:
Los radio buttons del layout del perfil del usuario eran editables aun cuando no se había seleccionado la opción de editar perfil, y aun así, al hacer algún cambio, salir y volver a entrar a la actividad, no había ningún radio button seleccionado o devolvía que el índice del radio button seleccionado era un numero descabellado tal que el 2 millones. Esto se solucionó cambiando de radio group a rado button y trabajar a nivel individual.

Por último, mencionar que varios problemas menores como la implementación errónea de algún adapter, o el trato de la BBDD fueron también solucionados mediante la breve investigación usando internet o bien acudiendo a la ayuda del profesorado si era necesario.
