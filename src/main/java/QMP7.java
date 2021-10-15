public class QMP7 {
  //solucion hecha con el resto del grupo 21

  //1
  get https://qmp_7/prendas. Codigo resp:200
  Respuesta:
    [
      {
        "id": 1,
        "tipo": "inferior",
        "color" : "rojo"
        "talle": 35
      },
      {
        "id": 2,
        "tipo": "superior",
        "color" : "azul"
        "talle": 36
      },
      {
        "id": 3,
        "tipo": "calzado",
        "color" : "verde"
        "talle": 37
      },
    ]

  //2
  post 'https://qmp_7/prendas/' --data '{"id": 4,"tipo": "calzado","color" : "rosa","talle": 27}' -H 'Content-Type: application/json' Codigo resp:201


  //3
  get https://qmp_7/prendas/3 Codigo resp:200/resp:404

  {
    "id": 3,
      "tipo": "calzado",
      "color" : "verde"
    "talle": 37
  }

  //4
  delete https://qmp_7/prendas/nro_prenda Codigo resp:202

  //5
  get https://qmp_7/eventos Codigo resp:200

  Respuesta:
    [
      {
        "id": 1,
        "descripción": "comunión"
      }
      {
        "id": 2,
        "descripción": "cumpleaños"
      }
      {
        "id": 3,
        "descripción": "navidad"
      }
    ]
  get https://qmp_7/sugerencias?evento=2 Codigo resp:200
  Respuesta:
    [
      {
        "id": 2,
        "descripción": "cumpleaños"
      }
    ]

}
