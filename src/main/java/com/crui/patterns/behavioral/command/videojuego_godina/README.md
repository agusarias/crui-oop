# Patrón Command - Ejemplo de Videojuego

Este directorio contiene un ejemplo práctico del patrón Command aplicado a un sistema de controles de videojuego, mostrando la evolución desde una implementación tradicional hasta una implementación que utiliza el patrón.

## 📁 Estructura del Proyecto

```
videojuego-godina/
├── before/
│   └── Player.java     # Implementación sin patrón Command
├── after/
│   └── Player.java     # Implementación con patrón Command
└── README.md
```

## 🎮 Contexto del Ejemplo

El ejemplo simula un sistema de controles para un videojuego donde un jugador puede realizar 4 acciones básicas:

1. **Moverse a la izquierda** (tecla A)
2. **Moverse a la derecha** (tecla D)
3. **Saltar** (tecla W)
4. **Atacar** (tecla SPACE)

## 📝 Versión SIN Patrón Command (`before/Player.java`)

### Características de la implementación:

```java
public void handleInput(String key) {
    switch (key) {
        case "A":
            player.moveLeft();
            break;
        case "D":
            player.moveRight();
            break;
        case "W":
            player.jump();
            break;
        case "SPACE":
            player.attack();
            break;
        default:
            System.out.println("Tecla no asignada");
            break;
    }
}
```

### Problemas identificados:

❌ **Acoplamiento fuerte**: El `InputHandler` está directamente acoplado a los métodos específicos del `Player`

❌ **Dificultad para personalizar**: No es fácil cambiar las asignaciones de teclas sin modificar el código

❌ **Switch statement rígido**: Cada nueva acción requiere modificar el switch

❌ **Sin flexibilidad**: No se pueden asignar dinámicamente diferentes acciones a las teclas

❌ **Viola el Principio Abierto/Cerrado**: Para agregar nuevas funcionalidades hay que modificar código existente

## ✅ Versión CON Patrón Command (`after/Player.java`)

### Estructura del patrón implementado:

#### 1. **Interfaz Command**

```java
interface Command {
    void execute();
}
```

#### 2. **Comandos Concretos**

```java
static class MoveLeftCommand implements Command {
    private Player player;

    public MoveLeftCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.moveLeft();
    }
}
```

#### 3. **Invoker mejorado**

```java
static class InputHandler {
    private Map<String, Command> commandMap = new HashMap<>();

    public void setCommand(String key, Command command) {
        commandMap.put(key, command);
    }

    public void handleInput(String key) {
        Command command = commandMap.get(key);
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Tecla no asignada");
        }
    }
}
```

## 🚀 Mejoras que Aporta el Patrón Command

### 1. **Desacoplamiento**

- El `InputHandler` ya no conoce los detalles específicos de las acciones
- Solo sabe que debe ejecutar un comando, no cómo se ejecuta

### 2. **Flexibilidad en las Asignaciones**

```java
// Fácil reasignación de teclas
inputHandler.setCommand("A", new AttackCommand(player));  // A ahora ataca
inputHandler.setCommand("SPACE", new MoveLeftCommand(player)); // SPACE mueve
```

### 3. **Extensibilidad**

- Agregar nuevas acciones es tan simple como crear una nueva clase Command
- No se necesita modificar el código existente del `InputHandler`

## 📊 Comparación Resumida

| Aspecto            | Sin Patrón | Con Patrón Command |
| ------------------ | ---------- | ------------------ |
| **Acoplamiento**   | Alto       | Bajo               |
| **Flexibilidad**   | Baja       | Alta               |
| **Extensibilidad** | Difícil    | Fácil              |
| **Mantenimiento**  | Complejo   | Simple             |
| **Reutilización**  | Limitada   | Alta               |
| **Testabilidad**   | Difícil    | Fácil              |

## 🎯 Cuándo Usar el Patrón Command

✅ **Usar cuando:**

- Necesitas desacoplar el objeto que invoca una operación del que la realiza
- Quieres parametrizar objetos con diferentes solicitudes
- Necesitas implementar undo/redo
- Quieres implementar logging de operaciones
- Necesitas encolar operaciones o ejecutarlas en diferentes momentos

❌ **No usar cuando:**

- Las operaciones son muy simples y no cambiarán
- No necesitas las funcionalidades adicionales que proporciona
- El overhead de crear múltiples clases no se justifica

## 🏁 Conclusión

El patrón Command transforma este ejemplo de un sistema rígido y fuertemente acoplado a uno flexible, extensible y mantenible. La inversión inicial en complejidad se ve compensada por las múltiples ventajas que ofrece, especialmente cuando el sistema necesita crecer y evolucionar.
