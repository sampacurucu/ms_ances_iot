Frontend (o Postman) envía una petición HTTP (por ejemplo un POST) con datos de la actividad.

La petición llega al controller:

El controller es quien recibe las peticiones externas.

Extrae los datos que vienen del frontend (por ejemplo en un ActivityDto).

El controller llama al service:

El service contiene la lógica de qué hacer con esa información.

El controller no trabaja directamente con la base de datos, siempre delega al service.

El service usa el mapper:

El mapper convierte el objeto que viene del frontend (ActivityDto) en el objeto que entiende la base de datos (Activity).

El service llama al repository:

El repository se conecta con la base de datos.

Usa JPA para guardar o consultar los datos (por ejemplo, guardar el Activity).

Finalmente, el service devuelve una respuesta al controller, y el controller la manda de vuelta al frontend.