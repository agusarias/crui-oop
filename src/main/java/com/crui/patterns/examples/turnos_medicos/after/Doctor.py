from Especialidad import Especialidad
from Turno import Turno

class Doctor:
    def __init__(self, nombre: str, especialidad: Especialidad, email: str):
        self.nombre = nombre
        self.especialidad = especialidad
        self.email = email

    def avisarCambioDeFechayHora(self, turno: Turno) -> None:
        print(
            "Mail para "
            + self.email
            + ": El turno para "
            + turno.paciente
            + " se ha cambiado a "
            + turno.fechaYHora
        )

    def __str__(self):
        return self.nombre + " (" + self.especialidad + ")"
