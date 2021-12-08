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
 *  Class      :   CloseButton.java
 *  Author     :   Sean Carrick
 *  Created    :   Dec 8, 2021 @ 9:27:52 AM
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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author Sean Carrick &lt;sean at gs-unitedlabs dot com&gt;
 * 
 * @version 1.0
 * @since 1.0
 */
public class DetatchButton extends javax.swing.JButton {
    
    private static final int SIZE = 17;
    
    private final javax.swing.JTabbedPane tabbedPane;
    private final Dockable contentPane;
    
    public DetatchButton (javax.swing.JTabbedPane tabbedPane, 
            Dockable contentPane) {
        this.tabbedPane = tabbedPane;
        this.contentPane = contentPane;
    }
    
    private void initComponents() {
        // Set the size to something reasonable for a tab component.
        setPreferredSize(new Dimension(SIZE, SIZE));
        
        // Let the user see a tip as to what this button does.
        setToolTipText("Detatch this tab to a floating window");
        
        //Make the button looks the same for all Laf's
        setUI(new BasicButtonUI());
        
        //Make it transparent
        setContentAreaFilled(false);
        
        //No need to be focusable
        setFocusable(false);
        
        // Set a nice icon for the button that visually describes its function.
        setIcon(new ImageIcon(getClass().getResource("/com/gs/api/desktop/"
                + "docking/resources/view-fullscreen.png")));
        
        // Give the button a nice border...
        setBorder(BorderFactory.createEtchedBorder());
        
        // ...but, we only want it shown when the mouse is over it.
        setBorderPainted(false);
        
        //Making nice rollover effect
        //we use the same listener for all buttons
        addMouseListener(new ButtonMouseListener());
        setRolloverEnabled(true);
    }
    
    private class ButtonMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            javax.swing.JFrame floatingFrame = new javax.swing.JFrame(contentPane.getTitle());
            floatingFrame.setContentPane(contentPane.getContentPane());
            floatingFrame.pack();
            floatingFrame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
            floatingFrame.setVisible(true);
            tabbedPane.remove(contentPane.getContentPane());
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    }

}
