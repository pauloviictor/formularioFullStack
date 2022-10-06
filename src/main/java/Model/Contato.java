package Model;


import javax.persistence.*;

@Entity
@Table(name = "contatos")
public class Contato {

    public Contato() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Pessoa pessoa;
    private String nome;
    private String email;
    private String telefone;

    public int getId() {
        return id;
    }

    public void setId(int id_cadastro) {
        this.id = id_cadastro;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

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


    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + "\n" +
                "• Nome Contato: " + this.getNome() + "\n" +
                "• E-mail Contato: " + this.getEmail() + "\n" +
                "• Telefone Contato: " + this.telefone + "\n";
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == null){
            System.out.println("objeto Null");
            return false;
        }

        Contato contato = (Contato) obj;

        if(contato.id == this.id){
            System.out.println("ID igual");
            return true;
        }

        System.out.println("Nenhuma condição equals");
        return false;
    }
}
