package ru.spbau.devdays.clionvalgrind.results;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.HyperlinkInfo;
import com.intellij.execution.filters.RegexpFilter;
import com.intellij.execution.impl.EditorHyperlinkSupport;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.impl.DocumentImpl;
import com.intellij.openapi.editor.impl.EditorFactoryImpl;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.keymap.impl.ui.Hyperlink;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.*;
import com.intellij.ui.speedSearch.SpeedSearchUtil;
import com.intellij.ui.treeStructure.SimpleTreeBuilder;
import com.intellij.ui.treeStructure.SimpleTreeStructure;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.ui.treeStructure.treetable.ListTreeTableModelOnColumns;
import com.intellij.ui.treeStructure.treetable.TreeColumnInfo;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import com.intellij.ui.treeStructure.treetable.TreeTableTree;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.devdays.clionvalgrind.parser.errors.Error;
import ru.spbau.devdays.clionvalgrind.parser.errors.ErrorsHolder;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValgrindConsoleView implements ConsoleView {

    private @NotNull final JBSplitter mainPanel;
    private @NotNull final Project project;
    private @NotNull final ConsoleView console;
    private @NotNull final ErrorsHolder errors;

    private EditorHyperlinkSupport hyperlinks;

    public ValgrindConsoleView(@NotNull final Project project, @NotNull ConsoleView console, @NotNull ErrorsHolder errors) {
        this.project = project;
        this.console = console;
        this.errors = errors;
        mainPanel = new JBSplitter();
        JComponent consoleComponent = console.getComponent();
        mainPanel.setFirstComponent(consoleComponent);

        JTree tree = new Tree(errors.getTree());
        tree.add(new JScrollBar(Adjustable.HORIZONTAL));

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
