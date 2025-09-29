# Checkout System - Patrones de Diseño

Este proyecto simula un sistema de checkout con carrito de compras, cálculo de totales, extras opcionales y distintos medios de pago. Se utilizan patrones de diseño para mejorar la flexibilidad y mantenibilidad.

## Patrones usados en el código original

- **Observer:** La clase `Orden` notifica a listeners (`EmailListener`, `AnalyticsListener`) cuando se realiza un pago. Permite agregar nuevas reacciones sin modificar `Orden`.
- **Strategy:** La interfaz `MedioDePago` define cómo pagar, y las clases concretas (`PagoEfectivo`, `PagoTarjeta`) implementan diferentes estrategias. `Orden` delega el pago a la estrategia configurada.

## Patrón agregado: Factory

- **Factory (`MedioDePagoFactory`):** Encapsula la creación de objetos `MedioDePago` según el tipo de pago (`"cash"`, `"card"`).  
- **Razón:** Antes, la elección del medio de pago se hacía con un `if` en el `main`, violando el principio abierto/cerrado. Ahora, agregar nuevos medios de pago solo requiere modificar la factory, sin tocar la lógica de `Orden`.

