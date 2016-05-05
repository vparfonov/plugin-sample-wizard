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

import org.eclipse.che.ide.api.mvp.View;

public interface SamplePageView extends View<SamplePageView.ActionDelegate> {

    public interface ActionDelegate {
        void onCoordinatesChanged();

        void packagingChanged(String packaging);

        void generateFromArchetypeChanged(boolean isGenerateFromArchetype);
    }
}
