from typing_extensions import override
from Paciente import Paciente
from Doctor import Doctor
from Turno import Turno
from Strategy import StrategyPrecio as ICalcularPrecio

class Turno(ICalcularPrecio):
    def __init__(
        self, paciente: Paciente, doctor: Doctor, fechaYHora: str, precio: float
    ):
        self.paciente = paciente
        self.doctor = doctor
        self.fechaYHora = fechaYHora
        self.precio = precio

    def setFechaYHora(self, fechaYHora: str) -> None:
        self.fechaYHora = fechaYHora
        self.avisarCambioDeFechayHora(self)

    def avisarCambioDeFechayHora(self, turno: Turno) -> None:
        self.doctor.avisarCambioDeFechayHora(turno)
        self.paciente.avisarCambioDeFechayHora(turno)

    def __str__(self):
        return (
            "Turno para "
            + self.paciente
            + " con "
            + self.doctor
            + " el "
            + self.fechaYHora
            + " - $"
            + self.precio
        )

    @override
    def getTotalPrecio(self) -> float:
        precioBase: float
        if self.doctor.especialidad.contiene("Cardiología"):
            precioBase = 8000
        elif self.doctor.especialidad.contiene("Neumonología"):
            precioBase = 7000
        elif self.doctor.especialidad.contiene("Kinesiología"):
            precioBase = 7000
        else:
            precioBase = 5000

        descuento: float
        match self.paciente.obraSocial:
            case "OSDE":
                descuento = (
                    1.0  # 100% de descuento en cardiología
                    if self.doctor.especialidad.contiene("Cardiología")
                    else 0.2  # 20% de descuento
                )
            case "IOMA":
                descuento = (
                    1.0  # 100% de descuento en kinesiología
                    if self.doctor.especialidad.contiene("Kinesología")
                    else 0.15  # 15% de descuento
                )
            case "PAMI":
                descuento = 1.0  # 100% de descuento
            case _:
                descuento = 0.0  # 0% de descuento

        # Aplico el descuento
        precio: float = precioBase - precioBase * descuento
        return precio
