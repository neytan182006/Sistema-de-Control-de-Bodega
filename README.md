# Sistema de Control de Bodega

Control de existencias de bodega con alerta de reabastecimiento (Java + MySQL) para el curso de **Bases de Datos**.

## Funcionalidades

- Listar artículos, marcando los que están por debajo de su stock mínimo.
- Registrar entrada/salida de un artículo (ajusta el stock en una transacción; una salida sin stock suficiente se rechaza).
- Ver solo los artículos que necesitan reabastecerse.

## Estructura

```
src/
├── dao/ConexionBD.java, ArticuloDAO.java, MovimientoBodegaDAO.java
└── app/Main.java
```

## Base de datos

[`database/bodega.sql`](database/bodega.sql): `ARTICULOS`, `MOVIMIENTOS_BODEGA`.

## Cómo ejecutarlo

```bash
mysql -u root -p < database/bodega.sql
javac -d bin -cp "lib/mysql-connector-j-9.5.0.jar" src/dao/*.java src/app/*.java
java -cp "bin;lib/mysql-connector-j-9.5.0.jar" app.Main
```

> Compilado y verificado con `javac` sin errores; conexión real a MySQL no probada en este entorno (sin servidor corriendo, como acordamos).

## Capturas

_Pendiente: agregar capturas en `capturas/`._

## Licencia

MIT — ver [LICENSE](LICENSE).
