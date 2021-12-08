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
 *  Project    :   FakeDockingPoC
 *  Class      :   Dockable.java
 *  Author     :   Sean Carrick
 *  Created    :   Dec 7, 2021 @ 4:14:31 PM
 *  Modified   :   Dec 7, 2021
 *  
 *  Purpose:
 *  
 *  Revision History:
 *  
 *   WHEN          BY                  REASON
 *   ------------  ------------------- ------------------------------------------
 *  Dec 07, 2021  Sean Carrick        Initial creation.
 * *****************************************************************************
 */

package com.gs.api.desktop.docking;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author Sean Carrick &lt;sean at gs-unitedlabs dot com&gt;
 * 
 * @version 1.0
 * @since 1.0
 */
public class Dockable extends JPanel {
    
    private static final ImageIcon ATTACH_ICON = new ImageIcon(Dockable.class
            .getResource("/com/gs/api/desktop/docking/resources/view-restore.png"));
    private static final ImageIcon DETACH_ICON = new ImageIcon(Dockable.class
            .getResource("/com/gs/api/desktop/docking/resources/view-fullscreen.png"));
    
    private static JTabbedPane pane;
    private static JPanel panel;
    private static JFrame frame;
    private static JTabbedPane dPane;
    
    private static int detachedEditorIdx = 0;
    
    private Dockable () {
    }
    
    public static JPanel getTitlePanel(final JTabbedPane tabbedPane, 
            final JPanel panel, String title, boolean detached) {
        pane = tabbedPane;
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        titlePanel.add(titleLabel);
        JButton detachButton = new DetatchButton();
        detachButton.setSize(titleLabel.getHeight(), titleLabel.getHeight());
        detachButton.setName("detatchButton");
        if (!detached) {
            detachButton.setIcon(DETACH_ICON);
        } else {
            detachButton.setIcon(ATTACH_ICON);
        }
        detachButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                if (detachButton.getIcon() == DETACH_ICON) {
//                    detachButton.setIcon(ATTACH_ICON);
                if (!detached) {
                    frame = new JFrame(titleLabel.getText());
                    dPane = new JTabbedPane();
                    dPane.add(panel);
                    // Need to think about creating a JPanel subclass that allows
                    //+ us to get/set the text of the JLabel so that we can get 
                    //+ the title that was set previously.
                    dPane.setTabComponentAt(dPane.indexOfComponent(panel), 
                            Dockable.getTitlePanel(tabbedPane, panel, title, true));
                    frame.setContentPane(dPane);
                    frame.pack();
                    frame.setVisible(true);
                    tabbedPane.remove(panel);
                } else {
                    detachButton.setIcon(DETACH_ICON);
                    Dockable.panel = (JPanel) dPane.getComponentAt(dPane.getSelectedIndex());
                    tabbedPane.add(Dockable.panel);
                    tabbedPane.setComponentAt(tabbedPane.indexOfComponent(
                            Dockable.panel), 
                            Dockable.getTitlePanel(tabbedPane, panel, title, true));
                    frame.dispose();
                    frame = null;
                    dPane = null;
                    System.gc();
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

            @Override
            public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setBorderPainted(true);
                }
            }

        });
        titlePanel.add(detachButton);
        
        JButton closeButton = new CloseButton();
        closeButton.setSize(titleLabel.getHeight(), titleLabel.getHeight());
        closeButton.setName("closeButton");
        closeButton.setIcon(new ImageIcon(Dockable.class.getResource(
                "/com/gs/api/desktop/docking/resources/window-close.png")));
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tabbedPane.remove(panel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setBorderPainted(false);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setBorderPainted(true);
                }
            }
            
        });
        titlePanel.add(closeButton);
        
        return titlePanel;
    }
    
    private static class CloseButton extends JButton implements ActionListener {
        public CloseButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("close this tab");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(CloseButton.this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            pane.remove(panel);
        }

        //we don't want to update UI for this button
        public void updateUI() {
        }

    }
    
    private static class DetatchButton extends JButton implements ActionListener {
        
        public DetatchButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("close this tab");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(DetatchButton.this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            pane.remove(panel);
        }

        //we don't want to update UI for this button
        public void updateUI() {
        }

    }

}
