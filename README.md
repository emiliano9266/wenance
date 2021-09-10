# Wenance Challenge

El objetivo de este pequeño ejercicio es demostrar la utilización del api de Streams (java.util.stream)
Construir un Micro Servicio que haciendo uso del siguiente servicio REST
(https://cex.io/api/last_price/BTC/USD) realice una llamada recurrente cada 10 segundos, almacene
los datos y que exponga a través de un API REST las siguientes funcionalidades:

1. Obtener el precio del Bitcoin en cierto Timestamp.
2. Conocer el promedio de valor entre dos Timestamps así como la diferencia porcentual entre
ese valor promedio y el valor máximo almacenado para toda la serie temporal disponible.
3. Incorporar un archivo READ.ME que contenga una descripción de la solución propuesta así
como instrucciones de ejecución en entorno local.

### Indicaciones
- La aplicación deberá estar desarrollada usando Springboot y subida a un repositorio en
github con permisos públicos de acceso y clonado
- La aplicación deberá ser ejecutada en entorno local sin necesidad de dockerización ni de
otro software más que java 1.8
- El uso de frameworks accesorios queda a la elección del candidato
- La persistencia de información se realizará en una estructura de datos en memoria lo más
optimizada posible.
Puntos extras si
- Se incorpora un conjunto de test unitarios que demuestran la corrección de la solución
- Se usa WebClient

## Diagrama de solución 

![plot](https://github.com/emiliano9266/wenance/blob/master/Screenshot%20from%202021-09-10%2016-01-03.png)

## Comandos

Run application

``mvn spring-boot:run``

Run test

``mvn test``

## Documentacion

http://localhost:8080/docs-ui.html

## Coleccion de request para postman

https://www.getpostman.com/collections/ae725af431c6c063bb3a
