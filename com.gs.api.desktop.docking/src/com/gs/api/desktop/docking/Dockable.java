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
 *  Class      :   Dockable.java
 *  Author     :   Sean Carrick
 *  Created    :   Dec 8, 2021 @ 9:35:04 AM
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

package com.gs.api.desktop.docking;

import com.gs.api.desktop.docking.enums.DockingLocation;

/**
 *
 * @author Sean Carrick &lt;sean at gs-unitedlabs dot com&gt;
 * 
 * @version 1.0
 * @since 1.0
 */
public interface Dockable {
    public String getTitle();
    
    public javax.swing.JPanel getContentPane();
    
    public default DockingLocation getDockingLocation() {
        return DockingLocation.EDITOR;
    }
}
