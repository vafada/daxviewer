package org.vafada.daxviewer.ui;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;

public class FileExplorerPanel extends JPanel{
    private JTree tree;
    private JList<File> fileList;
    private DefaultListModel<File> listModel;

    public FileExplorerPanel(File rootDirectory) {
        setLayout(new BorderLayout());

        // Tree setup - add root node and its first-level subdirectories
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootDirectory);
        File[] firstLevelDirs = rootDirectory.listFiles(File::isDirectory);
        if (firstLevelDirs != null) {
            Arrays.sort(firstLevelDirs);
            for (File dir : firstLevelDirs) {
                if (dir.isHidden()) continue;
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(dir);
                addDummyChild(childNode); // Make child expandable
                rootNode.add(childNode);
            }
        }
        tree = new JTree(rootNode);

        // Custom renderer: only show file/folder name
        tree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree,
                                                          Object value,
                                                          boolean sel,
                                                          boolean expanded,
                                                          boolean leaf,
                                                          int row,
                                                          boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                Object userObject = node.getUserObject();
                if (userObject instanceof File file) {
                    setText(file.getName().isEmpty() ? file.getPath() : file.getName());
                } else {
                    setText(userObject.toString());
                }
                return this;
            }
        });

        // Lazy load children when expanded
        tree.addTreeWillExpandListener(new TreeWillExpandListener() {
            public void treeWillExpand(TreeExpansionEvent event) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
                if (hasDummyChild(node)) {
                    node.removeAllChildren();
                    File dir = (File) node.getUserObject();
                    {
                        File[] files = dir.listFiles(File::isDirectory);
                        if (files != null) {
                            Arrays.sort(files);
                            for (File file : files) {
                                if (file.isHidden()) continue;
                                DefaultMutableTreeNode child = new DefaultMutableTreeNode(file);
                                addDummyChild(child);
                                node.add(child);
                            }
                        }
                    }
                    {
                        File[] files = dir.listFiles(File::isFile);
                        if (files != null) {
                            Arrays.sort(files);
                            for (File file : files) {
                                if (file.isHidden()) continue;
                                DefaultMutableTreeNode child = new DefaultMutableTreeNode(file);

                                node.add(child);
                            }
                        }
                    }
                    ((DefaultTreeModel) tree.getModel()).reload(node);
                }
            }

            public void treeWillCollapse(TreeExpansionEvent event) {}
        });

        // File list setup
        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);

        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) return;
            File nodeFile = (File) node.getUserObject();
            listModel.clear();
            File[] files = nodeFile.listFiles(File::isFile);
            if (files != null) {
                Arrays.sort(files);
                for (File f : files) listModel.addElement(f);
            }
        });


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(tree),
                new JScrollPane(fileList));
        splitPane.setDividerLocation(150);

        add(splitPane, BorderLayout.CENTER);
    }

    // Add a dummy child to indicate expandability
    private void addDummyChild(DefaultMutableTreeNode node) {
        node.add(new DefaultMutableTreeNode("Loading..."));
    }

    // Check if node only has dummy child
    private boolean hasDummyChild(DefaultMutableTreeNode node) {
        return node.getChildCount() == 1 &&
                node.getChildAt(0) instanceof DefaultMutableTreeNode &&
                "Loading...".equals(node.getChildAt(0).toString());
    }

    public static void main(String[] args) {
        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        System.out.println("Filesystem Root Directories:");
        for (Path root : rootDirectories) {
            System.out.println(root);
        }


        JFrame frame = new JFrame("Custom File Explorer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);

        File root = new File(System.getProperty("user.home"));
        frame.add(new FileExplorerPanel(root));
        frame.setVisible(true);

    }
}
