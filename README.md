#Challenge Cupon

## Acerca del proyecto

Mercado Libre está implementando un nuevo beneficio para los usuarios que más usan la plataforma con un cupón de cierto monto gratis que les permitirá comprar tantos items marcados como favoritos que no excedan el monto total.


## Despliegue Local

Para que funcione el servicio debe estar corriendo redis [local](https://redis.io/topics/quickstart) o en un contenedor [Docker](https://hub.docker.com/_/redis)

```bash
./gradlew bootRun
```

Eso levantará un servicio HTTP por el puerto 8080, una vez esté arriba, puede ejecutar el siguiente cURL:

```bash
curl --location --request POST 'http://127.0.0.1:8080/cupon' \
--header 'Content-Type: application/json' \
--data-raw '{
    "item_ids": [
        "MLA844702264",
        "MLA844702265",
        "MLA844702266",
        "MLA844702267",
        "MLA844702268",
        "MLA844702268",
        "MLA844702264",
        "MLA844702265",
        "MLA844702266",
        "MLA844702267",
        "MLA844702268",
        "MLA844702268",
        "MLA844702264",
        "MLA844702265",
        "MLA844702266",
        "MLA844702267",
        "MLA844702268",
        "MLA844702268",
        "MLA844702264"
    ],
    "amount": 16748
}'
```

## Correr Tests

Para correr los test se debe ejecutar el siguiente comando:

```bash
./gradlew jacocoTestReport
```

El reporte se puede ver en:
> *build/coverage-report/test/html/index.htm*

## Despliegue a Produccion

Este servicio está desplegado en GCP-AppEngine.

Para desplegar una nueva versión a producción necesitas tener configurado el SDK de GCP.
Aquí hay una guia de como configurarlo dependiendo tu sistema operativo


> <https://cloud.google.com/sdk/docs/quickstart>

Una vez haya configurado el proyecto, puede ejecutar el siguiente comando para desplegar una nueva versión;

```bash
gcloud app deploy
```

esto desplegará una nueva versión en esta URL :

> <https://challenge-334623.ue.r.appspot.com/cupon>

Hay una colección de postman con los posibles request.
