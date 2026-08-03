package net.financeiro.connection;

public class VerificarPermissao {
    private static String permissao;

    public static void setPermissao(String permissao){
        VerificarPermissao.permissao = permissao;
    }

    public static boolean validar(String permissao){
        return VerificarPermissao.permissao.contains(permissao);
    }
}
