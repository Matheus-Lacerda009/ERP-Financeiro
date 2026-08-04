package net.financeiro.menus;

import com.williamcallahan.tui4j.compat.bubbles.textinput.TextInput;
import com.williamcallahan.tui4j.compat.bubbles.viewport.Viewport;
import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.lipgloss.Style;
import com.williamcallahan.tui4j.compat.lipgloss.color.AdaptiveColor;
import net.financeiro.menus.events.JdbcQueryResult;
import net.financeiro.model.Forma_Pagamento;
import net.financeiro.model.Funcionario;
import net.financeiro.service.FuncionarioService;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioMenu implements TableMenu {
    //Paleta e styles
    private final AdaptiveColor RED = new AdaptiveColor("#ff1f31", "#ff1f31");
    private final AdaptiveColor GRAY = new AdaptiveColor("#878787", "#878787");

    private final Style WARN_STYLE = Style.newStyle()
            .foreground(RED);

    private final FuncionarioService service = new FuncionarioService();
    private enum Estado { VISUALIZANDO, EDITANDO, CRIANDO, DELETANDO };
    private final Viewport viewport;

    //estado do menu
    private Estado estado = Estado.VISUALIZANDO;

    //posicao na lista de entidades
    private int cursor = 0;

    //variaveis de inputs (Para Insert e Update)
    private int inputCursor = 0;
    private List<TextInput> inputs;

    //dados retornados por consultas
    private String errorMessage = "";
    List<Funcionario> itens = new ArrayList<>();

    public FuncionarioMenu() {
        this.viewport = new Viewport();
    }

    @Override
    public String getTitle() {
        return "Funcionario";
    }

    @Override
    public Command init() {
        estado = Estado.VISUALIZANDO;
        cursor = 0;
        errorMessage = "";
        inicarCampos();

        return consultarMenu();
    }

    private Command consultarMenu() {
        return () -> {
            try {
                List<Funcionario> result = service.listarInfo();
                return new JdbcQueryResult(result, null);
            } catch(Exception e) {
                return new JdbcQueryResult(null, e.getMessage());
            }
        };
    }

    @Override
    public Command handleInput(Message msg) {
        return null;
    }

    @Override
    public void onDataReceived(Object data, String error) {
        if(data == null) {
            errorMessage = error;
        } else {
            if(data instanceof List<?>) {
                itens = (List<Funcionario>) data;
                cursor = 0;
                viewport.gotoTop();
                atualizarViewport();
            }
        }
    }

    private void atualizarViewport() {

    }

    private void inicarCampos() {
        TextInput nomeInput = new TextInput();
        nomeInput.setPrompt("Nome: ");
        nomeInput.setPlaceholder("Ex: Roberto");

        TextInput cpfInput = new TextInput();
        cpfInput.setPrompt("CPF: ");
        cpfInput.setPlaceholder("Ex: 01234567899");

        TextInput telefoneInput = new TextInput();
        telefoneInput.setPrompt("Telefone: ");
        telefoneInput.setPlaceholder("Ex: 11999998888");

        TextInput emailInput = new TextInput();
        emailInput.setPrompt("Email: ");
        emailInput.setPlaceholder("Ex: erp.valentim@dev.com");

        inputs = new ArrayList<>();
        inputs.add(nomeInput);
        inputs.add(cpfInput);
        inputs.add(telefoneInput);
        inputs.add(emailInput);
    }

    private void inicarFormulario() {
        //reseta a posição do cursor
        inputCursor = 0;

        if(estado == Estado.EDITANDO) {
            //atualiza os campos para corresponderem aos valores da entidade
            Funcionario item = itens.get(cursor);
            //todo adicionar campos
        } else if(estado == Estado.CRIANDO) {
            //limpa os campos
            for(TextInput input : inputs) {
                input.setValue("");
            }
        }

        //garante que o sistema esta com foco no primeiro input
        for(int i = 0; i < inputs.size(); i++) {
            if(i == inputCursor) {
                inputs.get(i).focus();
            } else {
                inputs.get(i).blur();
            }
        }

        //atualiza o viewport
        atualizarViewport();
    }

    @Override
    public String render(boolean isLoading, String spinnerView) {
        if(isLoading) return "\n   Aguardando servidor " + spinnerView;

        if(!errorMessage.isEmpty()) {
            return WARN_STYLE.render("\n   ⚠️ " + errorMessage);
        }

        return viewport.view() + "\n\n";
    }
}
