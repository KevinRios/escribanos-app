$(document).ready(() => {
    const form = $('#searchForm');
    const cuit = $('#cuit');
    const result = $('#result');
    const error = $('#error-message');
    const validation = $('#validation-message');
    const photo = $('#photo-container');
    const table = $('#data-table');

    const fields = [
        ['nombre', 'Nombre'],
        ['apellido', 'Apellido'],
        ['matricula', 'Matrícula'],
        ['cargo', 'Cargo'],
        ['estado', 'Estado'],
        ['domicilio', 'Domicilio'],
        ['telefono', 'Teléfono'],
        ['celular', 'Celular'],
        ['horario', 'Horario'],
        ['email', 'Email'],
        ['barrio', 'Barrio'],
        ['localidad', 'Localidad'],
        ['registro', 'Registro'],
        ['sexo', 'Sexo']
    ];

    const showError = (message, isValidation = false) => {
        result.hide();
        if (isValidation) {
            validation.show();
            error.hide();
        } else {
            error.text(message).show();
            validation.hide();
        }
    };

    const mostrarDatosEscribano = (escribano) => {
        // Actualizar foto
        if (escribano.mostrarImagen) {
            photo.css('visibility', 'visible');
        } else {
            photo.css('visibility', 'hidden');
        }

        // Actualizar tabla
        table.empty();
        fields.forEach(([key, label]) => {
            const value = escribano[key];
            if (value) {
                table.append(`<tr><td>${label}</td><td>${value}</td></tr>`);
            }
        });
    };

    form.on('submit', async (e) => {
        e.preventDefault();
        const cuitValue = cuit.val();

        if (!cuitValue || cuitValue.length !== 11) {
            showError('El CUIT debe tener 11 dígitos', true);
            return;
        }

        try {
            const response = await $.ajax({
                url: '/nomina/search',
                method: 'GET',
                data: { cuit: cuitValue }
            });

            const data = typeof response === 'string' ? JSON.parse(response) : response;
            
            if (!data.data) {
                showError(data.mensaje || 'No se encontró un escribano para la CUIT informada');
                return;
            }

            mostrarDatosEscribano(data.data);
            result.show();
            error.hide();
            validation.hide();

        } catch (error) {
            try {
                const errorData = JSON.parse(error.responseText);
                showError(errorData.mensaje);
            } catch {
                showError('Error en la petición al servidor');
            }
        }
    });

    cuit.on('input', function() {
        this.value = this.value.replace(/[^0-9]/g, '').slice(0, 11);
        result.hide();
        if (this.value && this.value.length !== 11) {
            showError('El CUIT debe tener 11 dígitos', true);
        } else {
            validation.hide();
        }
    }).val('');
});
