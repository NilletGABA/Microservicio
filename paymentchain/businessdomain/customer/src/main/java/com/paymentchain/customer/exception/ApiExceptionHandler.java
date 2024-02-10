/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.exception;
import com.paymentchain.customer.common.StandarizedApiExceptionResponse;
import java.io.IOException;
import java.net.UnknownHostException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 *
 * @author Nillet Gamboa
 * La comunicación http estándar tiene cinco niveles de códigos de respuesta.
  * Nivel 100 (Informativo): el servidor reconoce una solicitud, significa que la solicitud fue recibida y comprendida, es una respuesta transitoria, alerta al cliente en espera de respuesta.
  * Nivel 200 (éxito): el servidor completó la solicitud como se esperaba
  * Nivel 300 (Redirección): el cliente debe realizar más acciones para completar la solicitud
  * Nivel 400 (error del cliente): el cliente envió una solicitud no válida
  * Nivel 500 (error del servidor): el servidor no pudo cumplir con una solicitud válida debido a un error con el servidor
  *
  * El objetivo de la excepción del controlador es proporcionar al cliente el código apropiado y
  * información adicional comprensible para ayudar a solucionar el problema.
  * La parte del cuerpo del mensaje debe estar presente como interfaz de usuario, incluso si
  * el cliente envía un encabezado de idioma aceptado (inglés o francés, es decir), debemos traducir la parte del título
  * al idioma del cliente si apoyamos la internacionalización, el detalle está destinado al desarrollador
  * de clientes, por lo que no es necesaria la traducción. Si es necesario informar más de un error, podemos
  * responder una lista de errores.
 */
@RestControllerAdvice//Indicate that this class assit a controller class and can have a body in response
public class ApiExceptionHandler {
    
    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<StandarizedApiExceptionResponse> handleUnknownHostException(UnknownHostException ex) {
        StandarizedApiExceptionResponse response = new StandarizedApiExceptionResponse("Error de conexion","erorr-1024",ex.getMessage());
        return new ResponseEntity(response, HttpStatus.PARTIAL_CONTENT);
    }
    
     @ExceptionHandler(BussinesRuleException.class)
    public ResponseEntity<StandarizedApiExceptionResponse> handleBussinesRuleException(BussinesRuleException ex) {
        StandarizedApiExceptionResponse response = new StandarizedApiExceptionResponse("Error de validacion",ex.getCode(),ex.getMessage());
        return new ResponseEntity(response, HttpStatus.PARTIAL_CONTENT);
    }
}
