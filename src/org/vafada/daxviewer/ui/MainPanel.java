package org.vafada.daxviewer.ui;

import org.vafada.daxviewer.DaxImageFile;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class MainPanel extends JPanel {
    private JTree tree;
    private PicturesContainer picturesContainer = new PicturesContainer();

    private boolean isDAXFile(File file) {
        return file.getName().toLowerCase().endsWith(".dax");
    }

    public MainPanel() {
        setLayout(new BorderLayout());

        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();

        DefaultMutableTreeNode virtualRoot = new DefaultMutableTreeNode("Virtual Root");

        for (Path rootDirectory : rootDirectories) {
            DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootDirectory.toFile());
            {
                File[] firstLevelDirs = rootDirectory.toFile().listFiles(File::isDirectory);
                if (firstLevelDirs != null) {
                    Arrays.sort(firstLevelDirs);
                    for (File dir : firstLevelDirs) {
                        if (dir.isHidden()) continue;
                        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(dir);
                        addDummyChild(childNode); // Make child expandable
                        rootNode.add(childNode);
                    }
                }
            }
            {
                File[] files = rootDirectory.toFile().listFiles(File::isFile);
                if (files != null) {
                    Arrays.sort(files);
                    for (File file : files) {
                        if (file.isHidden()) continue;
                        // show only DAX file
                        if (!isDAXFile(file)) continue;
                        DefaultMutableTreeNode child = new DefaultMutableTreeNode(file);
                        rootNode.add(child);
                    }
                }
            }
            virtualRoot.add(rootNode);
        }


        tree = new JTree(virtualRoot);
        tree.expandRow(1);

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
                                // show only DAX file
                                if (!isDAXFile(file)) continue;
                                DefaultMutableTreeNode child = new DefaultMutableTreeNode(file);
                                node.add(child);
                            }
                        }
                    }
                    ((DefaultTreeModel) tree.getModel()).reload(node);
                }
            }

            public void treeWillCollapse(TreeExpansionEvent event) {
            }
        });

        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) return;
            File nodeFile = (File) node.getUserObject();
            if (isDAXFile(nodeFile)) {
                DaxImageFile daxImageFile = new DaxImageFile(nodeFile.getAbsolutePath(), true);
                picturesContainer.setBitmaps(daxImageFile.getBitmaps());
            }
        });

        tree.setRootVisible(false);



        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(tree),
                new JScrollPane(picturesContainer));
        splitPane.setDividerLocation(200);

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
}
