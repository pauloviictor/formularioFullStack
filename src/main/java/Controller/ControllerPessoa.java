package Controller;

import DAO.PessoaDao;
import Model.Contato;
import Model.Pessoa;
import Util.utilServlet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("pessoa")
public class ControllerPessoa {

    PessoaDao dao = new PessoaDao();
    Util.utilServlet utilServlet = new utilServlet();

    public ControllerPessoa() throws SQLException {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("novoCadastro/")
    public Response inserePessoa(Pessoa pessoa) throws SQLException {
        System.out.println(pessoa);
        pessoa.getEndereco().setPessoa(pessoa);
        for(Contato contato : pessoa.getContatosAlternativos()){
            contato.setPessoa(pessoa);
        }
        dao.inserirPessoa(pessoa);
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("listaPessoa/")
    public List imprimePessoas(){
        List<Pessoa> pessoas;
        pessoas = dao.listarPessoas();

        return pessoas;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public Pessoa buscaPessoa( @PathParam("id") int id){
        return dao.buscaPessoa(id);
    }

    @GET
    @Path("removePessoa/{id}/")
    public void removePessoa( @PathParam("id") int id){
        Pessoa pessoa = dao.buscaPessoa(id);
        dao.removePessoa(pessoa);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("editaPessoa/")
    public void editaPessoa(Pessoa pessoa){
        pessoa.getEndereco().setPessoa(pessoa);
        for(Contato contato : pessoa.getContatosAlternativos()){
            contato.setPessoa(pessoa);
        }
        dao.editaPessoa(pessoa);
    }

}
