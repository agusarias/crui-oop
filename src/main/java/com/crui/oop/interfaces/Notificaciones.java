package com.crui.oop.interfaces;

public class Notificaciones {

  public interface ICanalDeNotificacionSaludos {
    public void enviarSaludo();
  }

  public interface ICanalDeNotificacionAlertas {
    public void enviarAlerta();
  }

  public interface ICanalDeNotificacionReportes {
    public void enviarReporte();
  }

  public static class NotificacionesPorSMS
      implements ICanalDeNotificacionSaludos,
          ICanalDeNotificacionAlertas,
          ICanalDeNotificacionReportes {
    public void enviarAlerta() {
      System.out.println("Alerta enviada");
    }

    public void enviarReporte() {
      System.out.println("Reporte enviado");
    }

    public void enviarSaludo() {
      System.out.println("Saludo enviado");
    }
  }

  public static class NotificacionesPorLlamada implements ICanalDeNotificacionAlertas {
    public void enviarAlerta() {
      System.out.println("Alerta enviada!!!!!");
    }
  }
}
