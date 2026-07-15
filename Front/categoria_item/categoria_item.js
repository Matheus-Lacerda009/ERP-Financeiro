document.getElementById("formPost").addEventListener("submit", async (event) => {
    event.preventDefault();
    const resultado = document.getElementById("resultados");
    try{
        const obj = {"nome" : document.getElementById("nomePost").value};
        const resposta = await fetch("http://localhost:8085/categoria_item", {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        });
        const retorno = await resposta.json();
        resultado.innerHTML = `Inserido com sucesso!`;
    } catch (error) {
        resultado.innerHTML = `<p style="background-color:#C5D8D1; color: black; padding: 1%; border: 3px solid white; margin: 1%">Erro ao inserir!</p>`;
    }
});


document.getElementById("getTodos").addEventListener("click", async (event) => {
    event.preventDefault();
    const resultado = document.getElementById("resultados");
    try{
        const resposta = await fetch("http://localhost:8085/categoria_item", {
            method : "GET"
        });
        const retorno = await resposta.json();
        let res = "";
        for(let i = 0; i < retorno.length; i++){
            res += `<p style="background-color:#C5D8D1; color: black; padding: 1%; border: 3px solid white; margin: 1%">> Id: ${retorno[i].id_categoria_item} | Nome: ${retorno[i].nome}<p>`;
        }
        resultado.innerHTML = res;
    } catch (error) {
        resultado.innerHTML = `<p style="background-color:#C5D8D1; color: black; padding: 1%; border: 3px solid white; margin: 1%">Erro ao listar!</p>`;
    }
});

document.getElementById("maiorVenda").addEventListener("click", async (event) => {
    event.preventDefault();
    const resultado = document.getElementById("resultados");
    try{
        const resposta = await fetch("http://localhost:8085/categoria_item/maiorVenda", {
            method : "GET"
        });
        const retorno = await resposta.json();
        let res = "";
        const nomes = retorno["NomeCategoria"];
        const vendas = retorno["VendaCategoria"];
        for(let i = 0; i < nomes.length; i++){
            res += `<p style="background-color:#C5D8D1; color: black; padding: 1%; border: 3px solid white; margin: 1%">> Nome da categoria: ${nomes[i]} | Valor de venda: ${vendas[i]}<p>`;
        }
        resultado.innerHTML = res;
    } catch (error) {
        resultado.innerHTML = `<p style="background-color:#C5D8D1; color: black; padding: 1%; border: 3px solid white; margin: 1%">Erro ao ordenar</p>!`;
    }
});

document.getElementById("menorVenda").addEventListener("click", async (event) => {
    event.preventDefault();
    const resultado = document.getElementById("resultados");
    try{
        const resposta = await fetch("http://localhost:8085/categoria_item/menorVenda", {
            method : "GET"
        });
        const retorno = await resposta.json();
        let res = "";
        let nomes = retorno["NomeCategoria"];
        let vendas = retorno["VendaCategoria"];
        for(let i = 0; i < nomes.length; i++){
            res += `<p style="background-color:#C5D8D1; color: black; padding: 1%; border: 3px solid white; margin: 1%">> Nome da categoria: ${nomes[i]} | Valor de venda: ${vendas[i]}<p>`;
        }
        resultado.innerHTML = res;
    } catch (error) {
        resultado.innerHTML = `<p style="background-color:#C5D8D1; color: black; padding: 1%; border: 3px solid white; margin: 1%">Erro ao ordenar!</p>`;
    }
});

document.getElementById("mediaVenda").addEventListener("click", async (event) => {
    event.preventDefault();
    const resultado = document.getElementById("resultados");
    try{
        const resposta = await fetch("http://localhost:8085/categoria_item/mediaVenda", {
            method : "GET"
        });
        const retorno = await resposta.json();
        let res = "";
        let nomes = retorno["NomeCategoria"];
        let vendas = retorno["VendaCategoria"];
        for(let i = 0; i < nomes.length; i++){
            res += `<p style="background-color:#C5D8D1; color: black; padding: 1%; border: 3px solid white; margin: 1%">> Nome da categoria: ${nomes[i]} | Valor médio de venda: ${vendas[i]}<p>`;
        }
        resultado.innerHTML = res;
    } catch (error) {
        resultado.innerHTML = `<p style="background-color:#C5D8D1; color: black; padding: 1%; border: 3px solid white; margin: 1%">Erro ao ordenar!</p>`;
    }
});

document.getElementById("formPut").addEventListener("submit", async (event) => {
    event.preventDefault();
    const resultado = document.getElementById("resultados");
    try{
        const id = document.getElementById("idPut").value;
        const obj = {"nome" : document.getElementById("nomePut").value};
        const resposta = await fetch(`http://localhost:8085/categoria_item/${id}`, {
            method : "PUT",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        });
        const retorno = await resposta.json();
        resultado.innerHTML = `Atualizado com sucesso!`;
    } catch (error) {
        resultado.innerHTML = `<p style="background-color:#C5D8D1; color: black; padding: 1%; border: 3px solid white; margin: 1%">Erro ao atualizar!</p>`;
    }
});

document.getElementById("formDelete").addEventListener("submit", async (event) => {
    event.preventDefault();
    const resultado = document.getElementById("resultados");
    try{
        const id = document.getElementById("idDelete").value;
        const resposta = await fetch(`http://localhost:8085/categoria_item/${id}`, {
            method : "DELETE"
        });
        resultado.innerHTML = `Deletado com sucesso!`;
    } catch (error) {
        resultado.innerHTML = `<p style="background-color:#C5D8D1; color: black; padding: 1%; border: 3px solid white; margin: 1%">Erro ao deletar!</p>`;
    }
});