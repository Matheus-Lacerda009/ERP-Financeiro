package net.financeiro.menus;

import com.williamcallahan.tui4j.compat.bubbles.textinput.EchoMode;
import com.williamcallahan.tui4j.compat.bubbles.textinput.TextInput;
import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.lipgloss.Style;
import com.williamcallahan.tui4j.compat.lipgloss.border.StandardBorder;
import com.williamcallahan.tui4j.compat.lipgloss.color.AdaptiveColor;
import net.financeiro.connection.RateLimiting;
import net.financeiro.menus.events.AutenticacaoUsuario;
import net.financeiro.model.Usuario;
import net.financeiro.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;

public class LoginMenu implements TableMenu {
    //Paleta e styles
    private final AdaptiveColor RED = new AdaptiveColor("#ff1f31", "#ff1f31");
    private final AdaptiveColor GRAY = new AdaptiveColor("#878787", "#878787");

    private final Style WARN_STYLE = Style.newStyle()
            .foreground(RED);

    private final UsuarioService service = new UsuarioService();
    private final RateLimiting rateLimiting = new RateLimiting();

    //variaveis de inputs (Para Insert e Update)
    private int inputCursor = 0;
    private List<TextInput> inputs;

    //dados retornados por consultas
    private String errorMessage = "";
    private boolean accessAuthorized = false;
    private boolean loading = false;

    public LoginMenu() {
        iniciarCampos();
    }

    @Override
    public String getTitle() {
        return "LOGIN";
    }

    @Override
    public Command init() {
        errorMessage = "";
        accessAuthorized = false;
        iniciarFormulario();

        return null;
    }

    private Command realizarLogin(Usuario user) {
        return () -> {
          try {
              boolean result = service.validacao(user);
              return new AutenticacaoUsuario(result, null);
          } catch(Exception e) {
              return new AutenticacaoUsuario(null, e.getMessage());
          }
        };
    }

    @Override
    public Command handleInput(Message msg) {
        if(msg instanceof KeyPressMessage key) {
            String k = key.key();

            if(inputs.isEmpty()) {
                return null;
            }

            switch(k) {
                case "down", "tab":
                    //avança o cursor para o próximo input
                    inputs.get(inputCursor).blur();
                    inputCursor = (inputCursor + 1) % inputs.size();
                    inputs.get(inputCursor).focus();

                    return null;
                case "up", "shift+tab":
                    //volta o cursor para o input anterior
                    inputs.get(inputCursor).blur();
                    inputCursor = (inputCursor - 1 + inputs.size()) % inputs.size();
                    inputs.get(inputCursor).focus();

                    return null;
                case "enter":
                    if(!loading) {
                        if(rateLimiting.permitir()) {
                            final String usuario = inputs.get(0).value();
                            final String senha = inputs.get(1).value();

                            Usuario item = new Usuario(
                                    (usuario == null) ? "" : usuario,
                                    (senha == null) ? "" : senha
                            );

                            loading = true;
                            return realizarLogin(item);
                        } else {
                            errorMessage = "Limite de tentativas atingido.";
                        }
                    } else {
                        errorMessage = "Tentativa de login em andamento.";
                    }
            }
        }

        if(!inputs.isEmpty()) {
            UpdateResult<TextInput> res = inputs.get(inputCursor).update(msg);
            inputs.set(inputCursor, res.model());
            return res.command();
        }

        //retorna null pois não temos nada para o Orquestrador processar
        return null;
    }

    @Override
    public void onDataReceived(Object data, String error) {
        if(data == null) {
            errorMessage = error;
        } else {
            if(data instanceof Boolean) {
                accessAuthorized = (boolean) data;
                if(accessAuthorized == false) {
                    errorMessage = "Usuário ou Senha inválido.";
                }
            }
        }
        loading = false;
    }

    private void iniciarCampos() {
        TextInput usuarioInput = new TextInput();
        usuarioInput.setPrompt("Usuário: ");

        TextInput senhaInput = new TextInput();
        senhaInput.setPrompt("Senha: ");
        senhaInput.setEchoMode(EchoMode.EchoPassword);

        inputs = new ArrayList<>();
        inputs.add(usuarioInput);
        inputs.add(senhaInput);
    }

    private void iniciarFormulario() {
        //reseta a posição do cursor
        inputCursor = 0;

        if(inputs.isEmpty()) {
            iniciarCampos();
        }

        for(TextInput input : inputs) {
            input.setValue("");
        }

        //garante que o sistema esta com foco no primeiro input
        for(int i = 0; i < inputs.size(); i++) {
            if(i == inputCursor) {
                inputs.get(i).focus();
            } else {
                inputs.get(i).blur();
            }
        }
    }

    @Override
    public String render(boolean isLoading, String spinnerView) {
        if (isLoading) return "\n   Aguardando servidor " + spinnerView;

        StringBuilder buffer = new StringBuilder();

        for(TextInput input : inputs) {
            buffer.append("\n   ").append(input.view()).append("\n");
        }

        Style PANEL_BORDER = Style.newStyle()
                .border(StandardBorder.RoundedBorder)
                .borderForeground(GRAY);

        if(!errorMessage.isEmpty()) {
            String text = WARN_STYLE.render(" ⚠️ " + errorMessage);
            buffer.append("\n").append(PANEL_BORDER.render(text));
        }

        return buffer.toString();
    }
}
