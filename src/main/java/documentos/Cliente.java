package documentos;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Document(collection = "collection") //para que se cree la colección de clientes
public class Cliente {
    @Id
    @Getter @Setter
    private String id;

    @NotEmpty
    @Getter @Setter
    private String nombre;

    @NotEmpty
    @Getter @Setter
    private String apellido;

    @Getter @Setter
    private String foto;

    public Cliente(){

    }

}
