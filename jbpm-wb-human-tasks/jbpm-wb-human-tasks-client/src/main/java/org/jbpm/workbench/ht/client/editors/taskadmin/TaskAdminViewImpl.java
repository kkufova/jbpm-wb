/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jbpm.workbench.ht.client.editors.taskadmin;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Composite;
import elemental2.dom.Document;
import elemental2.dom.Element;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLUListElement;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.FormControlStatic;
import org.gwtbootstrap3.client.ui.FormLabel;
import org.gwtbootstrap3.client.ui.Legend;
import org.gwtbootstrap3.client.ui.TextBox;
import org.jboss.errai.common.client.dom.elemental2.Elemental2DomUtil;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jbpm.workbench.ht.client.resources.i18n.Constants;

@Dependent
@Templated(value = "TaskAdminViewImpl.html")
public class TaskAdminViewImpl extends Composite implements TaskAdminPresenter.TaskAdminView {

    @Inject
    @DataField
    public Legend adminDetailsAccordionLabel;

    @Inject
    @DataField
    public FormLabel adminUserOrGroupLabel;

    @Inject
    @DataField
    public FormLabel adminUsersGroupsControlsLabel;

    @Inject
    @DataField
    public TextBox adminUserOrGroupText;

    @Inject
    @DataField
    public Button adminForwardButton;

    @Inject
    @DataField
    public HTMLUListElement adminUsersGroupsControlsPanel;

    @Inject
    @DataField
    public Legend reminderDetailsAccordionLabel;

    @Inject
    @DataField
    public FormLabel actualOwnerLabel;

    @Inject
    @DataField
    public FormControlStatic actualOwnerPanel;

    @Inject
    @DataField
    public Button adminReminderButton;

    @Inject
    @DataField
    private HTMLDivElement adminUserOrGroupFormGroup;

    @Inject
    @Named("span")
    @DataField
    private HTMLElement adminUserOrGroupHelp;

    private TaskAdminPresenter presenter;

    @Inject
    private Document document;

    private Constants constants = GWT.create(Constants.class);

    @Override
    public void init(TaskAdminPresenter presenter) {
        this.presenter = presenter;
        adminUserOrGroupLabel.setText(constants.Delegate_User());
        adminDetailsAccordionLabel.setText(constants.Details());
        adminForwardButton.setText(constants.Forward());
        adminUsersGroupsControlsLabel.setText(constants.Potential_Owners());

        reminderDetailsAccordionLabel.setText(constants.Reminder_Details());
        actualOwnerLabel.setText(constants.Actual_Owner());
        adminReminderButton.setText(constants.Reminder());
    }

    @EventHandler("adminForwardButton")
    public void adminForwardButton(ClickEvent e) {
        String userOrGroup = adminUserOrGroupText.getText();
        if (!userOrGroup.equals("")) {
            presenter.forwardTask(userOrGroup);
            adminForwardButton.setEnabled(false);
        } else {
            adminUserOrGroupFormGroup.classList.add("has-error");
            adminUserOrGroupHelp.textContent = constants.PleaseEnterUserOrAGroupToForward();
        }
    }

    @EventHandler("adminReminderButton")
    public void adminReminderButton(ClickEvent e) {
        presenter.reminder();
    }

    @Override
    public void setUsersGroupsControlsPanelText(final List<String> text) {
        new Elemental2DomUtil().removeAllElementChildren(adminUsersGroupsControlsPanel);
        text.forEach(t -> {
            Element li = document.createElement("li");
            li.textContent = t;
            adminUsersGroupsControlsPanel.appendChild(li);
        });
    }

    @Override
    public void enableForwardButton(boolean enabled) {
        adminForwardButton.setEnabled(enabled);
    }

    @Override
    public void enableUserOrGroupText(boolean enabled) {
        adminUserOrGroupText.setEnabled(enabled);
    }

    @Override
    public void clearUserOrGroupText() {
        adminUserOrGroupText.setText("");
        adminUserOrGroupFormGroup.classList.remove("has-error");
        adminUserOrGroupHelp.textContent = "";
    }

    @Override
    public void enableReminderButton(boolean enabled) {
        adminReminderButton.setEnabled(enabled);
    }

    @Override
    public void setActualOwnerText(String actualOwnerText) {
        actualOwnerPanel.setText(actualOwnerText);
    }
}
