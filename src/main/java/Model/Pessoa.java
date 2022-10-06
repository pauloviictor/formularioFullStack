package Model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "pessoa")
public class Pessoa implements EntidadeBase {

    public Pessoa(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private int id;
    private String nome;
    private String email;
    private String cpf;

    private String escolaridade;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private Endereco endereco;

    @OneToMany (mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contato> contatosAlternativos;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Contato> getContatosAlternativos() {
        return contatosAlternativos;
    }

    public void setContatosAlternativos(List<Contato> contatosAlternativos) {
        this.contatosAlternativos = contatosAlternativos;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

//    @Override
//    public String toString(){
//        return "ID: " + this.id + " Nome: " + this.nome + " | " + " CPF: " + this.cpf + "\n" +
//                " E-mail: " + this.email + "\n" + "Escolaridade: " + this.escolaridade;
//    }

}
