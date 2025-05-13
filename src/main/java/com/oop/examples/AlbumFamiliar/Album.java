package com.oop.examples.AlbumFamiliar;


import com.oop.examples.shared.Picture;
import com.oop.examples.shared.Fotografiable;
import java.util.ArrayList;
import java.util.List;

public class Album {

    private List<Picture> fotos;

    public Album() {
        this.fotos = new ArrayList<>();
    }

    
    public void agregarFoto(Picture foto) {
        fotos.add(foto);
    }

    
    public void agregarFotoDe(Fotografiable elementoFamiliar) {
        Picture foto = elementoFamiliar.takePicture();
        this.agregarFoto(foto);
    }

    
    public void mostrarAlbum() {
        System.out.println("Álbum familiar:");
        for (Picture foto : fotos) {
            System.out.println("📷 Foto - Formato: " + foto.format + ", Tamaño: " + foto.width + "x" + foto.height);
        }
    }
}
