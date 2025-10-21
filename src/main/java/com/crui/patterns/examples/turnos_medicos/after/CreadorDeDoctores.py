from Doctor import Doctor
from Especialidad import Especialidad

class CreadorDeDoctores:
    def crearCardiologoGeneral(nombre: str, email: str) -> Doctor:
        return Doctor(nombre, Especialidad("Cardiología > General"), email)

    def crearNeumonologo(self, nombre: str, email: str) -> Doctor:
        return Doctor(nombre, Especialidad("Neumonología > General"), email)

    def crearAlergista(self, nombre: str, email: str) -> Doctor:
        return Doctor(nombre, Especialidad("Neumonología > Alergias"), email)

    def crearKinesiologo(self, nombre: str, email: str) -> Doctor:
        return Doctor(nombre, Especialidad("Kinesiología > General"), email)

    def crearTraumatologo(self, nombre: str, email: str) -> Doctor:
        return Doctor(nombre, Especialidad("Kinesiología > Traumatología"), email)

    def crearClinicoGeneral(self, nombre: str, email: str) -> Doctor:
        return Doctor(nombre, Especialidad("Clínica > General"), email)