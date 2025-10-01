/\*\*

- Turnos Medicos
-
- <p>Contestar a continuación las siguientes preguntas:
-
- <p>- Qué patrón de diseño podés identificar en el código dado?
-
- <p>- Qué patrones de diseño se podrían agregar para mejorar el código?
-
- <p>Implementar uno o más de estos patrones adicionales para mejorar el código.
  */
    
    
  ########### RESPUESTA PRIMER PUNTO ############  
       Los patrones de diseño que se ESTAN IMPLEMENTANDO son:

  
 Ptron de diseño SINGLETON: este esta definido de la linea 181 cominza la  clase y en la linea 185 comienza la implementacion del patron de diseño  SINGLETON:
   Constructor privado (private Database())  O sea que desde afuera no se pueda hacer new Database().
 Con el atributo estatico lo que hace es que (Database instance) guarde la  unica instancia de la clase, y de esta forma es la unica que puede  existir.  por ultimo, si Si todavía no existe la instancia (instance == null), la crea. y si existe solo vuelve a traer la que se encuentra creada

   El otro patron de diseño que puedo ver en el archivo es el FACTORY este  patron comienza en la linea 216.  Utiliza a (Doctor) como tipo de dato en la clase "CreadorDeDoctores" con  todos lo metodos estaticos dentro de la clase para crear CARDIOLOGO, NEUMONOLOGO, ALERGISTA, ETC. Al ser metodos estaticos no hace falta instanciarlos utilizando new

  ########### RESPUESTA SEGUNDO PUNTO ############

        Los patrones de diseño que se ESTAN SE PUDEN IMPLEMENTAR son:

   Primero, antes de utilizar los patrones de diseños probables, voy a refactorizar el codigo para que quede mas limpiio y ordenado. 
   STRATEGY: Implementando este patron en la logica de calcular precio y \*\*** metodos de pago.
   DECORATOR; OBSERVER en los turnos
   ########### RESPUESTA SEGUNDO PUNTO ############
   
    Por ultimo agregue STRATEGY en el pago
