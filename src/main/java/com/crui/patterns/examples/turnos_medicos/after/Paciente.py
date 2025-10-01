from Turno import Turno

class Paciente:
    def __init__(self, nombre: str, obraSocial: str, email: str):
        self.nombre = nombre
        self.obraSocial = obraSocial
        self.email = email

    def avisarCambioDeFechayHora(self, turno: Turno) -> None:
        print(
            "Mail para "
            + self.email
            + ": El turno con "
            + turno.doctor
            + " se ha cambiado a "
            + turno.fechaYHora
        )

    def __str__(self) -> str:
        return self.nombre + " (" + self.obraSocial + ")"