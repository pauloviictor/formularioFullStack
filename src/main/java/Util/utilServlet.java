package Util;

import Model.Contato;
import Model.Endereco;
import Model.Pessoa;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class utilServlet extends HttpServlet {

    public String lerRequestBody(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line + "\n");
            line = reader.readLine();
        }
        reader.close();
        String params = sb.toString();
        return params;
    }

    public JSONObject stringToJson(String stringToConvert) {
        return new JSONObject(stringToConvert);
    }

    public Pessoa jsonToPessoa(JSONObject objetoJson) {
        Pessoa pessoa = new Pessoa();

        String id = objetoJson.getString("id");
        if (validaCampoVazio(id)) {
            pessoa.setId(objetoJson.getInt("id"));
        }
        pessoa.setNome(objetoJson.getString("nome"));
        pessoa.setEmail(objetoJson.getString("email"));
        pessoa.setCpf(objetoJson.getString("cpf"));
        pessoa.setEscolaridade(objetoJson.getString("escolaridade"));

        Endereco endereco = new Endereco();

        endereco.setPessoa(pessoa);
        endereco.setCidade(objetoJson.getJSONObject("endereco").getString("cidade"));
        endereco.setEstado(objetoJson.getJSONObject("endereco").getString("estado"));
        endereco.setLogradouro(objetoJson.getJSONObject("endereco").getString("logradouro"));
        endereco.setComplemento(objetoJson.getJSONObject("endereco").getString("complemento"));

        pessoa.setEndereco(endereco);

        int quantidadeContatos = objetoJson.getInt("contatos");
        System.out.println(quantidadeContatos);

        List<Contato> contatos = new ArrayList<>();

        for (int i = 0; i < quantidadeContatos; i++) {
            Contato contato = new Contato();

            String nomeContato = String.format("contato%d", i);


            contato.setPessoa(pessoa);

            if (Integer.parseInt(objetoJson.getJSONObject(nomeContato).getString("id")) != 0) {
                contato.setId(objetoJson.getJSONObject(nomeContato).getInt("id"));
            }
            contato.setNome(objetoJson.getJSONObject(nomeContato).getString("nome"));
            contato.setTelefone(objetoJson.getJSONObject(nomeContato).getString("telefone"));
            contato.setEmail(objetoJson.getJSONObject(nomeContato).getString("email"));
            contatos.add(contato);
        }

        pessoa.setContatosAlternativos(contatos);
        return pessoa;
    }

    public boolean isLetter(String campo) {
        String campoAux = campo.trim();
        campoAux = campoAux.toUpperCase();
        campoAux = campoAux.replace(" ", "");
        if (campoAux.matches("[A-Z]*")) {
            return true;
        }
        return false;
    }

    public static boolean validaCampoVazio(String campo) {
        String campoAux = campo.trim();

        return campoAux.length() != 0;

    }

}
