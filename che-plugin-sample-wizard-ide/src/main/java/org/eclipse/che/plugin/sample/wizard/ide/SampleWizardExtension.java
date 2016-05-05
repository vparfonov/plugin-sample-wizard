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
package org.eclipse.che.plugin.sample.wizard.ide;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.eclipse.che.ide.api.action.ActionManager;
import org.eclipse.che.ide.api.action.DefaultActionGroup;
import org.eclipse.che.ide.api.constraints.Constraints;
import org.eclipse.che.ide.api.extension.Extension;
import org.eclipse.che.ide.api.filetypes.FileType;
import org.eclipse.che.ide.api.filetypes.FileTypeRegistry;
import org.eclipse.che.ide.api.icon.Icon;
import org.eclipse.che.ide.api.icon.IconRegistry;
import org.eclipse.che.plugin.sample.wizard.ide.action.SampleAction;

import static org.eclipse.che.ide.api.action.IdeActions.GROUP_FILE_NEW;

/**
 *
 *
 * */
@Extension(title = "Sample Wizard")
public class SampleWizardExtension {

    public static String C_CATEGORY       = "C/C++";

    @Inject
    public SampleWizardExtension(FileTypeRegistry fileTypeRegistry,
                                 @Named("CFileType") FileType cFile) {
        fileTypeRegistry.registerFileType(cFile);
    }

    @Inject
    private void prepareActions(SampleAction newCSourceFileAction,
                                ActionManager actionManager,
                                SampleWizardResources resources,
                                IconRegistry iconRegistry) {

        DefaultActionGroup newGroup = (DefaultActionGroup)actionManager.getAction(GROUP_FILE_NEW);

        actionManager.registerAction("newCFile", newCSourceFileAction);
        newGroup.add(newCSourceFileAction, Constraints.FIRST);
        iconRegistry.registerIcon(new Icon(C_CATEGORY + ".samples.category.icon", resources.category()));
    }

}
