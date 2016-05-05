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
package org.eclipse.che.plugin.sample.wizard.ide.wizard;

import com.google.gwt.user.client.ui.AcceptsOneWidget;

import org.eclipse.che.api.workspace.shared.dto.ProjectConfigDto;
import org.eclipse.che.ide.api.wizard.AbstractWizardPage;

public class SamplePagePresenter extends AbstractWizardPage<ProjectConfigDto> implements SamplePageView.ActionDelegate {
    @Override
    public void go(AcceptsOneWidget acceptsOneWidget) {

    }

    @Override
    public void onCoordinatesChanged() {

    }

    @Override
    public void packagingChanged(String packaging) {

    }

    @Override
    public void generateFromArchetypeChanged(boolean isGenerateFromArchetype) {

    }
}
