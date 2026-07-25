package net.financeiro.menus;

import com.williamcallahan.tui4j.compat.bubbles.spinner.Spinner;
import com.williamcallahan.tui4j.compat.bubbles.spinner.SpinnerType;
import com.williamcallahan.tui4j.compat.bubbletea.*;
import com.williamcallahan.tui4j.compat.lipgloss.Position;
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

    //windows default size
    private int height = 30;
    private int width = 60;

    //variables
    private boolean isLoading = false;
    private Spinner spinner;

    //windows management
    private final HomeMenu homeMenu = new HomeMenu();
    private final SaldoAtualMenu saldoAtualMenu = new SaldoAtualMenu();
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

        //todo WindowSizeMessage: Por Algum motivo o tamanho fica cortando

        // if(msg instanceof WindowSizeMessage w) {
        //     this.width = w.width();
        //     this.height = w.height();
        //     return UpdateResult.from(this);
        // }

        if(msg instanceof JdbcQueryResult result) {
            this.isLoading = false;
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
                this.isLoading = false;
                return UpdateResult.from(this);
            }

            Command cmdService = menuAtivo.handleInput(key);

            if(menuAtivo == homeMenu && homeMenu.getTabelaSelecionada() != null) {
                String moduloSelecionado = homeMenu.getTabelaSelecionada();
                homeMenu.limparSelecao();
                return trocarModulo(moduloSelecionado);
            }

            if(cmdService != null) {
                this.isLoading = true;
                return UpdateResult.from(this, Command.batch(() -> spinner.tick(), cmdService));
            }
        }

        if(isLoading) {
            UpdateResult<Spinner> spinnerResult = spinner.update(msg);
            this.spinner = spinnerResult.model();
            return UpdateResult.from(this, spinnerResult.command());
        }

        return UpdateResult.from(this);
    }

    private UpdateResult<? extends Model> trocarModulo(String modulo) {
        switch (modulo) {
            case "Saldo Atual":
                this.menuAtivo = saldoAtualMenu;
                break;
        }

        Command cmdInit = this.menuAtivo.init();

        if(cmdInit != null) {
            this.isLoading = true;
            return UpdateResult.from(this, Command.batch(() -> spinner.tick(), cmdInit));
        }

        this.isLoading = false;
        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        String viewMenuAtivo = menuAtivo.render(isLoading, spinner.view());

        int larguraUtil = Math.max(0, this.width - 2);

        Style tituloStyle = Style.newStyle()
                .foreground(ERP_DARKBLUE)
                .width(larguraUtil)
                .align(Position.Center)
                .bold(true);

        String tituloDinamico = " [ ERP Finanças :: " + menuAtivo.getTitle().toUpperCase() + " ] ";
        String tituloCentralizado = tituloStyle.render(tituloDinamico);

        String renderCompleta = tituloCentralizado + "\n\n" + viewMenuAtivo;

        return PANEL_BORDER
                .height(this.height)
                .width(this.width)
                .render(renderCompleta);
    }
}
