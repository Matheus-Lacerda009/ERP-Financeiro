package net.financeiro.menus;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.lipgloss.Position;
import com.williamcallahan.tui4j.compat.lipgloss.Style;
import com.williamcallahan.tui4j.compat.lipgloss.color.AdaptiveColor;
import net.financeiro.menus.events.JdbcQueryResult;
import net.financeiro.model.SaldoAtual;
import net.financeiro.service.SaldoAtualService;

import java.text.NumberFormat;
import java.util.Locale;

public class SaldoAtualMenu implements TableMenu {
    private final AdaptiveColor RED = new AdaptiveColor("#ff1f31", "#ff1f31");
    private final AdaptiveColor GREEN = new AdaptiveColor("#00c431", "#00c431");

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
    public Command handleInput(Message msg) {
        if(msg instanceof KeyPressMessage key) {
            if("r".equals(key.key()) || "enter".equals(key.key())) {
                return consultarSaldo();
            }
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
                .foreground(GREEN);

        if(isLoading) {
            return "\n   Aguardando servidor " + spinnerView;
        }
        if(!errorMessage.isEmpty()) {
            return errorMessage;
        }

        return dinheiroStyle.render("\n > " + NumberFormat.getCurrencyInstance().format(saldoAtual.getValor()));
    }
}
