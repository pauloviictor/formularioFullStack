package Model;

import javax.persistence.*;

@Entity
@Table(name = "endereco")
public class Endereco {

    public Endereco(){

    }

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @OneToOne
    @JoinColumn (nullable = false)
    private Pessoa pessoa;


    private String logradouro;
    private String cidade;
    private String estado;
    private String complemento;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }


}
