package main.net.financeiro.model;

public class Valor_Salario {
    private Long id_funcionario;
    private double salario;

    public Valor_Salario(Long id_funcionario, double salario) {
        this.id_funcionario = id_funcionario;
        this.salario = salario;
    }

    public Valor_Salario(double salario) {
        this.salario = salario;
    }

    public Long getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(Long id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public double getSalario() {
        return salario;
    }

    @Override
    public String toString(){
        return "ID do funcionário: " + id_funcionario
                + "Salário: " + salario;
    }
}
