/* ====================================================================
 * 
 * The ObjectStyle Group Software License, version 1.1
 * ObjectStyle Group - http://objectstyle.org/
 * 
 * Copyright (c) 2002-2004, Andrei (Andrus) Adamchik and individual authors
 * of the software. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 * 
 * 3. The end-user documentation included with the redistribution, if any,
 *    must include the following acknowlegement:
 *    "This product includes software developed by independent contributors
 *    and hosted on ObjectStyle Group web site (http://objectstyle.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 * 
 * 4. The names "ObjectStyle Group" and "Cayenne" must not be used to endorse
 *    or promote products derived from this software without prior written
 *    permission. For written permission, email
 *    "andrus at objectstyle dot org".
 * 
 * 5. Products derived from this software may not be called "ObjectStyle"
 *    or "Cayenne", nor may "ObjectStyle" or "Cayenne" appear in their
 *    names without prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE OBJECTSTYLE GROUP OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 * 
 * This software consists of voluntary contributions made by many
 * individuals and hosted on ObjectStyle Group web site.  For more
 * information on the ObjectStyle Group, please see
 * <http://objectstyle.org/>.
 */
package org.objectstyle.cayenne.modeler.dialog.pref;

import java.awt.Component;

import org.objectstyle.cayenne.modeler.util.CayenneController;
import org.objectstyle.cayenne.pref.CayennePreferenceEditor;
import org.objectstyle.cayenne.pref.CayennePreferenceService;
import org.objectstyle.cayenne.pref.PreferenceEditor;
import org.objectstyle.cayenne.swing.BindingBuilder;
import org.objectstyle.cayenne.swing.ObjectBinding;
import org.objectstyle.cayenne.validation.ValidationException;

/**
 * @author Andrei Adamchik
 */
public class GeneralPreferences extends CayenneController {

    protected GeneralPreferencesView view;
    protected CayennePreferenceEditor editor;
    protected ObjectBinding saveIntervalBinding;

    public GeneralPreferences(PreferenceDialog parentController) {
        super(parentController);
        this.view = new GeneralPreferencesView();

        PreferenceEditor editor = parentController.getEditor();
        if (editor instanceof CayennePreferenceEditor) {
            this.editor = (CayennePreferenceEditor) editor;
            this.view.setEnabled(true);
            initBindings();
            
            saveIntervalBinding.updateView();
        }
        else {
            this.view.setEnabled(false);
        }
    }

    public Component getView() {
        return view;
    }

    protected void initBindings() {
        BindingBuilder builder = new BindingBuilder(
                getApplication().getBindingFactory(),
                this);

        this.saveIntervalBinding = builder.bindToTextField(
                view.getSaveInterval(),
                "timeInterval");
    }

    public double getTimeInterval() {
        return this.editor.getSaveInterval() / 1000.0;
    }

    public void setTimeInterval(double d) {
        int ms = (int) (d * 1000.0);
        if (ms < CayennePreferenceService.MIN_SAVE_INTERVAL) {
            throw new ValidationException(
                    "Time interval is too small, minimum allowed is "
                            + (CayennePreferenceService.MIN_SAVE_INTERVAL / 1000.0));
        }

        this.editor.setSaveInterval(ms);
    }
}