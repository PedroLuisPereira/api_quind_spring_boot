## Especificaciones técnicas: 

 - Spring boot 3.2.5
 - Java 17
 - MySql - 8.4.0 
 
## Endpoint

### Clientes

Get http://localhost:8080/api/clientes

Get http://localhost:8080/api/clientes/1

Post http://localhost:8080/api/clientes

    {
    "tipoDeIdentificacion": "CC",
    "numeroDeIdentificacion": "123456789",
    "nombres": "Luis David",
    "apellidos": "Guardo Marrugo",
    "correoElectronico": "luis@gmail.com",
    "fechaDeNacimiento": "2000-05-16"
    }

Put http://localhost:8080/api/clientes/1

    {
    "tipoDeIdentificacion": "CC",
    "numeroDeIdentificacion": "123456789",
    "nombres": "Luis David",
    "apellidos": "Guardo Marrugo",
    "correoElectronico": "luis@gmail.com",
    "fechaDeNacimiento": "2000-05-16"
    }

Delete http://localhost:8080/api/clientes/1

### Cuentas
Get http://localhost:8080/api/cuentas

Get http://localhost:8080/api/cuentas/numeroCuenta/5365029203

Post http://localhost:8080/api/cuentas

    {
    "tipoDeCuenta" : "CUENTA_AHORRO",
    "saldo" : "2000000",
    "exentaGMF" : "SI",
    "clienteId" : "64"
    }

### Operaciones
Post http://localhost:8080/api/cuentas/operacion/consignar

    {
    "numeroCuentaDestino" : "5365029203",
    "valor" : "500000"
    }


Post http://localhost:8080/api/cuentas/operacion/transferir

    {
    "numeroCuentaOrigen" : "5365029203",
    "numeroCuentaDestino" : "5312345678",
    "valor" : "40000"
    }


Post http://localhost:8080/api/cuentas/operacion/retirar

    {
    "numeroCuentaOrigen" : "5365029203",
    "valor" : "460000"
    }

Post http://localhost:8080/api/cuentas/operacion/estado

    {
    "numeroDeCuenta": "5365029203",
    "estado": "Cancelada"
    }
