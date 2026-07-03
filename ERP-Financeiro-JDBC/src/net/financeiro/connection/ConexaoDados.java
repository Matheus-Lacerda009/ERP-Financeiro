package net.financeiro.connection;

public class ConexaoDados {
    private String USER, PASSWORD;

    public ConexaoDados(String USER, String PASSWORD) {
        this.USER = USER;
        this.PASSWORD = PASSWORD;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    @Override
    public String toString(){
        return USER + "  " + PASSWORD;
    }
}
