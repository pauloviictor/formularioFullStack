const formElemento = document.getElementById("formulario");
var tableElemento = document.getElementById("cadastros-table");
const idElemento = document.getElementById("identificador");
const nomeElemento = document.getElementById("nome");
const emailElemento = document.getElementById("email");
const cpfElemento = document.getElementById("cpf");
const escolaridadeElemento = document.getElementById("schooling");
const cidadeElemento = document.getElementById("city");
const estadoElemento = document.getElementById("uf");
const logradouroElemento = document.getElementById("street");
const complementoElemento = document.getElementById("complement");
const divContato = document.getElementById("contato-alternativo");
const maskCpf = /(\d{3})\.(\d{3})\.(\d{3})-(\d{2})/;
let quantContatos = document.querySelector("#quantContatos");
let contatosAux;

window.addEventListener('load', (event) => {
    console.log("pegou")
    exibeCadastros()
})

formElemento.addEventListener("submit", salvaDados)

function resetaCampo() {

    idElemento.value = "";
    nomeElemento.value = "";
    emailElemento.value = "";
    cpfElemento.value = "";
    escolaridadeElemento.value = "";
    cidadeElemento.value = "";
    estadoElemento.value = "";
    logradouroElemento.value = "";

    resetaCamposContatos()

    complementoElemento.value = "";
    nomeElemento.style.cssText = "background-color: #ccc;' + 'border: 1px solid #ccc;";
    emailElemento.style.cssText = "background-color: #ccc;' + 'border: 1px solid #ccc;";
    cpfElemento.style.cssText = "background-color: #ccc;' + 'border: 1px solid #ccc;";

}

function valorInvalido(campo) {
    campo.style.cssText = 'background-color: #FEC7B3;' + 'border: 1px solid #B30C00;'

}

function valorValido(campo) {
    campo.style.cssText = 'background-color: #BCFFBA;' + 'border: 1px solid #73B370;'

}

function validaNome() {

    if (!campoVazio(nomeElemento)) {
        valorValido(nomeElemento);
        return true;
    } else {
        valorInvalido(nomeElemento)
    }

}

function campoVazio(campo) {
    if (campo.value.length === 0 || !campo.value.trim()) {
        return true;
    }
    return false;
}

function validaEmail() {

    let mask = /\S+@\S*\.\S*/;
    if (mask.test(emailElemento.value)) {
        valorValido(emailElemento)
        return true;
    } else {
        valorInvalido(emailElemento);
        return false;
    }

}

function mascaraCpf() {

    validaCpf() ? valorValido(cpfElemento) : valorInvalido(cpfElemento);

}

function validaCpf() {
    let strCPF = document.getElementById("cpf").value;
    let Soma;
    let Resto;

    if (maskCpf.test(strCPF)) {
        strCPF = strCPF.replace(/(\d{3})\.(\d{3})\.(\d{3})-(\d{2})/, '$1$2$3$4');
    }

    Soma = 0;
    if (strCPF == "00000000000") {
        return false;
    }
    for (i = 1; i <= 9; i++) {
        Soma = Soma + parseInt(strCPF.substring(i - 1, i)) * (11 - i);
    }

    Resto = (Soma * 10) % 11;
    if ((Resto == 10) || (Resto == 11)) Resto = 0;
    if (Resto != parseInt(strCPF.substring(9, 10))) {
        return false;
    }
    Soma = 0;
    for (i = 1; i <= 10; i++) Soma = Soma + parseInt(strCPF.substring(i - 1, i)) * (12 - i);

    Resto = (Soma * 10) % 11;
    if ((Resto == 10) || (Resto == 11)) Resto = 0;
    if (Resto != parseInt(strCPF.substring(10, 11))) {
        return false;
    }
    return true;
}

function adicionaContato() {

    let nomeContatoAnterior = document.querySelector(`#nomeContato${quantContatos.value}`)
    let emailContatoAnterior = document.querySelector(`#emailContato${quantContatos.value}`)
    let telefoneContatoAnterior = document.querySelector(`#telefoneContato${quantContatos.value}`)
    console.log("antes if " + quantContatos.value)
    if (nomeContatoAnterior.value != "" && emailContatoAnterior.value != "" && telefoneContatoAnterior.value != "") {
        adicionaCampoContato()
    }
    console.log("depois if " + quantContatos.value)
    // if (quantContatos.value == 0) {
    //     const nomeContato = document.querySelector("#nomeContato0")
    //     const emailContato = document.querySelector("#emailContato0")
    //     const telefoneContato = document.querySelector("#telefoneContato0")
    //     console.log("Contato0 antes if " + quantContatos.value)
    //     if (nomeContato.value != "" && emailContato.value != "" && telefoneContato.value != "") {
    //         adicionaCampoContato()
    //     }
    //     console.log("Contato0 depois if " + quantContatos.value)
    // }

}

function exibeContatos(contatos) {

    for (let i = 0; i < contatos.length; i++) {
        if (i == 0) {
            const idContato = document.getElementById('idContato0')
            const nomeContatoEl = document.getElementById(`nomeContato0`);
            const telefoneContatoEl = document.getElementById('telefoneContato0');
            const emailContatoEl = document.getElementById('emailContato0');

            idContato.value = contatos[i].id;
            nomeContatoEl.value = contatos[i].nome;
            telefoneContatoEl.value = contatos[i].telefone;
            emailContatoEl.value = contatos[i].email;

            nomeContatoEl.setAttribute("readonly", "true");
            telefoneContatoEl.setAttribute("readonly", "true");
            emailContatoEl.setAttribute("readonly", "true");

        } else {
            let divFilho = document.createElement("div");
            divContato.appendChild(divFilho);

            divFilho.setAttribute("class", "contatos-child")

            let idContato = document.createElement("input");
            let nomeContato = document.createElement("input");
            let telefoneContato = document.createElement("input");
            let emailContato = document.createElement("input");
            let divButton = document.createElement("div");

            divFilho.appendChild(idContato)
            divFilho.appendChild(nomeContato);
            divFilho.appendChild(telefoneContato);
            divFilho.appendChild(emailContato);
            divFilho.appendChild(divButton);


            divFilho.setAttribute("id", `contato${i}`)

            idContato.setAttribute("hidden", "true");
            idContato.setAttribute("id", `idContato${i}`)

            nomeContato.setAttribute("placeholder", "Insira o nome do contato");
            nomeContato.setAttribute("id", `nomeContato${i}`);
            nomeContato.setAttribute("required", "true");
            nomeContato.setAttribute("readonly", "true");

            telefoneContato.setAttribute("placeholder", "Insira o telefone  com ddd");
            telefoneContato.setAttribute("id", `telefoneContato${i}`);
            telefoneContato.setAttribute("required", "true");
            telefoneContato.setAttribute("class", "tell");
            telefoneContato.setAttribute("readonly", "true");

            emailContato.setAttribute("placeholder", "Insira o e-mail do contato");
            emailContato.setAttribute("id", `emailContato${i}`);
            emailContato.setAttribute("required", "true");
            emailContato.setAttribute("readonly", "true");

            idContato.value = contatos[i].id;
            nomeContato.value = contatos[i].nome;
            telefoneContato.value = contatos[i].telefone;
            emailContato.value = contatos[i].email;

            if (i > 1) {
                divButton.innerHTML = "<button class='btn-contato' type='button' onclick='excluirContato(" + i + ")'>Deletar Campo</button>"
            }

        }
    }
}

function salvaDados(e) {

    let cadastro;

    if (validaCpf() && validaNome() && validaEmail() && quantContatos.value >= 1) {

        let contato;
        let contatoNomeEl;
        let contatoEmailEl;
        let contatoTelefoneEl

        cadastro = {
            id: idElemento.value,
            nome: nomeElemento.value,
            email: emailElemento.value,
            cpf: cpfElemento.value,
            escolaridade: escolaridadeElemento.value,
            endereco: {
                cidade: cidadeElemento.value,
                estado: estadoElemento.value,
                logradouro: logradouroElemento.value,
                complemento: complementoElemento.value
            },
            contatosAlternativos: []
        }

        for (let i = 0; i <= quantContatos.value; i++) {

            let contatoIdEl = document.querySelector(`#idContato${i}`)
            contatoNomeEl = document.querySelector(`#nomeContato${i}`);
            contatoEmailEl = document.querySelector(`#emailContato${i}`);
            contatoTelefoneEl = document.querySelector(`#telefoneContato${i}`);

            contato = {
                id: contatoIdEl.value,
                nome: contatoNomeEl.value,
                email: contatoEmailEl.value,
                telefone: contatoTelefoneEl.value
            }

            cadastro.contatosAlternativos.push(contato);
        }


        if (maskCpf.test(cadastro.cpf)) {
            cadastro.cpf = cadastro.cpf.replace(/(\d{3})\.(\d{3})\.(\d{3})-(\d{2})/, '$1$2$3$4');
        }

        if (idElemento.value == 0) {


            // cadastro.contatos = parseInt(quantContatos.value) + 1;
            console.log(JSON.stringify(cadastro))

            fetch("../rest/pessoa/novoCadastro/", {
                method: "POST",
                body: JSON.stringify(cadastro),
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(() => exibeCadastros())
                .catch(err => console.log(err));


        } else {

            // cadastro.contatos = parseInt(quantContatos.value) + 1;
            console.log(cadastro);

            fetch("../rest/pessoa/editaPessoa/", {
                method: "POST",
                body: JSON.stringify(cadastro),
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(() => exibeCadastros())
                .catch(err => console.log(err));
        }

        quantContatos.value = 0;
        resetaCampo();

    } else {

        if (!validaCpf()) {
            alert("Por favor digite um CPF válido");
            cpfElemento.focus();
        }
        if (campoVazio(nomeElemento)) {
            alert("Por favor digite um Nome válido");
            nomeElemento.focus();
        }
        if (!validaEmail()) {
            alert("Por favor digite um E-mail válido");
            emailElemento.focus();
        }
        if (quantContatos.value <= 0) {
            alert("É preciso no mínimo 2 contatos alternativos");
        }
    }


    e.preventDefault(e);
}

function exibeCadastros() {
    fetch("../rest/pessoa/listaPessoa/")
        .then(response => {
            response.json().then(json => {

                let cadastroArr = json;
                tableElemento.innerHTML = "";

                let trHead = document.createElement("tr");

                tableElemento.appendChild(trHead);

                let thNome = document.createElement("th");
                let thEmail = document.createElement("th");
                let thEdit = document.createElement("th");
                let thDelete = document.createElement("th");

                trHead.appendChild(thNome);
                trHead.appendChild(thEmail);
                trHead.appendChild(thEdit);
                trHead.appendChild(thDelete);

                thNome.innerText = "Nome";
                thEmail.innerText = "Email";
                thEdit.innerText = "Editar";
                thDelete.innerText = "Excluir";


                for (let i = 0; i < cadastroArr.length; i++) {
                    let trFilho = document.createElement("tr");

                    tableElemento.appendChild(trFilho);

                    let tdNome = document.createElement("td");
                    let tdEmail = document.createElement("td");
                    let tdEdit = document.createElement("td");
                    let tdDelete = document.createElement("td");

                    trFilho.appendChild(tdNome);
                    trFilho.appendChild(tdEmail);
                    trFilho.appendChild(tdEdit);
                    trFilho.appendChild(tdDelete);

                    tdNome.innerText = cadastroArr[i].nome;
                    tdEmail.innerText = cadastroArr[i].email;
                    tdEdit.innerHTML = "<td class='icon-cell'><a href='#top' onclick='buscaCadastro(" + cadastroArr[i].id + ")' ><i class=\"fa-solid fa-pen-to-square\"></i></a></td>";
                    tdDelete.innerHTML = "<td class='icon-cell' ><a onclick='removerCadastro(" + cadastroArr[i].id + ")'><i class=\"fa-solid fa-trash-can\"></i></a></td>";
                }
            })
        })
        .catch(err => console.log(err));

}

function buscaCadastro(id) {

    resetaCampo()

    fetch("../rest/pessoa/" + id + "/")
        .then(response => {
            response.json().then(json => {
                quantContatos.value = json.contatosAlternativos.length - 1;
                console.log(json)
                idElemento.value = json.id;
                nomeElemento.value = json.nome;
                emailElemento.value = json.email;
                cpfElemento.value = json.cpf;
                escolaridadeElemento.value = json.escolaridade;
                cidadeElemento.value = json.endereco.cidade;
                estadoElemento.value = json.endereco.estado;
                logradouroElemento.value = json.endereco.logradouro;
                complementoElemento.value = json.endereco.complemento;
                console.log(quantContatos.value)
                exibeContatos(Array.from(json.contatosAlternativos));
                contatosAux = Array.from(json.contatosAlternativos);


            })
        })
        .catch(err => console.log(err))

}

function removerCampoContato(cont) {
    let removido = document.querySelector(`#contato${cont}`);
    console.log(removido)
    removido.remove();
    quantContatos.value--;
}

function excluirContato(cont) {
    contatosAux.splice(cont, 1);
    removerCampoContato(cont)
    resetaCamposContatos()
    exibeContatos(contatosAux);
}

function removerCadastro(id) {
    fetch("../rest/pessoa/removePessoa/" + id + "/")
        .then(() => exibeCadastros())
        .catch(err => console.log(err))
}

function adicionaCampoContato() {
    let divFilho = document.createElement("div");

    let idContato = document.createElement("input");
    let nomeContato = document.createElement("input");
    let telefoneContato = document.createElement("input");
    let emailContato = document.createElement("input");
    let divButton = document.createElement("div");
    let contadorAux = parseInt(quantContatos.value) + 1;

    divContato.appendChild(divFilho);
    divFilho.setAttribute("class", "contatos-child")
    divFilho.appendChild(idContato)
    divFilho.appendChild(nomeContato);
    divFilho.appendChild(telefoneContato);
    divFilho.appendChild(emailContato);
    divFilho.appendChild(divButton);

    divFilho.setAttribute("id", `contato${contadorAux}`)

    divButton.setAttribute("class", "btn")

    if (contadorAux > 1) {
        divButton.innerHTML = "<button class='btn-contato' type='button' onclick='removerCampoContato(" + contadorAux + ")'>Deletar Campo</button>"
    }

    idContato.setAttribute("hidden", "true");
    idContato.setAttribute("id", `idContato${contadorAux}`);
    idContato.setAttribute("value", "0");

    nomeContato.setAttribute("placeholder", "Insira o nome do contato");
    nomeContato.setAttribute("name", `nomeContato${contadorAux}`);
    nomeContato.setAttribute("id", `nomeContato${contadorAux}`);
    nomeContato.setAttribute("required", "true");

    telefoneContato.setAttribute("placeholder", "Insira o telefone  com ddd");
    telefoneContato.setAttribute("name", `telefoneContato${contadorAux}`);
    telefoneContato.setAttribute("id", `telefoneContato${contadorAux}`);
    telefoneContato.setAttribute("class", "tell");
    telefoneContato.setAttribute("required", "true");

    emailContato.setAttribute("placeholder", "Insira o e-mail do contato");
    emailContato.setAttribute("name", `emailContato${contadorAux}`);
    emailContato.setAttribute("id", `emailContato${contadorAux}`);
    emailContato.setAttribute("required", "true");

    quantContatos.value++;
}

function resetaCamposContatos() {

    let contatoIdEl = document.querySelector(`#idContato0`);
    let contatoNomeEl = document.querySelector(`#nomeContato0`);
    let contatoEmailEl = document.querySelector(`#emailContato0`);
    let contatoTelefoneEl = document.querySelector(`#telefoneContato0`);

    contatoIdEl.value = 0;
    contatoNomeEl.value = "";
    contatoEmailEl.value = "";
    contatoTelefoneEl.value = "";

    contatoNomeEl.removeAttribute("readonly");
    contatoEmailEl.removeAttribute("readonly");
    contatoTelefoneEl.removeAttribute("readonly");

    divContato.innerHTML = "";
}