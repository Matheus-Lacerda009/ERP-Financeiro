document.getElementById("formLogin").addEventListener("submit", async (event) => {
    event.preventDefault();
    try{
        const loginErro = document.getElementById("loginErro");
        loginErro.style.color = "black";
        loginErro.style.backgroundColor = "#C5D8D1";
        loginErro.style.border = ".5vmin solid white";
        loginErro.style.width = "10vmin";
        loginErro.style.borderRadius = "10px";
        loginErro.style.padding = ".5vmin";
        loginErro.style.fontSize = "1vmin";
        loginErro.innerHTML = "Carregando...";
        const form = document.getElementById("formLogin");
        const nome = document.getElementById("inputNome").value;
        const senha = document.getElementById("inputSenha").value;
        const resposta = await fetch(`http://localhost:8085/login?nome=${nome}&senha=${senha}`);
        const retorno = await resposta.json();
        if(retorno){
            window.location.href = "menu/menu.html";
        } else {
            loginErro.style.color = "red";
            loginErro.innerHTML = "Erro ao logar!";
            form.appendChild(loginErro);
        }
    } catch(erro){
        console.log(erro);
    }
});