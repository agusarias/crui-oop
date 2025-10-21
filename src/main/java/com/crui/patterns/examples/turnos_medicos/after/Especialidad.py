class Especialidad:
    def __init__(self, descripcion: str):
        self.descripcion = descripcion

    def contiene(self, descripcion: str) -> bool:
        return descripcion in self.descripcion

    def __str__(self) -> str:
        return self.descripcion
