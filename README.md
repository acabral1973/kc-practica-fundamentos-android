<p align="center"><img width=30% src="https://raw.githubusercontent.com/acabral1973/kc-practica-fundamentos-android/master/app/src/main/res/mipmap-xxxhdpi/foodr_icon.png"></p>

# Foodr - Práctica Fundamentos Android (Autor: Alejandro Cabral)

## Origen de datos
Para simular el servicio web desde el que se descargan los datos se ha utilizado Mocky. El link de descarga del json con los platos es el siguiente: http://www.mocky.io/v2/5a042df2310000cb2d916c5a  
Los resultados corresponden a un *json* de *platos*, tal que cada plato tiene los siguientes campos: *name, description, price, image, allergens*  
Para los **alergenos** solo se informará de alergias a marisco, lactosa, gluten, frutos secos y huevos, por lo que se utilizan los siguientes posibles valores: *egg, nuts, lactose, gluten, shellfish  
En la pantalla de detalle de los diferentes platos, debajo del nombre del plato, se podrán apreciar unos iconos, que estarán encendidos (verde), si el plato contiene el alergeno, o apagados (gris), en caso contrario.

## Pantalla principal
Al iniciarse la aplicación se inicia la descarga, que está señalizada mediante una barra de progreso. Al acabar de descargar la información del restaurante, se carga un Menú de Navegación inferior (BottomNavigationView) que permite acceder a la funcionalidad de pedidos (lista de mesas) o a la carta del restaurante (lista de platos). La idea es que el camarero pueda acceder rápidamente a la información de un plato, sin tener que comenzar un pedido.  

## Menú de pedidos
Se muestra como una lista de mesas, cada mesa se muestra con su número de mesa, su estado (libre u ocupada) y el importe total de la orden que se ha pedido hasta el momento.
Al pulsar sobre una mesa se accede al gestor de la orden (comanda) para esa mesa.

## Gestor de órdenes
Al acceder a la orden de una mesa, la misma se muestra como una lista de los platos pedidos hasta el momento. Si no se han pedido platos aún, aparece un texto que así lo indica.  
En la parte inferior aparecen tres botones:
- **Agregar plato:** este botón lanza la carta del restaurante, que incluye todos los platos descargados desde el servidor.
- **Pedir:** Una vez que se han agregado los platos solicitados y se desea tramitar el pedido a cocina, se debe pulsar este botón, que confirmará la orden asociada a la mesa y volverá al menú de pedidos (listado de mesas). En este momento se actualiza la información de la mesa, marcándola como ocupada y actualizando el importe total de lo pedido hasta el momento. Si estando en el listado de platos de una mesa, se pulsa el botón de ir hacia atrás, los platos que se agregaran sin haber pulsado después el botón de **Pedir** se descartan.
- **Cobrar:** Este botón lanza una nueva pantalla (actividad) que muestra, además del listado de platos, la suma total y un botón para hacer efectivo el cobro. Al pulsar este último  botón, la aplicación vuelve al **Menú de Pedidos**, actualizando la información de la mesa que se ha cobrado (vuelve a aparecer como Libre y el importe se muestra como 0€)  
<br>
Si se pulsa sobre uno de los platos de la orden, se muestran las notas con las peticiones especiales del cliente, asociadas a ese plato.

## Agregar plato
La pantalla que nos permite agregar platos se ha desarrollado como un *Fragment* para utilizarlo desde la opción de **Agregar platos** del **Gestor de pedidos**, y desde la opción **Carta** de la **Pantalla Principal**.  
La diferencia es que si se están agregando platos aparece el botón de agregar (+) y el espacio para agregar notas del cliente, mientras que si solo se está consultando la carta, estos dos elementos permanecen ocultos he inactivos.

## Licencia
Este repositorio es de uso libre para todo el que a él acceda. Mucha suerte y disculpe las burradas en el código.
