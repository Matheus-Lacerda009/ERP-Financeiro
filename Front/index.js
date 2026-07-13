document.getElementById("formLogin").addEventListener("submit", async (event) => {
    event.preventDefault();
    try{
        const nome = document.getElementById("inputNome").value;
        const senha = document.getElementById("inputSenha").value;
        const resposta = await fetch(`http://localhost:8085/login?nome=${nome}&senha=${senha}`);
        const retorno = await resposta.json();
        if(retorno){
            window.location.href = "menu/menu.html";
        }
    } catch(erro){
        console.log(erro);
    }
});