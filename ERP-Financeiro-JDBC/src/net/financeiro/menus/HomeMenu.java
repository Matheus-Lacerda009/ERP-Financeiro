package net.financeiro.menus;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.KeyPressMessage;

import java.util.List;

public class HomeMenu implements TableMenu {
    private final List<String> tabelas = List.of("Operações", "Vendas", "Saldo Atual");
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
    public Command handleInput(KeyPressMessage key) {
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
        return null;
    }

    @Override
    public void onDataReceived(Object data, String error) {
        //Nenhum dado para processar
    }

    @Override
    public String render(boolean isLoading, String spinnerView) {
        StringBuilder buffer = new StringBuilder();

        for(int i = 0; i < tabelas.size(); i++) {
            String prefix = i == cursor ? " > " : "   ";
            buffer.append(prefix).append(tabelas.get(i)).append("\n");
        }

        return buffer.toString();
    }
}
