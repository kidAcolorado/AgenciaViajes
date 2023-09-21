/**
 * 
 */

	document
				.getElementById("tipoBusqueda")
				.addEventListener(
						"change",
						function() {
							var campoBusqueda = document
									.getElementById("campoBusqueda");
							var tipoBusqueda = this.value;

							if (tipoBusqueda === "reserva") {
								campoBusqueda.innerHTML = '<label for="idBusqueda" class="form-label">Buscar por ID de reserva:</label>'
										+ '<input type="number" class="form-control" id="idBusqueda" name="idBusqueda" required>';
							} else if (tipoBusqueda === "vuelo") {
								campoBusqueda.innerHTML = '<label for="idBusqueda" class="form-label">Buscar por ID de vuelo:</label>'
										+ '<input type="number" class="form-control" id="idBusqueda" name="idBusqueda" required>';
							} else if (tipoBusqueda === "pasajero") {
								campoBusqueda.innerHTML = '<label for="idBusqueda" class="form-label">Buscar por ID de pasajero:</label>'
										+ '<input type="number" class="form-control" id="idBusqueda" name="idBusqueda" required>';
							}
						});

		function enviarFormularioReserva() {
			var tipoBusqueda = document.getElementById("tipoBusqueda").value;
			var id = document.getElementById("idBusqueda").value;

			var url;
			if (tipoBusqueda === "reserva") {
				url = "/reserva/mostrar/idreserva/" + id;
			} else if (tipoBusqueda === "vuelo") {
				url = "/reserva/mostrar/idvuelo/" + id;
			} else if (tipoBusqueda === "pasajero") {
				url = "/reserva/mostrar/idpasajero/" + id;
			}

			window.location.href = url; // Redirige a la URL actualizada
		}