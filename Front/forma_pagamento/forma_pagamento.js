document.getElementById("formPost").addEventListener("submit", async (event) => {
    event.preventDefault();
    const resultado = document.getElementById("resultados");
    try{
        const obj = {"nome" : document.getElementById("nomePost").value};
        const resposta = await fetch("http://localhost:8085/forma_pagamento", {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        });
        const retorno = await resposta.json();
        if(retorno.error == null){
            resultado.innerHTML = `<p style="width: fit-content;background-color:#22A9D3; color: white; padding: 1.25vmin; border: .5vmin solid white; margin: .5vmin">Inserido com sucesso!</p>`;
        } else {
            throw error;
        }
    } catch (error) {
        resultado.innerHTML = `<p style="width: fit-content;background-color:#22A9D3; color: white; padding: 1.25vmin; border: .5vmin solid white; margin: .5vmin">Erro ao inserir!</p>`;
    }
});


document.getElementById("getTodos").addEventListener("click", async (event) => {
    event.preventDefault();
    const resultado = document.getElementById("resultados");
    try{
        const resposta = await fetch("http://localhost:8085/forma_pagamento", {
            method : "GET"
        });
        const retorno = await resposta.json();
        retorno.sort((a, b) => a.id_forma_pagamento - b.id_forma_pagamento);
        let res = "";
        for(let i = 0; i < retorno.length; i++){
            res += `<p style="width: fit-content;background-color:#22A9D3; color: white; padding: 1.25vmin; border: .5vmin solid white; margin: .5vmin">> Id: ${retorno[i].id_forma_pagamento} | Nome: ${retorno[i].nome}<p>`;
        }
        if(retorno.error == null){
            resultado.innerHTML = res;
        } else {
            throw error;
        }
    } catch (error) {
        resultado.innerHTML = `<p style="width: fit-content;background-color:#22A9D3; color: white; padding: 1.25vmin; border: .5vmin solid white; margin: .5vmin">Erro ao listar!</p>`;
    }
});

document.getElementById("formPut").addEventListener("submit", async (event) => {
    event.preventDefault();
    const resultado = document.getElementById("resultados");
    try{
        const id = document.getElementById("idPut").value;
        const obj = {"nome" : document.getElementById("nomePut").value};
        const resposta = await fetch(`http://localhost:8085/forma_pagamento/${id}`, {
            method : "PUT",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        });
        const retorno = await resposta.json();
        if(retorno.error == null){
            resultado.innerHTML = `<p style="width: fit-content;background-color:#22A9D3; color: white; padding: 1.25vmin; border: .5vmin solid white; margin: .5vmin">Atualizado com sucesso!</p>`;
        } else {
            throw error;
        }
    } catch (error) {
        resultado.innerHTML = `<p style="width: fit-content;background-color:#22A9D3; color: white; padding: 1.25vmin; border: .5vmin solid white; margin: .5vmin">Erro ao atualizar!</p>`;
    }
});

document.getElementById("formDelete").addEventListener("submit", async (event) => {
    event.preventDefault();
    const resultado = document.getElementById("resultados");
    try{
        const id = document.getElementById("idDelete").value;
        const resposta = await fetch(`http://localhost:8085/forma_pagamento/${id}`, {
            method : "DELETE"
        });
        if(resposta.ok){
            resultado.innerHTML = `<p style="width: fit-content;background-color:#22A9D3; color: white; padding: 1.25vmin; border: .5vmin solid white; margin: .5vmin">Deletado com sucesso!</p>`;
        } else {
            throw error;
        }
    } catch (error) {
        resultado.innerHTML = `<p style="width: fit-content;background-color:#22A9D3; color: white; padding: 1.25vmin; border: .5vmin solid white; margin: .5vmin">Erro ao deletar!</p>`;
    }
});