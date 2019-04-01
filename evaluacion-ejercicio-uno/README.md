Aplicación realizada con Spring boot, basa en micro servicios, 
con gradle nos ayuda a administrar las dependencias.

Ide: STS
JavaVersion: 1.8
Gradle: 4.8.1


En consola.

1. The distance of the route A-B-C.-->>:  9 
2. The distance of the route A-D.-->>:  5 
3. The distance of the route A-D-C.-->>:  13 
4. The distance of the route A-E-B-C-D.-->>:  22 
5. The distance of the route A-E-D..-->>:  no such rout 
6. The number of trips starting at C and ending at C with a maximum of 3 stops.-->>:  2 
7. The number of trips starting at A and ending at C with exactly 4 stops.-->>:  3 
8. The length of the shortest route (in terms of distance to travel) from A to C.-->>:  9 
9. The length of the shortest route (in terms of distance to travel) from B to B.-->>:  9 
10. The number of different routes from C to C with a distance of less than 30-->>:  7 


Servicio web.
http://localhost:8282/evaluacionWS/api/getevaluacion


[  
   {  
      "accion":"1. The distance of the route A-B-C.",
      "respuesta":9,
      "error":null
   },
   {  
      "accion":"2. The distance of the route A-D.",
      "respuesta":5,
      "error":null
   },
   {  
      "accion":"3. The distance of the route A-D-C.",
      "respuesta":13,
      "error":null
   },
   {  
      "accion":"4. The distance of the route A-E-B-C-D.",
      "respuesta":22,
      "error":null
   },
   {  
      "accion":"5. The distance of the route A-E-D.",
      "respuesta":null,
      "error":"no such rout"
   },
   {  
      "accion":"6. The number of trips starting at C and ending at C with a maximum of 3 stops.",
      "respuesta":2,
      "error":null
   },
   {  
      "accion":"7. The number of trips starting at A and ending at C with exactly 4 stops.",
      "respuesta":3,
      "error":null
   },
   {  
      "accion":"8. The length of the shortest route (in terms of distance to travel) from A to C..",
      "respuesta":9,
      "error":null
   },
   {  
      "accion":"9. The length of the shortest route (in terms of distance to travel) from B to B.",
      "respuesta":9,
      "error":null
   },
   {  
      "accion":"10. The number of different routes from C to C with a distance of less than 30.",
      "respuesta":7,
      "error":null
   }
]

Se adjunta Jar para ejecutar la aplicación

java -jar evaluacion-ejercicio-uno-1.0.0-SNAPSHOT.jar 