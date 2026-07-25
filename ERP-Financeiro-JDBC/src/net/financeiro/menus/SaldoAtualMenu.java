package net.financeiro.menus;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.KeyPressMessage;
import com.williamcallahan.tui4j.compat.lipgloss.Style;
import com.williamcallahan.tui4j.compat.lipgloss.color.AdaptiveColor;
import net.financeiro.menus.events.JdbcQueryResult;
import net.financeiro.model.SaldoAtual;
import net.financeiro.service.SaldoAtualService;

public class SaldoAtualMenu implements TableMenu {
    private final SaldoAtualService service = new SaldoAtualService();

    private String errorMessage = "";
    SaldoAtual saldoAtual = null;

    @Override
    public String getTitle() {
        return "Saldo Atual";
    }

    @Override
    public Command init() {
        return consultarSaldo();
    }

    @Override
    public Command handleInput(KeyPressMessage key) {
        if("r".equals(key.key()) || "enter".equals(key.key())) {
            return consultarSaldo();
        }
        return null;
    }

    private Command consultarSaldo() {
        return () -> {
            try {
                SaldoAtual result = service.visualizar();
                return new JdbcQueryResult(result, null);
            } catch(Exception e) {
                return new JdbcQueryResult(null, e.getMessage());
            }
        };
    }

    @Override
    public void onDataReceived(Object data, String error) {
        if(data == null) {
            errorMessage = error;
        } else {
            saldoAtual = (SaldoAtual) data;
        }
    }

    @Override
    public String render(boolean isLoading, String spinnerView) {
        Style dinheiroStyle = Style.newStyle()
                .foreground(new AdaptiveColor("#00c431", "#00c431"));

        if(isLoading) {
            return "Aguardando servidor " + spinnerView;
        }
        if(!errorMessage.isEmpty()) {
            return errorMessage;
        }

        return dinheiroStyle.render(" > R$ " + saldoAtual.getValor());
    }
}
