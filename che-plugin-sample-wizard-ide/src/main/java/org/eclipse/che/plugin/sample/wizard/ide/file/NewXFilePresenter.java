/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.plugin.sample.wizard.ide.file;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.ide.api.project.ProjectServiceClient;
import org.eclipse.che.api.project.shared.dto.ItemReference;
import org.eclipse.che.api.promises.client.Function;
import org.eclipse.che.api.promises.client.FunctionException;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.project.node.HasStorablePath;
import org.eclipse.che.ide.api.data.tree.Node;
import org.eclipse.che.ide.api.selection.Selection;
import org.eclipse.che.ide.json.JsonHelper;
import org.eclipse.che.ide.part.explorer.project.ProjectExplorerPresenter;
import org.eclipse.che.ide.project.node.FileReferenceNode;
import org.eclipse.che.ide.rest.AsyncRequestCallback;
import org.eclipse.che.ide.rest.DtoUnmarshallerFactory;
import org.eclipse.che.ide.api.dialogs.DialogFactory;
import org.eclipse.che.plugin.sample.wizard.shared.Constants;


/**
 * Presenter for creating
 *
 * @author Vitalii Parfonov
 */
@Singleton
public class NewXFilePresenter implements NewXFileView.ActionDelegate {
    private static final String DEFAULT_CONTENT = " #include <${header}>";

    private final ProjectExplorerPresenter projectExplorer;
    private final NewXFileView view;
    private final ProjectServiceClient projectServiceClient;
    private final DtoUnmarshallerFactory dtoUnmarshaller;
    private final AppContext appContext;
    private final DialogFactory dialogFactory;

    @Inject
    public NewXFilePresenter(NewXFileView view,
                             ProjectExplorerPresenter projectExplorer,
                             AppContext appContext,
                             ProjectServiceClient projectServiceClient,
                             DtoUnmarshallerFactory dtoUnmarshaller,
                             DialogFactory dialogFactory) {
        this.appContext = appContext;
        this.dialogFactory = dialogFactory;
        this.view = view;
        this.projectExplorer = projectExplorer;
        this.projectServiceClient = projectServiceClient;
        this.dtoUnmarshaller = dtoUnmarshaller;
        this.view.setDelegate(this);
    }

    public void showDialog() {
        view.showDialog();
    }

    @Override
    public void onCancelClicked() {
        view.close();
    }

    @Override
    public void onOkClicked() {
        final String fileName = view.getName();
        view.close();
        createClass(fileName);
    }

    private void createClass(String name) {
        String content = DEFAULT_CONTENT.replace("${header}", view.getHeader());
        createSourceFile(name, content);
    }

    private void createSourceFile(final String nameWithoutExtension, final String content) {
        Selection<?> selection = projectExplorer.getSelection();
        if (selection.isEmpty() || selection.getAllElements().size() > 1) {
            return;
        }
        Object selectedNode = selection.getHeadElement();
        String path;
        if (selectedNode instanceof FileReferenceNode) {
            HasStorablePath parent = (HasStorablePath) ((FileReferenceNode) selectedNode).getParent();
            path = parent.getStorablePath();
        } else {
            path = ((HasStorablePath)selectedNode).getStorablePath();
        }
        createFile(path, nameWithoutExtension, content);
    }


    private void createFile(final String path, final String nameWithoutExtension, final String content) {
        projectServiceClient.createFile(appContext.getDevMachine(), path, nameWithoutExtension + Constants.C_EXT,
                content,
                createCallback());
    }


    protected AsyncRequestCallback<ItemReference> createCallback() {
        return new AsyncRequestCallback<ItemReference>(dtoUnmarshaller.newUnmarshaller(ItemReference.class)) {
            @Override
            protected void onSuccess(final ItemReference itemReference) {
                HasStorablePath path = new HasStorablePath.StorablePath(itemReference.getPath());

                projectExplorer.getNodeByPath(path, true)
                        .then(selectNode())
                        .then(openNode());

            }

            @Override
            protected void onFailure(Throwable exception) {
                dialogFactory.createMessageDialog("", JsonHelper.parseJsonMessage(exception.getMessage()), null).show();
            }
        };
    }


    protected Function<Node, Node> selectNode() {
        return new Function<Node, Node>() {
            @Override
            public Node apply(Node node) throws FunctionException {
                projectExplorer.select(node, false);
                return node;
            }
        };
    }

    protected Function<Node, Node> openNode() {
        return new Function<Node, Node>() {
            @Override
            public Node apply(Node node) throws FunctionException {
                if (node instanceof FileReferenceNode) {
                    ((FileReferenceNode) node).actionPerformed();
                }
                return node;
            }
        };
    }
}
