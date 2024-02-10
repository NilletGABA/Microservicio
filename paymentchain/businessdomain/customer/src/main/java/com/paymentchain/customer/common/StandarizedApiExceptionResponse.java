package com.paymentchain.customer.common;
import io.swagger.annotations.ApiModelProperty;
/**
 *
 * @author Nillet Gamboa
 * El esfuerzo por estandarizar los reportes de errores de API rest cuenta con el apoyo del ITEF
  (Internet Engineering Task Force, organización de estándares abiertos que desarrolla y promueve estándares voluntarios de Internet)
  en RFC 7807 que creó un esquema generalizado de manejo de errores compuesto por cinco partes.
    1 tipo: un identificador URI que clasifica el error
    2 títulos: un mensaje breve y legible por humanos sobre el error
    3 códigos: el código de error único
    4 detalles: una explicación del error legible por humanos
    5 instancias: un URI que identifica la ocurrencia específica del error.
  Estandarizado es opcional pero tiene la ventaja de que se usa para Facebook y Twitter, es decir.
 */
public class StandarizedApiExceptionResponse {
    @ApiModelProperty(notes = "El identificador uri único que categoriza el error.", name = "type", 
           required = true, example = "/errors/authentication/not-authorized")
    private String type ="/errors/uncategorized";
    @ApiModelProperty(notes = "Un mensaje breve y legible por humanos sobre el error.", name = "title", 
           required = true, example = "El usuario no tiene autorización")
    private String title;
     @ApiModelProperty(notes = "El código de error único", name = "code", 
           required = false, example = "192")
    private String code;
      @ApiModelProperty(notes = "Una explicación legible por humanos del error.", name = "detail", 
           required = true, example = "El usuario no tiene los permisos de propiedad para acceder al "
                   + "recurso, por favor contacte con https://digitalthinking.biz/es/ada-enterprise-core#contactus")
    private String detail;
       @ApiModelProperty(notes = "A URI that identifies the specific occurrence of the error", name = "detail", 
           required = true, example = "/errors/authentication/not-authorized/01")
    private String instance ="/errors/uncategorized/bank";

    public StandarizedApiExceptionResponse(String title, String code, String detail) {
        super();
        this.title = title;
        this.code = code;
        this.detail = detail;
    }       
       
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

   

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return code;
    }

    public void setStatus(String status) {
        this.code = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }
    
}
