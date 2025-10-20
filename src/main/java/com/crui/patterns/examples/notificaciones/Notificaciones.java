package com.crui.patterns.examples.notificaciones;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Sistema de Envío de Notificaciones
 *
 * <p>Contestar a continuación las siguientes preguntas:
 *
 * <p>- Qué patrón de diseño podés identificar en el código dado?
 *
 * <p>- Qué patrones de diseño se podrían agregar para mejorar el código?
 *
 * <p>- Implementar uno o más de estos patrones adicionales para mejorar el código.
 */
public class Notificaciones {

  public static void main(String[] args) {
    ConfigurationManager config = ConfigurationManager.getInstance();
    System.out.println("Sistema configurado por: " + config.getAdminName());
    System.out.println("------------------------------------------");

    // 2. Creamos un mensaje y le agregamos funcionalidades adicionales.
    Mensaje miMensaje = new MensajeSimple("Este es el cuerpo del mensaje principal.");
    miMensaje = new MensajeConTimestamp(miMensaje);
    miMensaje = new MensajeUrgente(miMensaje);
    miMensaje = new MensajeEnBase64(miMensaje); // Codifica el contenido final

    String contenidoFinal = miMensaje.getContenido();
    System.out.println("Mensaje formateado listo para enviar:");
    System.out.println(contenidoFinal);
    System.out.println("------------------------------------------");

    // 3. Enviamos el mensaje por diferentes canales.
    EnviadorDeMensajes enviador = new EnviadorDeMensajes();
    enviador.enviar(miMensaje, "EMAIL");
    enviador.enviar(miMensaje, "SMS");
    enviador.enviar(miMensaje, "PUSH");
  }

  /** Gestiona la configuración global de la aplicación. */
  public static class ConfigurationManager {
    private static ConfigurationManager instance;
    private String adminName = "admin@sistema.com";

    private ConfigurationManager() {}

    public static ConfigurationManager getInstance() {
      if (instance == null) {
        instance = new ConfigurationManager();
      }
      return instance;
    }

    public String getAdminName() {
      return adminName;
    }
  }

  public interface Mensaje {
    String getContenido();
  }

  public static class MensajeSimple implements Mensaje {
    private String texto;

    public MensajeSimple(String texto) {
      this.texto = texto;
    }

    @Override
    public String getContenido() {
      return texto;
    }
  }

  public abstract static class MensajeMejorado implements Mensaje {
    protected Mensaje mensajeEnvuelto;

    public MensajeMejorado(Mensaje mensaje) {
      this.mensajeEnvuelto = mensaje;
    }

    @Override
    public String getContenido() {
      return mensajeEnvuelto.getContenido();
    }
  }

  public static class MensajeUrgente extends MensajeMejorado {
    public MensajeUrgente(Mensaje mensaje) {
      super(mensaje);
    }

    @Override
    public String getContenido() {
      return "[URGENTE] " + super.getContenido();
    }
  }

  public static class MensajeConTimestamp extends MensajeMejorado {
    public MensajeConTimestamp(Mensaje mensaje) {
      super(mensaje);
    }

    @Override
    public String getContenido() {
      String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
      return "(" + timestamp + ") " + super.getContenido();
    }
  }

  public static class MensajeEnBase64 extends MensajeMejorado {
    public MensajeEnBase64(Mensaje mensaje) {
      super(mensaje);
    }

    @Override
    public String getContenido() {
      String contenidoOriginal = super.getContenido();
      return Base64.getEncoder().encodeToString(contenidoOriginal.getBytes());
    }
  }

  /**
   * Esta clase es responsable de enviar mensajes. Su lógica de selección de canal de envío es
   * rígida y difícil de extender.
   */
  public static class EnviadorDeMensajes {

    public void enviar(Mensaje mensaje, String canal) {
      String contenido = mensaje.getContenido();

      switch (canal.toUpperCase()) {
        case "EMAIL":
          System.out.println("Enviando por EMAIL...");
          System.out.println("Asunto: Nueva Notificación");
          System.out.println(
              "Cuerpo (decodificado): " + new String(Base64.getDecoder().decode(contenido)));
          System.out.println("Email enviado.\n");
          break;
        case "SMS":
          System.out.println("Enviando por SMS...");
          System.out.println(
              "Mensaje: "
                  + contenido.substring(
                      0, Math.min(contenido.length(), 160))); // Limita a 160 caracteres
          System.out.println("SMS enviado.\n");
          break;
        case "PUSH":
          System.out.println("Enviando Notificación PUSH...");
          System.out.println("Payload: { \"content\": \"" + contenido + "\" }");
          System.out.println("Push enviada.\n");
          break;
        default:
          System.err.println("Error: El canal '" + canal + "' no es soportado.\n");
          break;
      }
    }
  }
}
