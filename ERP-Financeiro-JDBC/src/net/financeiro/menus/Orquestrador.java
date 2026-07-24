package net.financeiro.menus;

import com.williamcallahan.tui4j.compat.bubbles.spinner.Spinner;
import com.williamcallahan.tui4j.compat.bubbles.spinner.SpinnerType;
import com.williamcallahan.tui4j.compat.bubbletea.*;
import com.williamcallahan.tui4j.compat.lipgloss.Style;
import com.williamcallahan.tui4j.compat.lipgloss.border.StandardBorder;
import com.williamcallahan.tui4j.compat.lipgloss.color.AdaptiveColor;
import net.financeiro.menus.events.JdbcQueryResult;

public class Orquestrador implements Model {
    //COLOR PALETTE
    //main colors
    private final AdaptiveColor ERP_BLUE = new AdaptiveColor("#2359f0", "#2359f0");
    private final AdaptiveColor ERP_DARKBLUE = new AdaptiveColor("#1a41aa", "#1a41aa");

    //secondary colors
    private final AdaptiveColor TEXT_DIM = new AdaptiveColor("#6e6e6e", "#6e6e6e");

    //styles
    private final Style SELECTED = Style.newStyle()
            .foreground(ERP_BLUE)
            .bold(true)
            .padding(0, 2);

    private final Style DESELECTED = Style.newStyle()
            .foreground(TEXT_DIM)
            .padding(0, 2);

    //border style
    private final Style PANEL_BORDER = Style.newStyle()
            .border(StandardBorder.RoundedBorder)
            .borderForeground(ERP_BLUE);

    //variables
    private boolean isLoading = false;
    private Model spinner;

    //windows management
    private final HomeMenu homeMenu = new HomeMenu();
    private TableMenu menuAtivo = homeMenu;
    
    //constructor
    public Orquestrador() {
        this.spinner = new Spinner(SpinnerType.POINTS)
                .setStyle(Style.newStyle().foreground(ERP_BLUE));
    }

    @Override
    public Command init() {
        return spinner.init();
    }

    @Override
    public UpdateResult<? extends Model> update(Message msg) {
        if(msg instanceof JdbcQueryResult result) {
            this.isLoading = true;
            menuAtivo.onDataReceived(result.data(), result.error());
            return UpdateResult.from(this);
        }

        if(msg instanceof KeyPressMessage key && !isLoading) {
            String k = key.key();

            if("q".equals(k)) {
                return UpdateResult.from(this, QuitMessage::new);
            }

            if("esc".equals(k) && menuAtivo != homeMenu) {
                homeMenu.limparSelecao();
                this.menuAtivo = homeMenu;
                return UpdateResult.from(this);
            }

            Command cmdService = menuAtivo.handleInput(key);

            if(menuAtivo == homeMenu && homeMenu.getTabelaSelecionada() != null) {
                trocarModulo(homeMenu.getTabelaSelecionada());
            }

            if(cmdService != null) {
                this.isLoading = true;
                return UpdateResult.from(this, cmdService);
            }
        }

        if(isLoading) {
            UpdateResult<? extends Model> spinnerResult = spinner.update(msg);
            this.spinner = spinnerResult.model();
            return UpdateResult.from(this, spinnerResult.command());
        }

        return UpdateResult.from(this);
    }

    private void trocarModulo(String modulo) {
        //todo add a switch case to change between modules
    }

    @Override
    public String view() {
        String viewActiveMenu = menuAtivo.render(isLoading, spinner.view());
        return PANEL_BORDER.render(viewActiveMenu);
    }
}
