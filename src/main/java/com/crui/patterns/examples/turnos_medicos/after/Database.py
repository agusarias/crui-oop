from CreadorDeDoctores import CreadorDeDoctores
from Doctor import Doctor
from . import Database  # Posible error

class Database:
    _instance = None

    def __init__(self):
        self.doctores = (
            CreadorDeDoctores.crearCardiologoGeneral(
                "Dra. Girgenti Ana", "agirgenti@gmail.com"
            ),
            CreadorDeDoctores.crearNeumonologo(
                "Dr. Jorge Gutierrez", "jgutierrez@gmail.com"
            ),
            CreadorDeDoctores.crearAlergista(
                "Dra. Florencia Aranda", "faranda@gmail.com"
            ),
            CreadorDeDoctores.crearClinicoGeneral(
                "Dr. Esteban Quiroga", "equiroga@gmail.com"
            ),
            CreadorDeDoctores.crearTraumatologo("Dr. Mario Gómez", "mgomez@gmail.com"),
        )

    @staticmethod
    def getInstance() -> Database:
        if Database._instance is None:
            Database._instance = Database()
        return Database._instance

    def getDoctores(self) -> list[Doctor]:
        return self.doctores

    def getDoctor(self, descripcionEspecialidad: str) -> Doctor | None:
        for doctor in self.doctores:
            if doctor.especialidad.contiene(descripcionEspecialidad):
                return doctor
        return None