/*
 * Copyright (C) 2021 GS United Labs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * *****************************************************************************
 *  Project    :   com.gs.api.desktop.docking
 *  Class      :   TabTitle.java
 *  Author     :   Sean Carrick
 *  Created    :   Dec 8, 2021 @ 9:42:36 AM
 *  Modified   :   Dec 8, 2021
 *  
 *  Purpose:
 *  
 *  Revision History:
 *  
 *   WHEN          BY                  REASON
 *   ------------  ------------------- ------------------------------------------
 *  Dec 08, 2021  Sean Carrick        Initial creation.
 * *****************************************************************************
 */

package com.gs.api.desktop.docking.support;

import com.gs.api.desktop.docking.Dockable;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Sean Carrick &lt;sean at gs-unitedlabs dot com&gt;
 * 
 * @version 1.0
 * @since 1.0
 */
public class FloatingTabTitle extends javax.swing.JPanel {
    
    private final javax.swing.JTabbedPane tabbedPane;
    private final Dockable contentPane;
    
    public FloatingTabTitle (javax.swing.JTabbedPane tabbedPane, Dockable contentPane) {
        // Unset default FlowLayout' gaps
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        
        // Verify that we were given a valid JTabbedPane.
        if (tabbedPane == null) {
            throw new NullPointerException("tabbedPane is null.");
        }
        
        this.tabbedPane = tabbedPane;
        
        // Verify that we were given a valid Dockable
        if (contentPane == null) {
            throw new NullPointerException("contentPane is null.");
        }
        
        this.contentPane = contentPane;
        
        initComponents();
    }
    
    private void initComponents() {
        setOpaque(false);
        
        // Make JLabel read titles from JTabbedPane
        JLabel label = new JLabel() {
            public String getText() {
                int i = tabbedPane.indexOfTabComponent(FloatingTabTitle.this);
                if (i != -1) {
                    return tabbedPane.getTitleAt(i);
                }
                return null;
            }
        };
        
        add(label);
        //add more space between the label and the button
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        //tab button
        JButton attachButton = new AttachButton(tabbedPane, contentPane);
        add(attachButton);
        //add more space to the top of the component
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        JButton closeButton = new CloseButton(tabbedPane, contentPane);
        add(closeButton);
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

}
