/**
 * 
 */
 function enviarFormularioVuelo() {
        var id = document.getElementById("vueloId").value; // Obtiene el valor del campo de entrada
        var url = "/vuelo/mostrar/" + id; // Construye la URL con el valor del ID
        window.location.href = url; // Redirige a la URL actualizada
    }