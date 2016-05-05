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
package org.eclipse.che.plugin.sample.wizard.ide.action;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.ide.api.action.Action;
import org.eclipse.che.ide.api.action.ActionEvent;
import org.eclipse.che.plugin.sample.wizard.ide.SampleWizardLocalizationConstant;
import org.eclipse.che.plugin.sample.wizard.ide.SampleWizardResources;


/**
 * Sample action.
 *
 */
@Singleton
public class SampleAction extends Action {

    @Inject
    public SampleAction(SampleWizardLocalizationConstant localizationConstant,
                        SampleWizardResources sampleWizardResources) {
        super(localizationConstant.createCFileActionTitle(),
              localizationConstant.createCFileActionDescription(),
              null,
              sampleWizardResources.cFile());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
