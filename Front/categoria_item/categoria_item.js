document.getElementById("formPost").addEventListener("submit", async (event) => {
    event.preventDefault();
    try{
        const obj = {"nome" : document.getElementById("nomePost").value};
        const resposta = await fetch("http://localhost:8085/categoria_item", {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        });
        const retorno = await resposta.json();
    } catch (error) {
        console.log(error);
    }
});


document.getElementById("getTodos").addEventListener("click", async (event) => {
    event.preventDefault();
    const resultado = document.getElementById("resultadoBusca");
    try{
        const resposta = await fetch("http://localhost:8085/categoria_item", {
            method : "GET"
        });
        const retorno = await resposta.json();
        let res = "";
        for(let i = 0; i < retorno.length; i++){
            res += `<p>> Id: ${retorno[i].id_categoria_item} | Nome: ${retorno[i].nome}<p><br>`;
        }
        resultado.innerHTML = res;
    } catch (error) {
        console.log(error);
    }
});

document.getElementById("maiorVenda").addEventListener("click", async (event) => {
    event.preventDefault();
    const resultado = document.getElementById("resultadoBusca");
    try{
        const resposta = await fetch("http://localhost:8085/categoria_item/maiorVenda", {
            method : "GET"
        });
        const retorno = await resposta.json();
        let res = "";
        let nomes = retorno["NomeCategoria"];
        let vendas = retorno["VendaCategoria"];
        for(let i = 0; i < nomes.length; i++){
            res += `<p>> Nome da categoria: ${nomes[i]} | Valor de venda: ${vendas[i]}<p><br>`;
        }
        resultado.innerHTML = res;
    } catch (error) {
        console.log(error);
    }
});

document.getElementById("menorVenda").addEventListener("click", async (event) => {
    event.preventDefault();
    const resultado = document.getElementById("resultadoBusca");
    try{
        const resposta = await fetch("http://localhost:8085/categoria_item/menorVenda", {
            method : "GET"
        });
        const retorno = await resposta.json();
        let res = "";
        let nomes = retorno["NomeCategoria"];
        let vendas = retorno["VendaCategoria"];
        for(let i = 0; i < nomes.length; i++){
            res += `<p>> Nome da categoria: ${nomes[i]} | Valor de venda: ${vendas[i]}<p><br>`;
        }
        resultado.innerHTML = res;
    } catch (error) {
        console.log(error);
    }
});

document.getElementById("mediaVenda").addEventListener("click", async (event) => {
    event.preventDefault();
    const resultado = document.getElementById("resultadoBusca");
    try{
        const resposta = await fetch("http://localhost:8085/categoria_item/mediaVenda", {
            method : "GET"
        });
        const retorno = await resposta.json();
        let res = "";
        let nomes = retorno["NomeCategoria"];
        let vendas = retorno["VendaCategoria"];
        for(let i = 0; i < nomes.length; i++){
            res += `<p>> Nome da categoria: ${nomes[i]} | Valor médio de venda: ${vendas[i]}<p><br>`;
        }
        resultado.innerHTML = res;
    } catch (error) {
        console.log(error);
    }
});

document.getElementById("formPut").addEventListener("submit", async (event) => {
    event.preventDefault();
    try{
        const id = document.getElementById("idPut").value;
        const obj = {"nome" : document.getElementById("nomePut").value};
        const resposta = await fetch(`http://localhost:8085/categoria_item/${id}`, {
            method : "PUT",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        });
        const retorno = await resposta.json();
    } catch (error) {
        console.log(error);
    }
});

document.getElementById("formDelete").addEventListener("submit", async (event) => {
    event.preventDefault();
    try{
        const id = document.getElementById("idDelete").value;
        const resposta = await fetch(`http://localhost:8085/categoria_item/${id}`, {
            method : "DELETE"
        });
    } catch (error) {
        console.log(error);
    }
});