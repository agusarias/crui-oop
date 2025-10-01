from Paciente import Paciente
from Doctor import Doctor
from Database import Database
from Turno import Turno

class TurnosMedicos:
    @staticmethod
    def main():
        print()
        print("Turnos Medicos")
        print("=============")
        print()

        database: Database = Database.getInstance()

        paciente: Paciente = Paciente("Ignacio Segovia", "OSDE", "isegovia@gmail.com")
        especialidad: str = "Cardiología"
        doctor: Doctor = database.getDoctor(especialidad)

        if doctor is None:
            print("No se encontró el doctor para la especialidad: " + especialidad)
            exit

        # Precio base en base a la especialidad
        

        # Descuento en base a la obra social y la especialidad
        precio = Turno.getTotalPrecio()

        # Nuevo turno
        turno: Turno = Turno(paciente, doctor, "2025-01-01 10:00", precio)
        print(turno)

        # Cambio de turno
        turno.setFechaYHora("2025-01-01 11:00")
        print()

if __name__ == "__main__":
    TurnosMedicos.main()