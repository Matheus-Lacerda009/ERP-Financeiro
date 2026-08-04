package net.financeiro.menus;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.Message;

import java.util.List;

public class HomeMenu implements TableMenu {
    private final List<String> tabelas = List.of(
            "Categoria Item", "Conta Bancaria", "Fluxo Caixa",
            "Folha Pagamento", "Forma Pagamento", "Fornecedor/Cliente",
            "Funcionario", "Itens Operação", "Operação",
            "Produtos", "Saldo Atual", "Usuario",
            "Valor por Venda", "Valor Salário", "Nota Fiscal"
    );

    private int cursor = 0;
    private String tabelaSelecionada = null;

    public String getTabelaSelecionada() {
        return tabelaSelecionada;
    }

    public void limparSelecao() {
        tabelaSelecionada = null;
    }

    @Override
    public String getTitle() {
        return "Home";
    }

    @Override
    public Command handleInput(Message msg) {
        if(msg instanceof KeyPressMessage key) {
            String k = key.key();
            switch(k) {
                case "s", "down":
                    if(cursor < tabelas.size() - 1) cursor++;
                    break;
                case "w", "up":
                    if(cursor > 0) cursor--;
                    break;
                case "enter":
                    tabelaSelecionada = tabelas.get(cursor);
            }
        }
        return null;
    }

    @Override
    public void onDataReceived(Object data, String error) {
        //Nenhum dado para processar
    }

    @Override
    public String render(boolean isLoading, String spinnerView) {
        StringBuilder buffer = new StringBuilder();

        buffer.append("\n");
        for(int i = 0; i < tabelas.size(); i++) {
            String prefix = i == cursor ? " > " : "   ";
            buffer.append(prefix).append(tabelas.get(i)).append("\n");
        }

        return buffer.toString();
    }
}
