package com.crui.patterns.examples.turnos_medicos;

import java.util.List;

public class DataBase {
    private static volatile DataBase instance;
    private final List<Doctor> doctores;

    private DataBase() {
        this.doctores = List.of(
            CreadorDeDoctores.crearCardiologoGeneral("Dra. Girgenti Ana", "agirgenti@gmail.com"),
            CreadorDeDoctores.crearNeumonologo("Dr. Jorge Gutierrez", "jgutierrez@gmail.com"),
            CreadorDeDoctores.crearAlergista("Dra. Florencia Aranda", "faranda@gmail.com"),
            CreadorDeDoctores.crearClinicoGeneral("Dr. Esteban Quiroga", "equiroga@gmail.com"),
            CreadorDeDoctores.crearTraumatologo("Dr. Mario Gómez", "mgomez@gmail.com")
        );
    }

    public static DataBase getInstance() {
        if (instance == null) {
            synchronized (DataBase.class) {
                if (instance == null) {
                    instance = new DataBase();
                }
            }
        }
        return instance;
    }

    public List<Doctor> getDoctores() {
        return doctores;
    }

    public Doctor getDoctor(String descripcionEspecialidad) {
        if (descripcionEspecialidad == null || descripcionEspecialidad.isEmpty()) {
            return null;
        }
        for (Doctor doctor : doctores) {
            if (doctor.getEspecialidad().contiene(descripcionEspecialidad)) {
                return doctor;
            }
        }
        return null;
    }
}