package ru.spbau.devdays.clionvalgrind.results;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.HyperlinkInfo;
import com.intellij.execution.impl.EditorHyperlinkSupport;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.Project;
import com.intellij.ui.*;
import com.intellij.ui.treeStructure.Tree;
import org.jetbrains.annotations.NotNull;
import ru.spbau.devdays.clionvalgrind.parser.Parser;
import ru.spbau.devdays.clionvalgrind.parser.errors.ErrorsHolder;

import javax.swing.*;
import java.awt.*;

public class ValgrindConsoleView implements ConsoleView {

    private @NotNull final JBSplitter mainPanel;
    private @NotNull final Project project;
    private @NotNull final ConsoleView console;
    private @NotNull ErrorsHolder errors;
    private String pathToXml;
    private JTree tree = new Tree();

    private EditorHyperlinkSupport hyperlinks;

    public ValgrindConsoleView(@NotNull final Project project, @NotNull ConsoleView console,
                               @NotNull String pathToXml) {
        this.project = project;
        this.console = console;
        this.pathToXml = pathToXml;
        mainPanel = new JBSplitter();
        JComponent consoleComponent = console.getComponent();
        mainPanel.setFirstComponent(consoleComponent);

    }


    public void refreshErrors() {
        try {
            errors = Parser.parse(pathToXml);
        }
        catch (Exception ex) {
            errors = new ErrorsHolder();
        }

        tree = new Tree(errors.getTree());
        tree.add(new JScrollBar(Adjustable.HORIZONTAL));
        tree.add(new JScrollBar(Adjustable.VERTICAL));
        mainPanel.setSecondComponent(tree);
    }

    @Override
    public JComponent getComponent() {
        return mainPanel;
    }

    @Override
    public void dispose() {
        hyperlinks = null;
    }

    @Override
    public void print(@NotNull String s, @NotNull ConsoleViewContentType contentType) {}

    @Override
    public void clear() {}

    @Override
    public void scrollTo(int offset) {}

    @Override
    public void attachToProcess(ProcessHandler processHandler) { console.attachToProcess(processHandler); }

    @Override
    public void setOutputPaused(boolean value) {}

    @Override
    public boolean isOutputPaused() {
        return false;
    }

    @Override
    public boolean hasDeferredOutput() {
        return false;
    }

    @Override
    public void performWhenNoDeferredOutput(@NotNull Runnable runnable) {}

    @Override
    public void setHelpId(@NotNull String helpId) {}

    @Override
    public void addMessageFilter(@NotNull Filter filter) { console.addMessageFilter(filter); }

    @Override
    public void printHyperlink(@NotNull String hyperlinkText, HyperlinkInfo info) {}

    @Override
    public int getContentSize() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @NotNull
    @Override
    public AnAction[] createConsoleActions() {
        return AnAction.EMPTY_ARRAY;
    }

    @Override
    public void allowHeavyFilters() {}

    @Override
    public JComponent getPreferredFocusableComponent() {
        return mainPanel.getSecondComponent();
    }
}
