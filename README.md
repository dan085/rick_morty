# Rick_Morty
Aplicación que muestra los personajes de Rick y Morty en formato lista, con la opción de ver una descripción general de cada uno.

Detalles importantes:
- Está desarrollada con arquitectura MVVM (Modelo-Vista-Vista-Modelo).
- Cuenta con un scroll infinito de paginación.

Vistas importantes:
- Splash Screen: Es la primera vista que ve el usuario. Muestra el logo de la aplicación y luego envía al usuario a la vista inicial.
- Vista Inicial: Muestra dos opciones para ingresar a la vista principal. La primera es con una cuenta de Google y la segunda sin una cuenta. Al seleccionar la 
                 opción con una cuenta de Google, aparecerá un mensaje con el correo elegido, en el otro caso sólo aparecerá un mensaje que inicia sesión sin 
                 cuenta. Después se enviará al usuario a la vista principal.
- Vista Principal: Muestra a los personajes en formato lista.
- Vista Detalle: Al seleccionar un personaje de la vista principal, se puede ver una descripción general del personaje.


Librerías más importantes utilizadas:
  - Volley, para realizar las consultas a la API.
  - Glide, para cargar las imágenes.
  - Firebase Authentication, para iniciar sesión con una cuenta de Google.
  - Firebase Firestone, para guardar información de un usuario en la base de datos al momento de iniciar sesión (El código esta oculto).
  - Firebase Cloud Messaging, se integró el sistema para recibir notificaciones push y su icono de notificación.
