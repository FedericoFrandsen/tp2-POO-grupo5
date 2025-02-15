Administrador de Torneo de Fútbol 5

Este proyecto consiste en el desarrollo de un programa que modela la estructura y los resultados de un torneo de fútbol 5, siguiendo las siguientes especificaciones.

## 📌 Especificaciones

### 🏆 Torneo
Un torneo cuenta con los siguientes atributos:

- Nombre
- Equipos
- Partidos
- Máximo goleador
- Máximo asistidor
- Se requiere el uso de constructores para la recepción de parámetros, la carga de equipos y la inicialización de datos.


### ⚽ Equipos
Cada equipo tiene los siguientes atributos:

- Nombre
- Lista de jugadores
- Goles marcados
- Goles recibidos



### 👥 Jugadores
Cada jugador posee las siguientes características:

- Nombre y apellido
- Edad
- Rol (titular o suplente) # Comentario de fran, yo no pondría esto, porque agrega complejidad al tp(manejar la existencia de suplentes), y si terminamos sin usarlo no tiene sentido tenerlo
- Cantidad de goles anotados (¿durante el torneo o historico?)
- Cantidad de asistencias realizadas (¿durante el torneo o historico? Esto lo dejaria para lo último, si sentimos que falta algo.) 



### 🔧 Funcionalidades
El programa debe incluir los siguientes métodos:

- Mostrar formación del equipo → Devuelve un string formateado con la alineación del equipo (Estaria bueno si podemos hacer como que muestre la cancha y las posiciones de los jugadores).

- Obtener datos del equipo campeón → Devuelve el nombre del equipo ganador junto con su lista de jugadores, goles anotados y goles recibidos.

- Sortear los enfrentamientos → Genera los cruces del torneo en formato de eliminación directa (brackets).

- Registrar goles de un partido → Permite ingresar los goles de un partido, registrando el jugador que anotó y el que asistió, actualizando sus estadísticas.

- Iniciar un partido → No se todavia, pero si tenemos que finalizarlo tenemos que iniciarlo supongo.

- Finalizar un partido → Guarda los datos del encuentro y actualiza la fase del torneo, permitiendo que el ganador avance a la siguiente ronda y eliminando al perdedor.

- Obtener el máximo goleador → Devuelve al jugador con más goles en el torneo.

- Obtener el máximo asistente → Devuelve al jugador con más asistencias en el torneo.

- Agregar un equipo → Permite incorporar un nuevo equipo al torneo.

- Eliminar un equipo → Permite remover un equipo del torneo.

- Agregar un jugador a un equipo → Permite incorporar un nuevo jugador a un equipo existente.

- Eliminar un jugador de un equipo → Permite eliminar un jugador de un equipo. (El objetivo de esto que sería? para reemplazarlo por otro? Tmb dejaría para lo último)



### 📝 Clase de Prueba
Se debe crear una clase con un método main que:

- Permita al usuario cargar los datos necesarios para funcionar.
- Genere un torneo de fútbol.
- Agregue equipos con nombres y listas de jugadores predefinidos.
- Simule partidos y registre los resultados.
