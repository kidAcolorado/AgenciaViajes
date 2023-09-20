/**
 * 
 */
function enviarFormularioPasajero() {
    var id = document.getElementById("pasajeroId").value; // Obtén el valor del campo de entrada
    var url = "/pasajero/mostrar/" + id; // Construye la URL con el valor del ID
    window.location.href = url; // Redirige a la URL actualizada
}
